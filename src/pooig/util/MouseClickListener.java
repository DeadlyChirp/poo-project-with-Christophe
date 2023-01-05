package pooig.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface MouseClickListener extends MouseListener {

    @Override
    default void mousePressed(MouseEvent event) {}

    @Override
    default void mouseClicked(MouseEvent event) {}

    @Override
    default void mouseEntered(MouseEvent event) {}

    @Override
    default void mouseExited(MouseEvent event) {}
}
