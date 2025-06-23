package BoardTests;
import game.board.ArrayGameBoard;
import game.tiles.Tile;
import game.tiles.board_components.Wall;
import game.tiles.units.player.Player;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Warrior;
import game.utils.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ArrayGameBoardTest {

    private ArrayGameBoard board;
    private int rows = 3;
    private int cols = 4;
    private Player player;


    @Before
    public void setup() {
        char[][] raw = {
                {'.', '#', '.', 's'},
                {'.', '@', '.', '.'},
                {'.', '.', '#', '.'}
        };
        Position position = new Position(1, 1);
        player = new Warrior("Test Player", position, 10, 5, 3, 2);
        this.board = new ArrayGameBoard(raw, player);
    }

    @Test
    public void testGetRowsCols() {
        assertEquals("The number of rows should match the board initialization", rows, board.getBoard().length);
        assertEquals("The number of columns should match the board initialization", cols, board.getBoard()[0].length);
    }

    @Test
    public void testGetAndSetTile() {
        Position position = new Position(1, 2);
        Wall wall = new Wall(position);
        board.setTile(wall, position);
        assertSame("Tile at position (1,2) should be the newly set wall", wall, board.getTile(position));
    }

    @Test
    public void testGetPlayer() {
        assertNotNull("Player should not be null after initialization", board.getPlayer());
        assertEquals("Player name should match the initialized player", "Test Player", board.getPlayer().getName());
        assertEquals("Player position should match the starting position", new Position(1, 1), board.getPlayer().getPosition());
    }

    @Test
    public void testRemoveEnemy() {
        List<Enemy> enemies = board.getEnemies();
        int initialEnemyCount = enemies.size();

        if (!enemies.isEmpty()) {
            Enemy firstEnemy = enemies.get(0);
            boolean removed = board.RemoveEnemy(firstEnemy);

            assertTrue("Enemy should be removed successfully", removed);
            assertEquals("Enemies list size should decrease by one", initialEnemyCount - 1, board.getEnemies().size());
            assertFalse("Removed enemy should not exist in the enemies list", board.getEnemies().contains(firstEnemy));
        }
        else {
            fail("Enemies list should not be empty for this test");
        }
    }

    @Test
    public void testKillPlayer() {
        board.KillPlayer();

        Tile tileAtPlayerPosition = board.getTile(player.getPosition());
        assertEquals("Player's position should be marked with 'X' after death", 'X', tileAtPlayerPosition.getTile());
    }

    @Test
    public void testInitialPlayerPosition() {
        Position initialPosition = board.getInitialPlayerPosition();

        assertNotNull("Initial player position should not be null", initialPosition);
        assertEquals("Initial player position should match the set position", new Position(1, 1), initialPosition);
    }

    @Test
    public void testCallPositionChangeListener() {
        Position newPosition = new Position(2, 1);
        Position oldPosition = player.getPosition();
        Tile playerTile = board.getTile(oldPosition);
        Tile emptyTile = board.getTile(newPosition);

        playerTile.swapPosition(emptyTile);

        assertSame("Player should be at the new position (2,1)", playerTile, board.getTile(newPosition));

        assertSame("empty should be at the old position (2,1)", emptyTile, board.getTile(oldPosition));
    }

    @Test
    public void testPlayersAndEnemiesLocations() {
        Position playerPosition = player.getPosition();
        Tile playerTile = board.getTile(playerPosition);


        assertEquals("Player should be located at (1,1)", '@', playerTile.getTile());


        for (Enemy enemy : board.getEnemies()) {
            Tile enemyTile = board.getTile(enemy.getPosition());
            assertSame("Enemy at its position should be consistent on the board", enemy, enemyTile);
        }
    }
}