package pooig.view.dialog;

import pooig.controller.DominoController;
import pooig.model.Player;
import pooig.model.tile.dom.TileDo;
import pooig.util.ImageLoadingUtil;
import pooig.util.MouseClickListener;
import pooig.view.tile.TileDoView;

import javax.swing.*;
import java.awt.*;

public class TileDoDialog extends JDialog {

    private static final int BOTTOM_SPACE = 5;
    private static final int VERTICAL_SPACE = 10;

    private final int selectionSize;
    private JButton buttonRotateLeft;
    private JButton buttonRotateRight;
    private JButton buttonSkip;
    private TileDoView view;
    private TileDo tile;
    private final DominoController controller;
    private Player<TileDo> currentPlayer;
    private final JPanel dialogPanel;

    public TileDoDialog(DominoController controller, JFrame ui) {
        super(ui);

        dialogPanel = new JPanel(new GridBagLayout());
        this.controller = controller;

        buildFrame();
        buildContent();
        pack();

        selectionSize = dialogPanel.getWidth() - VERTICAL_SPACE;
    }

    public void showUI() {
        pack();
        setVisible(true);
        toFront();
    }

    public void exit() {
        setVisible(false);
    }

    public TileDo getSelectedTile() {
        return tile;
    }

    public void rotateLeft() {
        if (isVisible()) {
            tile.rotateLeft();
            updateTileLabel();
        }
    }

    public void rotateRight() {
        if (isVisible()) {
            tile.rotateRight();
            updateTileLabel();
        }
    }

    public void setTile(Player<TileDo> currentPlayer) {
        if (currentPlayer.getHandOfTile() != null) {
            tile = currentPlayer.getHandOfTile();
            this.currentPlayer = currentPlayer;
            updateTitle();
            updateTileLabel();
            showUI();
        }
    }

    private void buildFrame() {
        getContentPane().add(dialogPanel);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    private void buildContent() {
        buttonSkip = new JButton(ImageLoadingUtil.SKIP.createImageIcon(20));
        buttonRotateLeft = new JButton(ImageLoadingUtil.LEFT.createImageIcon(20));
        buttonRotateRight = new JButton(ImageLoadingUtil.RIGHT.createImageIcon(20));

        buttonSkip.addMouseListener((MouseClickListener) event -> controller.skipTile());
        buttonRotateLeft.addMouseListener((MouseClickListener) event -> rotateLeft());
        buttonRotateRight.addMouseListener((MouseClickListener) event -> rotateRight());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.weightx = 1;

        dialogPanel.add(buttonRotateLeft, constraints);
        dialogPanel.add(buttonSkip, constraints);
        dialogPanel.add(buttonRotateRight, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 3;

        view = new TileDoView(-1, -1, controller);
        constraints.gridy++;
        dialogPanel.add(view, constraints);
        constraints.gridy++;
        dialogPanel.add(Box.createVerticalStrut(BOTTOM_SPACE), constraints);
    }

    private void updateTitle() {
        setTitle(currentPlayer.getName());
    }

    private void updateTileLabel() {
        view.setTile(tile);
    }
}
