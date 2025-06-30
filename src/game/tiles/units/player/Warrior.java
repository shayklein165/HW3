package game.tiles.units.player;
import game.Level;
import game.utils.Position;
import game.utils.SoundPlayer;

public class Warrior extends Player {
    private int abilityCooldown;
    private int remainingCooldown;
    private String spellname;

    public Warrior(String name, Position position, int maxhp, int attack, int defense, int abilityCooldown) {
        super(name, position, maxhp, attack, defense, 3);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
        this.spellname = "Avenger’s Shield";
    }


    @Override
    public void gameTick() {
        remainingCooldown = Math.max(remainingCooldown-1,0);
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
        this.remainingCooldown = 0;
        this.setMaxHp(this.getMaxHp() + 5 * this.getLevel());
        this.setAttack(this.getAttack() + 2 * this.getLevel());
        this.setDefense(this.getDefense() + this.getLevel());
    }

    public String canCastability(){
        if (this.remainingCooldown <= 0){
            return "";
        }
        return(String.format("%s tried to cast %s, but there is a cooldown: %s", getName(), getSpellName(), getRemainingColldown()));
    }

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
        String message = (getName() + " used " + getSpellName() + ", healing for " + (10 * getDefense()) + ".");
        setHp(Math.min(getHp() + (10 * getDefense()), getMaxHp()));
        setRemainingColldown(getAbilityCooldown());
        level.WarriorAttack(this, message);
    }

    public String getSpellName(){return this.spellname;}
}
