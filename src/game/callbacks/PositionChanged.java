package game.callbacks;

import game.utils.Position;

public interface PositionChanged {
    void call(Position from, Position to);
}