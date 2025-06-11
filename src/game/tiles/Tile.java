package game.tiles;

import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;
import game.utils.Position;

public abstract class Tile implements Visited, Visitor {
    private Position position;
    private char tile;

    public Tile(Position position, char tile) {
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

    @Override
    public abstract boolean accept(Tile tile);

    @Override
    public abstract boolean visit(Player player);
    @Override
    public abstract boolean visit(Enemy enemy);
    @Override
    public abstract boolean visit(Wall wall);
    @Override
    public abstract boolean visit(Empty empty);

}
