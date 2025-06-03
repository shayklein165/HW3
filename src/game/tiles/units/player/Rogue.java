package game.tiles.units.player;

import game.tiles.units.Energy;
import game.tiles.units.Health;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class Rogue extends Player{
    private Health health;
    private int damage;
    private int armor;
    private Energy energy;
    private int remainingColldown;


    public Rogue(String name, Position position, int maxhp, int manaPool, int attack, int defense, int range, int maxHealth, int damage, int armor, Energy energy) {
        super(name, position, maxhp, manaPool, attack, defense, range);
        this.health = new Health(maxHealth,maxHealth);
        this.damage = damage;
        this.armor = armor;
        this.remainingColldown = remainingColldown;
        this.energy = energy;
    }

    public Health getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health.setHp(health);
    }

    public void OnGameTick(){
        this.energy.setCurrentEnergy(Math.min(energy.getCurrentEnergy()+10, 100));
    }

    public void OnAbilityCast() throws ExecutionControl.NotImplementedException {
        this.energy.setCurrentEnergy(energy.getCurrentEnergy()- energy.getCost());
        List<Enemy> EnemyInRange = this.SelectEnemyInRange();
        for (Enemy enemy : EnemyInRange){
            attack(enemy);
        }
    }
}
