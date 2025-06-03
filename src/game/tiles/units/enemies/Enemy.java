package game.tiles.units.enemies;

import game.tiles.units.Unit;
import game.tiles.units.player.Player;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class Enemy extends Unit {
    private int experience_val;
    private char tile;

    public Enemy(String name, int maxhp, int attack, int defense, Position position, int range, int exp, char tile) {
        super(name, maxhp, attack, defense, position, range);
        experience_val = exp;
        this.tile = tile;
    }

    public int getExperience() {
        return experience_val;
    }

    public void setExperience(int experience) {
        this.experience_val = experience;
    }

    public Player SelectPlayerInRange() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
    }

    public char getTile() {
        return tile;
    }

    public void setTile(char tile) {
        this.tile = tile;
    }
}
