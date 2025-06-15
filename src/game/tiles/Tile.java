package game.tiles;

import game.callbacks.PositionChanged;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;
import game.utils.Position;

public abstract class Tile implements Visited, Visitor {
    private Position position;
    private char tile;
    private PositionChanged listener;

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
        Position lastposition = this.position;
        this.position = position;
        listener.call(this, lastposition, position);
    }

    public void swapPosition(Tile tile) {
        Position newPosition = tile.position;
        Position thisPosition = this.position;
        this.setPosition(newPosition);
        tile.setPosition(thisPosition);
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

    public void setListener(PositionChanged listener){
        this.listener = listener;
    }

    public void InitPosition(){
        this.position = new Position(0, 0);
    }

}
