package game.tiles.units.player;
import game.Level;
import game.utils.Position;


public class Hunter extends Player {

    private int arrowscnt;
    private int tickscnt;
    private String spellname;

    public Hunter(String name, Position position, int maxhp, int attack, int defense, int range) {
        super(name, position, maxhp, attack, defense, range);
        arrowscnt = 0;
        tickscnt = 0;
        spellname = "Shoot";
    }

    public void LevelUp(){
        LevelUp();
    }

    @Override
    public String describe() {
        return "";
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

    }

    public String getSpellName(){return this.spellname;}


}
