package pooig.model.board.cc;

import pooig.model.Direction;
import pooig.model.tile.cc.TerrainType;

public class CastleAndRoadPattern extends GamePattern {

    private static final double UNFINISHED_CASTLE_SCORE_PER_CELL = 0.5;

    public CastleAndRoadPattern(CellCc startingCell, Direction startingDirection, TerrainType patternType) {
        super(patternType, (patternType == TerrainType.CASTLE) ? 2 : 1);
        checkArgs(startingCell, startingDirection, patternType);
        startingCell.setTag(startingDirection, this);
        add(startingCell);
        complete = buildPattern(startingCell, startingDirection);
    }

    @Override
    public int getPatternScore() {
        int baseScore = super.getPatternScore();
        if (patternType == TerrainType.CASTLE) {
            int emblems = (int) containedCell.stream().filter(it -> it.getTile().hasEmblem()).count();
            baseScore += emblems * scorePerCell;
            if (!complete) {
                baseScore *= UNFINISHED_CASTLE_SCORE_PER_CELL;
            }
        }
        return baseScore;
    }

    private boolean buildPattern(CellCc spot, Direction startingPoint) {
        boolean isClosed = true;
        for (Direction direction : Direction.directNeighbors()) {
            if (spot.getTile().hasConnection(startingPoint, direction)) {
                CellCc neighbor = spot.getBoard().getNeighbor(spot, direction);
                if (neighbor == null) {
                    isClosed = false;
                } else {
                    isClosed &= checkNeighbor(spot, neighbor, direction);
                }
            }
        }
        return isClosed;
    }

    private void checkArgs(CellCc spot, Direction direction, TerrainType terrain) {
        if (terrain != TerrainType.CASTLE && terrain != TerrainType.ROAD) {
            throw new IllegalArgumentException("Can only create CastleAndRoadPatterns from type castle or road");
        }
        checkArgs(spot, direction);
    }

    private boolean checkNeighbor(CellCc startingCell, CellCc neighbor, Direction direction) {
        Direction oppositeDirection = direction.opposite();
        if (!neighbor.isIndirectlyTaggedBy(oppositeDirection, this)) {
            startingCell.setTag(direction, this);
            neighbor.setTag(oppositeDirection, this);
            add(neighbor);
            return buildPattern(neighbor, oppositeDirection);
        }

        return true;
    }
}
