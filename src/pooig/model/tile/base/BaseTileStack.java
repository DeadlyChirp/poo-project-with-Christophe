package pooig.model.tile.base;

import java.util.Collections;
import java.util.Stack;

public abstract class BaseTileStack<S extends BaseTile> {

    protected final Stack<S> tiles;

    public BaseTileStack() {
        tiles = new Stack<>();
        fillStack();
        // rotateRandomly();

        Collections.shuffle(tiles);
    }

    public S getTile() {
        if (tiles.isEmpty()) {
            return null;
        }

        return tiles.pop();
    }

    public int getSize() {
        return tiles.size();
    }

    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    public void putBack(S tile) {
        if (tile == null || tile.isPlaced()) {
            return;
        }

        tiles.add(Math.toIntExact(Math.round(Math.random() * tiles.size() - 0.5)), tile);
    }

    protected abstract void fillStack();

}
