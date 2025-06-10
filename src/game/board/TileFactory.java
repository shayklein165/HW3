package game.board;

import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.enemies.Trap;
import game.tiles.units.player.Mage;
import game.tiles.units.player.Player;
import game.tiles.units.player.Rogue;
import game.tiles.units.player.Warrior;
import game.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class TileFactory {
    private Map<Character, Function<Position, Enemy>> enemylist;
    private Map<String, Function<Position, Player>> players;
    private List<Enemy> enemies;

    public TileFactory() {
        enemylist = InitEnemyList();
        players = InitPlayer();
        enemies = new ArrayList<>();
    }

    private Map<Character, Function<Position, Enemy>> InitEnemyList(){
        Map<Character, Function<Position, Enemy>> map = new HashMap<>();
        map.put('s', (Position position)-> new Monster("Lannister Solider", 's', position, 80, 8, 3, 3, 25));
        map.put('k', (Position position)-> new Monster("Lannister Knight", 'k', position, 200, 14, 8, 4, 50));
        map.put('q', (Position position)-> new Monster("Queen's Guard", 'q', position, 400, 20, 15, 5, 100));
        map.put('z', (Position position)-> new Monster("Wright", 'z', position, 600, 30, 15, 3, 100));
        map.put('b', (Position position)-> new Monster("Bear-Wright", 'b', position, 1000, 75, 30, 4, 250));
        map.put('g', (Position position)-> new Monster("Giant-Wright", 'g', position, 1500, 100, 40, 5, 500));
        map.put('w', (Position position)-> new Monster("White Walker", 'w', position, 2000, 150, 50, 6, 1000));
        map.put('M', (Position position)-> new Monster("The Mountain", 'M', position, 1000, 60, 25, 6, 500));
        map.put('C', (Position position)-> new Monster("Queen Cersei", 'C', position, 100, 10, 10, 1, 1000));
        map.put('K', (Position position)-> new Monster("Night's King", 'K', position, 5000, 300, 150, 8, 5000));
        map.put('B', (Position position)-> new Trap(" Bonus Trap", 'B', position, 1, 1, 1, 2, 250, 1, 5, false));
        map.put('Q', (Position position)->new Trap(" Queen's Trap", 'Q', position, 250, 50, 10, 2, 100, 3, 7, false));
        map.put('D', (Position position)->new Trap(" Death Trap", 'D', position, 500, 100, 20, 2, 250, 1, 10, false));
        return map;
    }

    private Function<Position, Wall> CreateWall(){
        return (Position position)->new Wall(position);
    }

    private Function<Position, Empty> CreateEmpty(){
        return (Position position)->new Empty(position);
    }

    private Map<String, Function<Position, Player>> InitPlayer(){
        Map<String, Function<Position, Player>> map = new HashMap<>();
        map.put("Jon Snow", (Position position)-> new Warrior("Jon Snow", position, 300, 40, 4, 3));
        map.put("The Hound", (Position position)-> new Warrior("The Hound", position, 400, 20, 6, 5));
        map.put("Melisandre", (Position position)-> new Mage("Melisandre", position, 400, 20, 6, 5, 30, 15, 5, 6));
        map.put("Thoros of Myr", (Position position)-> new Mage("Thoros of Myr", position, 250, 25, 4, 150, 20, 20, 3, 4));
        map.put("Arya Stark", (Position position)-> new Rogue("Arya Stark", position, 150, 40, 2, 20));
        map.put("Bronn", (Position position)-> new Rogue("Bronn", position, 250, 35, 3, 50));
        return map;
    }

    public Tile CreateTile(char c, Position position, Player player){
        if (c == '.')
            return CreateEmpty().apply(position);
        else if (c == '#')
            return CreateWall().apply(position);
        else if (c == '@')
            return player;
        else if (c == 's') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'k') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'q') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'z') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'b'){
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'g') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'w') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'M') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'C') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'K') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'B') {
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else if (c == 'Q'){
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
        else{
            Enemy enemy = enemylist.get(c).apply(position);
            enemies.add(enemy);
            return enemy;
        }
    }

    public List<Enemy> getEnemies(){
        return enemies;
    }

    public List<Player> listPlayers(){
        List<Player> list = new ArrayList<>();
        list.add(players.get("Jon Snow").apply(new Position(0,0)));
        list.add(players.get("The Hound").apply(new Position(0,0)));
        list.add(players.get("Melisandre").apply(new Position(0,0)));
        list.add(players.get("Thoros of Myr").apply(new Position(0,0)));
        list.add(players.get("Arya Stark").apply(new Position(0,0)));
        list.add(players.get("Bronn").apply(new Position(0,0)));
        return list;
    }

}
