package game.tiles.units.enemies;

import game.utils.Position;

public class Monster extends Enemy{
    private int range;


    public Monster(String name, int maxhp, int attack, int defense, Position position, int exp, int range) {
        super(name, maxhp, attack, defense, position, exp);
        this.range = range;
    }

    public void OnEnemyTurn(){

    }
}
