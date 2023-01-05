package pooig.model.tile.cc;

import java.util.Arrays;
import java.util.List;

public enum TerrainType {

    CASTLE,
    ROAD,
    MONASTERY,
    FIELDS,
    OTHER;

    public static List<TerrainType> basicTerrain() {
        return Arrays.asList(CASTLE, ROAD, MONASTERY, FIELDS);
    }

    public String toReadableString() {
        return toString().charAt(0) + toString().substring(1).toLowerCase();
    }
}
