package game.tiles.board_components;

import game.tiles.Tile;
import game.utils.Position;

public class Wall extends Tile {
     public Wall(Position position) {
         super(position, '#');
     }
}
