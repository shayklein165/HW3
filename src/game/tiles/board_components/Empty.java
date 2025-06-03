package game.tiles.board_components;

import game.tiles.Tile;
import game.utils.Position;

public class Empty extends Tile {
    public Empty(Position position) {
        super(position, '.');
    }
}
