package game.tiles.units.enemies;

import game.Level;
import game.utils.Position;

public class Monster extends Enemy {

    public Monster(String name, char tile, Position position, int maxhp, int attack, int defense, int range, int exp) {
        super(name, tile, position, maxhp, attack, defense, range, exp);
    }


    @Override
    public void Move(Level level){
        level.MonsterMove(this);
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
