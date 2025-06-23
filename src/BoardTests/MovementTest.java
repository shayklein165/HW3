package BoardTests;
import game.Level;
import game.board.ArrayGameBoard;
import game.tiles.Tile;
import game.tiles.board_components.Wall;
import game.tiles.units.enemies.Monster;
import game.tiles.units.player.Player;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Warrior;
import game.utils.Position;
import org.junit.Before;
import org.junit.Test;
import view.GameRunner;
import view.parser.FileParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class MovementTest {

    private ArrayGameBoard board;
    private Player player;
    private Level level;

    @Before
    public void setup() {
        Position position = new Position(2, 2);
        player = new Warrior("Test Player", position, 10000, 5000, 0, 2);
        FileParser parser = new FileParser(player, this::send);

        File root = new File("C:\\Users\\DELL\\IdeaProjects\\HW3\\testLevel");
        File[] files = root.listFiles();
        assert files != null;
        parser.parseLevel(files[0]);
        level = parser.interpret();
        board = level.getArrayGameBoard();
        board.getEnemies().get(0).setHp(0);
    }

    @Test
    public void testPlayerMoveRightLeft() {
        Position RightPos = new Position(player.getPosition().getX()+1, player.getPosition().getY());
        level.playerMove('d');
        assertEquals("Player's position should be moved to the right", RightPos, player.getPosition());
        Position LeftPos = new Position(player.getPosition().getX()-1, player.getPosition().getY());
        level.playerMove('a');
        assertEquals("Player's position should be moved to the left", LeftPos, player.getPosition());
    }


    @Test
    public void testPlayerMoveDownUp() {
        Position DownPos = new Position(player.getPosition().getX(), player.getPosition().getY()+1);
        level.playerMove('s');
        assertEquals("Player's position should be moved down", DownPos, player.getPosition());
        Position UpPos = new Position(player.getPosition().getX(), player.getPosition().getY()-1);
        level.playerMove('w');
        assertEquals("Player's position should be moved up", UpPos, player.getPosition());
    }

    @Test
    public void testPlayerMoveOutOfBounds() {
        Position initialPos = player.getPosition();
        level.playerMove('s');
        level.playerMove('s');
        level.playerMove('w');
        assertEquals("Player's position should be the same after out of bounds", initialPos, player.getPosition());
    }

    @Test
    public void testPlayerMoveOnWall() {
        Position initialPos = player.getPosition();
        level.playerMove('w');
        assertEquals("Player's position should be the same after hitting a wall", initialPos, player.getPosition());
    }

    @Test
    public void testAttackMonster(){
        Position newPos = new Position(player.getPosition().getX(), player.getPosition().getY());
        newPos.setX(newPos.getX()+4);
        level.playerMove('d');
        level.playerMove('d');
        level.playerMove('d');
        level.playerMove('d'); // monster s must die.
        assertEquals("Players position should be the monster's position", newPos, player.getPosition());
        assertEquals("Board should have the player at the new position", newPos, board.getTile(player.getPosition()).getPosition());
        assertEquals("Board should have no enemies", board.getEnemies().size(), 0);
        assertEquals("Player should gain the monster's experience", 25, player.getExperience());
        assertEquals("Player should level up", 1, player.getLevel());

        level.playerMove('a');
        level.playerMove('a');
        level.playerMove('a');
        level.playerMove('a');
        level.getArrayGameBoard().setTile(new Monster("Lannister Solider", 's', newPos, 80, 8, 3, 3, 25),newPos);

    }

    @Test
    public void testAttackPlayer(){
        Position newPos = new Position(player.getPosition().getX(), player.getPosition().getY());
        level.playerMove('d');
        level.playerMove('d');
        level.playerMove('d');
        int hp = player.getHp();
        level.EnemyMove(board.getEnemies().get(0));
        assertNotEquals("Player should lose HP after attacking", hp, player.getHp());

        level.playerMove('a');
        level.playerMove('a');
        level.playerMove('a');

    }


    private void send(String message){
        System.out.println(message);
    }
}