package game.tiles.units.actions;

import game.utils.Position;

import java.util.Random;

public class Movement {
    private Position enemyPostition;
    private Position playerPostition;
    private double distance;
    private int enemyRange;

    public Movement(Position enemyPosition, Position playerPosition, int enemyRange) {
        this.enemyPostition = enemyPosition;
        this.playerPostition = playerPosition;
        this.distance = this.enemyPostition.Range(this.playerPostition);
        this.enemyRange = enemyRange;
    }

    public double getDistance() {
        return distance;
    }

    public Position getPlayerPostition(){
        return this.playerPostition;
    }

    public Position getEnemyPostition(){
        return this.enemyPostition;
    }

    public void setPlayerPostition(Position playerPosition){
        this.playerPostition = playerPosition;
    }

    public void setEnemyPostition(Position enemyPostition){
        this.enemyPostition = enemyPostition;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }


    public int Move(){
        // need to check if there is a wall. this method only choose a new movement.
        this.distance = this.enemyPostition.Range(this.playerPostition);
        int move;
        if(distance<enemyRange){
            move = this.followPlayer();
        }
        else{
            move = (int) (Math.random()*4);
        }
        return move;
    }

    // 0 = left, 1 = right, 2 = up, 3 = down
    public int followPlayer(){
        int distX = this.enemyPostition.getX()-this.playerPostition.getX();
        int distY = this.enemyPostition.getY()-this.playerPostition.getY();

        if(Math.abs(distX) > Math.abs(distY)){
            if(distX > 0){
                return 0;
            }
            else{
                return 1;
            }
        }
        else{
            if(distY > 0){
                return 2;
            }
            else{
                return 3;
            }
        }
    }
}
