package pooig.view.dialog;

import pooig.controller.CarcassonneController;
import pooig.model.Direction;
import pooig.model.Player;
import pooig.model.tile.cc.TerrainType;
import pooig.model.tile.cc.TileCc;
import pooig.util.Constants;
import pooig.util.ImageLoadingUtil;
import pooig.util.MouseClickListener;
import pooig.util.TileUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MeepleDialog extends JDialog {

    private static final long serialVersionUID = 1449264387665531286L;
    private Map<Direction, JButton> meepleButtons;
    private Color defaultButtonColor;
    private TileCc tile;
    private final CarcassonneController controller;
    private Player currentPlayer;
    private final JPanel dialogPanel;

    public MeepleDialog(CarcassonneController controller, JFrame ui) {
        super(ui);

        this.controller = controller;

        dialogPanel = new JPanel(new GridBagLayout());

        buildFrame();
        buildButtonSkip();
        buildButtonGrid();
        pack();
    }

    public void showUI() {
        pack();
        setVisible(true);
        toFront();
    }

    public void exit() {
        setVisible(false);
    }

    public void setTile(TileCc tile, Player currentPlayer) {
        this.tile = tile;
        this.currentPlayer = currentPlayer;

        setTitle(currentPlayer.getName());

        updatePlacementButtons();
        showUI();
    }

    private void buildFrame() {
        getContentPane().add(dialogPanel);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    private void buildButtonGrid() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        meepleButtons = new HashMap<>();
        int index = 0;
        for (Direction direction : Direction.byRow()) {
            JButton button = new JButton();
            constraints.gridx = index % 3;
            constraints.gridy = index / 3 + 1;
            dialogPanel.add(button, constraints);
            meepleButtons.put(direction, button);
            index++;
        }
    }

    private void buildButtonSkip() {
        JButton buttonSkip = new JButton(ImageLoadingUtil.SKIP.createImageIcon(20));
        buttonSkip.setToolTipText("Don't place meeple and preserve for later use");
        defaultButtonColor = buttonSkip.getBackground();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 3;
        dialogPanel.add(buttonSkip, constraints);

        buttonSkip.addMouseListener((MouseClickListener) event -> controller.skipPlacingMeeple());
    }

    private void updatePlacementButtons() {
        for (Direction direction : Direction.values()) {
            TerrainType terrain = tile.getTerrainType(direction);
            boolean placeable = tile.hasMeeplePosition(direction);
            JButton button = meepleButtons.get(direction);
            if (placeable) {
                button.setIcon(ImageLoadingUtil.createImageIcon(TileUtil.getMeeplePath(terrain, false), Constants.MEEPLE_SIZE));
            } else {
                button.setIcon(ImageLoadingUtil.createImageIcon(TileUtil.getMeeplePath(TerrainType.OTHER, false), Constants.MEEPLE_SIZE));
            }

            if (placeable && tile.allowsPlacingMeeple(direction, currentPlayer)) {
                button.addMouseListener((MouseClickListener) event -> controller.placeMeeple(direction));
                button.setEnabled(true);
                button.setBackground(defaultButtonColor);
            } else {
                button.setEnabled(false);
                button.setBackground(currentPlayer.getColor());
            }
        }
    }
}
