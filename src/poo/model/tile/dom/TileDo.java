package poo.model.tile.dom;

import poo.model.tile.base.BaseTile;

import java.util.List;

public class TileDo extends BaseTile<List<Integer>, TileDoTerrain> {

    public TileDo() {
        super();

        terrain = initTerrain();
    }

    @Override
    protected TileDoTerrain initTerrain() {
        return new TileDoTerrain();
    }
}
