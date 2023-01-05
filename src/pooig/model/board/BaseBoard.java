package pooig.model.board;

import pooig.model.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseBoard<T extends BaseCell> {

    protected int width;
    protected int height;
    protected BaseCell[][] board;
    protected T firstCell;

    public BaseBoard(int width, int height) {
        this.width = width;
        this.height = height;

        initBoard();
    }

    protected abstract void initBoard();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BaseCell[][] getBoard() {
        return board;
    }

    public T getFirstCell() {
        return firstCell;
    }

    public T getCell(int x, int y) {
        if (!isValidLocation(x, y)) {
            return null;
        }

        return (T) board[x][y];
    }

    public boolean isFull() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y].isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<T> getNeighbors(T cell, List<Direction> directions) {
        ArrayList<T> neighbors = new ArrayList<>();

        for (Direction direction : directions) {
            int x = direction.getX() + cell.getX();
            int y = direction.getY() + cell.getY();

            if (isValidLocation(x, y) && board[x][y].isFull()) {
                neighbors.add((T) board[x][y]);
            }
        }

        return neighbors;
    }

    public T getNeighbor(T cell, Direction direction) {
        List<T> neighbors = getNeighbors(cell, Collections.singletonList(direction));
        if (neighbors.isEmpty()) {
            return null;
        } else {
            return neighbors.get(0);
        }
    }

    protected boolean isValidLocation(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
