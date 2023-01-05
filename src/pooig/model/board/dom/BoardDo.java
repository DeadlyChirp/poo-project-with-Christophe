package pooig.model.board.dom;

import pooig.model.board.BaseBoard;
import pooig.model.tile.dom.TileDo;

public class BoardDo extends BaseBoard<CellDo> {

    public BoardDo(int width, int height) {
        super(width, height);
    }

    @Override
    protected void initBoard() {
        board = new CellDo[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = new CellDo(this, x, y);
            }
        }

        placeFirstTile();
    }

    public boolean place(TileDo tile, int x, int y) {
        if (tile == null || !isValidLocation(x, y)) {
            return false;
        }

        return board[x][y].place(tile);
    }

    private void placeFirstTile() {
        int x = (width - 1) / 2;
        int y = (height - 1) / 2;
        firstCell = (CellDo) board[x][y];
        firstCell.forcePlace(new TileDo());
    }
}
