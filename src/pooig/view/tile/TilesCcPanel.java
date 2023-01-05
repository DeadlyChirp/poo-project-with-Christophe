package pooig.view.tile;

import pooig.controller.BaseController;
import pooig.model.tile.cc.TileCc;
import pooig.model.tile.cc.TileCcType;

import java.awt.*;

public class TilesCcPanel extends TilesPanel {

    public TilesCcPanel(int gridWidth, int gridHeight, BaseController controller) {
        super(gridWidth, gridHeight, controller);
    }

    @Override
    public void initGrid(int gridWidth, int gridHeight) {
        GridBagConstraints constraints = new GridBagConstraints();

        TileCc defaultTile = new TileCc(TileCcType.Null);
        TileCc highlightTile = new TileCc(TileCcType.Null);
        defaultTile.rotateRight();
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                tileViewGrid[x][y] = new TileCcView(defaultTile, highlightTile, x, y, controller);
                tileViews.add(tileViewGrid[x][y]);
                constraints.gridx = x;
                constraints.gridy = y;
                add(tileViewGrid[x][y], constraints);
            }
        }
    }
}
