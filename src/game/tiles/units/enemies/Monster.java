package game.tiles.units.enemies;

import game.board.ArrayGameBoard;
import game.callbacks.MessageCallback;
import game.tiles.units.Energy;
import game.tiles.units.actions.Movement;
import game.utils.Position;

import java.util.Random;

public class Monster extends Enemy {
    private Movement movement;
    private int range;

    public Monster(String name, char tile, Position position, int maxhp, int attack, int defense, int range, int exp, ArrayGameBoard arrayGameBoard) {
        super(name, tile, position, maxhp, attack, defense,  range, exp, arrayGameBoard);
        this.range = range;
        movement = new Movement(position, null ,range);
    }

    public Movement getMovement() {
        return movement;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public void OnEnemyTurn(){
        int move;
        if(movement.getPlayerPostition() != null) {
            move = movement.MonsterMove();
        }
        else{
            Random random = new Random();
            move = random.nextInt(4)-1;
        }
        if (move == 0) {
            this.getPosition().setX(this.getPosition().getX() + 1);
        } else if (move == 1) {
            this.getPosition().setX(this.getPosition().getX() - 1);
        } else if (move == 2) {
            this.getPosition().setY(this.getPosition().getY() + 1);
        } else if (move == 3) {
            this.getPosition().setY(this.getPosition().getY() - 1);
        }


        if(movement.getPlayerPostition().getX()==getPosition().getX() && movement.getPlayerPostition().getY()==getPosition().getY()){
            attack(SelectPlayer());
        }
    }

}
