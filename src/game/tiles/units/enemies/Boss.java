package game.tiles.units.enemies;

import game.Level;
import game.tiles.units.HeroicUnit;
import game.utils.Position;

public class Boss extends Enemy implements HeroicUnit {
    private int abilityFrequency;
    private int combatTicks;

    public Boss(String name, char tile, Position position, int maxhp, int attack, int defense, int range, int exp, int abilityFrequency) {
        super(name, tile, position, maxhp, attack, defense,  range, exp);
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    public void setCombatTicks(int combatTicks){this.combatTicks = combatTicks;}

    public int getCombatTicks(){return combatTicks;}


    @Override
    public void castAbility(Level level) {
        level.BossAttack(this);
    }

    public boolean canCast(){
        if (combatTicks == abilityFrequency){
            combatTicks = 0;
            return true;
        }
        return false;
    }

    @Override
    public void Move(Level level){
        level.BossMove(this);
    }

    @Override
    public String describe() {
        String description = "";
        description += getName() + "          ";
        description += "Health: " + getHp() + "/" + getMaxHp() + "          ";
        description += "Attack: " + getAttack() + "          ";
        description += "Defense: " + getDefense() + "          ";
        description += "Experience value: " + getExperience() + "          ";
        description += "Vision range: " + getRange() + "          ";
        return description;
    }
}
