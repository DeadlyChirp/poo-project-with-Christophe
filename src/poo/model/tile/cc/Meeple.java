package poo.model.tile.cc;


import poo.model.Direction;
import poo.model.Player;
import poo.model.board.cc.CellCc;

public class Meeple {

    private CellCc location;
    private final Player owner;
    private Direction position;

    public Meeple(Player owner) {
        this.owner = owner;
    }

    public CellCc getLocation() {
        return location;
    }

    public Player getOwner() {
        return owner;
    }

    public Direction getPosition() {
        return position;
    }

    public boolean isPlaced() {
        return location != null;
    }

    public void removePlacement() {
        if (location != null) {
            owner.returnMeeple(this);
            location = null;
        }
    }

    public void setLocation(CellCc placementLocation) {
        this.location = placementLocation;
    }

    public void setPosition(Direction placementPosition) {
        this.position = placementPosition;
    }

    @Override
    public String toString() {
        String placement = "";
        String type = "Unplaced ";
        if (isPlaced()) {
            type = "";
            placement = "placed on (" + location.getX() + "|" + location.getY() + ")" + " at " + position + " ";
        }
        return type + getClass().getSimpleName() + " by Player " + owner.getID() + " " + placement;
    }
}
