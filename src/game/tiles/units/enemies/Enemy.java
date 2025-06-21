package game.tiles.units.enemies;

import game.Level;
import game.tiles.Tile;
import game.tiles.units.Unit;
import game.utils.Position;

public abstract class Enemy extends Unit {
    private int experience_val;

    public Enemy(String name, char tile, Position position, int maxhp, int attack, int defense, int range, int exp) {
        super(name, tile, position, maxhp, attack, defense,  range );
        experience_val = exp;
    }

    public int getExperience() {
        return experience_val;
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

    @Override
    public boolean accept(Tile tile)
    {
        return tile.visit(this);
    }

    public abstract void Move(Level level);

    public abstract String describe();
}