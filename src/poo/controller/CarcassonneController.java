package poo.controller;

import poo.model.Direction;
import poo.model.Player;
import poo.model.board.cc.BoardCc;
import poo.model.board.cc.CellCc;
import poo.model.board.cc.GamePattern;
import poo.model.tile.cc.Meeple;
import poo.model.tile.cc.TileCc;
import poo.model.tile.cc.TileCcStack;
import poo.util.GameMessage;
import poo.view.CarcassonneFrame;
import poo.view.dialog.MeepleDialog;
import poo.view.dialog.SettingsDialog;
import poo.view.dialog.TileDialog;

import java.util.Arrays;
import java.util.List;

public class CarcassonneController extends BaseController {

    private CarcassonneFrame mainFrame;
    private TileDialog tileDialog;
    private MeepleDialog meepleDialog;
    private SettingsDialog settingsDialog;
    private BoardCc board;
    private TileCcStack tileStack;
    private Player<TileCc>[] players;
    private int currentPlayerIndex;

    public CarcassonneController() {
        super();
    }

    public void start() {
        startNewGame();
    }

    @Override
    public void placeTile(int x, int y) {
        if (!tileDialog.isVisible()) {
            return;
        }

        TileCc tile = tileDialog.getSelectedTile();
        if (board.place(tile, x, y)) {
            getCurrentPlayer().dropTile();
            mainFrame.placeTile(tile, x, y);

            CellCc cell = board.getCell(x, y);
            highlightNeighbors(cell);

            tileDialog.exit();
            placingMeeple();
        }
    }

    @Override
    public void skipTile() {
        TileCc tile = tileDialog.getSelectedTile();
        tileStack.putBack(tile);
        mainFrame.getScoreBar().updateStackSize(tileStack.getSize());
        getCurrentPlayer().dropTile();
        tileDialog.exit();
        nextTurn();
    }

    public void placingMeeple() {
        Player player = getCurrentPlayer();
        TileCc selectedTile = tileDialog.getSelectedTile();

        if (player.hasFreeMeeple()) {
            mainFrame.setMeeplePreview(selectedTile, player);
            meepleDialog.setTile(selectedTile, player);
        } else {
            nextTurn();
        }
    }

    public void placeMeeple(Direction position) {
        Player player = getCurrentPlayer();
        TileCc tile = tileDialog.getSelectedTile();
        mainFrame.resetMeeplePreview(tile);

        if (player.hasFreeMeeple() && tile.allowsPlacingMeeple(position, player)) {
            tile.placeMeeple(player, player.getMeeple(), position);
            mainFrame.setMeeple(tile, position, player);

            updateScores();
            runGamePatterns();

            meepleDialog.exit();
            nextTurn();
        }
    }

    public void skipPlacingMeeple() {
        TileCc tile = tileDialog.getSelectedTile();
        mainFrame.resetMeeplePreview(tile);

        runGamePatterns();

        meepleDialog.exit();
        nextTurn();
    }

    private void startNewGame() {
        mainFrame = new CarcassonneFrame(this);
        tileDialog = new TileDialog(this, mainFrame);
        meepleDialog = new MeepleDialog(this, mainFrame);
        settingsDialog = new SettingsDialog(settings, mainFrame);

        settingsDialog.setVisible(true);

        mainFrame.showFrame();
        board = new BoardCc(settings.getGridWidth(), settings.getGridHeight());
        CellCc firstCell = board.getFirstCell();
        mainFrame.placeTile(firstCell.getTile(), firstCell.getX(), firstCell.getY());
        highlightNeighbors(firstCell);

        tileStack = new TileCcStack();

        players = new Player[settings.getNumberOfPlayers()];
        for (int i = 0; i < settings.getNumberOfPlayers(); i++) {
            players[i] = new Player<>(i, settings);
            mainFrame.getScoreBar().update(players[i]);
        }
        mainFrame.getScoreBar().updateStackSize(tileStack.getSize());

        currentPlayerIndex = -1;
        nextTurn();
    }

    private void nextTurn() {
        if (isOver()) {
            gameOver();
            return;
        }

        currentPlayerIndex = ++currentPlayerIndex % players.length;
        mainFrame.setCurrentPlayer(players[currentPlayerIndex]);

        Player<TileCc> player = getCurrentPlayer();
        if (player.hasEmptyHand() && !tileStack.isEmpty()) {
            player.addTile(tileStack.getTile());
        }

        mainFrame.getScoreBar().updateStackSize(tileStack.getSize());
        tileDialog.setTile(player);
    }

    private boolean isOver() {
        return board.isFull() || tileStack.isEmpty() && Arrays.stream(players).allMatch(Player::hasEmptyHand);
    }

    private Player<TileCc> getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    private void highlightNeighbors(CellCc cell) {
        List<CellCc> neighbors = board.getAllNeighbors(cell, Direction.directNeighbors());
        for (CellCc neighbor : neighbors) {
            if (neighbor != null && neighbor.isEmpty()) {
                mainFrame.highlightTile(neighbor.getX(), neighbor.getY());
            }
        }
    }

    private void runGamePatterns() {
        TileCc tile = tileDialog.getSelectedTile();
        for (GamePattern pattern : board.getPatterns((CellCc) tile.getLocation())) {
            if (pattern.isComplete()) {
                for (Meeple meeple : pattern.getMeepleList()) {
                    CellCc cell = meeple.getLocation();
                    mainFrame.removeMeeple(cell.getX(), cell.getY());
                }

                pattern.disburse();
                updateScores();
            }
        }
    }

    private void gameOver() {
        for (GamePattern pattern : board.getAllPatterns()) {
            pattern.forceDisburse();
        }
        updateScores();

        GameMessage.showMessage("Game Over!!!");

        cleanView();
        startNewGame();
    }

    private void updateScores() {
        for (int playerNumber = 0; playerNumber < settings.getNumberOfPlayers(); playerNumber++) {
            Player player = players[playerNumber];
            mainFrame.getScoreBar().update(player);
        }
    }

    private void cleanView() {
        if (tileDialog != null) {
            tileDialog.dispose();
        }

        if (meepleDialog != null) {
            meepleDialog.dispose();
        }

        if (mainFrame != null) {
            mainFrame.dispose();
        }

        if (settingsDialog != null) {
            settingsDialog.dispose();
        }
    }
}
