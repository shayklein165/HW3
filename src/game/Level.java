package game;

import game.board.ArrayGameBoard;
import game.callbacks.MessageCallback;
import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.player.Player;
import game.utils.Position;
import view.input.InputProvider;

import java.util.ArrayList;
import java.util.List;

public class Level {
    ArrayGameBoard arrayGameBoard;
    private ArrayList<Character> moves;
    MessageCallback messageCallback;
    private InputProvider inputProvider;

    public Level(ArrayGameBoard arrayGameBoard){
        this.arrayGameBoard = arrayGameBoard;
        this.moves = new ArrayList<Character>();
        this.moves.add('a');
        this.moves.add('d');
        this.moves.add('w');
        this.moves.add('s');
    }

    public void setArrayGameBoard(ArrayGameBoard arrayGameBoard) {
        this.arrayGameBoard = arrayGameBoard;
    }

    public ArrayGameBoard getArrayGameBoard() {
        return arrayGameBoard;
    }


    // here, the monster is the attacker. returns true if the player is dead. otherwise, false.
    public boolean attack(Enemy enemy, Player player) {
        int attackRoll = (int) (Math.random() * enemy.getAttack());
        int defenseRoll = (int) (Math.random() * player.getDefense());
        int damage = Math.max(attackRoll - defenseRoll, 0);

        player.reciveDamage(damage);

        if(!player.isAlive()){
            arrayGameBoard.KillPlayer();
            return true;
        }
        return false;
    }


    // here, the player is the attacker. returns true if the enemy is dead. otherwise, false.
    public boolean attack(Player player, Enemy enemy){
        int attackRoll = (int) (Math.random() * player.getAttack());
        int defenseRoll = (int) (Math.random() * enemy.getDefense());
        int damage = Math.max(attackRoll - defenseRoll,0);

        enemy.reciveDamage(damage);
        return enemy.isAlive();
    }

    public List<Enemy> SelectEnemyInRange() {
        List<Enemy> inRange = new ArrayList<>();
        for (Enemy enemy : arrayGameBoard.getEnemies()) {
            double distance = arrayGameBoard.getPlayer().getPosition().Range(enemy.getPosition());
            if (distance <= arrayGameBoard.getPlayer().getRange()) {
                inRange.add(enemy);
            }
        }
        return inRange;
    }

    public void playerMove(char action){
        Position newPosition = getNewPosition(arrayGameBoard.getPlayer().getPosition(), action);
        if(!inBounds(newPosition)){
            return;
        }

        Tile tile = arrayGameBoard.getBoard()[newPosition.getX()][newPosition.getY()];
        List<Enemy> enemies = arrayGameBoard.getEnemies();
        for(Enemy e: enemies){
            if(e.getPosition().equals(tile.getPosition())){
                attack(arrayGameBoard.getPlayer(), e);
            }
        }
        boolean isEmpty = tile.accept(arrayGameBoard.getPlayer());
        if(isEmpty){
            Position oldPosition = arrayGameBoard.getPlayer().getPosition();

            arrayGameBoard.setTile(new Empty(oldPosition),oldPosition);
            arrayGameBoard.setTile(arrayGameBoard.getPlayer(), newPosition);

            arrayGameBoard.getPlayer().setPosition(newPosition);
            tile.setPosition(oldPosition);
        }
    }



    protected boolean inBounds(Position position){
        return position.getX()>=0 && position.getX()<arrayGameBoard.getBoard().length && position.getY()>=0 && position.getY()<arrayGameBoard.getBoard()[0].length;
    }

    public void MonsterMove(Monster monster){
        Position newPosition;
        do {
            char move = this.MonsterChooseMove(monster);
            newPosition = getNewPosition(monster.getPosition(), move);
        } while (!inBounds(newPosition));


        List<Enemy> enemies = arrayGameBoard.getEnemies();
        for(Enemy e: enemies){
            if(e.getPosition().equals(newPosition)){
                return;
            }
        }
        boolean isEmpty = arrayGameBoard.getBoard()[newPosition.getX()][newPosition.getY()].accept(monster);
        if(isEmpty){
            Position oldPosition = monster.getPosition();
            monster.setPosition(newPosition);
            arrayGameBoard.setTile(monster, newPosition);
            arrayGameBoard.setTile(new Empty(oldPosition),oldPosition);
        }
    }


    public char MonsterChooseMove(Monster monster){

        char move;
        if(monster.InRange(arrayGameBoard.getPlayer().getPosition())){
            move = this.followPlayer(monster);
        }
        else{
            move = moves.get((int) (Math.random() * moves.size()));
        }
        return move;
    }

    // 0 = left, 1 = right, 2 = up, 3 = down
    public char followPlayer(Monster monster){
        int distX = monster.getPosition().getX() - arrayGameBoard.getPlayer().getPosition().getX();
        int distY = monster.getPosition().getY() - arrayGameBoard.getPlayer().getPosition().getY();

        if(Math.abs(distX) > Math.abs(distY)){
            if(distX > 0){
                return 'a';
            }
            else{
                return 'd';
            }
        }
        else{
            if(distY > 0){
                return 'w';
            }
            else{
                return 's';
            }
        }
    }

    public Position getNewPosition(Position current, char direction) {
        return switch (direction) {
            case 'w' -> current.up();
            case 's' -> current.down();
            case 'a' -> current.left();
            case 'd' -> current.right();
            default -> current; // Invalid direction = no movement
        };
    }

    public boolean won() {
        return arrayGameBoard.getEnemies().isEmpty();
    }

    public void gameDisplay()
    {
        Tile[][] board = arrayGameBoard.getBoard();
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                System.out.println(board[i][j]);
            }
        }
        System.out.println();
    }

    public boolean processRound()
    {
        gameDisplay();
        char move = inputProvider.inputQuery();
        if (this.moves.contains(move))
        {
            playerMove(move);
            List<Enemy> enemies= arrayGameBoard.getEnemies();
            for(Enemy e: enemies)
            {
                MonsterMove(e);
            }
        }
        return false;
    }

    /*
    public void start() {
        gameDisplay();
        System.out.println("Starting new level...");



        if(arrayGameBoard.getPlayer().isAlive()){

        }
    }
    */

}
