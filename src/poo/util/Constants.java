package poo.util;

import java.awt.*;

public final class Constants {

    private Constants() {}

    public static final int MAXIMAL_PLAYERS = 5;
    public static final int DEFAULT_NUM_PLAYERS = 2;
    public static final int MAXIMAL_MEEPLES = 7;
    public static final int DEFAULT_GRID_WIDTH = 20;
    public static final int DEFAULT_GRID_HEIGHT = 20;
    public static final int GRID_SIZE = 125;
    public static final int MEEPLE_SIZE = 25;

    public static final String WINDOWS = "Windows";
    public static final String OS_NAME_PROPERTY = "os.name";
    public static final String TILE_FILE_TYPE = ".png";
    public static final String TILE_FOLDER_PATH = "tiles/";
    public static final String EMPTY = "";
    public static final String MEEPLE_PATH = "meeple/meeple_";
    public static final String PNG = ".png";
    public static final String TEMPLATE = "_template";

    public static final String[] DEFAULT_PLAYER_NAMES = { "Player 1", "Player 2", "Player 3", "Player 4", "Player 5" };
    public static final Color[] DEFAULT_PLAYER_COLORS = {
            Color.decode("#2196f3"),
            Color.decode("#f44336"),
            Color.decode("#4caf50"),
            Color.decode("#ff9800"),
            Color.decode("#9c27b0")
    };
}
