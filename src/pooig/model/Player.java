package pooig.model;

import pooig.model.tile.base.BaseTile;
import pooig.model.tile.cc.Meeple;
import pooig.model.tile.cc.TerrainType;
import pooig.util.Constants;
import pooig.util.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player<T extends BaseTile> {

    private final int id;
    private final Settings settings;
    private Map<TerrainType, Integer> terrainSpecificScores;
    private int score;
    private T tile;
    private final List<Meeple> placedMeeples;
    private int freeMeeples;
    private final boolean computerControlled;

    public Player(int id, Settings settings) {
        this.id = id;
        this.settings = settings;
        freeMeeples = Constants.MAXIMAL_MEEPLES;
        tile = null;
        placedMeeples = new ArrayList<>();
        computerControlled = (settings != null && settings.isPlayerComputerControlled(id));

        initializeScores();
    }

    public void addPoints(int amount, TerrainType scoreType) {
        terrainSpecificScores.put(scoreType, terrainSpecificScores.get(scoreType) + amount);
        score += amount;
    }

    public void addPoints(int amount) {
        score += amount;
    }

    public boolean addTile(T tile) {
        this.tile = tile;
        return true;
    }

    public boolean dropTile() {
        tile = null;
        return true;
    }

    public Color getColor() {
        return settings.getPlayerColor(id);
    }

    public int getFreeMeeples() {
        return freeMeeples;
    }

    public T getHandOfTile() {
        return tile;
    }

    public Meeple getMeeple() {
        if (hasFreeMeeple()) {
            freeMeeples--;
            Meeple meeple = new Meeple(this);
            placedMeeples.add(meeple);
            assert placedMeeples.size() <= Constants.MAXIMAL_MEEPLES;
            return meeple;
        }

        throw new IllegalStateException("No unused meeples are left.");
    }

    public String getName() {
        return settings.getPlayerName(id);
    }

    public int getID() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getTerrainScore(TerrainType scoreType) {
        if (terrainSpecificScores.containsKey(scoreType)) {
            return terrainSpecificScores.get(scoreType);
        }

        return -1;
    }

    public boolean hasFreeMeeple() {
        return freeMeeples > 0;
    }

    public boolean hasFullHand() {
        return !hasEmptyHand();
    }

    public boolean hasEmptyHand() {
        return tile == null;
    }

    public void returnMeeple(Meeple meeple) {
        assert placedMeeples.remove(meeple);
        freeMeeples++;
    }

    @Override
    public String toString() {
        return "Player[id: " + id + ", score: " + score + ", free meeples: " + freeMeeples + "]";
    }

    private void initializeScores() {
        score = 0;
        terrainSpecificScores = new HashMap<>();

        for (int i = 0; i < TerrainType.values().length - 1; i++) {
            terrainSpecificScores.put(TerrainType.values()[i], 0);
        }
    }

    public boolean isComputerControlled() {
        return computerControlled;
    }
}
