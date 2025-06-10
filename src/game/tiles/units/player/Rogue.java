package game.tiles.units.player;

import game.board.ArrayGameBoard;
import game.tiles.units.Energy;
import game.tiles.units.Health;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class Rogue extends Player{

    private int cost;
    private Energy energy;


    public Rogue(String name, Position position, int maxhp, int attack, int defense, int cost) {
        super(name, position, maxhp, attack, defense, 2);
        this.cost = cost;
        this.energy = new Energy(cost, 100);
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public void OnGameTick(){
        this.energy.setCurrentEnergy(Math.min(energy.getCurrentEnergy()+10, 100));
    }

    public void OnLevelUp(){
        LevelUp();
        energy.setCurrentEnergy(100);
        setAttack(getAttack()+3*getLevel());
    }

    public void OnAbilityCast() throws ExecutionControl.NotImplementedException {
        this.energy.setCurrentEnergy(energy.getCurrentEnergy()- energy.getCost());
        List<Enemy> EnemyInRange = this.SelectEnemyInRange();
        for (Enemy enemy : EnemyInRange){
            attackEnemy(2,enemy);
        }
    }
}
