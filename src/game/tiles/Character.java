package game.tiles;

import game.utils.Position;

public class Character {
    private Position position;

    public Character(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }
}
