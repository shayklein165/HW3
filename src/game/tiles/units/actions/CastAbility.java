package game.tiles.units.actions;

import game.tiles.Tile;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;

import java.util.List;

public class CastAbility {

    public CastAbility() {
    }

    public void executeAbility(Player player) {
        List<Enemy> enemies = player.SelectEnemyInRange();
        if(enemies.isEmpty()){
            return;
        }

        for(Enemy e: enemies){
            int damage = 10 + player.getLevel(); // Example of scaling. need to be changed.
            e.reciveDamage(damage);
        }

        player.setMana(player.getMana() - 10); // Example of cost. need to be changed.
    }

    public boolean isAbleToCast(Player player){
        return player.getMana() >= 10 && !player.SelectEnemyInRange().isEmpty();
    }

}
