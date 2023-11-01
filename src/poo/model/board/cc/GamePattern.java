package poo.model.board.cc;

import poo.model.Direction;
import poo.model.Player;
import poo.model.tile.cc.Meeple;
import poo.model.tile.cc.TerrainType;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class GamePattern {

    private boolean disbursed;
    protected boolean complete;
    private final Map<Player, Integer> involvedPlayers;
    private final List<Meeple> meepleList;
    protected final TerrainType patternType;
    protected int scorePerCell;
    protected List<CellCc> containedCell;

    protected GamePattern(TerrainType patternType, int scorePerCell) {
        this.patternType = patternType;
        this.scorePerCell = scorePerCell;
        containedCell = new LinkedList<>();
        meepleList = new LinkedList<>();
        involvedPlayers = new HashMap<>();
    }

    public void disburse() {
        if (complete) {
            distributePatternScore();
            meepleList.forEach(it -> it.getLocation().getTile().removeMeeple());
            involvedPlayers.clear();
        }
    }

    public void forceDisburse() {
        if (!complete) {
            distributePatternScore();
        }
    }

    public List<Player> getDominantPlayers() {
        if (involvedPlayers.isEmpty()) {
            return Collections.emptyList();
        }
        int maximum = Collections.max(involvedPlayers.values());
        return involvedPlayers.keySet().stream().filter(player -> involvedPlayers.get(player) == maximum).collect(toList());
    }

    public List<Meeple> getMeepleList() {
        return meepleList;
    }

    public int getPatternScore() {
        return containedCell.size() * scorePerCell;
    }

    public TerrainType getType() {
        return patternType;
    }

    public int getScoreFor(Player player) {
        List<Player> dominantPlayers = getDominantPlayers();
        if (dominantPlayers.contains(player)) {
            return divideScore(getPatternScore(), dominantPlayers);
        }
        return 0;
    }

    public int getSize() {
        return containedCell.size();
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isNotOccupied() {
        return involvedPlayers.isEmpty();
    }

    public boolean isOccupiedBy(Player player) {
        return involvedPlayers.containsKey(player);
    }

    public void removeOwnTags() {
        containedCell.forEach(it -> it.removeTagsFrom(this));
    }

    public void removeTileTags() {
        containedCell.forEach(CellCc::removeTags);
    }

    @Override
    public String toString() {
        return "GridPattern[type: " + patternType + ", size: " + getSize() + ", complete: " + complete +
                ", disbursed: " + disbursed + ", meeples: " + meepleList + ", on: " +
                containedCell.stream().map(it -> "(" + it.getX() + "|" + it.getY() + ")").collect(toList());
    }

    private void distributePatternScore() {
        if (!disbursed && !involvedPlayers.isEmpty()) {
            List<Player> dominantPlayers = getDominantPlayers();
            int stake = getPatternScore();
            for (Player player : dominantPlayers) {
                player.addPoints(stake, patternType);
            }

            disbursed = true;
        }
    }

    private void addMeepleFrom(CellCc cell) {
        assert !disbursed;
        Meeple meeple = cell.getTile().getMeeple();
        if (!meepleList.contains(meeple) && isPartOfPattern(cell, meeple.getPosition())) {
            Player player = meeple.getOwner();
            if (involvedPlayers.containsKey(player)) {
                involvedPlayers.put(player, involvedPlayers.get(player) + 1);
            } else {
                involvedPlayers.put(player, 1);
            }
            meepleList.add(meeple);
        }
    }

    private int divideScore(int score, List<Player> dominantPlayers) {
        return (int) Math.ceil(score / (double) dominantPlayers.size());
    }

    private boolean isPartOfPattern(CellCc cell, Direction position) {
        boolean onCorrectTerrain = cell.getTile().getTerrainType(position) == patternType;
        boolean onPattern = cell.isIndirectlyTaggedBy(position, this) || patternType == TerrainType.MONASTERY;
        return onCorrectTerrain && onPattern;
    }

    protected void add(CellCc cell) {
        containedCell.add(cell);
        if (cell.getTile().hasMeeple()) {
            addMeepleFrom(cell);
        }
    }

    protected void checkArgs(CellCc cell, Direction direction) {
        if (cell == null || direction == null) {
            System.out.println("Arguments can't be null");
        }
    }
}
