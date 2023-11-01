package poo.view.dialog;

import poo.util.GameMessage;
import poo.util.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerDialog extends JDialog implements ActionListener {

    private static final String CHANGE_COLOR = "Choose Color:";
    private static final String EMPTY_NAME = "The player name cannot be empty!";
    private static final String ACCEPT_CHANGES = "Accept Changes";
    private static final String CHANGE_NAME = "Choose Name:";
    private JColorChooser colorChooser;
    private final Settings settings;
    private final int playerNumber;
    private JTextField nameTextField;

    public PlayerDialog(int playerNumber, Settings settings, JFrame mainView) {
        super(mainView);

        this.playerNumber = playerNumber;
        this.settings = settings;
        setLayout(new BorderLayout());
        createNamePanel();
        createCloseButton();
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

    public void updateAndShow() {
        if (colorChooser == null) {
            createColorChooser();
            pack();
            setLocationRelativeTo(null);
        }

        colorChooser.setColor(settings.getPlayerColor(playerNumber));
        nameTextField.setText(settings.getPlayerName(playerNumber));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (nameTextField.getText().isEmpty()) {
            GameMessage.showMessage(EMPTY_NAME);
        } else {
            settings.setPlayerName(nameTextField.getText(), playerNumber);
            settings.setPlayerColor(colorChooser.getColor(), playerNumber);
            setVisible(false);
        }
    }

    private void createNamePanel() {
        nameTextField = new JTextField();
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BorderLayout());
        namePanel.add(nameTextField);
        namePanel.setBorder(BorderFactory.createTitledBorder(CHANGE_NAME));
        add(namePanel, BorderLayout.NORTH);
    }

    private void createColorChooser() {
        colorChooser = new JColorChooser();
        colorChooser.setBorder(BorderFactory.createTitledBorder(CHANGE_COLOR));
        add(colorChooser, BorderLayout.CENTER);
    }

    private void createCloseButton() {
        JButton closeButton = new JButton(ACCEPT_CHANGES);
        closeButton.addActionListener(this);
        add(closeButton, BorderLayout.SOUTH);
    }
}
