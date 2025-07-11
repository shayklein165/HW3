package game.tiles.units.player;
import game.Level;
import game.utils.Position;
import game.utils.SoundPlayer;

public class Rogue extends Player {

    private int energycost;
    private int currentEnergy;
    private String spellname;

    public Rogue(String name, Position position, int maxhp, int attack, int defense, int cost) {
        super(name, position, maxhp, attack, defense, 2);
        this.energycost = cost;
        this.currentEnergy = 100;
        this.spellname = "Fan of Knives";
    }

    @Override
    public void gameTick() {this.currentEnergy = (Math.min(currentEnergy+10, 100));}

    public int getCurrentEnergy() {return currentEnergy;}
    public int getEnergycost() {return energycost;}

    public void LevelUp(){
        super.LevelUp();
        currentEnergy = 100;
        setAttack(getAttack()+3*getLevel());
    }

    public String canCastability(){
        if (currentEnergy >= energycost)
            return "";
        return(String.format("%s tried to cast %s, but there was not enough energy: %s", getName(), getSpellName(), getCurrentEnergy() + "/" + getEnergycost()));
    }

    public String describe(){
        String description = "";
        description += getName() + "          ";
        description += "Health: " + getHp() + "/" + getMaxHp() + "          ";
        description += "Attack: " + getAttack() + "          ";
        description += "Defense: " + getDefense() + "          ";
        description += "Level: " + getLevel() + "          ";
        description += "Experience: " + getExperience() + "/" + (50*getLevel()) + "          ";
        description += "Energy: " + getCurrentEnergy() + "/" + "100" + "          ";
        return description;
    }

    @Override
    public String gainExperience(int xp) {
        setExperience(getExperience() + xp);
        String ret = "";
        while (getExperience() >= 50 * getLevel()) {
            SoundPlayer.playSound("level_up.wav");
            ret = (getName() + "reached level " + getLevel() + ": + " + 5 * this.getLevel()+ " Health, +" + 2 * this.getLevel()+ " Attack, +" +  this.getLevel() + "Defense");
            this.LevelUp();
        }
        return ret;
    }

    @Override
    public void castAbility(Level level){
        String message = (getName()+" used " + getSpellName()+".");
        currentEnergy -= energycost;
        level.RogueAttack(this, message);
    }

    public String getSpellName(){return this.spellname;}
}
