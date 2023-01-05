package pooig.view;

import javax.swing.Box.Filler;
import java.awt.*;

public class RigidRectangle extends Filler {

    private static final long serialVersionUID = 8847635761705081422L;

    public RigidRectangle(Dimension dimension) {
        super(dimension, dimension, dimension);
    }

    public RigidRectangle(int sideLength) {
        this(new Dimension(sideLength, sideLength));
    }

    public void changeShape(Dimension dimension) {
        changeShape(dimension, dimension, dimension);
    }

}
