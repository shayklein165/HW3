package game.tiles.units.enemies;

import game.Level;
import game.board.ArrayGameBoard;
import game.tiles.units.player.Player;
import game.utils.Position;

import java.util.List;

public class Trap extends Enemy{
    private int visibility_time;
    private int invisibility_time;
    private int ticks_cnt;
    private boolean visible;
    private char visibletile;

    public Trap(String name, char tile, Position position, int maxhp, int attack, int defense,  int exp , int visibility_time, int invisibility_time, boolean visible) {
        super(name, tile, position, maxhp, attack, defense, 2, exp);
        this.visibility_time = visibility_time;
        this.invisibility_time = invisibility_time;
        this.ticks_cnt = 0;
        this.visible = visible;
        visibletile = tile;
        setCurrTile('.');
    }

    public boolean isVisible() {
        return visible;
    }

    public void state() {
        visible = (ticks_cnt < visibility_time);
        if (ticks_cnt == (visibility_time + invisibility_time))
            ticks_cnt = 0;
        else
            ticks_cnt++;
    }

    public char getVisibletile(){
        return visibletile;
    }

    public void setCurrTile(char c){
        setTile(c);
    }

    @Override
    public void Move(Level level){
        level.TrapAction(this);
    }

    @Override
    public String describe() {
        String description = "";
        description += getName() + "          ";
        description += "Health: " + getHp() + "/" + getMaxHp() + "          ";
        description += "Attack: " + getAttack() + "          ";
        description += "Defense: " + getDefense() + "          ";
        description += "Experience value: " + getExperience() + "          ";
        return description;
    }


}
