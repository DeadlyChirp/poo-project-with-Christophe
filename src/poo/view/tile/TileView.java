package poo.view.tile;

import poo.controller.BaseController;
import poo.model.tile.base.BaseTile;
import poo.util.Constants;

import javax.swing.*;
import java.awt.*;

public abstract class TileView<T extends BaseTile> extends JPanel {

    protected T tile;
    protected BaseController controller;

    public TileView(BaseController controller) {
        this.controller = controller;

        setPreferredSize(new Dimension(Constants.GRID_SIZE, Constants.GRID_SIZE));
        setOpaque(false);
    }

    public abstract void setTile(T tile);

    public abstract void setColoredHighlight(ImageIcon coloredHighlight);

    public abstract void highlight();

    public abstract void reset();
}
