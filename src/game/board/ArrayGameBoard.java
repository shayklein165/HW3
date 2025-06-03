package game.board;

import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.player.Player;
import game.utils.Position;

public class ArrayGameBoard {
    private Tile[][] board;

    public ArrayGameBoard(char[][] board) {

    }

    public Tile[][] getBoard() {
        return board;
    }



}
