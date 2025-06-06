package game.board;

import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.enemies.Trap;
import game.tiles.units.player.Player;
import game.utils.Position;

import java.util.List;

public class ArrayGameBoard {

    private Tile[][] board;
    Player player;
    List<Enemy> enemies;
    public ArrayGameBoard(char[][] board, Player player) {
        this.board = new Tile[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Position position = new Position(i, j);
                if (board[i][j] == '.' )
                    this.board[i][j] = new Empty(position);
                else if (board[i][j] == '#')
                    this.board[i][j] = new Wall(position);
                else if (board[i][j] == '@')
                    this.board[i][j] = player;
                else if (board[i][j] == 's')
                    this.board[i][j] = new Monster("Lannister Solider", 's', position, 80, 8, 3, 3, 25);
                else if (board[i][j] == 'k')
                    this.board[i][j] = new Monster("Lannister Knight", 'k', position, 200, 14, 8, 4, 50);
                else if (board[i][j] == 'q')
                    this.board[i][j] = new Monster("Queen's Guard", 'q', position, 400, 20, 15, 5, 100);
                else if (board[i][j] == 'z')
                    this.board[i][j] = new Monster("Wright", 'z', position, 600, 30, 15, 3, 100);
                else if (board[i][j] == 'b')
                    this.board[i][j] = new Monster("Bear-Wright", 'b', position, 1000, 75, 30, 4, 250);
                else if (board[i][j] == 'g')
                    this.board[i][j] = new Monster("Giant-Wright", 'g', position, 1500, 100, 40, 5, 500);
                else if (board[i][j] == 'w')
                    this.board[i][j] = new Monster("White Walker", 'w', position, 2000, 150, 50, 6, 1000);
                else if (board[i][j] == 'M')
                    this.board[i][j] = new Monster("The Mountain", 'M', position, 1000, 60, 25, 6, 500);
                else if (board[i][j] == 'C')
                    this.board[i][j] = new Monster("Queen Cersei", 'C', position, 100, 10, 10, 1, 1000);
                else if (board[i][j] == 'K')
                    this.board[i][j] = new Monster("Night's King", 'K', position, 5000, 300, 150, 8, 5000);
                else if (board[i][j] == 'B')
                    this.board[i][j] = new Trap(" Bonus Trap", 'B', position, 1, 1, 1, 2, 250, 1, 5, false);
                else if (board[i][j] == 'Q')
                    this.board[i][j] = new Trap(" Queen's Trap", 'Q', position, 250, 50, 10, 2, 100, 3, 7, false);
                else
                    this.board[i][j] = new Trap(" Death Trap", 'D', position, 500, 100, 20, 2, 250, 1, 10, false);
            }
        }
    }

    public Tile[][] getBoard() {
        return board;
    }



}
