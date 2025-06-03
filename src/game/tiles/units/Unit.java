package game.tiles.units;

import game.utils.Position;

public class Unit {
    private String name;
    private Health health;
    private int attack;
    private int defense;
    private Position position;
    private int range;

    public Unit(String name, int maxhp, int attack, int defense, Position position, int range){
        this.name = name;
        health = new Health(maxhp, maxhp);
        this.attack = attack;
        this.defense = defense;
        this.position = position;
        this.range = range;
    }

    public String getName() {return name;}

    public int getHp() {
        return health.getHp();
    }

    public int getMaxHp() {
        return health.getMaxHp();
    }

    public int getAttack(){return attack;}

    public int getDefense(){return defense;}

    public void SetName(String name){
        this.name = name;
    }

    public void SetHp(int hp){
        health.setHp(hp);
    }

    public void SetMaxHp(int maxHp){
        health.setMaxHp(maxHp);
    }

    public void SetAttack(int attack){
        this.attack = attack;
    }

    public void SetDefense(int defense){
        this.defense = defense;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

}
