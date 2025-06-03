package game.tiles.units;

public class Mana {
    private int currmana;
    private int maxMana;

    public Mana(int maxMana) {
        this.currmana = maxMana;
        this.maxMana = maxMana;
    }

    public int getCurrmana() {
        return currmana;
    }

    public int getMaxMana() {
        return maxMana;
    }
    public void setCurrmana(int currmana) {
        this.currmana = currmana;
    }
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
}
