package game.tiles;

import game.tiles.units.Unit;
import game.utils.Position;

public class Tile{
    private Position position;
    private char tile;

    public Tile(Position position, char tile){
        this.position = position;
        this.tile = tile;
    }

    public Position getPosition(){
        return position;
    }

    public char getTile(){
        return tile;
    }

    public void setTile(char tile){
        this.tile = tile;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    @Override
    public String toString(){
        return String.valueOf(tile);
    }

    public boolean accept(Unit unit){
        return false;
    };

}
