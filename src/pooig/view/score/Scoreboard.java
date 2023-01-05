package pooig.view.score;

import pooig.model.Player;
import pooig.util.Constants;
import pooig.util.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Is the scoreboard class of the game. Manages a score label for each player.
 * @author Timur Saglam
 */
public class Scoreboard {

    private static final String FONT_TYPE = "Helvetica";
    private final JLabel[] scoreLabels;
    private final JLabel stackSizeLabel;
    private final List<JLabel> allLabels;
    private final Settings settings;

    public Scoreboard(Settings settings) {
        this.settings = settings;
        scoreLabels = new JLabel[Constants.MAXIMAL_PLAYERS];
        for (int i = 0; i < scoreLabels.length; i++) {
            scoreLabels[i] = new JLabel();
            scoreLabels[i].setForeground(settings.getPlayerColor(i));
        }

        stackSizeLabel = new JLabel();
        allLabels = new ArrayList<>(Arrays.asList(scoreLabels));
        allLabels.add(stackSizeLabel);
        for (JLabel label : allLabels) {
            label.setFont(new Font(FONT_TYPE, Font.BOLD, 12));
        }
    }
    public List<JLabel> getLabels() {
        return allLabels;
    }

    public void rebuild() {
        for (int i = 0; i < settings.getNumberOfPlayers(); i++) {
            scoreLabels[i].setVisible(true);
        }

        stackSizeLabel.setVisible(true);
    }

    public void update(Player player) {
        String playerName = player.getName();
        String text = "[" + playerName + ": " + player.getScore() + " points, " + player.getFreeMeeples() + " meeples]    ";
        scoreLabels[player.getID()].setText(text);
    }

    public void updateStackSize(int stackSize) {
        stackSizeLabel.setText("   [Stack Size: " + stackSize + "]");
    }
}
