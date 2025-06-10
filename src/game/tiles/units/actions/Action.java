package game.tiles.units.actions;

import game.board.ArrayGameBoard;
import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.player.Player;
import game.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Action {
    ArrayGameBoard arrayGameBoard;

    public Action(ArrayGameBoard arrayGameBoard){
        this.arrayGameBoard = arrayGameBoard;
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
        int damage = Math.max(attackRoll-defenseRoll,0);

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
        int damage = Math.max(attackRoll-defenseRoll,0);

        enemy.reciveDamage(damage);

        if(enemy.isAlive()) {
            return true;
        }
        return false;
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

    public void PlayerMove(char action){
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

    public int MonsterMove(Monster monster){
        // need to check if there is a wall. this method only choose a new movement.
        double distance = monster.getPosition().Range(arrayGameBoard.getPlayer().getPosition());
        int move;
        if(distance<monster.getRange()){
            move = this.followPlayer(monster);
        }
        else{
            move = (int) (Math.random()*4);
        }
        return move;
    }

    // 0 = left, 1 = right, 2 = up, 3 = down
    public int followPlayer(Monster monster){
        int distX = monster.getPosition().getX()-arrayGameBoard.getPlayer().getPosition().getX();
        int distY = monster.getPosition().getY()-arrayGameBoard.getPlayer().getPosition().getY();

        if(Math.abs(distX) > Math.abs(distY)){
            if(distX > 0){
                return 0;
            }
            else{
                return 1;
            }
        }
        else{
            if(distY > 0){
                return 2;
            }
            else{
                return 3;
            }
        }
    }

    public static Position getNewPosition(Position current, char direction) {
        switch (direction) {
            case 'w':
                return current.up();
            case 's':
                return current.down();
            case 'a':
                return current.left();
            case 'd':
                return current.right();
            default:
                return current; // Invalid direction = no movement
        }
    }

}
