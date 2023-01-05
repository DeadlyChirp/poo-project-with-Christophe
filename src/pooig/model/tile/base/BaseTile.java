package pooig.model.tile.base;

import pooig.model.Direction;
import pooig.model.board.BaseCell;

public abstract class BaseTile <T, V extends BaseTerrain<T>> {

    protected BaseCell location;
    protected V terrain;

    public BaseTile() {
        location = null;
    }

    protected abstract V initTerrain();

    public BaseCell getLocation() {
        return location;
    }

    public void setLocation(BaseCell location) {
        this.location = location;
    }

    public boolean isPlaced() {
        return location != null;
    }

    public V getTerrain() {
        return terrain;
    }

    public T getTerrainType(Direction direction) {
        return terrain.at(direction);
    }

    public void rotateLeft() {
        terrain.rotateLeft();
    }

    public void rotateRight() {
        terrain.rotateRight();
    }

    public boolean canConnectTo(Direction direction, BaseTile<T, V> other) {
        return getTerrainType(direction).equals(other.getTerrainType(direction.opposite()));
    }
}
