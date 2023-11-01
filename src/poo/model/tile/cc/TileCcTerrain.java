package poo.model.tile.cc;

import poo.model.Direction;
import poo.model.tile.base.BaseTerrain;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static poo.model.Direction.CENTER;

public class TileCcTerrain extends BaseTerrain<TerrainType> {

    private Set<Direction> meeplePositions;

    public TileCcTerrain(TileCcType type) {
        super();

        for (int i = 0; i < Direction.values().length; i++) {
            terrain.put(Direction.values()[i], type.getTerrain()[i]);
        }

        createMeeplePositions();
    }

    public Set<Direction> getMeeplePositions() {
        return meeplePositions;
    }

    public final boolean isConnected(Direction from, Direction towards) {
        if (isDirectConnected(from, towards)) {
            return true;
        } else if (from != CENTER && towards != CENTER && isIndirectConnected(from, towards)) {
            return true;
        } else if (terrain.get(from) == TerrainType.FIELDS && terrain.get(towards) == TerrainType.FIELDS) {
            return isImplicitlyConnected(from, towards);
        }
        return false;
    }

    private void createMeeplePositions() {
        meeplePositions = new HashSet<>();
        for (Direction position : Direction.values()) {
            if (terrain.get(position) != TerrainType.OTHER) {
                createMeepleSpot(position);
            }
        }

        removeRedundantSpots(Direction.directNeighbors(), false);
        removeRedundantSpots(Direction.indirectNeighbors(), true);
        removeRedundantSpots(Direction.directNeighbors(), true);
    }

    private void createMeepleSpot(Direction position) {
        List<Direction> connectedPositions = Stream.of(Direction.values()).filter(it -> isConnected(position, it)).collect(toList());
        Point sum = new Point();
        for (Direction connectedPosition : connectedPositions) {
            sum.x += connectedPosition.getX();
            sum.y += connectedPosition.getY();
        }
        Direction center = Direction.values2D()[(int) Math.round(sum.x / 3.0) + 1][(int) Math.round(sum.y / 3.0) + 1];
        if (isConnected(center, position)) {
            meeplePositions.add(center);
        } else {
            meeplePositions.add(position);
        }
    }

    private boolean isDirectConnected(Direction from, Direction towards) {
        TerrainType middle = terrain.get(CENTER);
        return terrain.get(from) == middle && terrain.get(towards) == middle;
    }

    private boolean isIndirectConnected(Direction from, Direction towards) {
        boolean connected = false;
        for (RotationDirection side : RotationDirection.values()) {
            connected |= isIndirectConnected(from, towards, side);
        }
        return connected;
    }

    private boolean isIndirectConnected(Direction from, Direction towards, RotationDirection side) {
        Direction current = from;
        Direction next;
        while (current != towards) {
            next = current.nextDirectionTo(side);
            if (terrain.get(current) != terrain.get(next)) {
                return false;
            }
            current = next;
        }
        return true;
    }

    private boolean isImplicitlyConnected(Direction from, Direction towards) {
        boolean connected = false;
        for (Direction direction : Arrays.asList(from, towards)) {
            Direction other = (from == direction) ? towards : from;
            for (Direction corner : Direction.indirectNeighbors()) {
                if (isDirectConnected(direction, corner) || isIndirectConnected(direction, corner)) {
                    for (RotationDirection side : RotationDirection.values()) {
                        connected |= isImplicitlyConnected(corner, other, side);
                    }
                }
            }
        }
        return connected;
    }

    private boolean isImplicitlyConnected(Direction from, Direction towards, RotationDirection side) {
        if (from == towards) {
            return true;
        }
        Direction inbetween = from.nextDirectionTo(side);
        Direction nextCorner = inbetween.nextDirectionTo(side);
        if (hasNoCastleEntry(inbetween)) {
            return isImplicitlyConnected(nextCorner, towards, side);
        }
        return false;
    }

    private void removeRedundantSpots(List<Direction> anchorDirections, boolean addAnchor) {
        List<Direction> removalList = new LinkedList<>();
        for (Direction anchor : anchorDirections) {
            Direction left = anchor.nextDirectionTo(RotationDirection.LEFT);
            Direction right = anchor.nextDirectionTo(RotationDirection.RIGHT);
            if (terrain.get(anchor) == terrain.get(left) && terrain.get(anchor) == terrain.get(right) &&
                    meeplePositions.contains(left) && meeplePositions.contains(right)) {
                removalList.add(left);
                removalList.add(right);
                if (addAnchor && !isConnected(anchor, CENTER)) {
                    meeplePositions.add(anchor);
                }
            }
        }

        removalList.forEach(meeplePositions::remove);
    }

    private boolean hasPassingStreet() {
        return terrain.get(CENTER) == TerrainType.ROAD
                && Direction.tilePositions().stream().filter(it -> isDirectConnected(CENTER, it)).count() > 2;
    }

    private boolean hasNoCastleEntry(Direction castlePosition) {
        return terrain.get(castlePosition) == TerrainType.CASTLE && (terrain.get(CENTER) == TerrainType.OTHER || hasPassingStreet());
    }

    @Override
    protected void rotate(List<Direction> directions) {
        super.rotate(directions);

        createMeeplePositions();
    }
}
