package game.tiles;

import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;

public interface Visitor {
    public boolean visit(Player player);
    public boolean visit(Enemy enemy);
    public boolean visit(Wall wall);
    public boolean visit(Empty empty);
}
