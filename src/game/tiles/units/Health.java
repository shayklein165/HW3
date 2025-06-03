package game.tiles.units;

public class Health {
    private int hp;
    private int maxHp;

    public Health(int hp, int maxHp) {
        this.maxHp = maxHp;
        this.hp = Math.min(hp, maxHp);
    }
    public int getHp() {
        return hp;
    }
    public int getMaxHp() {
        return maxHp;
    }
    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp);
    }
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}
