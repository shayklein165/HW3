package game.tiles.units.player;

import game.Level;
import game.utils.Position;
import game.utils.SoundPlayer;

public class RandomKiller extends Player {

    private String spellname;

    public RandomKiller(String name, Position position, int maxhp, int attack, int defense) {
        super(name, position, maxhp, attack, defense, 3);
        this.spellname = "Life Taker";
    }


    @Override
    public void gameTick() {
        return;
    }


    public void LevelUp(){
        super.LevelUp();
        this.setMaxHp(this.getMaxHp() + this.getLevel());
        this.setDefense(this.getDefense() +  2 * this.getLevel());
    }

    public String canCastability(){
        if (getHp() > (getMaxHp()/2)){
            return "";
        }
        return(String.format("%s tried to cast %s, but there is not enough health: %s", getName(), getSpellName(), getHp()));
    }

    public String describe(){
        String description = "";
        description += getName() + "          ";
        description += "Health: " + getHp() + "/" + getMaxHp() + "          ";
        description += "Attack: " + getAttack() + "          ";
        description += "Defense: " + getDefense() + "          ";
        description += "Level: " + getLevel() + "          ";
        description += "Experience: " + getExperience() + "/" + (50*getLevel()) + "          ";
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
        String message = (getName() + " used " + getSpellName() + ".");
        setHp(getHp() - (getMaxHp()/2));
        level.RandomKillerAttack(this, message);
    }

    public String getSpellName(){return this.spellname;}

}
