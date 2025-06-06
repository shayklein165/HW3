package game.tiles.units.enemies;

import game.tiles.Tile;
import game.tiles.units.Energy;
import game.tiles.units.actions.Movement;
import game.utils.Position;

public class Monster extends Enemy {
    private Movement movement;
    private int range;
    private Tile tile;

    public Monster(String name, int maxhp, int attack, int defense, Position position, int range, int exp, char tile) {
        super(name, maxhp, attack, defense, position, range, exp);
        this.tile = new Tile(position, tile);
        this.range = range;
        this.mobt
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
