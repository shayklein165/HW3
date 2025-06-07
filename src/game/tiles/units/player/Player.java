package game.tiles.units.player;

import game.board.ArrayGameBoard;
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
    private final ArrayGameBoard arrayGameBoard;



    public Player(String name, Position position, int maxhp, int manaPool, int attack, int defense , int range, ArrayGameBoard arrayGameBoard){
        super(name, '@' ,position, maxhp, attack, defense, range);
        this.experience = 0;
        this.level = 1;
        this.mana = new Mana(manaPool);
        this.arrayGameBoard = arrayGameBoard;
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
        setAttack(getAttack() + 4 * level);
        setDefense(getDefense() + level);
    }

    public List<Enemy> SelectEnemyInRange() {
        List<Enemy> inRange = new ArrayList<>();
        for (Enemy enemy : arrayGameBoard.getEnemies()) {
            double distance = this.getPosition().Range(enemy.getPosition());
            if (distance <= this.getRange()) {
                inRange.add(enemy);
            }
        }
        return inRange;
    }


    public void move(char action){
        Position newPosition = Movement.getNewPosition(this.getPosition(), action);
        if(!inBounds(newPosition)){
            return;
        }
        Tile tile = arrayGameBoard.getBoard()[newPosition.getX()][newPosition.getY()];
        tile.accept(this);
    }

    public void castAbility(){
        new CastAbility().executeAbility(this);
    }

    protected boolean inBounds(Position position){
        return position.getX()>=0 && position.getX()<arrayGameBoard.getBoard().length && position.getY()>=0 && position.getY()<arrayGameBoard.getBoard()[0].length;
    }

    public void visit(Empty empty){
        Position newPosition = empty.getPosition();
        Position oldPosition = this.getPosition();

        arrayGameBoard.setTile(empty,oldPosition);
        arrayGameBoard.setTile(this, newPosition);

        this.setPosition(newPosition);
        empty.setPosition(oldPosition);
        
    }
    
    public void visit(Wall wall){
        return;
    }


    public void visit(Enemy enemy){
        this.attackEnemy(getRange(),enemy);
        if(!enemy.isAlive()){
            arrayGameBoard.setTile(this,enemy.getPosition());
            this.setPosition(enemy.getPosition());
        }
    }

    public void attackEnemy(int range,Enemy enemy) {
        int attackRoll = (int) (Math.random() * getAttack());
        int defenseRoll = (int) (Math.random() * enemy.getDefense());
        int damage = Math.max(attackRoll-defenseRoll,0);

        enemy.reciveDamage(damage);

        if(!enemy.isAlive()){
            arrayGameBoard.setTile(new Empty(enemy.getPosition()), enemy.getPosition());
            gainExperience(enemy.getExperience());
            arrayGameBoard.RemoveEnemy(enemy);
        }
    }

    public void gainExperience(int xp) {
        experience += xp;
        while (experience >= 50 * level) {
            LevelUp();
        }
    }

    public void reciveDamage(int damage) {
        setHp(getHp() - damage);
        if(getHp() < 0){
            setHp(0);
        }
        if (!isAlive()){
            arrayGameBoard.setTile(new Tile(getPosition(), 'X'), getPosition());
        }
    }

    public boolean isAlive(){
        return getHp() > 0;
    }
}
