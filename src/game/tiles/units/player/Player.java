package game.tiles.units.player;

import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.Unit;
import game.utils.Position;

public abstract class Player extends Unit {
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



    public void gainExperience(int xp) {
        experience += xp;
        while (experience >= 50 * level) {
            LevelUp();
        }
    }

    public void reciveDamage(int damage) {
        setHp(getHp() - damage);
        if(getHp() < 0){
            setHp(0);
        }
    }

    public boolean isAlive(){
        return getHp() > 0;
    }

    public String describe(){
        String description = "";
        description += getName() + "         ";
        description += getHp() + "/" + getMaxHp() + "         ";

        return ""; // to remove the error
    }
    @Override
    public boolean accept(Tile tile)
    {
        return tile.visit(this);
    }

    public abstract void gameTick();



}