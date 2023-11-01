package poo.model.tile.cc;

import poo.model.tile.base.BaseTileStack;

public class TileCcStack extends BaseTileStack<TileCc> {

    public TileCcStack() {
        super();
    }

    @Override
    protected void fillStack() {
        for (TileCcType tileType : TileCcType.validTiles()) {
            int amount = tileType.getAmount();
            for (int i = 0; i < amount; i++) {
                tiles.add(new TileCc(tileType));
            }
        }
    }
}
