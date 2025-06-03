package game.tiles;

import game.utils.Position;

public class GameCharacter {
    private Position position;

    public GameCharacter(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }
}
