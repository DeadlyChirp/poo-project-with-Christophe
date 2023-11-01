package poo.view;

import poo.controller.CarcassonneController;
import poo.controller.DominoCLIController;
import poo.controller.DominoController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MenuFrame extends JFrame {

    public MenuFrame() {
        setSize(700, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pong & Carcassonne by Christophe & Thanh");

        initUI();

        setVisible(true);
    }

    private void initUI() {
        GridLayout layout = new GridLayout(3, 1);
        layout.setVgap(20);


        JPanel contentPanel = new JPanel(layout);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        contentPanel.setBorder(padding);

        JButton dominoCLIButton = new JButton("Domino Terminal");
        dominoCLIButton.addActionListener(e -> {
            MenuFrame.this.dispose();
            new DominoCLIController().start();
        });
        contentPanel.add(dominoCLIButton);

        JButton dominoButton = new JButton("Domino");
        dominoButton.addActionListener(e -> {
            MenuFrame.this.dispose();
            new DominoController().start();
        });
        contentPanel.add(dominoButton);

        JButton carcassonneButton = new JButton("Carcassonne");
        carcassonneButton.addActionListener(e -> {
            MenuFrame.this.dispose();
            new CarcassonneController().start();
        });
        contentPanel.add(carcassonneButton);

        setContentPane(contentPanel);
    }
}
