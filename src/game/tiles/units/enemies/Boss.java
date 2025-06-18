package game.tiles.units.enemies;

import game.Level;
import game.tiles.units.HeroicUnit;
import game.utils.Position;

public class Boss extends Enemy implements HeroicUnit {
    private int range;
    private int abilityFrequency;
    private int combatTicks;

    public Boss(String name, char tile, Position position, int maxhp, int attack, int defense, int range, int exp, int abilityFrequency) {
        super(name, tile, position, maxhp, attack, defense,  range, exp);
        this.range = range;
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    public int getRange() {return range;}

    public void setRange(int range) {this.range = range;}

    public int getAbilityFrequency() {return abilityFrequency;}

    public void setAbilityFrequency(int abilityFrequency) {this.abilityFrequency = abilityFrequency;}

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
        return "";
    }
}
