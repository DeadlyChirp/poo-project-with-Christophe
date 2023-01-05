package pooig.view.meeple;

import pooig.controller.CarcassonneController;
import pooig.model.Direction;
import pooig.model.Player;
import pooig.model.tile.cc.TerrainType;
import pooig.util.Constants;
import pooig.util.ImageUtil;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MeepleView {

    private Player player;
    private final MouseAdapter mouseAdapter;
    private TerrainType terrain;
    private final JLabel label;
    private boolean preview;

    public MeepleView(CarcassonneController controller, Direction direction) {
        terrain = TerrainType.OTHER;
        label = new JLabel(ImageUtil.getPreviewMeeple(terrain, Constants.MEEPLE_SIZE));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        preview = false;
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                controller.placeMeeple(direction);
            }

            @Override
            public void mouseEntered(MouseEvent event) {
                setMeepleIcon();
            }

            @Override
            public void mouseExited(MouseEvent event) {
                setPreviewIcon();
            }
        };
    }

    public void refresh() {
        if (terrain != TerrainType.OTHER && !preview) {
            setMeepleIcon();
        }
    }

    public void reset() {
        terrain = TerrainType.OTHER;
        setPreviewIcon();
        label.removeMouseListener(mouseAdapter);
    }

    public void setIcon(TerrainType terrain, Player player) {
        this.terrain = terrain;
        this.player = player;
        preview = false;
        refresh();
    }

    public void setPreview(TerrainType terrain, Player player) {
        this.terrain = terrain;
        this.player = player;
        preview = true;
        label.addMouseListener(mouseAdapter);
        setPreviewIcon();
    }

    public JLabel getLabel() {
        return label;
    }

    private void setMeepleIcon() {
        label.setIcon(ImageUtil.getColoredMeeple(terrain, player.getColor(), Constants.MEEPLE_SIZE));
    }

    private void setPreviewIcon() {
        label.setIcon(ImageUtil.getPreviewMeeple(terrain, Constants.MEEPLE_SIZE));
    }
}
