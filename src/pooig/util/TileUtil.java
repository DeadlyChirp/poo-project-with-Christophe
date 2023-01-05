package pooig.util;

import pooig.model.tile.cc.TerrainType;
import pooig.model.tile.cc.TileCc;
import pooig.model.tile.cc.TileCcRotation;
import pooig.model.tile.cc.TileCcType;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class TileUtil {

    private final static Map<TileCcType, Integer> rotations = new HashMap<>();

    private TileUtil() {}

    public static String getMeeplePath(TerrainType type, boolean isTemplate) {
        if (type == TerrainType.OTHER) {
            return Constants.MEEPLE_PATH + type.toString().toLowerCase() + (isTemplate ? Constants.TEMPLATE : Constants.EMPTY) + Constants.PNG;
        } else {
            return Constants.MEEPLE_PATH.substring(0, Constants.MEEPLE_PATH.length() - 1) + (isTemplate ? Constants.TEMPLATE : Constants.EMPTY) + Constants.PNG;
        }
    }

    public static String getTilePath(TileCc tile) {
        return Constants.TILE_FOLDER_PATH + tile.getType().name() + tile.getImageIndex() + Constants.TILE_FILE_TYPE;
    }

    public static int getRotationLimit(TileCcType type) {
        return rotations.computeIfAbsent(type, TileUtil::determineRotationLimit);
    }

    private static int determineRotationLimit(TileCcType type) {
        int rotations = 0;
        for (int rotation = 0; rotation < TileCcRotation.values().length; rotation++) {
            String path = Constants.TILE_FOLDER_PATH + type.name() + rotation + Constants.TILE_FILE_TYPE;
            InputStream file = TileUtil.class.getClassLoader().getResourceAsStream(path);
            if (file != null) {
                rotations++;
            }
        }

        if (rotations == 0) {
            throw new IllegalStateException(type + " tile needs at least one image file!");
        }

        return rotations;
    }
}
