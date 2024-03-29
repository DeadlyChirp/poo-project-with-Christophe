package poo.model.tile.dom;

import poo.model.tile.base.BaseTileStack;

public class TileDoStack extends BaseTileStack<TileDo> {

    public TileDoStack() {
        super();
    }

    @Override
    protected void fillStack() {
        for (int i = 0; i < 72; i++) {
            tiles.add(new TileDo());
        }
    }
}
