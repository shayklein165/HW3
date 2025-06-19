package game.tiles.units.actions;

import game.board.ArrayGameBoard;
import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.units.Unit;
import game.tiles.units.enemies.Boss;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.player.Player;
import game.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Movement {
    private ArrayGameBoard arrayGameBoard;
    private ArrayList<Character> moves;

    public Movement(ArrayGameBoard arrayGameBoard) {
        this.arrayGameBoard = arrayGameBoard;
        this.moves = new ArrayList<>();
        this.moves.add('a');
        this.moves.add('d');
        this.moves.add('w');
        this.moves.add('s');
    }

    public void Up(Unit unit){
        Position position = unit.getPosition().up();
        extracted(unit, position);
    }

    private void extracted(Unit unit, Position position) {
        unit.swapPosition(arrayGameBoard.getTile(position));
    }

    public void Down(Unit unit){
        Position position = unit.getPosition().down();
        unit.swapPosition(arrayGameBoard.getTile(position));
    }

    public void Left(Unit unit){
        Position position = unit.getPosition().left();
        unit.swapPosition(arrayGameBoard.getTile(position));
    }

    public void Right(Unit unit){
        Position position = unit.getPosition().right();
        unit.swapPosition(arrayGameBoard.getTile(position));
    }

    public boolean Contains(char c){
        return moves.contains(c);
    }


    public void makeMove(char action, Unit unit) {
        if(action == 'a'){
            Left(unit);
        }
        else if(action == 'd'){
            Right(unit);
        }
        else if(action == 'w'){
            Up(unit);
        }
        else if(action == 's'){
            Down(unit);
        }
    }

    public char RandomMove() {
        Random rnd = new Random();
        return moves.get(rnd.nextInt(moves.size()));
    }

    public char MonsterChooseMove(Monster monster){
        char move;
        if(monster.InRange(arrayGameBoard.getPlayer().getPosition())){
            move = followPlayer(monster);
        }
        else{
            move = RandomMove();
        }
        return move;
    }

    public char BossChooseMove(Boss boss){
        char move;
        if(boss.InRange(arrayGameBoard.getPlayer().getPosition())){
            move = followPlayer(boss);
        }
        else{
            Random rnd = new Random();
            int m = rnd.nextInt(moves.size()+1);
            if(m == moves.size())
                move = 'n';
            else
                move = moves.get(m);
        }
        return move;
    }

    // 0 = left, 1 = right, 2 = up, 3 = down
    public char followPlayer(Enemy enemy){
        int distX = enemy.getPosition().getX() - arrayGameBoard.getPlayer().getPosition().getX();
        int distY = enemy.getPosition().getY() - arrayGameBoard.getPlayer().getPosition().getY();

        if(Math.abs(distX) > Math.abs(distY)){
            if(distX > 0){
                return 'a';
            }
            else{
                return 'd';
            }
        }
        else{
            if(distY > 0){
                return 'w';
            }
            else{
                return 's';
            }
        }
    }
}