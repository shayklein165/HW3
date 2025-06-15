package game.tiles.units.player;
import game.Level;
import game.tiles.units.Mana;
import game.utils.Position;

public class Mage extends Player{
    private int costmana;
    private int spellpower;
    private int hitscnt;
    private Mana mana;


    public Mage(String name, Position position, int maxhp, int attack, int defense, int manaPool , int costmana, int spellpower, int hitscnt, int range) {
        super(name, position, maxhp, attack, defense, range);
        this.costmana = costmana;
        this.spellpower = spellpower;
        this.hitscnt = hitscnt;
        this.mana = new Mana(manaPool);
    }

    public int getCostmana()
    {
        return costmana;
    }

    public int getHitscnt(){
        return hitscnt;
    }

    public int getMana() {
        return mana.getCurrmana();
    }
    public int getManaPool() {
        return mana.getMaxMana();
    }
    public int getSpellpower() {
        return spellpower;
    }
    public void setSpellpower(int spellpower) {
        this.spellpower = spellpower;
    }
    public void setMana(int mana) {
        this.mana.setCurrmana(mana);
    }
    public void setManaPool(int manaPool) {
        this.mana.setMaxMana(manaPool);
    }

    public void setCostMana(int costmana){
        this.costmana = costmana;
    }

    public void setHitscnt(int hitscnt){
        this.hitscnt = hitscnt;
    }

    public void LeveUp(){
        LevelUp();
        setManaPool(getManaPool() + (25 * getLevel()));
        int currmana = getManaPool();
        currmana = Math.min(currmana, getMana() + (currmana/4));
        setMana(currmana);
        setSpellpower(getSpellpower() + (10 * getLevel()));
    }

    public void gameTick(){
        int currmana = getManaPool();
        currmana = Math.min(currmana, getMana() + getLevel());
        setMana(currmana);
    }

    public boolean canCastabilityCast(){
        if (mana.getCurrmana() >= costmana) {
            return true;
        }
        return false;
    }

    public String describe(){
        String description = "";
        description += getName() + "          ";
        description += getHp() + "/" + getMaxHp() + "          ";
        description += getAttack() + "          ";
        description += getDefense() + "          ";
        description += getLevel() + "          ";
        description += getExperience() + "/" + (50*getLevel()) + "          ";
        description += getMana() + "/" + getManaPool() + "          ";
        return description;
    }

    @Override
    public void castAbility(Level level){
        level.MageAttack(this);
    }

}
