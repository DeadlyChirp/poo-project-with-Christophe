package poo.view.dialog;

import poo.util.Constants;
import poo.util.MouseClickListener;
import poo.util.Settings;
import poo.view.FixedSizeLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class SettingsDialog extends JDialog {

    private static final int PADDING = 5;
    private static final int PLAYER_LABEL_WIDTH = 75;
    private static final int PLAYER_LABEL_POSITION = 1;
    private static final int BUTTON_VERTICAL_STRUT = 200;
    private static final int CLOSE_BUTTON_WIDTH = 125;

    private static final String EDIT = "Edit";
    private static final String START = "Start";
    private static final String AI_PLAYER = "AI player";
    private static final String PLAYERS = "Players:";
    private static final String TITLE = "Settings";
    private static final String COLON = ":";
    private static final String PLAYER = "Player ";
    private static final String SPACE = " ";

    private final Settings settings;
    private final JFrame mainFrame;
    private final List<JPanel> playerPanels;
    private final List<JLabel> playerLabels;

    public SettingsDialog(Settings settings, JFrame mainFrame) {
        this.settings = settings;
        this.mainFrame = mainFrame;

        playerPanels = new ArrayList<>();
        playerLabels = new ArrayList<>();
        buildPanels();
        notifyChange();
        buildWindow();
    }

    public void notifyChange() {
        for (int player = 0; player < Constants.MAXIMAL_PLAYERS; player++) {
            JLabel label = new FixedSizeLabel(SPACE + settings.getPlayerName(player), PLAYER_LABEL_WIDTH);
            label.setForeground(settings.getPlayerColor(player));
            if (playerLabels.size() == Constants.MAXIMAL_PLAYERS) {
                playerPanels.get(player).remove(playerLabels.remove(player));
            }
            playerPanels.get(player).add(label, PLAYER_LABEL_POSITION);
            playerLabels.add(player, label);
        }

        validate();
    }

    private void addWithBox(JPanel panel, JComponent component) {
        panel.add(component);
        panel.add(Box.createRigidArea(new Dimension(0, PADDING)));
    }

    private void buildPanels() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        addWithBox(mainPanel, createPlayerNumberPanel());
        addWithBox(mainPanel, createPlayerPanel());
        mainPanel.add(createStartButton());
        getContentPane().add(mainPanel);
    }

    private void buildWindow() {
        setTitle(TITLE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createBasicPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        return panel;
    }

    private JPanel createBasicPanel(String labelText) {
        JPanel panel = createBasicPanel();
        panel.add(embolden(new JLabel(labelText + SPACE)));
        return panel;
    }

    private JPanel createStartButton() {
        JButton button = new JButton(START);
        button.addActionListener(it -> dispose());
        button.setPreferredSize(new Dimension(CLOSE_BUTTON_WIDTH, button.getPreferredSize().height));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(button);
        return buttonPanel;
    }

    private void createPlayerNumberButton(int numberOfPlayers, JPanel panel, ButtonGroup group) {
        JRadioButton button = new JRadioButton(numberOfPlayers + SPACE + PLAYERS);
        button.setSelected(settings.getNumberOfPlayers() == numberOfPlayers);
        button.addMouseListener((MouseClickListener) event -> settings.setNumberOfPlayers(numberOfPlayers));
        group.add(button);
        panel.add(button);
    }

    private JPanel createPlayerNumberPanel() {
        JPanel panel = createBasicPanel(PLAYERS);
        ButtonGroup group = new ButtonGroup();
        for (int numberOfPlayers = 2; numberOfPlayers <= Constants.MAXIMAL_PLAYERS; numberOfPlayers++) {
            createPlayerNumberButton(numberOfPlayers, panel, group);
        }
        return panel;
    }

    private JPanel createPlayerPanel() {
        JPanel playerPanel = createBasicPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < Constants.MAXIMAL_PLAYERS; i++) {
            playerPanel.add(createPlayerRow(i));
        }

        return playerPanel;
    }

    private JPanel createPlayerRow(int playerNumber) {
        JPanel panel = new JPanel();
        playerPanels.add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.add(embolden(new JLabel(PLAYER + (playerNumber + 1) + COLON)));
        JCheckBox checkBox = new JCheckBox(AI_PLAYER);
        checkBox.setSelected(settings.isPlayerComputerControlled(playerNumber));
        checkBox.addActionListener(event -> settings.setPlayerComputerControlled(checkBox.isSelected(), playerNumber));
        checkBox.setEnabled(false);
        panel.add(checkBox);
        panel.add(Box.createHorizontalStrut(BUTTON_VERTICAL_STRUT));
        JButton configurationButton = new JButton(EDIT);
        configurationButton.addActionListener(e -> {
            new PlayerDialog(playerNumber, settings, mainFrame).updateAndShow();
            notifyChange();
        });
        panel.add(configurationButton);
        return panel;
    }

    private JLabel embolden(JLabel label) {
        Font font = label.getFont();
        label.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
        return label;
    }
}
