package game.tiles.units.player;

import game.Level;
import game.tiles.Tile;
import game.tiles.units.HeroicUnit;
import game.tiles.units.Unit;
import game.utils.Position;

public abstract class Player extends Unit implements HeroicUnit {
    private int experience;
    private int level;

    public Player(String name, Position position, int maxhp, int attack, int defense , int range){
        super(name, '@' ,position, maxhp, attack, defense, range);
        this.experience = 0;
        this.level = 1;

    }


    public int getExperience() {
        return experience;
    }
    public int getLevel() {
        return level;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
    public void setLevel(int level) {
        this.level = level;
    }


    protected void LevelUp(){
        this.experience -= 50*level;
        this.level += 1;
        setMaxHp(getMaxHp() + 10*level);
        setHp(getMaxHp());
        setAttack(getAttack() + 4 * level);
        setDefense(getDefense() + level);
    }


    public abstract String describe();

    @Override
    public boolean accept(Tile tile)
    {
        return tile.visit(this);
    }

    public abstract void gameTick();


    public abstract void castAbility(Level level);

    public abstract String canCastability();

    public abstract String getSpellName();

    public abstract void gainExperience(int xp);
}