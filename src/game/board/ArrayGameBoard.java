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

    public ArrayGameBoard(char[][] board, Player player) {
        this.board = new Tile[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Position position = new Position(i, j);
                this.board[i][j] = tileFactory.CreateTile(board[i][j], position, player);
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
