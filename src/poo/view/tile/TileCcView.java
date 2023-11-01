package poo.view.tile;

import poo.controller.BaseController;
import poo.model.tile.cc.TileCc;
import poo.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileCcView extends TileView<TileCc> {

    private final TileCc defaultTile;
    private final TileCc highlightTile;
    private ImageIcon coloredHighlight;
    private final JLabel view;

    public TileCcView(TileCc defaultTile, TileCc highlightTile, int x, int y, BaseController controller) {
        super(controller);

        this.defaultTile = defaultTile;
        this.highlightTile = highlightTile;
        this.coloredHighlight = null;

        setLayout(null);

        view = new JLabel();
        view.setPreferredSize(new Dimension(Constants.GRID_SIZE, Constants.GRID_SIZE));
        view.setMinimumSize(new Dimension(Constants.GRID_SIZE, Constants.GRID_SIZE));
        view.setBounds(0, 0, Constants.GRID_SIZE, Constants.GRID_SIZE);
        add(view);

        reset();

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null) {
                    controller.placeTile(x, y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (highlightTile.equals(tile) && coloredHighlight != null) {
                    view.setIcon(coloredHighlight);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (highlightTile.equals(tile)) {
                    setTile(highlightTile);
                }
            }
        });
    }

    @Override
    public void setTile(TileCc tile) {
        this.tile = tile;

        if (tile != null) {
            view.setIcon(tile.getImageIcon(Constants.GRID_SIZE));
        }
    }

    @Override
    public void setColoredHighlight(ImageIcon coloredHighlight) {
        this.coloredHighlight = coloredHighlight;
    }

    @Override
    public void highlight() {
        setTile(highlightTile);
    }

    @Override
    public void reset() {
        if (tile != defaultTile) {
            setTile(defaultTile);
        }
    }
}
