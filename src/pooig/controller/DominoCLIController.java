package pooig.controller;

import pooig.model.Direction;
import pooig.model.Player;
import pooig.model.board.dom.BoardDo;
import pooig.model.board.dom.CellDo;
import pooig.model.tile.dom.TileDo;
import pooig.model.tile.dom.TileDoStack;
import pooig.util.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DominoCLIController extends BaseController {

    private final Scanner scanner;
    private BoardDo board;
    private TileDoStack tileStack;
    private TileDo currentTile;
    private Player<TileDo>[] players;
    private char[][] characterBoard;
    private char[][] characterTile;
    private int currentPlayerIndex, row, col;

    public DominoCLIController() {
        super();

        scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        settings();
        settings.setGridWidth(8);
        settings.setGridHeight(4);
        startNewGame();
    }

    @Override
    public void placeTile(int x, int y) {}

    @Override
    public void skipTile() {
        getCurrentPlayer().dropTile();
        currentPlayerIndex--;
        nextTurn();
    }

    private void startNewGame() {
        row = settings.getGridHeight() * 6 + 1;
        col = settings.getGridWidth() * 8 + 1;

        board = new BoardDo(settings.getGridWidth(), settings.getGridHeight());
        characterBoard = new char[row][col];
        characterTile = new char[7][9];
        initCharBoard();

        CellDo firstCell = board.getFirstCell();
        placeToCharBoard(firstCell.getTile(), firstCell.getY(), firstCell.getX());

        tileStack = new TileDoStack();

        players = new Player[settings.getNumberOfPlayers()];
        for (int i = 0; i < settings.getNumberOfPlayers(); i++) {
            players[i] = new Player<>(i, settings);
        }

        currentPlayerIndex = -1;
        nextTurn();
    }

    private void nextTurn() {
        if (isOver()) {
            gameOver();
            return;
        }

        currentPlayerIndex = ++currentPlayerIndex % players.length;
        Player<TileDo> player = getCurrentPlayer();
        if (player.hasEmptyHand() && !tileStack.isEmpty()) {
            player.addTile(tileStack.getTile());
        }

        currentTile = player.getHandOfTile();
        setCharTile(currentTile);
        printMenu();
    }

    private void printMenu() {
        printScore();
        printBoard();
        System.out.println("Current tile: ");
        printTile();
        System.out.println("--------- " + getCurrentPlayer().getName() + "'s turn ---------");
        System.out.print("Please select skip/rotate/place (s/r/p row col): ");
        String line;
        while (true) {
            try {
                line = scanner.nextLine();
                String[] strings = line.split(" ");
                if (strings[0].equals("s")) {
                    skipTile();
                    break;
                } else if (strings[0].equals("r")) {
                    currentTile.rotateRight();
                    setCharTile(currentTile);
                    printBoard();
                    System.out.println("Current tile: ");
                    printTile();
                    System.out.println("--------- " + getCurrentPlayer().getName() + "'s turn ---------");
                    System.out.print("Please select skip/rotate/place (s/r/p row col): ");
                } else if (strings[0].equals("p")) {
                    int row = Integer.parseInt(strings[1]);
                    int col = Integer.parseInt(strings[2]);

                    if (board.place(currentTile, col, row)) {
                        getCurrentPlayer().dropTile();
                        placeToCharBoard(currentTile, row, col);
                        updateScore(currentTile, col, row);

                        nextTurn();
                    } else {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again: ");
            }
        }
    }

    private void initCharBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j <  col; j++) {
                characterBoard[i][j] = ' ';
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i % 6 == 0) {
                    if (j % 8 == 0) {
                        characterBoard[i][j] = '+';
                    } else {
                        characterBoard[i][j] = '-';
                    }
                } else if (j % 8 == 0) {
                    characterBoard[i][j] = '|';
                }
            }
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <  9; j++) {
                characterTile[i][j] = ' ';
            }
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <  9; j++) {
                if (i % 6 == 0) {
                    if (j % 8 == 0) {
                        characterTile[i][j] = '+';
                    } else {
                        characterTile[i][j] = '-';
                    }
                } else if (j % 8 == 0) {
                    characterTile[i][j] = '|';
                }
            }
        }
    }

    private void placeToCharBoard(TileDo tile, int r, int c) {
        List<Integer> list = tile.getTerrain().at(Direction.EAST);
        for (int i = 0; i < 3; i++) {
            characterBoard[r * 6 + 2 + i][c * 8 + 7] = String.valueOf(list.get(i)).charAt(0);
        }

        list = tile.getTerrain().at(Direction.WEST);
        for (int i = 0; i < 3; i++) {
            characterBoard[r * 6 + 2 + i][c * 8 + 1] = String.valueOf(list.get(i)).charAt(0);
        }

        list = tile.getTerrain().at(Direction.SOUTH);
        for (int i = 0; i < 3; i++) {
            characterBoard[r * 6 + 5][c * 8 + i * 2 + 2] = String.valueOf(list.get(i)).charAt(0);
        }

        list = tile.getTerrain().at(Direction.NORTH);
        for (int i = 0; i < 3; i++) {
            characterBoard[r * 6 + 1][c * 8 + i * 2 + 2] = String.valueOf(list.get(i)).charAt(0);
        }
    }

    private void setCharTile(TileDo tile) {
        List<Integer> list = tile.getTerrain().at(Direction.EAST);
        for (int i = 0; i < 3; i++) {
            characterTile[2 + i][7] = String.valueOf(list.get(i)).charAt(0);
        }

        list = tile.getTerrain().at(Direction.WEST);
        for (int i = 0; i < 3; i++) {
            characterTile[2 + i][1] = String.valueOf(list.get(i)).charAt(0);
        }

        list = tile.getTerrain().at(Direction.SOUTH);
        for (int i = 0; i < 3; i++) {
            characterTile[5][i * 2 + 2] = String.valueOf(list.get(i)).charAt(0);
        }

        list = tile.getTerrain().at(Direction.NORTH);
        for (int i = 0; i < 3; i++) {
            characterTile[1][i * 2 + 2] = String.valueOf(list.get(i)).charAt(0);
        }
    }

    private void printBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j <  col; j++) {
                System.out.print(characterBoard[i][j]);
            }

            System.out.println();
        }
    }

    private void printTile() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <  9; j++) {
                System.out.print(characterTile[i][j]);
            }
            System.out.println();
        }
    }

    private void gameOver() {
        System.out.println("Game Over!!!");
    }

    private void settings() {
        System.out.print("Enter number of players(2-5): ");
        String line;
        while (true) {
            try {
                line = scanner.nextLine();
                int nPlayers = Integer.parseInt(line);
                if (nPlayers < 2 || nPlayers > Constants.MAXIMAL_PLAYERS) {
                    throw new IllegalArgumentException();
                }
                settings.setNumberOfPlayers(nPlayers);
                break;
            } catch (Exception e) {
                System.out.print("Invalid input. Please try again: ");
            }
        }

        System.out.print("Enter board size(width, height): ");
        while (true) {
            try {
                line = scanner.nextLine();
                String[] strings = line.split(",");
                int width = Integer.parseInt(strings[0].trim()), height = Integer.parseInt(strings[1].trim());
                if (width < 1 || height < 1) {
                    throw new IllegalArgumentException();
                }
                settings.setGridWidth(width);
                settings.setGridHeight(height);
                break;
            } catch (Exception e) {
                System.out.print("Invalid input. Please try again: ");
            }
        }
    }

    private boolean isOver() {
        return board.isFull() || tileStack.isEmpty() && Arrays.stream(players).allMatch(Player::hasEmptyHand);
    }

    private Player<TileDo> getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    private void printScore() {
        String score = "--> Score: ";
        for (Player<TileDo> player : players) {
            score += (player.getName() + " - " + player.getScore() + ", ");
        }

        score += ("Stack size = " + tileStack.getSize());

        System.out.println(score);
    }

    private void updateScore(TileDo tile, int x, int y) {
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
        }
    }
}
