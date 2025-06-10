package game.tiles.units.enemies;

import game.board.ArrayGameBoard;
import game.callbacks.MessageCallback;
import game.tiles.units.Energy;
import game.tiles.units.actions.Movement;
import game.utils.Position;

import java.util.Random;

public class Monster extends Enemy {
    private int range;

    public Monster(String name, char tile, Position position, int maxhp, int attack, int defense, int range, int exp) {
        super(name, tile, position, maxhp, attack, defense,  range, exp);
        this.range = range;
    }


    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }




}
