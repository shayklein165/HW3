package game.tiles.units.player;
import game.Level;
import game.tiles.units.HeroicUnit;
import game.utils.Position;
import game.utils.SoundPlayer;


public class Hunter extends Player implements HeroicUnit {

    private int arrowscnt;
    private int tickscnt;
    private String spellname;

    public Hunter(String name, Position position, int maxhp, int attack, int defense, int range) {
        super(name, position, maxhp, attack, defense, range);
        arrowscnt = 0;
        tickscnt = 0;
        spellname = "Shoot";
    }

    @Override
    public void gainExperience(int xp) {
        setExperience(getExperience() + xp);
        while (getExperience() >= 50 * getLevel()) {
            SoundPlayer.playSound("sounds/level_up.wav");
            this.LevelUp();
        }
    }

    public void LevelUp(){
        super.LevelUp();
        arrowscnt = arrowscnt + 10*getLevel();
        setAttack(getAttack()+2*getLevel());
        setDefense(getDefense()+getLevel());
    }

    @Override
    public String describe() {
        String description = "";
        description += getName() + "          ";
        description += "Health: " + getHp() + "/" + getMaxHp() + "          ";
        description += "Attack: " + getAttack() + "          ";
        description += "Defense: " + getDefense() + "          ";
        description += "Level: " + getLevel() + "          ";
        description += "Experience: " + getExperience() + "/" + (50*getLevel()) + "          ";
        description += "Arrows: " + getArrowscnt() + "          ";
        description += "Range: " + getRange() + "          ";
        return description;
    }

    @Override
    public void gameTick() {
        if (tickscnt == 10 ) {
            arrowscnt = arrowscnt + getLevel();
            tickscnt = 0;
        }
        else
            tickscnt++;
    }

    @Override
    public void castAbility(Level level) {
        String message = (getName() + " fired an arrow at ");
        arrowscnt--;
        level.HunterAttack(this, message);
    }

    public String getSpellName(){return this.spellname;}


    public String canCastability() {
        if (arrowscnt > 0)
            return "";
        return(String.format("%s tried to shoot an arrow but there were no enemies in range.", getName()));
    }

    public int getArrowscnt() {return arrowscnt;}
}
