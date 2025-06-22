package game.tiles.units;
import game.utils.Resource;

public class Mana extends Resource {

    public Mana(int currmana, int maxMana) {
        super(currmana, maxMana);
    }

    public int getCurrmana() {
        return getAmount();
    }

    public int getMaxMana() {
        return getCapacity();
    }

    public void setCurrmana(int currmana) {
        setAmount(currmana);
    }

    public void setMaxMana(int maxMana) {
        setCapacity(maxMana);
    }
}
