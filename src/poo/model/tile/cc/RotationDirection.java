package poo.model.tile.cc;

public enum RotationDirection {

    LEFT(-1),
    RIGHT(1);

    private final int value;

    RotationDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
