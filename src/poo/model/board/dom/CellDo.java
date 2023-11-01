package poo.model.board.dom;

import poo.model.Direction;
import poo.model.board.BaseCell;
import poo.model.tile.dom.TileDo;

public class CellDo extends BaseCell<TileDo, BoardDo> {

    public CellDo(BoardDo board, int x, int y) {
        super(board, x, y);
    }

    @Override
    public boolean isPlaceable(TileDo tile) {
        if (isFull() || tile == null) {
            return false;
        }

        int count = 0;
        for (Direction direction : Direction.directNeighbors()) {
            CellDo neighbor = board.getNeighbor(this, direction);
            if (neighbor != null && neighbor.isFull()) {
                count++;
                if (!tile.canConnectTo(direction, neighbor.getTile())) {
                    return false;
                }
            }
        }

        return count > 0;
    }
}
