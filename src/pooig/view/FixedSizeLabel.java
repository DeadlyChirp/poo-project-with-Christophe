package pooig.view;

import javax.swing.*;
import java.awt.*;

public class FixedSizeLabel extends JLabel {

    private static final long serialVersionUID = 2548111454775629470L;
    private final int fixedWidth;

    public FixedSizeLabel(String text, int fixedWidth) {
        super(text);
        this.fixedWidth = fixedWidth;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(fixedWidth, super.getPreferredSize().height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(fixedWidth, super.getMinimumSize().height);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(fixedWidth, super.getMaximumSize().height);
    }

}
