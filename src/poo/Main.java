package poo;
import poo.view.MenuFrame;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            
        }

        // new DominoCLIController().start();
        // new DominoController().start();
        // new CarcassonneController().start();

        new MenuFrame();
    }
}
