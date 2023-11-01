package poo.view.meeple;

import poo.controller.CarcassonneController;
import poo.model.Direction;
import poo.model.Player;
import poo.model.tile.cc.TerrainType;
import poo.model.tile.cc.TileCc;
import poo.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MeeplePanel extends JPanel {

    private static final int GRID_INDEX_OFFSET = 1;
    private final CarcassonneController controller;
    private final Map<Direction, MeepleView> labels;

    public MeeplePanel(CarcassonneController controller) {
        this.controller = controller;
        labels = new HashMap<>(Direction.values().length);
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        for (Direction direction : Direction.values()) {
            MeepleView meepleLabel = new MeepleView(controller, direction);
            constraints.gridx = direction.getX() + GRID_INDEX_OFFSET;
            constraints.gridy = direction.getY() + GRID_INDEX_OFFSET;
            labels.put(direction, meepleLabel);
            add(meepleLabel.getLabel(), constraints);
        }
    }

    public void placeMeeple(TerrainType terrain, Direction position, Player owner) {
        labels.get(position).setIcon(terrain, owner);
    }

    public void setMeeplePreview(TileCc tile, Player currentPlayer) {
        for (Direction direction : Direction.values()) {
            if (tile.hasMeeplePosition(direction) && tile.allowsPlacingMeeple(direction, currentPlayer)) {
                labels.get(direction).setPreview(tile.getTerrainType(direction), currentPlayer);
            }
        }
    }

    public void refreshAll() {
        labels.values().forEach(MeepleView::refresh);
    }

    public void resetAll() {
        labels.values().forEach(MeepleView::reset);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.GRID_SIZE, Constants.GRID_SIZE);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Constants.GRID_SIZE, Constants.GRID_SIZE);
    }
}
