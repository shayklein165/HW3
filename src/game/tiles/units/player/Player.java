package game.tiles.units.player;

import game.callbacks.MessageCallback;
import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.Mana;
import game.tiles.units.Unit;
import game.tiles.units.actions.CastAbility;
import game.tiles.units.actions.Movement;
import game.tiles.units.enemies.Enemy;
import game.utils.Position;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class Player extends Unit {
    private int experience;
    private int level;
    private Mana mana;



    public Player(String name, Position position, int maxhp, int manaPool, int attack, int defense , int range, MessageCallback messageCallback){
        super(name, '@' ,position, maxhp, attack, defense, range, messageCallback);
        this.experience = 0;
        this.level = 1;
        this.mana = new Mana(manaPool);
    }

    public int getMana() {
        return mana.getCurrmana();
    }

    public int getManaPool() {
        return mana.getMaxMana();
    }


    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public void setMana(int mana) {
        this.mana.setCurrmana(mana);
    }

    public void setManaPool(int manaPool) {
        this.mana.setMaxMana(manaPool);
    }

    protected void LevelUp(){
        this.experience -= 50*level;
        this.level += 1;
        setMaxHp(getMaxHp() + 10*level);
        setHp(getMaxHp());
        setAttack(4 * level);
        setDefense(level);
    }

    public List<Enemy> SelectEnemyInRange(){
        //throw new ExecutionControl.NotImplementedException("");
        List<Enemy> ret = new ArrayList<>();
        return ret;
    }

    public void move(char action, Tile[][] board){
        Position newPosition = Movement.getNewPosition(this.getPosition(), action);
        if(!inBounds(newPosition,board)){
            messageCallback.send(getName() + " can't move to " + newPosition.toString());
            return;
        }
        Tile tile = board[newPosition.getX()][newPosition.getY()];
        tile.accept(this);
    }

    public void castAbility(Tile[][] board){
        new CastAbility(board).executeAbility(this);
        messageCallback.send(getName() + " casts a spell");
    }

    protected boolean inBounds(Position position, Tile[][] board){
        return position.getX()>=0 && position.getX()<board.length && position.getY()>=0 && position.getY()<board[0].length;
    }

    public void visit(Empty empty, Tile[][] board){
        Position newPosition = empty.getPosition();
        Position oldPosition = this.getPosition();
        
        board[oldPosition.getX()][oldPosition.getY()] = empty;
        board[newPosition.getX()][newPosition.getY()] = this;
        this.setPosition(newPosition);
        empty.setPosition(oldPosition);
        
        messageCallback.send(getName() + " moved to " + newPosition.toString());
    }
    
    public void visit(Wall wall, Tile[][] board){
        messageCallback.send(getName() + " can't move to " + wall.getPosition().toString());
    }


    public void visit(Enemy enemy, Tile[][] board){
        this.interact(enemy, board);
    }

    public void interact(Enemy enemy, Tile[][] board) {
        int attackRoll = (int) (Math.random() * getAttack());
        int defenseRoll = (int) (Math.random() * enemy.getDefense());
        int damage = Math.max(attackRoll-defenseRoll,0);

        messageCallback.send(getName()+ " attack " + enemy.getName() + " for " + damage + " damage");
        enemy.reciveDamage(damage);

        if(!enemy.isAlive()){
            messageCallback.send(enemy.getName()+" is dead");
            board[enemy.getPosition().getX()][enemy.getPosition().getY()] = new Empty(enemy.getPosition());
            this.experience += enemy.getExperience();
            if(this.experience >= 50*level){
                LevelUp();
            }
        }
    }

    public void reciveDamage(int damage) {
        setHp(getHp() - damage);
        if(getHp() < 0){
            setHp(0);
        }
    }

    public boolean isAlive(){
        return getHp() > 0;
    }
}
