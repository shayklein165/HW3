package game.tiles.units;

import game.utils.Resource;

public class Health extends Resource {

    public Health(int hp, int maxHp) {
        super(hp, maxHp);
    }
    public int getHp() {
        return getAmount();
    }
    public int getMaxHp() {
        return getCapacity();
    }

    public void setHp(int hp) {
        setAmount(hp);
    }
    public void setMaxHp(int maxHp) {
        setCapacity(maxHp);
    }
}
