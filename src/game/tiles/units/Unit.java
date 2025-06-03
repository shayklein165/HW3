package game.tiles.units;

public class Unit {
    private String name;
    private Health health;
    private int attack;
    private int defense;

    public Unit(String name, int maxhp, int attack, int defense){
        this.name = name;
        health = new Health(maxhp, maxhp);
        this.attack = attack;
        this.defense = defense;
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

}
