package game.callbacks;

import game.tiles.Tile;
import game.utils.Position;

public interface PositionChanged {
    void call(Tile tile, Position from, Position to);
}