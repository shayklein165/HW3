package game.tiles.units.actions;

import game.Level;
import game.board.ArrayGameBoard;
import game.tiles.Tile;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;

import java.util.List;

public class CastAbility {

    public CastAbility(ArrayGameBoard arrayGameBoard) {
    }

    public void executeAbility(Player player) {
        List<Enemy> enemies = Level.;
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
