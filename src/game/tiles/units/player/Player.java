package game.tiles.units.player;

import game.tiles.units.Mana;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class Player extends Unit {
    private int experience;
    private int level;
    private Mana mana;



    public Player(String name, Position position, int maxhp, int manaPool, int attack, int defense , int range){
        super(name, maxhp, attack, defense, position, range);
        this.experience = 0;
        this.level = 1;
        this.mana = new Mana(manaPool);
    }

    public int getMana() {
        return mana.getCurrmana();
    }

    public int getManaPool() {
        return mana.getMaxMana();
    }


    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public void setMana(int mana) {
        this.mana.setCurrmana(mana);
    }

    public void setManaPool(int manaPool) {
        this.mana.setMaxMana(manaPool);
    }

    protected void LevelUp(){
        this.experience -= 50*level;
        this.level += 1;
        setMaxHp(getMaxHp() + 10*level);
        setHp(getMaxHp());
        setAttack(4 * level);
        setDefense(level);
    }

    public List<Enemy> SelectEnemyInRange() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
    }
}
