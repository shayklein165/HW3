package game.tiles.board_components;

import game.tiles.Tile;
import game.tiles.units.Unit;
import game.utils.Position;

public class Wall extends Tile {
     public Wall(Position position) {
         super(position, '#');
     }

     @Override
     public boolean accept(Unit unit){
         return unit.visit(this);
     }
}
