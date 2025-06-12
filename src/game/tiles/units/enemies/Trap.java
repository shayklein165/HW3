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

    public int getVisibility_time() {
        return visibility_time;
    }

    public int getInvisibility_time() {
        return invisibility_time;
    }

    public int getTicks_cnt() {
        return ticks_cnt;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setTicks_cnt(int ticks_cnt) {
        this.ticks_cnt = ticks_cnt;
    }

    public void setInvisibility_time(int invisibility_time) {
        this.invisibility_time = invisibility_time;
    }

    public void setVisibility_time(int visibility_time) {
        this.visibility_time = visibility_time;
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


}
