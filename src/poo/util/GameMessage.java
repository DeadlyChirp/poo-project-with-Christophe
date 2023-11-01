package poo.util;

import javax.swing.*;

public final class GameMessage {

    private static final String TITLE = "Carcassonne by Christophe & Thanh";

    private GameMessage() {}

    public static void showError(String messageText) {
        show(messageText, JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessage(String messageText) {
        show(messageText, JOptionPane.DEFAULT_OPTION);
    }

    public static void showWarning(String messageText) {
        show(messageText, JOptionPane.WARNING_MESSAGE);
    }

    private static void show(String messageText, int type) {
        JOptionPane.showMessageDialog(null, messageText, TITLE, type);
    }
}
