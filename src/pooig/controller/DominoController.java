package pooig.controller;

import pooig.model.Direction;
import pooig.model.Player;
import pooig.model.board.dom.BoardDo;
import pooig.model.board.dom.CellDo;
import pooig.model.tile.dom.TileDo;
import pooig.model.tile.dom.TileDoStack;
import pooig.util.GameMessage;
import pooig.view.DominoFrame;
import pooig.view.dialog.SettingsDialog;
import pooig.view.dialog.TileDoDialog;

import java.util.Arrays;

public class DominoController extends BaseController {

    private DominoFrame mainFrame;
    private TileDoDialog tileDialog;
    private SettingsDialog settingsDialog;
    private BoardDo board;
    private TileDoStack tileStack;
    private Player<TileDo>[] players;
    private int currentPlayerIndex;

    public DominoController() {
        super();
    }

    @Override
    public void start() {
        startNewGame();
    }

    @Override
    public void placeTile(int x, int y) {
        if (!tileDialog.isVisible()) {
            return;
        }

        TileDo tile = tileDialog.getSelectedTile();
        if (board.place(tile, x, y)) {
            getCurrentPlayer().dropTile();
            mainFrame.placeTile(tile, x, y);
            tileDialog.exit();
            processScores(tile, x, y);

            nextTurn();
        }
    }

    @Override
    public void skipTile() {
        getCurrentPlayer().dropTile();
        tileDialog.exit();
        currentPlayerIndex--;
        nextTurn();
    }

    private void startNewGame() {
        mainFrame = new DominoFrame(this);
        tileDialog = new TileDoDialog(this, mainFrame);
        settingsDialog = new SettingsDialog(settings, mainFrame);

        settingsDialog.setVisible(true);

        mainFrame.showFrame();
        board = new BoardDo(settings.getGridWidth(), settings.getGridHeight());
        CellDo firstCell = board.getFirstCell();
        mainFrame.placeTile(firstCell.getTile(), firstCell.getX(), firstCell.getY());

        tileStack = new TileDoStack();

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

        Player<TileDo> player = getCurrentPlayer();
        if (player.hasEmptyHand() && !tileStack.isEmpty()) {
            player.addTile(tileStack.getTile());
        }

        mainFrame.getScoreBar().updateStackSize(tileStack.getSize());
        tileDialog.setTile(player);
    }

    private boolean isOver() {
        return board.isFull() || tileStack.isEmpty() && Arrays.stream(players).allMatch(Player::hasEmptyHand);
    }

    private Player<TileDo> getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    private void gameOver() {
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

    private void processScores(TileDo tile, int x, int y) {
        int count = 0, point = 0;
        for (Direction direction : Direction.directNeighbors()) {
            CellDo neighbor = board.getNeighbor(board.getCell(x, y), direction);
            if (neighbor != null && neighbor.isFull()) {
                count++;
                if (!tile.canConnectTo(direction, neighbor.getTile())) {
                    break;
                }

                for (Integer integer : tile.getTerrainType(direction)) {
                    point += integer;
                }
            }
        }

        if (count > 0) {
            getCurrentPlayer().addPoints(point);
            updateScores();
        }
    }

    private void cleanView() {
        if (tileDialog != null) {
            tileDialog.dispose();
        }

        if (mainFrame != null) {
            mainFrame.dispose();
        }

        if (settingsDialog != null) {
            settingsDialog.dispose();
        }
    }
}
