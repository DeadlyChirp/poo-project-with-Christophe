package poo.view.dialog;

import poo.controller.CarcassonneController;
import poo.model.Player;
import poo.model.tile.cc.TileCc;
import poo.model.tile.cc.TileCcType;
import poo.util.ImageLoadingUtil;
import poo.util.MouseClickListener;

import javax.swing.*;
import java.awt.*;

public class TileDialog extends JDialog {

    private static final int BOTTOM_SPACE = 5;
    private static final int VERTICAL_SPACE = 10;

    private final int selectionSize;
    private JButton buttonRotateLeft;
    private JButton buttonRotateRight;
    private JButton buttonSkip;
    private JLabel tileLabel;
    private TileCc tile;
    private final CarcassonneController controller;
    private Player<TileCc> currentPlayer;
    private final JPanel dialogPanel;

    public TileDialog(CarcassonneController controller, JFrame ui) {
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

    public TileCc getSelectedTile() {
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

    public void setTile(Player<TileCc> currentPlayer) {
        if (currentPlayer.getHandOfTile() != null) {
            tile = currentPlayer.getHandOfTile();
            this.currentPlayer = currentPlayer;
            updateTitle();
            updatePreviewLabels();
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

        ImageIcon defaultImage = new TileCc(TileCcType.Null).getImageIcon(50);
        tileLabel = new JLabel(defaultImage);
        constraints.gridy++;
        dialogPanel.add(tileLabel, constraints);
        constraints.gridy++;
        dialogPanel.add(Box.createVerticalStrut(BOTTOM_SPACE), constraints);
    }

    private void updateTitle() {
        setTitle(currentPlayer.getName());
    }

    private void updateTileLabel() {
        ImageIcon icon = tile.getImageIcon(selectionSize);
        tileLabel.setIcon(icon);
    }

    private void updatePreviewLabels() {
        updateTileLabel();
        pack();
    }
}
