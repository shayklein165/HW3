package game.tiles.units.player;

import game.Level;
import game.utils.Position;
import game.utils.SoundPlayer;

public class Healer extends Player{
    private int abilityCooldown;
    private int remainingCooldown;
    private String spellname;

    public Healer(String name, Position position, int maxhp, int attack, int defense, int abilityCooldown) {
        super(name, position, maxhp, attack, defense, 3);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
        this.spellname = "Healing";
    }


    @Override
    public void gameTick() {
        setRemainingColldown(Math.max(remainingCooldown-1,0));
    }
    public int getAbilityCooldown() {
        return abilityCooldown;
    }
    public int getRemainingColldown() {
        return remainingCooldown;
    }
    public void setRemainingColldown(int remainingCooldown) {
        this.remainingCooldown = remainingCooldown;
    }


    public void LevelUp(){
        super.LevelUp();
    }

    public String canCastability(){
        if (getRemainingColldown() <= 0){
            return "";
        }
        return(String.format("%s tried to cast %s, but there is a cooldown: %s", getName(), getSpellName(), getRemainingColldown()));    }

    public String describe(){
        String description = "";
        description += getName() + "          ";
        description += "Health: " + getHp() + "/" + getMaxHp() + "          ";
        description += "Attack: " + getAttack() + "          ";
        description += "Defense: " + getDefense() + "          ";
        description += "Level: " + getLevel() + "          ";
        description += "Experience: " + getExperience() + "/" + (50*getLevel()) + "          ";
        description += "Cooldown: " +getRemainingColldown() + "/" + getAbilityCooldown() + "          ";
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
        setRemainingColldown(getAbilityCooldown());
        level.HealerAttack(this, message);
    }

    public String getSpellName(){return this.spellname;}

}

