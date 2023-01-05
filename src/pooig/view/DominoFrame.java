package pooig.view;

import pooig.controller.DominoController;
import pooig.view.tile.TilesDoPanel;

public class DominoFrame extends BaseFrame {

    public DominoFrame(DominoController controller) {
        super(controller);
    }

    public void showFrame() {
        setVisible(true);
        mainPane.validateAndCenter();
    }

    @Override
    public void rebuild() {
        gridWidth = controller.getSettings().getGridWidth();
        gridHeight = controller.getSettings().getGridHeight();
        mainPane.removeLayers(tilesPanel);
        tilesPanel = new TilesDoPanel(gridWidth, gridHeight, controller);
        mainPane.addLayers(tilesPanel);
        mainPane.validateAndCenter();
    }

    @Override
    protected void buildFrame() {
        super.buildFrame();

        setTitle("Domino");

        tilesPanel = new TilesDoPanel(gridWidth, gridHeight, controller);
        mainPane.addLayers(tilesPanel);

        pack();
    }
}
