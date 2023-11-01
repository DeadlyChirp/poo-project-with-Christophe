package poo.view.tile;

import poo.controller.BaseController;

import java.awt.*;

public class TilesDoPanel extends TilesPanel {

    public TilesDoPanel(int gridWidth, int gridHeight, BaseController controller) {
        super(gridWidth, gridHeight, controller);
    }

    @Override
    public void initGrid(int gridWidth, int gridHeight) {
        GridBagConstraints constraints = new GridBagConstraints();

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                tileViewGrid[x][y] = new TileDoView(x, y, controller);
                tileViews.add(tileViewGrid[x][y]);
                constraints.gridx = x;
                constraints.gridy = y;
                add(tileViewGrid[x][y], constraints);
            }
        }
    }
}
