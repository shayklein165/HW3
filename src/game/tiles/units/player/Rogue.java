package game.tiles.units.player;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

public class Rogue extends Player{

    private int cost;
    private int currentEnergy;


    public Rogue(String name, Position position, int maxhp, int attack, int defense, int cost) {
        super(name, position, maxhp, attack, defense, 2);
        this.cost = cost;
        this.currentEnergy = 100;
    }

    @Override
    public void gameTick() {this.currentEnergy = (Math.min(currentEnergy+10, 100));}

    @Override
    public void abilityCast() {}

    public int getCurrentEnergy() {return currentEnergy;}
    public void setCurrentEnergy(int currentEnergy) {}
    public int getCost() {return cost;}
    public void setCost(int cost) {this.cost = cost;}

    public void OnLevelUp(){
        LevelUp();
        currentEnergy = 100;
        setAttack(getAttack()+3*getLevel());
    }

    public void OnAbilityCast(){
        /*
        this.energy.setCurrentEnergy(energy.getCurrentEnergy()- energy.getCost());
        List<Enemy> EnemyInRange = this.SelectEnemyInRange();
        for (Enemy enemy : EnemyInRange){
            attackEnemy(2,enemy);
        }
         */
    }

    public String describe(){
        String description = "";
        description += getName() + "          ";
        description += getHp() + "/" + getMaxHp() + "          ";
        description += getAttack() + "          ";
        description += getDefense() + "          ";
        description += getLevel() + "          ";
        description += getExperience() + "/" + (50*getLevel()) + "          ";
        description += getCurrentEnergy() + "/" + "100" + "          ";
        return description;
    }
}
