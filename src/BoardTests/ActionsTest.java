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

public class ActionsTest {

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
        board.getEnemies().get(0).setHp(0);
        Position newPos = new Position(player.getPosition().getX(), player.getPosition().getY());
        newPos.setX(newPos.getX()-1);
        level.playerMove('a'); // monster s must die.
        assertEquals("Players position should be the monster's position", newPos, player.getPosition());
        assertEquals("Board should have the player at the new position", newPos, board.getTile(player.getPosition()).getPosition());
        assertEquals("Board should have one enemie", 1, board.getEnemies().size());
        assertEquals("Player should gain the monster's experience", 25, player.getExperience());
        assertEquals("Player should level up", 1, player.getLevel());

        level.playerMove('d');
        Monster e = new Monster("Lannister Solider", 's', newPos, 80, 8, 0, 3, 25);
        level.getArrayGameBoard().setTile(e,newPos);
        level.getArrayGameBoard().AddEnemy(e);

    }

    @Test
    public void testAttackPlayer(){
        int hp = player.getHp();
        board.getEnemies().get(1).setAttack(1000);
        level.EnemyMove(board.getEnemies().get(1));
        assertNotEquals("Player should lose HP after attacking", hp, player.getHp());
    }


    @Test
    public void testTrapAttackPlayer(){
        Trap trap = new Trap("test trap", 'B', new Position(3,1), 100, 25, 10, 0, 1, 10, false);
        Position newPos = new Position(player.getPosition().getX(), player.getPosition().getY());
        level.playerMove('s');
        int hp = player.getHp();
        level.EnemyMove(board.getEnemies().get(0));

        assertNotEquals("Player should lose HP after attacking", hp, player.getHp());

        level.playerMove('a');
    }

    @Test
    public void testWarriorAbilityCastFail(){
        Player player = board.getPlayer();
        Warrior warrior = new Warrior("Test Player", new Position(2,2), 10000, 5000, 100, 2);
        warrior.setHp(100);
        warrior.setRemainingColldown(1);
        board.setPlayer(warrior);
        level.castAbility(warrior); //should fail because abilityCooldown != 0.
        assertEquals("Player's hp should not change", 100, warrior.getHp());
        board.setPlayer(player);
    }

    @Test
    public void testWarriorAbilityCast(){
        Player player = board.getPlayer();
        Warrior warrior = new Warrior("Test Player", new Position(2,2), 150, 5000, 3, 3);
        warrior.setHp(100);
        warrior.setRemainingColldown(0);
        int trapHp = board.getEnemies().get(0).getHp();
        int monsterHp = board.getEnemies().get(1).getHp();

        board.setPlayer(warrior);
        level.castAbility(warrior);
        assertEquals("Player's hp should be increased by min(current health + 10 * defense, health pool)", Math.min(100 + 10 * 3, 150), warrior.getHp());
        boolean eq = trapHp-0.1*warrior.getMaxHp() == board.getEnemies().get(0).getHp() || monsterHp-0.1*warrior.getMaxHp() == board.getEnemies().get(1).getHp();
        assertTrue("Player should hit trap or monster", eq);
        board.setPlayer(player);
    }

    @Test
    public void testMageAbilityCast(){
        Player player = board.getPlayer();
        Mage mage = new Mage("Test Player", new Position(2,2), 10000, 5000, 3, 300, 30,15,5,6);
        int newMana = mage.getMana() - mage.getCostmana();
        int enemyHp = board.getEnemies().get(0).getHp() + board.getEnemies().get(1).getDefense();
        board.setPlayer(mage);
        level.castAbility(mage);
        boolean damage = (enemyHp-(board.getEnemies().get(0).getHp() + board.getEnemies().get(1).getDefense()))>=mage.getSpellpower();

        assertEquals("Player's mana should be decreased by costmana", newMana, mage.getMana());
        assertEquals("Player should randomly hit enemies around him for an amount equals to the spellpower points",damage,true);

        board.getEnemies().get(0).setHp(250);
        board.getEnemies().get(1).setHp(80);
        board.setPlayer(player);
    }

    @Test
    public void testRogueAbilityCast(){
        Player player = board.getPlayer();
        Rogue rogue = new Rogue("Test player", new Position(2,2), 150, 40, 2, 20);
        int newCurrentEnergy = rogue.getCurrentEnergy() - rogue.getEnergycost();
        int enemyHp = board.getEnemies().get(0).getHp() + board.getEnemies().get(1).getDefense();
        board.setPlayer(rogue);
        level.castAbility(rogue);
        boolean damage = (enemyHp-(board.getEnemies().get(0).getHp() + board.getEnemies().get(1).getDefense()))>=rogue.getAttack();

        assertEquals("Player's current energy should be decreased by energycost", newCurrentEnergy, rogue.getCurrentEnergy());
        assertTrue("Player should hits everyone around him for an amount equals to the attack points",damage);

        board.getEnemies().get(0).setHp(250);
        board.getEnemies().get(1).setHp(80);
        board.setPlayer(player);
    }

    @Test
    public void testHunterAbilityCast(){
        Player player = board.getPlayer();
        Hunter hunter = new Hunter("Test player", new Position(2,2), 150, 40, 2, 20);
        int arrowsCnt = hunter.getArrowscnt()-1;
        board.setPlayer(hunter);
        level.castAbility(hunter);
        assertEquals("Player should lose one arrow", arrowsCnt, hunter.getArrowscnt());
        assertEquals("Monster's hp should be decreased by 40", 40, board.getEnemies().get(1).getHp());
        board.setPlayer(player);
    }


    private void send(String message){
        System.out.println();
    }
}