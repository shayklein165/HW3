package game.tiles.units.actions;

import game.tiles.board_components.Empty;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.player.Player;

public class Action {
    public Action(){

    }



    // here, the monster is the attacker. returns true if the enemy is dead. otherwise, false.


    // here, the player is the attacker. returns true if the enemy is dead. otherwise, false.
    public boolean attack(Player player, Enemy enemy){
        int attackRoll = (int) (Math.random() * player.getAttack());
        int defenseRoll = (int) (Math.random() * enemy.getDefense());
        int damage = Math.max(attackRoll-defenseRoll,0);

        enemy.reciveDamage(damage);

        if(enemy.isAlive()) {
            return true;
        }
        return false;
    }
}
