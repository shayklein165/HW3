package game.tiles.units.player;

import game.utils.Position;

public class Player {
    private String name;
    private int experience;
    private int level;
    private int healthPool;
    private int currentHelath;
    private int mana;
    private int manaPool;
    private int attack;
    private int defense;
    private Position position;


    public Player(String name, Position position, int healthPool, int manaPool, int attack, int defense){
        this.experience = 0;
        this.level = 1;
        this.healthPool = healthPool;
        this.currentHelath = healthPool;
        this.manaPool = manaPool;
        this.mana = manaPool;
        this.defense = defense;
        this.attack = attack;
        this.name = name;
        this.position = position;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getCurrentHelath() {
        return currentHelath;
    }

    public int getMana() {
        return mana;
    }

    public int getManaPool() {
        return manaPool;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHealth(int health){
        this.currentHelath = health;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setHealthPool(int healthPool) {
        this.healthPool = healthPool;
    }

    public void setCurrentHelath(int currentHelath) {
        this.currentHelath = currentHelath;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
    }

    public void setAttack(int attack){
        this.attack = attack;
    }

    public void setDefense(int defense){
        this.defense = defense;
    }

    public void levelUp(){
        this.experience -= 50*level;
        this.level += 1;
        this.healthPool += 10*level;
        this.currentHelath = healthPool;
        this.attack += 4*level;
        this.defense += level;
    }
}
