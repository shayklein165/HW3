package game.tiles.units.player;

import game.board.ArrayGameBoard;
import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.Mana;
import game.tiles.units.Unit;
import game.tiles.units.actions.Action;
import game.tiles.units.actions.CastAbility;
import game.tiles.units.actions.Movement;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class Player extends Unit {
    private int experience;
    private int level;
    private Mana mana;



    public Player(String name, Position position, int maxhp, int manaPool, int attack, int defense , int range){
        super(name, '@' ,position, maxhp, attack, defense, range);
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
        setAttack(getAttack() + 4 * level);
        setDefense(getDefense() + level);
    }

    public void castAbility(){
        new CastAbility().executeAbility(this);
    }



    public boolean visit(Empty empty){
        return true;
    }

    public boolean visit(Wall wall){
        return false;
    }



    public void gainExperience(int xp) {
        experience += xp;
        while (experience >= 50 * level) {
            LevelUp();
        }
    }

    public void reciveDamage(int damage) {
        setHp(getHp() - damage);
        if(getHp() < 0){
            setHp(0);
        }
    }

    public boolean isAlive(){
        return getHp() > 0;
    }

}