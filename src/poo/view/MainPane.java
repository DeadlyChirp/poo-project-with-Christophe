package poo.view;

import javax.swing.*;
import java.awt.*;

public class MainPane extends JScrollPane {

    private static final int SCROLL_SPEED = 15;

    private final JLayeredPane layeredPane;

    public MainPane() {
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        setViewportView(layeredPane);
        getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
    }


    public void addLayers(Component... components) {
        if (components == null) {
            return;
        }

        for (int i = 0; i < components.length; i++) {
            layeredPane.add(components[i], i);
        }
    }

    public void removeLayers(Component... components) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            layeredPane.remove(component);
        }
    }

    public void repaintLayers() {
        layeredPane.repaint();
    }

    public void validateAndCenter() {
        validate();
        centerScrollBars(getHorizontalScrollBar().getMaximum(), getVerticalScrollBar().getMaximum());
    }

    private void centerScrollBars(int width, int height) {
        Rectangle view = getViewport().getViewRect();
        getHorizontalScrollBar().setValue(Math.max(0, (width - view.width) / 2));
        getVerticalScrollBar().setValue(Math.max(0, (height - view.height) / 2));
    }
}
