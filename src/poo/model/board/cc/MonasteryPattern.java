package poo.model.board.cc;

import poo.model.Direction;

import java.util.List;

import static poo.model.Direction.CENTER;
import static poo.model.tile.cc.TerrainType.MONASTERY;

public class MonasteryPattern extends GamePattern {

    public MonasteryPattern(CellCc spot) {
        super(MONASTERY, 1);
        buildPattern(spot);
    }

    private void buildPattern(CellCc monasterySpot) {
        List<CellCc> neighbors = monasterySpot.getBoard().getNeighbors(monasterySpot, Direction.neighbors());
        add(monasterySpot);
        monasterySpot.setTag(CENTER, this);
        containedCell.addAll(neighbors);
        if (neighbors.size() == Direction.neighbors().size()) {
            complete = true;
        }
    }
}
