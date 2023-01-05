package pooig.model.tile.cc;

import pooig.model.Direction;
import pooig.model.Player;
import pooig.model.board.cc.CastleAndRoadPattern;
import pooig.model.board.cc.CellCc;
import pooig.model.board.cc.FieldsPattern;
import pooig.model.board.cc.GamePattern;
import pooig.model.tile.base.BaseTile;
import pooig.util.ImageUtil;
import pooig.util.TileUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TileCc extends BaseTile<TerrainType, TileCcTerrain> {

    private Meeple meeple;
    private final TileCcType type;
    private TileCcRotation rotation;
    private final int rotationLimit;

    public TileCc(TileCcType type) {
        super();

        this.type = type;
        terrain = initTerrain();
        rotation = TileCcRotation.UP;
        meeple = null;
        location = null;
        rotationLimit = TileUtil.getRotationLimit(type);
    }

    @Override
    protected TileCcTerrain initTerrain() {
        return new TileCcTerrain(type);
    }

    public void rotateLeft() {
        super.rotateLeft();
        rotation = rotation.rotate(RotationDirection.LEFT);
    }

    public void rotateRight() {
        super.rotateRight();
        rotation = rotation.rotate(RotationDirection.RIGHT);
    }

    public Meeple getMeeple() {
        return meeple;
    }

    public boolean hasMeeple() {
        return meeple != null;
    }

    public boolean hasMeeplePosition(Direction direction) {
        return terrain.getMeeplePositions().contains(direction);
    }

    public TileCcType getType() {
        return type;
    }

    public int getImageIndex() {
        return rotation.ordinal() % rotationLimit;
    }

    public boolean hasConnection(Direction from, Direction to) {
        return terrain.isConnected(from, to);
    }

    public void placeMeeple(Player player, Meeple meeple, Direction position) {
        if (this.meeple == null && allowsPlacingMeeple(position, player)) {
            this.meeple = meeple;
            meeple.setLocation((CellCc) location);
            meeple.setPosition(position);
        }
    }

    public void removeMeeple() {
        if (meeple == null) {
            throw new IllegalStateException("Meeple has already been removed.");
        }

        meeple.removePlacement();
        meeple = null;
    }

    public final boolean hasEmblem() {
        int castleSize = 0;
        for (Direction direction : Direction.values()) {
            if (terrain.at(direction).equals(TerrainType.CASTLE)) {
                castleSize++;
            }
        }

        return castleSize >= 6;
    }

    public boolean allowsPlacingMeeple(Direction position, Player player) {
        TerrainType terrain = getTerrainType(position);
        boolean placeable = false;
        if (isPlaced()) {
            if (terrain == TerrainType.OTHER) {
                placeable = false;
            } else if (terrain == TerrainType.MONASTERY) {
                placeable = true;
            } else {
                GamePattern pattern;
                if (terrain == TerrainType.FIELDS) {
                    pattern = new FieldsPattern((CellCc) getLocation(), position);
                } else {
                    pattern = new CastleAndRoadPattern((CellCc) getLocation(), position, terrain);
                }
                if (pattern.isNotOccupied() || (pattern.isOccupiedBy(player))) {
                    placeable = true;
                }
                pattern.removeTileTags();
            }
        }

        return placeable;
    }

    public ImageIcon getImageIcon(int size) {
        if (hasEmblem()) {
            return ImageUtil.getTileImageIcon(this, size);
        }

        String path = TileUtil.getTilePath(this);
        ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader().getResource(path));
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public BufferedImage getBufferedImage() {
        try {
            return ImageIO.read(this.getClass().getClassLoader().getResource(TileUtil.getTilePath(this)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
