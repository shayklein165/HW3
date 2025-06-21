package game.tiles.units;

import game.tiles.Tile;
import game.tiles.Visited;
import game.tiles.Visitor;
import game.tiles.board_components.Empty;
import game.tiles.board_components.Wall;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.player.Player;
import game.utils.Position;

public abstract class Unit extends Tile {
    private String name;
    private Health health;
    private int attack;
    private int defense;
    private int range;

    public Unit(String name, char tile, Position position, int maxhp, int attack, int defense,  int range){
        super(position, tile);
        this.name = name;
        health = new Health(maxhp, maxhp);
        this.attack = attack;
        this.defense = defense;
        this.range = range;
    }

    public String getName() {return name;}

    public int getHp() {
        return health.getHp();
    }

    public int getMaxHp() {
        return health.getMaxHp();
    }

    public int getAttack(){return attack;}

    public int getDefense(){return defense;}

    public void setHp(int hp){
        health.setHp(hp);
    }

    public void setMaxHp(int maxHp){
        health.setMaxHp(maxHp);
    }

    public void setAttack(int attack){
        this.attack = attack;
    }

    public void setDefense(int defense){
        this.defense = defense;
    }

    public int getRange() {
        return range;
    }

    @Override
    public abstract boolean accept(Tile tile);

    @Override
    public boolean visit(Player player) {
        return false;
    }

    @Override
    public boolean visit(Enemy enemy) {
        return false;
    }

    @Override
    public boolean visit(Wall wall) {
        return false;
    }

    @Override
    public boolean visit(Empty empty) {
        return true;
    }

    public boolean InRange(Position position1){
        double dx = position1.getX() - this.getPosition().getX();
        double dy = position1.getY() - this.getPosition().getY();
        return (range * range >= dx * dx + dy * dy);
    }
}
