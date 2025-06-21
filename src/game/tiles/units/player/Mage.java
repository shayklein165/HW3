package game.tiles.units.player;
import game.Level;
import game.tiles.units.HeroicUnit;
import game.tiles.units.Mana;
import game.utils.Position;
import game.utils.SoundPlayer;

public class Mage extends Player {
    private int costmana;
    private int spellpower;
    private int hitscnt;
    private Mana mana;
    private String spellname;


    public Mage(String name, Position position, int maxhp, int attack, int defense, int manaPool , int costmana, int spellpower, int hitscnt, int range) {
        super(name, position, maxhp, attack, defense, range);
        this.costmana = costmana;
        this.spellpower = spellpower;
        this.hitscnt = hitscnt;
        this.mana = new Mana(manaPool/4, manaPool);
        this.spellname = "Blizzard";
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


    public void LevelUp(){
        super.LevelUp();
        setManaPool(getManaPool() + (25 * getLevel()));
        int currmana = getManaPool();
        currmana = Math.min(currmana, getMana() + (currmana/4));
        setMana(currmana);
        setSpellpower(getSpellpower() + (10 * getLevel()));
    }

    @Override
    public void gameTick(){
        int currmana = getManaPool();
        currmana = Math.min(currmana, getMana() + getLevel());
        setMana(currmana);
    }

    // returns an empty string if true.
    @Override
    public String canCastability(){
        if (mana.getCurrmana() >= costmana) {
            return "";
        }
        return(String.format("%s tried to cast %s, but there was not enough mana: %s", getName(), getSpellName(), getMana() + "/" + getCostmana()));
    }

    @Override
    public String describe(){
        String description = "";
        description += getName() + "          ";
        description += "Health: " + getHp() + "/" + getMaxHp() + "          ";
        description += "Attack: " + getAttack() + "          ";
        description += "Defense: " + getDefense() + "          ";
        description += "Level: " + getLevel() + "          ";
        description += "Experience: " + getExperience() + "/" + (50*getLevel()) + "          ";
        description += "Mana: " + getMana() + "/" + getManaPool() + "          ";
        description += "Spellpower: " + getSpellpower() + "          ";
        return description;
    }

    @Override
    public void gainExperience(int xp) {
        setExperience(getExperience() + xp);
        while (getExperience() >= 50 * getLevel()) {
            SoundPlayer.playSound("sounds/level_up.wav");
            this.LevelUp();
        }
    }

    @Override
    public void castAbility(Level level){
        String message = (getName()+" cast " + getSpellName()+".");
        setMana(getMana() - getCostmana());
        level.MageAttack(this, message);
    }

    @Override
    public String getSpellName() {
        return spellname;
    }
}
