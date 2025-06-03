package game.tiles.units.player;

import game.tiles.units.enemies.Enemy;
import game.utils.Position;

import java.util.List;

public class Mage extends Player{
    private int costmana;
    private int spellpower;
    private int hitscnt;


    public Mage(String name, Position position, int maxhp, int manaPool, int attack, int defense, int costmana, int spellpower, int hitscnt, int range) {
        super(name, position, maxhp, manaPool, attack, defense, range);
        this.costmana = costmana;
        this.spellpower = spellpower;
        this.hitscnt = hitscnt;
    }
    public int getCostmana() {
        return costmana;
    }
    public int getSpellpower() {
        return spellpower;
    }
    public int getHitscnt() {
        return hitscnt;
    }
    public void setCostmana(int costmana) {
        this.costmana = costmana;
    }
    public void setSpellpower(int spellpower) {
        this.spellpower = spellpower;
    }
    public void setHitscnt(int hitscnt) {
        this.hitscnt = hitscnt;
    }

    public void LeveUp(){
        LevelUp();
        setManaPool(getManaPool() + (25 * getLevel()));
        int currmana = getManaPool();
        currmana = Math.min(currmana, getMana() + (currmana/4));
        setMana(currmana);
        setSpellpower(getSpellpower() + (10 * getLevel()));
    }

    public void OnGameTick(){
        int currmana = getManaPool();
        currmana = Math.min(currmana, getMana() + getLevel());
        setMana(currmana);
    }

    public void OnAbilityCast(){
        setMana(getMana() - costmana);
        int hits = 0;
        while (hits < hitscnt /* && need to implement the check if any enemy exist in rang*/ ){
            List<Enemy> lst = SelectEnemyInRange();
            attack(lst.getFirst());
            hits++;
        }

    }
}
