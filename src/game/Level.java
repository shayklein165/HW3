package game;

import game.board.ArrayGameBoard;
import game.callbacks.MessageCallback;
import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.units.actions.Movement;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.enemies.Trap;
import game.tiles.units.player.Mage;
import game.tiles.units.player.Player;
import game.tiles.units.player.Rogue;
import game.tiles.units.player.Warrior;
import game.utils.Position;
import view.input.InputProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    ArrayGameBoard arrayGameBoard;
    MessageCallback messageCallback;
    private InputProvider inputProvider;
    private Movement movement;

    public Level(ArrayGameBoard arrayGameBoard, MessageCallback messageCallback) {
        this.arrayGameBoard = arrayGameBoard;
        movement = new Movement(arrayGameBoard);
        this.messageCallback = messageCallback;
    }

    public void start(InputProvider input) {
        this.messageCallback.send("Starting a new Level..");
        this.inputProvider = input;
        this.arrayGameBoard.getPlayer().setPosition(this.arrayGameBoard.getInitialPlayerPosition());
        this.arrayGameBoard.getPlayer().setListener(arrayGameBoard);
    }

    public void setArrayGameBoard(ArrayGameBoard arrayGameBoard) {
        this.arrayGameBoard = arrayGameBoard;
    }

    public ArrayGameBoard getArrayGameBoard() {
        return arrayGameBoard;
    }


    // here, the monster is the attacker. returns true if the player is dead. otherwise, false.
    public boolean attack(Enemy enemy, Player player) {
        messageCallback.send(String.format("%s engaged in combat with %s.", enemy.getName(), player.getName()));
        messageCallback.send(enemy.describe());
        messageCallback.send(player.describe());

        int attackRoll = (int) (Math.random() * enemy.getAttack());
        int defenseRoll = (int) (Math.random() * player.getDefense());
        int damage = Math.max(attackRoll - defenseRoll, 0);

        messageCallback.send(String.format("%s rolled %d attack points.", enemy.getName(), attackRoll));
        messageCallback.send(String.format("%s rolled %d defense points.", player.getName(), defenseRoll));
        messageCallback.send(String.format("%s dealt %d damage to %s.", enemy.getName(), damage, player.getName()));

        player.reciveDamage(damage);

        if(!player.isAlive()){
            arrayGameBoard.KillPlayer();
            messageCallback.send(String.format("%s died. Game Over.", player.getName()));
            return true;
        }
        return false;
    }


    // here, the player is the attacker. returns true if the enemy is dead. otherwise, false.
    public boolean attack(Player player, Enemy enemy){
        messageCallback.send(String.format("%s engaged in combat with %s.", player.getName(), enemy.getName()));
        messageCallback.send(player.describe());
        messageCallback.send(enemy.describe());

        int attackRoll = (int) (Math.random() * player.getAttack());
        int defenseRoll = (int) (Math.random() * enemy.getDefense());
        int damage = Math.max(attackRoll - defenseRoll,0);

        messageCallback.send(String.format("%s rolled %d attack points.", player.getName(), attackRoll));
        messageCallback.send(String.format("%s rolled %d defense points.", enemy.getName(), defenseRoll));
        messageCallback.send(String.format("%s dealt %d damage to %s.", player.getName(), damage, enemy.getName()));

        enemy.reciveDamage(damage);
        boolean b = enemy.isAlive();
        if (!b){
            arrayGameBoard.RemoveEnemy(enemy);
            arrayGameBoard.setTile(new Empty(enemy.getPosition()),enemy.getPosition());
            arrayGameBoard.getBoard()[enemy.getPosition().getX()][enemy.getPosition().getY()].setListener(arrayGameBoard);
            player.gainExperience(enemy.getExperience());
            this.messageCallback.send(String.format("%s leveled up to level %d! and gained: +%d health, +%d attack, +%d defense", arrayGameBoard.getPlayer().getName(), arrayGameBoard.getPlayer().getLevel(), arrayGameBoard.getPlayer().getMaxHp(), arrayGameBoard.getPlayer().getAttack(), arrayGameBoard.getPlayer().getDefense()));


            messageCallback.send(String.format("%s died.", enemy.getName()));
            messageCallback.send(String.format("%s gained %d experience.", player.getName(), enemy.getExperience()));
        }
        return (!b);
    }

    public List<Enemy> SelectEnemyInRange() {
        List<Enemy> inRange = new ArrayList<>();
        for (Enemy enemy : arrayGameBoard.getEnemies()) {
            double distance = arrayGameBoard.getPlayer().getPosition().Range(enemy.getPosition());
            if (distance <= arrayGameBoard.getPlayer().getRange()) {
                inRange.add(enemy);
            }
        }
        return inRange;
    }

    public void TrapAction(Trap trap){
        Player p  = arrayGameBoard.getPlayer();
        trap.state();
        if (trap.InRange(p.getPosition())){
            attack(trap, p);
        }
        if (trap.isVisible())
            trap.setCurrTile(trap.getVisibletile());
        else
            trap.setCurrTile('.');
    }

    public boolean won() {
        return arrayGameBoard.getEnemies().isEmpty();
    }

    public void gameDisplay()
    {
        Tile[][] board = arrayGameBoard.getBoard();
        for (int i = 0; i < board[0].length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                System.out.print(board[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public Position getNewPosition(Position current, char direction) {
        return switch (direction) {
            case 'w' -> current.up();
            case 's' -> current.down();
            case 'a' -> current.left();
            case 'd' -> current.right();
            default -> current; // Invalid direction = no movement
        };
    }

    public void playerMove(char action){
        Position newPosition = getNewPosition(arrayGameBoard.getPlayer().getPosition(), action);
        if(!inBounds(newPosition)){
            return; // need to insert callback
        }

        Tile tile = arrayGameBoard.getBoard()[newPosition.getX()][newPosition.getY()];
        List<Enemy> enemies = arrayGameBoard.getEnemies();
        for(Enemy e: enemies){
            if(e.getPosition().equals(tile.getPosition())){
                attack(arrayGameBoard.getPlayer(), e);
                break;
            }
        }
        tile = arrayGameBoard.getBoard()[newPosition.getX()][newPosition.getY()];
        boolean isEmpty = tile.accept(arrayGameBoard.getPlayer());
        if(isEmpty){
            movement.makeMove(action, arrayGameBoard.getPlayer());
        }
    }

    protected boolean inBounds(Position position){
        return position.getX()>0 && position.getX()<arrayGameBoard.getBoard().length && position.getY()>0 && position.getY()<arrayGameBoard.getBoard()[0].length;
    }

    private boolean canMove(Monster monster){
        int i = arrayGameBoard.getBoard().length;
        int j = arrayGameBoard.getBoard()[0].length;
        Position position = monster.getPosition();
        boolean ans = false;
        if (position.getX() < i-1)
            ans = arrayGameBoard.getBoard()[position.getX()+1][position.getY()].accept(monster);
        if (!ans && position.getX() < j-1)
            ans = arrayGameBoard.getBoard()[position.getX()][position.getY()+1].accept(monster);
        if (!ans && position.getX() > 0)
            ans = arrayGameBoard.getBoard()[position.getX()-1][position.getY()].accept(monster);
        if (!ans && position.getX() > 0)
            ans = arrayGameBoard.getBoard()[position.getX()][position.getY()-1].accept(monster);
        return ans;
    }

    public void MonsterMove(Monster monster){
        Position newPosition;
        boolean isEmpty;
        char move;
        List<Enemy> enemies = arrayGameBoard.getEnemies();

        if(!canMove(monster))
            return;

        do {
            move = movement.MonsterChooseMove(monster);
            newPosition = getNewPosition(monster.getPosition(), move);
            isEmpty = true;
            for(Enemy e: enemies) {
                if (e.getPosition().equals(newPosition)) {
                    isEmpty = false;
                }
            }
        } while (!inBounds(newPosition));

        Player p = arrayGameBoard.getPlayer();
        if(newPosition.equals(p.getPosition())){
            attack(monster, p);
        }

        isEmpty = arrayGameBoard.getBoard()[newPosition.getX()][newPosition.getY()].accept(monster);
        if(isEmpty){
            movement.makeMove(move, monster);
        }
    }

    public boolean processRound()
    {
        gameDisplay();
        arrayGameBoard.getPlayer().describe();
        char move = inputProvider.inputQuery();
        if (movement.Contains(move)) {
            playerMove(move);
        }
        else if (move == 'q') {
            castAbility(arrayGameBoard.getPlayer());
        }

        List<Enemy> enemies= arrayGameBoard.getEnemies();
        for(Enemy e: enemies)
        {
            EnemyMove(e);
        }
        arrayGameBoard.getPlayer().gameTick();
        return arrayGameBoard.getPlayer().isAlive();
    }

    private void castAbility(Player player) {
        player.castAbility(this);
    }

    private void EnemyMove(Enemy e) {
        e.Move(this);
    }



    public void WarriorAttack(Warrior warrior){
        if (!warrior.canCastAbility())
            return;
        Random rnd = new Random();
        int i = 0;
        List<Enemy> list = SelectEnemyInRange();
        i = rnd.nextInt(list.size());
        Enemy e = list.get(i);
        e.reciveDamage(e.getHp() - warrior.getMaxHp()/10);
        if (!e.isAlive()) {
            messageCallback.send(String.format("%s died %s gained %d experience", e.getName(), warrior.getName() ,e.getExperience()));
            arrayGameBoard.RemoveEnemy(e);
            arrayGameBoard.setTile(new Empty(e.getPosition()), e.getPosition());
        }
        warrior.setHp(Math.min(warrior.getHp() + (10 * warrior.getDefense()), warrior.getMaxHp()));
        warrior.setRemainingColldown(warrior.getAbilityCooldown());
    }

    public void MageAttack(Mage mage){
        if (!mage.canCastabilityCast())
            return;
        Random rnd = new Random();
        int i = 0;
        List<Enemy> list = SelectEnemyInRange();
        mage.setMana(mage.getMana() - mage.getCostmana());
        int hits = 0;
        while (hits < mage.getHitscnt()) { // need to implement the check if any enemy exist in rang
            i = rnd.nextInt(list.size());
            Enemy e = list.get(i);
            e.reciveDamage(e.getHp() - mage.getSpellpower());
            if (!e.isAlive()) {
                messageCallback.send(String.format("%s died %s gained %d experience", e.getName(), mage.getName() ,e.getExperience()));
                arrayGameBoard.RemoveEnemy(e);
                arrayGameBoard.setTile(new Empty(e.getPosition()), e.getPosition());
            }
            hits++;
        }
    }

    public void RogueAttack(Rogue rogue){
        if(!rogue.CanCastAbility())
            return;
        rogue.setCurrentEnergy(rogue.getCurrentEnergy()- rogue.getEnergycost());
        List<Enemy> EnemyInRange = this.SelectEnemyInRange();
        for (Enemy e : EnemyInRange){
            int defenseRoll = (int) (Math.random() * e.getDefense());
            if (rogue.getAttack() > defenseRoll)
                e.reciveDamage(rogue.getAttack() - defenseRoll);
            if (!e.isAlive()) {
                messageCallback.send(String.format("%s died %s gained %d experience", e.getName(), rogue.getName() ,e.getExperience()));
                arrayGameBoard.RemoveEnemy(e);
                arrayGameBoard.setTile(new Empty(e.getPosition()), e.getPosition());
            }
        }
    }


}
