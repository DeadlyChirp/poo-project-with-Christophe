package pooig.model.board.cc;

import pooig.model.Direction;
import pooig.model.board.BaseCell;
import pooig.model.tile.cc.TerrainType;
import pooig.model.tile.cc.TileCc;

import java.util.*;

import static pooig.model.Direction.CENTER;

public class CellCc extends BaseCell<TileCc, BoardCc> {

    private final Map<Direction, Set<GamePattern>> tagMap;

    public CellCc(BoardCc board, int x, int y) {
        super(board, x, y);

        tagMap = new HashMap<>();
        for (Direction direction : Direction.values()) {
            tagMap.put(direction, new HashSet<>());
        }
    }

    @Override
    public boolean isPlaceable(TileCc tile) {
        if (isFull() || tile == null) {
            return false;
        }

        int count = 0;
        for (Direction direction : Direction.directNeighbors()) {
            CellCc neighbor = board.getNeighbor(this, direction);
            if (neighbor != null && neighbor.isFull()) {
                count++;
                if (!tile.canConnectTo(direction, neighbor.getTile())) {
                    return false;
                }
            }
        }

        return count > 0;
    }

    public void setTag(Direction direction, GamePattern tagger) {
        tagMap.get(direction).add(tagger);
    }

    public void removeTags() {
        tagMap.values().forEach(Set::clear);
    }

    public void removeTagsFrom(GamePattern pattern) {
        tagMap.values().forEach(it -> it.remove(pattern));
    }

    public Boolean isIndirectlyTagged(Direction tilePosition) {
        for (Direction otherPosition : Direction.values()) {
            if (isTagged(otherPosition) && tile.hasConnection(tilePosition, otherPosition)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isIndirectlyTaggedBy(Direction tilePosition, GamePattern tagger) {
        for (Direction otherPosition : Direction.values()) {
            if (tile.hasConnection(tilePosition, otherPosition) && tagMap.get(otherPosition).contains(tagger)) {
                return true;
            }
        }
        return false;
    }

    public Collection<GamePattern> createPatternList() {
        if (isEmpty()) {
            throw new IllegalStateException("GridSpot is free, cannot create patterns");
        }

        List<GamePattern> results = new LinkedList<>();

        for (Direction direction : Direction.tilePositions()) {
            TerrainType terrain = tile.getTerrainType(direction);
            if ((terrain == TerrainType.CASTLE || terrain == TerrainType.ROAD) && !isIndirectlyTagged(direction)) {
                results.add(new CastleAndRoadPattern(this, direction, terrain));
            }
        }

        for (Direction direction : Direction.values()) {
            TerrainType terrain = tile.getTerrainType(direction);
            if (terrain == TerrainType.FIELDS && !isIndirectlyTagged(direction)) {
                results.add(new FieldsPattern(this, direction));
            }
        }

        addPatternIfMonastery(this, results);
        board.getNeighbors(this, Direction.neighbors()).forEach(it -> addPatternIfMonastery(it, results));
        return results;
    }

    private void addPatternIfMonastery(CellCc cell, List<GamePattern> patternList) {
        if (cell.getTile().getTerrainType(CENTER) == TerrainType.MONASTERY && !cell.isIndirectlyTagged(CENTER)) {
            patternList.add(new MonasteryPattern(cell));
        }
    }

    private Boolean isTagged(Direction direction) {
        return !tagMap.get(direction).isEmpty();
    }
}
