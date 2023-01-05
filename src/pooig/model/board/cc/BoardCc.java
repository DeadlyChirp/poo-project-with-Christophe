package pooig.model.board.cc;

import pooig.model.Direction;
import pooig.model.board.BaseBoard;
import pooig.model.tile.cc.TileCc;
import pooig.model.tile.cc.TileCcType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BoardCc extends BaseBoard<CellCc> {

    private static final TileCcType FIRST_TILE_TYPE = TileCcType.CastleWallRoad;

    public BoardCc(int width, int height) {
        super(width, height);
    }

    @Override
    protected void initBoard() {
        board = new CellCc[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = new CellCc(this, x, y);
            }
        }

        placeFirstTile();
    }

    public List<CellCc> getAllNeighbors(CellCc cell, List<Direction> directions) {
        ArrayList<CellCc> neighbors = new ArrayList<>();

        for (Direction direction : directions) {
            int x = direction.getX() + cell.getX();
            int y = direction.getY() + cell.getY();

            if (isValidLocation(x, y)) {
                neighbors.add((CellCc) board[x][y]);
            }
        }

        return neighbors;
    }

    public boolean place(TileCc tile, int x, int y) {
        if (tile == null || !isValidLocation(x, y)) {
            return false;
        }

        return board[x][y].place(tile);
    }

    public List<GamePattern> getAllPatterns() {
        List<GamePattern> patterns = new LinkedList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[x][y].isFull()) {
                    patterns.addAll(((CellCc) board[x][y]).createPatternList());
                }
            }
        }

        patterns.forEach(GamePattern::removeTileTags);
        return patterns;
    }


    public Collection<GamePattern> getPatterns(CellCc cell) {
        if (cell == null || cell.isEmpty()) {
            throw new IllegalArgumentException("Invalid cell or cell is empty! " + cell);
        }

        Collection<GamePattern> patterns = cell.createPatternList();
        patterns.forEach(GamePattern::removeTileTags);
        return patterns;
    }

    private void placeFirstTile() {
        int x = (width - 1) / 2;
        int y = (height - 1) / 2;
        firstCell = (CellCc) board[x][y];
        firstCell.forcePlace(new TileCc(FIRST_TILE_TYPE));
    }
}
