package pooig.model.board.cc;

import pooig.model.Direction;
import pooig.model.tile.cc.TileCc;

import java.util.LinkedList;
import java.util.List;

import static pooig.model.Direction.*;
import static pooig.model.tile.cc.RotationDirection.LEFT;
import static pooig.model.tile.cc.RotationDirection.RIGHT;
import static pooig.model.tile.cc.TerrainType.CASTLE;
import static pooig.model.tile.cc.TerrainType.FIELDS;

public class FieldsPattern extends GamePattern {
    private static final int POINTS_PER_CASTLE = 3;
    private final List<CastleAndRoadPattern> adjacentCastles;
    private final BoardCc board;

    public FieldsPattern(CellCc startingCell, Direction startingDirection) {
        super(FIELDS, POINTS_PER_CASTLE);
        checkArgs(startingCell, startingDirection);
        board = startingCell.getBoard();
        adjacentCastles = new LinkedList<>();
        checkArgs(startingCell, startingDirection);
        startingCell.setTag(startingDirection, this);
        add(startingCell);
        buildPattern(startingCell, startingDirection);
        adjacentCastles.forEach(GamePattern::removeOwnTags);
    }

    @Override
    public int getPatternScore() {
        return adjacentCastles.size() * scorePerCell;
    }

    private void addIfNotCastle(List<Direction> results, TileCc tile, Direction next) {
        if (tile.getTerrainType(next) != CASTLE) {
            results.add(next);
        }
    }

    private void buildPattern(CellCc spot, Direction startingPoint) {
        List<Direction> fieldPositions = getFieldPositions(spot.getTile(), startingPoint);
        for (Direction position : fieldPositions) {
            countAdjacentCastles(spot, position);
            spot.setTag(position, this);
        }
        fieldPositions.forEach(it -> checkNeighbors(spot, it));
    }

    private void checkNeighbors(CellCc spot, Direction position) {
        for (Direction connectionDirection : getFieldConnections(position, spot.getTile())) {
            CellCc neighbor = board.getNeighbor(spot, connectionDirection);
            Direction oppositeDirection = getFieldOpposite(position, connectionDirection);
            if (neighbor != null && !neighbor.isIndirectlyTagged(oppositeDirection)) {
                neighbor.setTag(oppositeDirection, this);
                add(neighbor);
                buildPattern(neighbor, oppositeDirection);
            }
        }
    }

    private void countAdjacentCastles(CellCc spot, Direction position) {
        for (Direction neighbor : getAdjacentPositions(position)) {
            if (spot.getTile().getTerrainType(neighbor) == CASTLE && isUntagged(spot, neighbor)) {
                CastleAndRoadPattern castle = new CastleAndRoadPattern(spot, neighbor, CASTLE);
                if (castle.isComplete()) {
                    adjacentCastles.add(castle);
                } else {
                    castle.removeOwnTags();
                }
            }
        }
    }

    private List<Direction> getAdjacentPositions(Direction position) {
        List<Direction> neighbors = new LinkedList<>();
        if (position.isSmallerOrEquals(WEST)) {
            neighbors.add(CENTER);
        }

        if (position.isSmallerOrEquals(NORTH_WEST)) {
            neighbors.add(position.nextDirectionTo(LEFT));
            neighbors.add(position.nextDirectionTo(RIGHT));
        } else {
            neighbors.addAll(Direction.directNeighbors());
        }

        return neighbors;
    }

    private List<Direction> getFieldConnections(Direction position, TileCc tile) {
        List<Direction> results = new LinkedList<>();
        if (tile.getTerrainType(position) == FIELDS) {
            if (position.isSmallerOrEquals(WEST)) {
                results.add(position);
            } else if (position.isSmallerOrEquals(NORTH_WEST)) {
                addIfNotCastle(results, tile, position.nextDirectionTo(LEFT));
                addIfNotCastle(results, tile, position.nextDirectionTo(RIGHT));
            }
        }

        return results;
    }

    private Direction getFieldOpposite(Direction position, Direction neighborDirection) {
        if (position.isSmallerOrEquals(WEST)) {
            return position.opposite();
        } else if (position.isSmallerOrEquals(NORTH_WEST)) {
            if (neighborDirection.isLeftOf(position)) {
                return position.opposite().nextDirectionTo(LEFT).nextDirectionTo(LEFT);
            } else {
                return position.opposite().nextDirectionTo(RIGHT).nextDirectionTo(RIGHT);
            }
        }

        return position;
    }

    private List<Direction> getFieldPositions(TileCc tile, Direction startingPoint) {
        List<Direction> fieldPositions = new LinkedList<>();
        for (Direction position : Direction.values()) {
            if (tile.hasConnection(startingPoint, position)) {
                fieldPositions.add(position);
            }
        }

        return fieldPositions;
    }

    private boolean isUntagged(CellCc spot, Direction position) {
        boolean tagged = false;
        for (CastleAndRoadPattern castle : adjacentCastles) {
            tagged |= spot.isIndirectlyTaggedBy(position, castle);
        }
        return !tagged;
    }

}
