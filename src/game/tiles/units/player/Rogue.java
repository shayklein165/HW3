package game.tiles.units.player;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

public class Rogue extends Player{

    private int energycost;
    private int currentEnergy;


    public Rogue(String name, Position position, int maxhp, int attack, int defense, int cost) {
        super(name, position, maxhp, attack, defense, 2);
        this.energycost = cost;
        this.currentEnergy = 100;
    }

    @Override
    public void gameTick() {this.currentEnergy = (Math.min(currentEnergy+10, 100));}

    public int getCurrentEnergy() {return currentEnergy;}
    public void setCurrentEnergy(int currentEnergy) {}
    public int getEnergycost() {return energycost;}
    public void setEnergycost(int cost) {this.energycost = cost;}

    public void OnLevelUp(){
        LevelUp();
        currentEnergy = 100;
        setAttack(getAttack()+3*getLevel());
    }

    public boolean CanCastAbility(){
        if (currentEnergy >= energycost)
            return true;
        return false;

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
