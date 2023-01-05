package pooig.view;

import pooig.controller.CarcassonneController;
import pooig.model.Direction;
import pooig.model.Player;
import pooig.model.tile.cc.TileCc;
import pooig.view.meeple.MeeplesPanel;
import pooig.view.tile.TilesCcPanel;

public class CarcassonneFrame extends BaseFrame {

    private MeeplesPanel meeplesPanel;

    public CarcassonneFrame(CarcassonneController controller) {
        super(controller);
    }

    public void showFrame() {
        setVisible(true);
        mainPane.validateAndCenter();
    }

    public void reset() {
        meeplesPanel.reset();
        super.reset();
    }

    @Override
    public void rebuild() {
        gridWidth = controller.getSettings().getGridWidth();
        gridHeight = controller.getSettings().getGridHeight();
        mainPane.removeLayers(meeplesPanel, tilesPanel);
        tilesPanel = new TilesCcPanel(gridWidth, gridHeight, controller);
        meeplesPanel = new MeeplesPanel(gridWidth, gridHeight, (CarcassonneController) controller);
        mainPane.addLayers(meeplesPanel, tilesPanel);
        mainPane.validateAndCenter();
    }

    public void setMeeple(TileCc tile, Direction position, Player owner) {
        int x = tile.getLocation().getX();
        int y = tile.getLocation().getY();

        meeplesPanel.placeMeeple(x, y, tile.getTerrainType(position), position, owner);
        mainPane.repaintLayers();
    }

    public void setMeeplePreview(TileCc tile, Player currentPlayer) {
        int x = tile.getLocation().getX();
        int y = tile.getLocation().getY();

        meeplesPanel.enableMeeplePreview(x, y, tile, currentPlayer);
        mainPane.repaintLayers();
    }

    public void removeMeeple(int x, int y) {
        meeplesPanel.resetPanel(x, y);
    }

    public void resetMeeplePreview(TileCc tile) {
        int x = tile.getLocation().getX();
        int y = tile.getLocation().getY();

        meeplesPanel.resetPanel(x, y);
    }

    @Override
    protected void buildFrame() {
        super.buildFrame();

        setTitle("Carcassonne");

        tilesPanel = new TilesCcPanel(gridWidth, gridHeight, controller);
        meeplesPanel = new MeeplesPanel(gridWidth, gridHeight, (CarcassonneController) controller);
        mainPane.addLayers(meeplesPanel, tilesPanel);

        pack();
    }
}
