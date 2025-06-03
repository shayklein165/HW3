package game.tiles.units.enemies;

import game.tiles.units.Unit;
import game.utils.Position;

public class Enemy extends Unit {
    private int experience_val;

    public Enemy(String name, int maxhp, int attack, int defense, Position position, int exp) {
        super(name, maxhp, attack, defense, position);
        experience_val = exp;
    }

    public int getExperience() {
        return experience_val;
    }

    public void setExperience(int experience) {
        this.experience_val = experience;
    }
}
