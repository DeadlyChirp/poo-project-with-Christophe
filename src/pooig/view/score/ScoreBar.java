package pooig.view.score;

import pooig.controller.BaseController;
import pooig.model.Player;
import pooig.util.Settings;

import javax.swing.*;

public class ScoreBar extends JMenuBar {

    private final BaseController controller;
    private final Scoreboard scoreboard;
    private final Settings settings;

    public ScoreBar(BaseController controller) {
        super();
        this.controller = controller;
        settings = controller.getSettings();
        scoreboard = new Scoreboard(settings);

        add(scoreboard);
    }

    public void rebuild() {
        scoreboard.rebuild();
    }

    public void update(Player player) {
        scoreboard.update(player);
    }

    public void updateStackSize(int size) {
        scoreboard.updateStackSize(size);
    }

    private void add(Scoreboard scoreboard) {
        for (JLabel label : scoreboard.getLabels()) {
            add(label);
        }
    }
}
