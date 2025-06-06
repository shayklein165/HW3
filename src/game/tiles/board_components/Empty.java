package game.tiles.board_components;

import game.tiles.Tile;
import game.tiles.units.Unit;
import game.utils.Position;

public class Empty extends Tile {
    public Empty(Position position) {
        super(position, '.');
    }

    @Override
    public void accept(Unit unit){
        unit.visit(this);
    }
}
