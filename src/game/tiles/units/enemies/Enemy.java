package game.tiles.units.enemies;

import game.board.ArrayGameBoard;
import game.callbacks.MessageCallback;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.Unit;
import game.tiles.units.player.Player;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class Enemy extends Unit {
    private int experience_val;
    private ArrayGameBoard arrayGameBoard;

    public Enemy(String name, char tile, Position position, int maxhp, int attack, int defense, int range, int exp,ArrayGameBoard arrayGameBoard) {
        super(name, tile, position, maxhp, attack, defense,  range );
        this.arrayGameBoard = arrayGameBoard;
        experience_val = exp;
    }

    public int getExperience() {
        return experience_val;
    }

    public void setExperience(int experience) {
        this.experience_val = experience;
    }

    public Player SelectPlayer() {
        return arrayGameBoard.getPlayer();
    }

    @Override
    public void accept(Unit unit){
        unit.visit(this);
    };

    public void visit(Empty empty){}
    public void visit(Wall wall){}
    public void visit(Enemy enemy){}


    public void reciveDamage(int damage) {
        setHp(getHp() - damage);
        if(getHp() < 0){
            setHp(0);
        }
    }

    public boolean isAlive(){
        return getHp() > 0;
    }

    public void attack(Player player){
        int attackRoll = (int)(Math.random() * getAttack());
        int defenseRoll = (int)(Math.random() * player.getDefense());
        int damage = Math.max(attackRoll-defenseRoll,0);

        player.reciveDamage(damage);
    }


}
