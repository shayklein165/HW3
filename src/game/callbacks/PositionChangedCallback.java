package game.callbacks;

import game.utils.Position;

public interface PositionChangedCallback {
    void call(Position from, Position to);
}