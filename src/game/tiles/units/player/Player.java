package game.tiles.units.player;

import game.tiles.units.Mana;
import game.tiles.units.Unit;
import game.utils.Position;

public class Player extends Unit {
    private int experience;
    private int level;
    private Mana mana;



    public Player(String name, Position position, int maxhp, int manaPool, int attack, int defense){
        super(name, maxhp, attack, defense, position);
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
        SetMaxHp(getMaxHp() + 10*level);
        SetHp(getMaxHp());
        SetAttack(4 * level);
        SetDefense(level);
    }
}
