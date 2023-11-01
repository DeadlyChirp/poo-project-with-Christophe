package poo.model.board;

import poo.model.tile.base.BaseTile;

public abstract class BaseCell<T extends BaseTile, V extends BaseBoard> {

    protected V board;
    protected int x, y;
    protected T tile;

    public BaseCell(V board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public V getBoard() {
        return board;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public T getTile() {
        return tile;
    }

    public boolean isEmpty() {
        return tile == null;
    }

    public boolean isFull() {
        return !isEmpty();
    }

    public boolean place(T tile) {
        if (isPlaceable(tile)) {
            tile.setLocation(this);
            this.tile = tile;
            return true;
        }

        return false;
    }

    public void forcePlace(T tile) {
        if (tile == null) {
            return;
        }

        tile.setLocation(this);
        this.tile = tile;
    }

    public abstract boolean isPlaceable(T tile);
}
