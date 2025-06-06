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

    public ArrayGameBoard(char[][] board, Player player) {
        enemies = new ArrayList<>();
        this.board = new Tile[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Position position = new Position(i, j);
                if (board[i][j] == '.')
                    this.board[i][j] = new Empty(position);
                else if (board[i][j] == '#')
                    this.board[i][j] = new Wall(position);
                else if (board[i][j] == '@')
                    this.board[i][j] = player;
                else if (board[i][j] == 's') {
                    Monster monster = new Monster("Lannister Solider", 's', position, 80, 8, 3, 3, 25);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'k') {
                    Monster monster = new Monster("Lannister Knight", 'k', position, 200, 14, 8, 4, 50);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'q') {
                    Monster monster = new Monster("Queen's Guard", 'q', position, 400, 20, 15, 5, 100);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'z') {
                    Monster monster = new Monster("Wright", 'z', position, 600, 30, 15, 3, 100);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'b'){
                    Monster monster = new Monster("Bear-Wright", 'b', position, 1000, 75, 30, 4, 250);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'g') {
                    Monster monster = new Monster("Giant-Wright", 'g', position, 1500, 100, 40, 5, 500);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'w') {
                    Monster monster = new Monster("White Walker", 'w', position, 2000, 150, 50, 6, 1000);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'M') {
                    Monster monster = new Monster("The Mountain", 'M', position, 1000, 60, 25, 6, 500);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'C') {
                    Monster monster = new Monster("Queen Cersei", 'C', position, 100, 10, 10, 1, 1000);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'K') {
                    Monster monster = new Monster("Night's King", 'K', position, 5000, 300, 150, 8, 5000);
                    this.board[i][j] = monster;
                    enemies.add(monster);
                }
                else if (board[i][j] == 'B') {
                    Trap trap = new Trap(" Bonus Trap", 'B', position, 1, 1, 1, 2, 250, 1, 5, false);
                    this.board[i][j] = trap;
                    enemies.add(trap);
                }
                else if (board[i][j] == 'Q'){
                    Trap trap = new Trap(" Queen's Trap", 'Q', position, 250, 50, 10, 2, 100, 3, 7, false);
                    this.board[i][j] = trap;
                    enemies.add(trap);
                }
                else{
                    Trap trap = new Trap(" Death Trap", 'D', position, 500, 100, 20, 2, 250, 1, 10, false);
                    this.board[i][j] = trap;
                    enemies.add(trap);
                }
            }
        }
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



}
