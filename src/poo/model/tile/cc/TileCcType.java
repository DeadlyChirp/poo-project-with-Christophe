package poo.model.tile.cc;


import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static poo.model.tile.cc.TerrainType.*;

public enum TileCcType {

    Null(0, OTHER, OTHER, OTHER, OTHER, OTHER, OTHER, OTHER, OTHER, OTHER),
    CastleCenter(1, CASTLE, CASTLE, CASTLE, CASTLE, CASTLE, CASTLE, CASTLE, CASTLE, CASTLE),
    CastleCenterEntry(3, CASTLE, CASTLE, ROAD, CASTLE, CASTLE, FIELDS, FIELDS, CASTLE, CASTLE),
    CastleCenterSide(4, CASTLE, CASTLE, FIELDS, CASTLE, CASTLE, FIELDS, FIELDS, CASTLE, CASTLE),
    CastleEdge(5, CASTLE, CASTLE, FIELDS, FIELDS, CASTLE, FIELDS, FIELDS, FIELDS, FIELDS),
    CastleEdgeRoad(5, CASTLE, CASTLE, ROAD, ROAD, CASTLE, FIELDS, FIELDS, FIELDS, ROAD),
    CastleSides(3, CASTLE, FIELDS, CASTLE, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS),
    CastleSidesEdge(2, CASTLE, FIELDS, FIELDS, CASTLE, FIELDS, FIELDS, FIELDS, OTHER, FIELDS),
    CastleTube(3, FIELDS, CASTLE, FIELDS, CASTLE, FIELDS, FIELDS, FIELDS, FIELDS, CASTLE),
    CastleWall(5, CASTLE, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS),
    CastleWallCurveLeft(3, CASTLE, FIELDS, ROAD, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, ROAD),
    CastleWallCurveRight(3, CASTLE, ROAD, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, ROAD),
    CastleWallJunction(3, CASTLE, ROAD, ROAD, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, OTHER),
    CastleWallRoad(3, CASTLE, ROAD, FIELDS, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, ROAD),
    Monastery(4, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, MONASTERY),
    MonasteryRoad(2, FIELDS, FIELDS, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, MONASTERY),
    Road(8, ROAD, FIELDS, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, ROAD),
    RoadCurve(9, FIELDS, FIELDS, ROAD, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, ROAD),
    RoadJunctionLarge(1, ROAD, ROAD, ROAD, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, OTHER),
    RoadJunctionSmall(4, FIELDS, ROAD, ROAD, ROAD, FIELDS, FIELDS, FIELDS, FIELDS, OTHER);

    private final TerrainType[] terrain;
    private final int amount;

    TileCcType(int amount, TerrainType... terrain) {
        this.amount = amount;
        this.terrain = terrain;
    }

    public int getAmount() {
        return amount;
    }

    public TerrainType[] getTerrain() {
        return terrain;
    }

    public static List<TileCcType> validTiles() {
        return stream(values()).filter(it -> it != Null).collect(toList());
    }

}
