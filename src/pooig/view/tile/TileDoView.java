package pooig.view.tile;

import pooig.controller.BaseController;
import pooig.model.Direction;
import pooig.model.tile.dom.TileDo;
import pooig.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TileDoView extends TileView<TileDo> {

    private final JLabel[][] labels;

    public TileDoView(int tileX, int tileY, BaseController controller) {
        super(controller);

        labels = new JLabel[5][5];

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                labels[x][y] = new JLabel("", SwingConstants.CENTER);
                labels[x][y].setOpaque(true);
                if (x == 0 || x == 4 || y == 0 || y == 4) {
                    labels[x][y].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    labels[x][y].setBackground(Color.WHITE);
                }
                labels[x][y].setFont(new Font("Arial", Font.BOLD, 16));
                labels[x][y].setPreferredSize(new Dimension(Constants.GRID_SIZE / 5, Constants.GRID_SIZE / 5));
                constraints.gridx = x;
                constraints.gridy = y;
                add(labels[x][y], constraints);
            }
        }

        labels[0][0].setBackground(Color.LIGHT_GRAY);
        labels[0][4].setBackground(Color.LIGHT_GRAY);
        labels[4][0].setBackground(Color.LIGHT_GRAY);
        labels[4][4].setBackground(Color.LIGHT_GRAY);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null && tileX != -1 && tileY != -1) {
                    controller.placeTile(tileX, tileY);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    @Override
    public void setTile(TileDo tile) {
        this.tile = tile;

        if (tile != null) {
            List<Integer> list = tile.getTerrain().at(Direction.EAST);
            int index = 1;
            for (Integer integer : list) {
                labels[4][index].setText(integer.toString());
                index++;
            }

            list = tile.getTerrain().at(Direction.WEST);
            index = 1;
            for (Integer integer : list) {
                labels[0][index].setText(integer.toString());
                index++;
            }

            list = tile.getTerrain().at(Direction.SOUTH);
            index = 1;
            for (Integer integer : list) {
                labels[index][4].setText(integer.toString());
                index++;
            }

            list = tile.getTerrain().at(Direction.NORTH);
            index = 1;
            for (Integer integer : list) {
                labels[index][0].setText(integer.toString());
                index++;
            }
        }

        repaint();
    }

    @Override
    public void setColoredHighlight(ImageIcon coloredHighlight) {}

    @Override
    public void highlight() {}

    @Override
    public void reset() {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                labels[x][y].setText("");
            }
        }
    }
}
