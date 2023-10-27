package game2048;

import java.util.Formatter;
import java.util.Observable;



public class Model extends Observable {
    private final Board _board;
    private int _score;
    private int _maxScore;
    private boolean _gameOver;
    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /**
     * Largest piece value.
     */
    public static final int MAX_PIECE = 2048;

    /**
     * A new 2048 game on a board of size SIZE with no pieces
     * and score 0.
     */
    public Model(int size) {
        _board = new Board(size);
        _score = _maxScore = 0;
        _gameOver = false;
    }

    /**
     * A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes.
     */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        _board = new Board(rawValues);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }

    ...

    /**
     * Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     * 0 <= COL < size(). Returns null if there is no tile there.
     * Used for testing. Should be deprecated and removed.
     */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /**
     * Return the number of squares on one side of the board.
     * Used for testing. Should be deprecated and removed.
     */
    public int size() {
        return _board.size();
    }

    /**
     * Return true iff the game is over (there are no moves, or
     * there is a tile with value 2048 on the board).
     */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /**
     * Return the current score.
     */
    public int score() {
        return _score;
    }

    /**
     * Return the current maximum game score (updated at end of game).
     */
    public int maxScore() {
        return _maxScore;
    }

    /**
     * Clear the board to empty and reset the score.
     */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /**
     * Allow initial game board to announce a hot start to the GUI.
     */
    public void hotStartAnnounce() {
        setChanged();
    }

    /**
     * Add TILE to the board. There must be no Tile currently at the
     * same position.
     */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /**
     * Tilt the board toward SIDE.
     * <p>
     * 1. If two Tile objects are adjacent in the direction of motion and have
     * the same value, they are merged into one Tile of twice the original
     * value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     * tilt. So each move, every tile will only ever be part of at most one
     * merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     * value, then the leading two tiles in the direction of motion merge,
     * and the trailing tile does not.
     */
    public void tilt(Side side) {
        _board.setViewingPerspective(side);
        moveNorth(_board);
        _board.setViewingPerspective(Side.NORTH);
        checkGameOver();

    }

    public void moveNorth(Board _board) {
        //check null tile
        for (int i = 0; i < _board.size(); i++) {
            for (int j = _board.size() - 1; j >= 0; j--) {
                Tile t = _board.tile(i, j);
                if (t == null) {
                    continue;
                }
                int destination = _board.size() - 1;
                while (destination > j) {
                    if (_board.tile(i, destination) == null) {
                        break;
                    }
                    destination--;
                }
                _board.move(i, destination, t);
            }
        }
        //merge
        for (int i = 0; i < _board.size(); i++) {
            for (int j = _board.size() - 1; j >= 0; j--) {
                Tile t = _board.tile(i, j);
                if (j - 1 < 0)
                {
                    break;
                }
                Tile below_Tile = _board.tile(i, j - 1);
                if (t == null || below_Tile == null) {
                    break;
                }
                if (below_Tile.value() == t.value()) {
                    _board.move(i, j, below_Tile);
                    _score += t.value() * 2;
                    for (int destination = j - 2; destination >= 0; destination--) {
                        Tile t0 = _board.tile(i, destination);
                        if (t0 == null) {
                            break;
                        }
                        _board.move(i, destination + 1, t0);
                    }
                }
            }
            setChanged();
        }
    }

    /**
     * Checks if the game is over and sets the gameOver variable
     * appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /**
     * Determine whether game is over.
     */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /**
     * Returns true if at least one space on the Board is empty.
     * Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null) {
                    continue;
                }
                if (b.tile(i, j).value() == Model.MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)) {
            return true;
        }
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                int up = i + 1;
                int left = j - 1;
                int down = i - 1;
                int right = j + 1;
                boolean check_up = (i + 1) > 0 && (i + 1) < b.size();
                boolean check_left = (j - 1) > 0 && (j - 1) < b.size();
                boolean check_down = (i - 1) > 0 && (i - 1) < b.size();
                boolean check_right = (j + 1) > 0 && (j + 1) < b.size();
                if (check_up && b.tile(i, j).value() == b.tile(up, j).value()) {
                    return true;
                }
                if (check_left && b.tile(i, j).value() == b.tile(i, left).value()) {
                    return true;
                }
                if (check_down && b.tile(i, j).value() == b.tile(down, j).value()) {
                    return true;
                }
                if (check_right && b.tile(i, j).value() == b.tile(i, right).value()) {
                    return true;
                }
            }
        }
        return false;
    }

    ...

}
