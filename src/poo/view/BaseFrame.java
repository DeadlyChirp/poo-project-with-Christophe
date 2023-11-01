package poo.view;

import poo.controller.BaseController;
import poo.model.Player;
import poo.model.tile.base.BaseTile;
import poo.util.Constants;
import poo.util.ImageUtil;
import poo.util.Settings;
import poo.view.score.ScoreBar;
import poo.view.tile.TilesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class BaseFrame extends JFrame {

    protected MainPane mainPane;
    protected TilesPanel tilesPanel;
    protected ScoreBar scoreBar;
    protected int gridWidth, gridHeight;
    protected BaseController controller;

    public BaseFrame(BaseController controller) {
        this.controller = controller;

        gridWidth = controller.getSettings().getGridWidth();
        gridHeight = controller.getSettings().getGridHeight();

        buildFrame();
    }
    public abstract void rebuild();

    public void showFrame() {
        setVisible(true);
        mainPane.validateAndCenter();
    }

    public void reset() {
        tilesPanel.reset();
        validate();
    }


    public void placeTile(BaseTile tile, int x, int y) {
        tilesPanel.placeTile(tile, x, y);
    }

    public void highlightTile(int x, int y) {
        tilesPanel.highlightTile(x, y);
    }

    public void setCurrentPlayer(Player currentPlayer) {
        tilesPanel.setHighlight(ImageUtil.getColoredHighlight(currentPlayer.getColor(), Constants.GRID_SIZE));
    }

    public ScoreBar getScoreBar() {
        return scoreBar;
    }

    protected void buildFrame() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //create MenuFrame and dispose this frame
                new MenuFrame();

            }
        });
        setMinimumSize(Settings.MINIMAL_WINDOW_SIZE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        scoreBar = new ScoreBar(controller);
        setJMenuBar(scoreBar);

        mainPane = createMainPane();
        add(mainPane, BorderLayout.CENTER);
        pack();
    }

    private MainPane createMainPane() {
        if (System.getProperty(Constants.OS_NAME_PROPERTY).startsWith(Constants.WINDOWS)) {
            MainPane pane;
            try {
                LookAndFeel previousLF = UIManager.getLookAndFeel();
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                pane = new MainPane();
                UIManager.setLookAndFeel(previousLF);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) {
                // exception.printStackTrace();
                pane = new MainPane();
            }

            return pane;
        }

        return new MainPane();
    }
}
