package poo.model.tile.dom;

import poo.model.Direction;
import poo.model.tile.base.BaseTerrain;

import java.util.ArrayList;
import java.util.List;

public class TileDoTerrain extends BaseTerrain<List<Integer>> {

    public TileDoTerrain() {
        super();

        List<Direction> directions = Direction.directNeighbors();
        for (Direction direction : directions) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add(Math.toIntExact(Math.round(Math.random() * 3 - 0.5)));
            }
            terrain.put(direction, list);
        }
    }
}
