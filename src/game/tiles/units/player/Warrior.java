package game.tiles.units.player;

import game.tiles.units.Health;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

public class Warrior extends Player{
    private int damage;
    private Health health;
    private int abilityCooldown;
    private int remainingColldown;

    public Warrior(String name, Position position, int maxhp, int manaPool, int attack, int defense, int range, int damage, int health, int abilityCooldown, int remainingColldown) {
        super(name, position, maxhp, manaPool, attack, defense, range);
        this.damage = damage;
        this.health = new Health(health,health);
        this.abilityCooldown = abilityCooldown;
        this.remainingColldown = remainingColldown;
    }

    public int getDamage() {
        return damage;
    }

    public Health getHealth() {
        return health;
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

    public void setHealth(int health) {
        this.health.setHp(health);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void WlevelUp(){
        this.LevelUp();
        this.remainingColldown = 0;
        this.health.setMaxHp(this.health.getMaxHp()+5*this.getLevel());
        this.setAttack(this.getAttack()+2*this.getLevel());
        this.setDefense(this.getDefense()+this.getLevel());
    }

    public void OnGameTick(){
        this.remainingColldown--;
    }

    public void OnAbilityCast(){
        this.remainingColldown = this.abilityCooldown;
        this.health.setHp(Math.min(this.health.getHp()+10*this.getDefense(),this.health.getMaxHp()));

    }

    public void randomHit(){
        return;
    }
}
