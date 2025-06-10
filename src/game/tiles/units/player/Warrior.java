package game.tiles.units.player;

import game.board.ArrayGameBoard;
import game.tiles.units.Health;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

public class Warrior extends Player{
    private int damage;
    private int abilityCooldown;
    private int remainingColldown;

    public Warrior(String name, Position position, int maxhp, int attack, int defense, int abilityCooldown) {
        super(name, position, maxhp, attack, defense, 3);
        this.damage = damage;
        this.abilityCooldown = abilityCooldown;
        this.remainingColldown = 0;
    }

    public int getDamage() {
        return damage;
    }


    public int getAbilityCooldown() {
        return abilityCooldown;
    }

    public int getRemainingColldown() {
        return remainingColldown;
    }

    public void setRemainingColldown(int remainingColldown) {
        this.remainingColldown = remainingColldown;
    }

    public void setAbilityCooldown(int abilityCooldown) {
        this.abilityCooldown = abilityCooldown;
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void WlevelUp(){
        this.LevelUp();
        this.remainingColldown = 0;
        this.setMaxHp(this.getMaxHp()+5*this.getLevel());
        this.setAttack(this.getAttack()+2*this.getLevel());
        this.setDefense(this.getDefense()+this.getLevel());
    }

    public void OnGameTick(){
        this.remainingColldown--;
    }

    public void OnAbilityCast(){
        if(!canCastAbility()){
            return;
        }
        this.remainingColldown = this.abilityCooldown;
        this.setHp(Math.min(this.getHp()+10*this.getDefense(),this.getMaxHp()));

    }

    public boolean canCastAbility(){
        return this.remainingColldown <= 0;
    }
    public void randomHit(){
        return;
    }
}
