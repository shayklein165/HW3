package game.board;

import game.callbacks.PositionChanged;
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

public class ArrayGameBoard implements PositionChanged {

    private Tile[][] board;
    private Player player;
    private List<Enemy> enemies;
    private TileFactory tileFactory = new TileFactory();
    private Position initialPlayerPosition;

    public ArrayGameBoard(char[][] charBoard, Player player) {
        this.board = new Tile[charBoard.length][charBoard[0].length];
        this.player = player;
        player.InitPosition();
        for (int i = 0; i < charBoard.length; i++) {
            for (int j = 0; j < charBoard[0].length; j++) {
                Position position = new Position(i, j);
                if (charBoard[i][j] == '@') {
                    this.player.setListener(this);
                    this.player.setPosition(new Position(i, j));
                    initialPlayerPosition = new Position(i, j);
                }
                else {
                    this.board[i][j] = tileFactory.CreateTile(charBoard[i][j], position, player);
                    this.board[i][j].setListener(this);
                    this.board[i][j].setPosition(position);
                }
            }
        }
        enemies = tileFactory.getEnemies();
    }

    public Position getInitialPlayerPosition() {
        return initialPlayerPosition;
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

    public void AddEnemy(Enemy enemy){ enemies.add(enemy);}

    public void setTile(Tile tile, Position position){
        this.board[position.getX()][position.getY()] = tile;
    }

    public Tile getTile(Position position){
        return this.board[position.getX()][position.getY()];
    }

    public void KillPlayer() {
        board[player.getPosition().getX()][player.getPosition().getY()].setTile('X');
    }

    public void setPlayer(Player player){this.player = player;}

    @Override
    public void call(Tile tile, Position oldPos, Position newPos) {
        if(newPos.getX() < board.length && newPos.getY() < board[0].length){
            board[newPos.getX()][newPos.getY()] = tile;
        }
    }
}
