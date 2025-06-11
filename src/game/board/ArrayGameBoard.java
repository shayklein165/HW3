package game.board;

import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.enemies.Trap;
import game.tiles.units.player.Player;
import game.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class ArrayGameBoard {

    private Tile[][] board;
    Player player;
    List<Enemy> enemies;
    TileFactory tileFactory = new TileFactory();

    public ArrayGameBoard(char[][] charBoard, Player player) {
        this.board = new Tile[charBoard.length][charBoard[0].length];
        this.player = player;
        for (int i = 0; i < charBoard.length; i++) {
            for (int j = 0; j < charBoard[0].length; j++) {
                Position position = new Position(i, j);
                this.board[i][j] = tileFactory.CreateTile(charBoard[i][j], position, player);

                if (charBoard[i][j] == '@')
                    this.player.setPosition(new Position(i, j));
            }
        }
        enemies = tileFactory.getEnemies();
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public boolean RemoveEnemy(Enemy enemy) {
        return enemies.remove(enemy);
    }

    public void setTile(Tile tile, Position position){
        this.board[position.getX()][position.getY()] = tile;
    }

    public void KillPlayer() {
        board[player.getPosition().getX()][player.getPosition().getY()].setTile('X');
    }
}
