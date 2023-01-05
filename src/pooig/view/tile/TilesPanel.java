package pooig.view.tile;

import pooig.controller.BaseController;
import pooig.model.tile.base.BaseTile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class TilesPanel extends JPanel {

    protected List<TileView> tileViews;
    protected TileView[][] tileViewGrid;
    protected BaseController controller;

    public TilesPanel(int gridWidth, int gridHeight, BaseController controller) {
        this.controller = controller;
        tileViews = new ArrayList<>();
        tileViewGrid = new TileView[gridWidth][gridHeight];

        setLayout(new GridBagLayout());

        initGrid(gridWidth, gridHeight);
    }

    public abstract void initGrid(int gridWidth, int gridHeight);

    public void placeTile(BaseTile tile, int x, int y) {
        tileViewGrid[x][y].setTile(tile);
    }

    public void highlightTile(int x, int y) {
        tileViewGrid[x][y].highlight();
    }

    public void setHighlight(ImageIcon highlight) {
        tileViews.forEach(it -> it.setColoredHighlight(highlight));
    }

    public void reset() {
        tileViews.forEach(TileView::reset);
    }
}
