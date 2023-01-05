package pooig.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Settings {

    public static final Dimension MINIMAL_WINDOW_SIZE = new Dimension(640, 480);

    private final List<Color> colors;
    private final List<String> names;

    private int gridWidth;
    private int gridHeight;
    private int numberOfPlayers;
    private final List<Boolean> playerTypes;

    public Settings() {
        colors = new ArrayList<>(Arrays.asList(Constants.DEFAULT_PLAYER_COLORS));
        names = new ArrayList<>(Arrays.asList(Constants.DEFAULT_PLAYER_NAMES));
        playerTypes = new ArrayList<>(Arrays.asList(false, false, false, false, false));
        numberOfPlayers = Constants.DEFAULT_NUM_PLAYERS;
        gridWidth = Constants.DEFAULT_GRID_WIDTH;
        gridHeight = Constants.DEFAULT_GRID_HEIGHT;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers < 2) {
            numberOfPlayers = 2;
        }

        if (numberOfPlayers > Constants.MAXIMAL_PLAYERS) {
            numberOfPlayers = Constants.MAXIMAL_PLAYERS;
        }

        this.numberOfPlayers = numberOfPlayers;
    }

    public Color getPlayerColor(int playerID) {
        return colors.get(playerID);
    }

    public void setPlayerColor(Color color, int playerID) {
        colors.set(playerID, color);
    }

    public String getPlayerName(int playerNumber) {
        return names.get(playerNumber);
    }

    public void setPlayerName(String name, int playerNumber) {
        names.set(playerNumber, name);
    }

    public void setPlayerComputerControlled(boolean computerControlled, int playerNumber) {
        playerTypes.set(playerNumber, computerControlled);
    }

    public boolean isPlayerComputerControlled(int playerNumber) {
        return playerTypes.get(playerNumber);
    }
}
