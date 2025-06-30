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

        File root = new File(ActionsTest.class.getResource("testLevel").getPath());
        File[] files = root.listFiles();
        assert files != null;
        parser.parseLevel(files[0]);
        level = parser.interpret();
        board = level.getArrayGameBoard();
    }


    @Test
    public void testPlayerLevelUp(){
        this.player = new Warrior("Test player", new Position(2,2), 300, 30, 4, 3);
        this.player.gainExperience(50);
        assertEquals(0, this.player.getExperience());
        assertEquals(2, this.player.getLevel());
    }
    @Test
    public void testWarriorLevelUp(){
        Warrior warrior = new Warrior("Test player", new Position(2,2), 300, 30, 4, 3);
        this.player = warrior;
        this.player.gainExperience(50);
        assertEquals(330, warrior.getMaxHp());
        assertEquals(0, warrior.getRemainingColldown());
        assertEquals(42,warrior.getAttack());
        assertEquals(8, warrior.getDefense());
    }

    @Test
    public void testMageLevelUp(){
        Mage mage = new Mage("Test player", new Position(2,2), 100, 5, 1, 300, 30, 15, 5, 6);
        this.player = mage;
        this.player.gainExperience(50);
        assertEquals(120, mage.getMaxHp());
        assertEquals(13, mage.getAttack());
        assertEquals(3, mage.getDefense());
        assertEquals(350, mage.getManaPool());
        assertEquals(162, mage.getMana());
        assertEquals(35, mage.getSpellpower());
    }

    @Test
    public void testRogueLevelUp(){
        Rogue rogue = new Rogue("Test player", new Position(2,2), 150, 40, 2, 20);
        this.player = rogue;
        this.player.gainExperience(50);
        assertEquals(170, rogue.getMaxHp());
        assertEquals(54, rogue.getAttack());
        assertEquals(4, rogue.getDefense());
        assertEquals(100, rogue.getCurrentEnergy());
    }

    @Test
    public void testHunterLevelUp(){
        Hunter hunter = new Hunter("Test player", new Position(2,2), 220, 30, 2, 6);
        this.player = hunter;
        this.player.gainExperience(50);
        assertEquals(42, hunter.getAttack());
        assertEquals(6, hunter.getDefense());
        assertEquals(240, hunter.getMaxHp());
        assertEquals(30, hunter.getArrowscnt());
    }




    private void send(String message){
        System.out.println();
    }
}