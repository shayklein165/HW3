package BoardTests;
import game.Level;
import game.board.ArrayGameBoard;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.enemies.Trap;
import game.tiles.units.player.*;
import game.utils.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.parser.FileParser;

import java.io.File;

import static org.junit.Assert.*;

public class GeneralTests {

    private ArrayGameBoard board;
    private Player player;
    private Level level;

    @Before
    public void setup() {
        Position position = new Position(2, 2);
        player = new Warrior("Test Player", position, 10000, 5000, 0, 2);
        FileParser parser = new FileParser(player, this::send);

        File root = new File("testLevel");
        File[] files = root.listFiles();
        assert files != null;
        parser.parseLevel(files[0]);
        level = parser.interpret();
        board = level.getArrayGameBoard();

        for(Enemy e: board.getEnemies()){
            e.setDefense(0);
        }
    }

    @Test
    public void testWarriorLevelUp(){
        this.player = new Warrior("Test player", new Position(2,2), 300, 30, 4, 3);
        this.player.gainExperience(50);
        //asserts
    }

    @Test
    public void testMageLevelUp(){
        this.player = new Mage("Test player", new Position(2,2), 100, 5, 1, 300, 30, 15, 5, 6);
        this.player.gainExperience(50);
        //asserts
    }

    @Test
    public void testRogueLevelUp(){
        this.player = new Rogue("Test player", new Position(2,2), 150, 40, 2, 20);
        this.player.gainExperience(50);
        //asserts
    }

    @Test
    public void testHunterLevelUp(){
        this.player = new Hunter("Test player", new Position(2,2), 220, 30, 2, 6);
        this.player.gainExperience(50);
        //asserts
    }




    private void send(String message){
        System.out.println();
    }
}