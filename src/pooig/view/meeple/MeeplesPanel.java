package pooig.view.meeple;

import pooig.controller.CarcassonneController;
import pooig.model.Direction;
import pooig.model.Player;
import pooig.model.tile.cc.TerrainType;
import pooig.model.tile.cc.TileCc;
import pooig.util.Constants;
import pooig.view.RigidRectangle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MeeplesPanel extends JPanel {

    private static final long serialVersionUID = -843137441362337953L;
    private final List<MeeplePanel> meeplePanels;
    private final MeeplePanel[][] meeplePanelGrid;
    private final JComponent[][] placeholderGrid;
    private final List<RigidRectangle> placeholders;
    private final CarcassonneController controller;

    public MeeplesPanel(int gridWidth, int gridHeight, CarcassonneController controller) {
        this.controller = controller;
        setOpaque(false);
        setLayout(new GridBagLayout());
        synchronizeLayerSizes(gridWidth, gridHeight);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        meeplePanels = new ArrayList<>();
        meeplePanelGrid = new MeeplePanel[gridWidth][gridHeight];
        placeholders = new ArrayList<>();
        placeholderGrid = new JComponent[gridWidth][gridHeight];
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                constraints.gridx = x;
                constraints.gridy = y;
                RigidRectangle rectangle = new RigidRectangle(Constants.GRID_SIZE);
                placeholderGrid[x][y] = rectangle;
                placeholders.add(rectangle);
                add(rectangle, constraints);
            }
        }
    }

    private void initializeLazily(int x, int y) {
        if (meeplePanelGrid[x][y] == null) {
            remove(placeholderGrid[x][y]);
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridx = x;
            constraints.gridy = y;
            meeplePanelGrid[x][y] = new MeeplePanel(controller);
            meeplePanels.add(meeplePanelGrid[x][y]);
            add(meeplePanelGrid[x][y], constraints);
            validate();
        }
    }

    public void enableMeeplePreview(int x, int y, TileCc tile, Player currentPlayer) {
        initializeLazily(x, y);
        meeplePanelGrid[x][y].setMeeplePreview(tile, currentPlayer);
    }

    public void placeMeeple(int x, int y, TerrainType terrain, Direction position, Player owner) {
        initializeLazily(x, y);
        meeplePanelGrid[x][y].placeMeeple(terrain, position, owner);
    }

    public void reset() {
        meeplePanels.stream().forEach(MeeplePanel::resetAll);
    }

    public void resetPanel(int x, int y) {
        meeplePanelGrid[x][y].resetAll();
    }

    public void synchronizeLayerSizes(int gridWidth, int gridHeight) {
        Dimension layerSize = new Dimension(gridWidth * Constants.GRID_SIZE, gridHeight * Constants.GRID_SIZE);
        setMaximumSize(layerSize);
        setPreferredSize(layerSize);
        setMinimumSize(layerSize);
    }
}
