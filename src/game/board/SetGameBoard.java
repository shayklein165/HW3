package game.board;

import game.tiles.GameCharacter;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.player.Player;
import game.utils.Position;

public class SetGameBoard {

    private GameCharacter[][] board;

    public SetGameBoard(char[][] board, Player player) {
        this.board = new GameCharacter[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Position position = new Position(i, j);
                if (board[i][j] == '.' )
                    this.board[i][j] = new Empty(position);
                else if (board[i][j] == '#')
                    this.board[i][j] = new Wall(position);
                else if (board[i][j] == '@') {
                    this.board[i][j] = player;
                }
                /*need to  */
            }
        }
    }
}
