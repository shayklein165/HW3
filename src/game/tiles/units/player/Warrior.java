package game.tiles.units.player;
import game.utils.Position;

public class Warrior extends Player{
    private int damage;
    private int abilityCooldown;
    private int remainingCooldown;

    public Warrior(String name, Position position, int maxhp, int attack, int defense, int abilityCooldown) {
        super(name, position, maxhp, attack, defense, 3);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
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

    @Override
    public void abilityCast(){
        if(!canCastAbility()){
            return;
        }
        this.remainingCooldown = this.abilityCooldown; // ???
        this.setHp(Math.min(this.getHp()+10*this.getDefense(),this.getMaxHp()));
    }

    public boolean canCastAbility(){
        return this.remainingCooldown <= 0;
    }

    public String describe(){
        String description = "";
        description += getName() + "          ";
        description += getHp() + "/" + getMaxHp() + "          ";
        description += getAttack() + "          ";
        description += getDefense() + "          ";
        description += getLevel() + "          ";
        description += getExperience() + "/" + (50*getLevel()) + "          ";
        description += getRemainingColldown() + "/" + getAbilityCooldown() + "          ";
        return description;
    }
}
