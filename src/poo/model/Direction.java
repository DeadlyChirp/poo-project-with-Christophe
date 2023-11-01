package poo.model;

import poo.model.tile.cc.RotationDirection;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH_WEST,
    NORTH_WEST,
    CENTER;

    public int getX() {
        if (this == NORTH_EAST || this == EAST || this == SOUTH_EAST) {
            return 1;
        } else if (this == NORTH_WEST || this == WEST || this == SOUTH_WEST) {
            return -1;
        }
        return 0;
    }

    public int getY() {
        if (this == SOUTH_WEST || this == SOUTH || this == SOUTH_EAST) {
            return 1;
        } else if (this == NORTH_WEST || this == NORTH || this == NORTH_EAST) {
            return -1;
        }
        return 0;
    }

    public boolean isLeftOf(Direction other) {
        return nextDirectionTo(RotationDirection.RIGHT) == other;
    }

    public boolean isSmallerOrEquals(Direction other) {
        return ordinal() <= other.ordinal();
    }

    public Direction nextDirectionTo(RotationDirection side) {
        if (this == CENTER) {
            return this;
        }
        Direction[] cycle = { NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST };
        int position = -2;
        for (int i = 0; i < cycle.length; i++) {
            if (cycle[i] == this) {
                position = i;
            }
        }
        return cycle[(cycle.length + position + side.getValue()) % cycle.length];
    }

    public Direction opposite() {
        if (ordinal() <= 3) {
            return values()[smallOpposite(ordinal())];
        } else if (ordinal() <= 7) {
            return values()[bigOpposite(ordinal())];
        }

        return CENTER;
    }

    private int bigOpposite(int ordinal) {
        return 4 + smallOpposite(ordinal - 4);
    }

    private int smallOpposite(int ordinal) {
        return (ordinal + 2) % 4;
    }

    public static List<Direction> directNeighbors() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST);
    }

    public static List<Direction> indirectNeighbors() {
        return Arrays.asList(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);
    }

    public static List<Direction> neighbors() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);
    }

    public static List<Direction> tilePositions() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST, CENTER);
    }

    public static List<Direction> byRow() {
        return Arrays.asList(NORTH_WEST, NORTH, NORTH_EAST, WEST, CENTER, EAST, SOUTH_WEST, SOUTH, SOUTH_EAST);
    }

    public static Direction[][] values2D() {
        return new Direction[][] { { NORTH_WEST, WEST, SOUTH_WEST }, { NORTH, CENTER, SOUTH }, { NORTH_EAST, EAST, SOUTH_EAST } };
    }
}
