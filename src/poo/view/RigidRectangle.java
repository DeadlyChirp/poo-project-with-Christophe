package poo.view;

import javax.swing.Box.Filler;
import java.awt.*;

public class RigidRectangle extends Filler {

    public RigidRectangle(Dimension dimension) {
        super(dimension, dimension, dimension);
    }

    public RigidRectangle(int sideLength) {
        this(new Dimension(sideLength, sideLength));
    }


}
