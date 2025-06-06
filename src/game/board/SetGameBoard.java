package game.board;

import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.player.Player;
import game.utils.Position;

public class SetGameBoard {

    private char[][] board;
    private ArrayGameBoard arrayGameBoard;

    public SetGameBoard(char[][] board, Player player) {
        this.board = board;
        arrayGameBoard = new ArrayGameBoard(board, player);
    }
}
