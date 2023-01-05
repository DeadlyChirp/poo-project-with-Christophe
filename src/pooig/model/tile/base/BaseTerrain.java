package pooig.model.tile.base;

import pooig.model.Direction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pooig.model.Direction.*;

public abstract class BaseTerrain<T> {

    protected final Map<Direction, T> terrain;

    public BaseTerrain() {
        terrain = new HashMap<>();
    }

    public T at(Direction direction) {
        if (terrain.containsKey(direction)) {
            return terrain.get(direction);
        }

        throw new IllegalArgumentException("TileTerrain not defined at " + direction);
    }

    public void rotateLeft() {
        rotate(Arrays.asList(NORTH, WEST, SOUTH, EAST));
        rotate(Arrays.asList(NORTH_EAST, NORTH_WEST, SOUTH_WEST, SOUTH_EAST));
    }

    public void rotateRight() {
        rotate(Direction.directNeighbors());
        rotate(Direction.indirectNeighbors());
    }

    protected void rotate(List<Direction> directions) {
        T temporary = terrain.get(directions.get(directions.size() - 1));
        for (Direction direction : directions) {
            temporary = terrain.put(direction, temporary);
        }
    }

    @Override
    public String toString() {
        return terrain.toString();
    }
}
