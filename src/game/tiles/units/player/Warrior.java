package game.tiles.units.player;
import game.Level;
import game.utils.Position;

public class Warrior extends Player{
    private int damage;
    private int abilityCooldown;
    private int remainingCooldown;
    private String spellname;

    public Warrior(String name, Position position, int maxhp, int attack, int defense, int abilityCooldown) {
        super(name, position, maxhp, attack, defense, 3);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
        this.spellname = "Avenger’s Shield";
    }


    @Override
    public void gameTick() {
        remainingCooldown+=1;
    }

    public int getDamage() {
        return damage;
    }
    public int getAbilityCooldown() {
        return abilityCooldown;
    }
    public int getRemainingColldown() {
        return remainingCooldown;
    }
    public void setRemainingColldown(int remainingCooldown) {
        this.remainingCooldown = remainingCooldown;
    }
    public void setAbilityCooldown(int abilityCooldown) {
        this.abilityCooldown = abilityCooldown;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }


    public void WlevelUp(){
        this.LevelUp();
        this.remainingCooldown = 0;
        this.setMaxHp(this.getMaxHp()+5*this.getLevel());
        this.setAttack(this.getAttack()+2*this.getLevel());
        this.setDefense(this.getDefense()+this.getLevel());
    }

    public void OnGameTick(){
        this.remainingCooldown--;
    }


    public boolean canCastAbility(){
        return this.remainingCooldown <= 0;
    }

    public String describe(){
        String description = "";
        description += getName() + "          ";
        description += "Health: " + getHp() + "/" + getMaxHp() + "          ";
        description += "Attack: " + getAttack() + "          ";
        description += "Defense: " + getDefense() + "          ";
        description += "Level: " + getLevel() + "          ";
        description += "Experience: " + getExperience() + "/" + (50*getLevel()) + "          ";
        description += "Cooldown: " +getRemainingColldown() + "/" + getAbilityCooldown() + "          ";
        return description;
    }

    @Override
    public void castAbility(Level level){
        level.WarriorAttack(this);
    }

    public String getSpellName(){return this.spellname;}
}
