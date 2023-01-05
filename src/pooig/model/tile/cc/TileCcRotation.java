package pooig.model.tile.cc;

public enum TileCcRotation {

    UP, // rotate 0 degree
    RIGHT, // rotate 90 degree clockwise
    DOWN, // rotate 180 degree
    LEFT; // rotate 90 degree counter-clockwise

    public TileCcRotation rotate(RotationDirection direction) {
        int newOrdinal = Math.floorMod(this.ordinal() + direction.getValue(), values().length);
        return values()[newOrdinal];
    }
}
