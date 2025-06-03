package game.tiles.units.enemies;

import game.tiles.units.Energy;
import game.tiles.units.actions.Movement;
import game.utils.Position;

public class Monster extends Energy {
    private Movement movement;
    private int range;
    private Position position;

    public Monster(int cost, int specialAbilityCost, int currentEnergy,int range, Position playerPosition, Position position) {
        super(cost, specialAbilityCost, currentEnergy);
        this.range = range;
        this.position = position;
        movement = new Movement(position, playerPosition, range);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
        int move = movement.Move();

        if(move == 0){
            this.position.setX(this.position.getX() + 1);
        }
        else if(move == 1){
            this.position.setX(this.position.getX() - 1);
        }
        else if(move == 2){
            this.position.setY(this.position.getY() + 1);
        }
        else if(move == 3){
            this.position.setY(this.position.getY() - 1);
        }
    }
}
