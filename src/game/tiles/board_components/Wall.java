package game.tiles.board_components;

import game.tiles.Tile;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;
import game.utils.Position;

public class Wall extends Tile {
     public Wall(Position position) {
         super(position, '#');
     }

    @Override
    public boolean accept(Tile tile){
        return tile.visit(this);
    }

    @Override
    public boolean visit(Player player){return false;}
    @Override
    public boolean visit(Enemy enemy){return false;}
    @Override
    public boolean visit(Wall wall){return false;}
    @Override
    public boolean visit(Empty empty){return false;}
}
