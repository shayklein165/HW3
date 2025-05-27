package game.tiles;

import game.utils.Position;

public class Tile {
    private Position position;
    private char tile;

    @Override
    public String toString(){
        return String.valueOf(tile);
    }
}
