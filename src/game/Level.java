package game;

import game.board.ArrayGameBoard;
import game.callbacks.MessageCallback;
import game.tiles.Tile;
import game.tiles.board_components.Empty;
import game.tiles.units.actions.Movement;
import game.tiles.units.enemies.Boss;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.enemies.Monster;
import game.tiles.units.enemies.Trap;
import game.tiles.units.player.*;
import game.utils.Position;
import game.utils.SoundPlayer;
import view.input.InputProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private ArrayGameBoard arrayGameBoard;
    private MessageCallback messageCallback;
    private InputProvider inputProvider;
    private Movement movement;

    public Level(ArrayGameBoard arrayGameBoard, MessageCallback messageCallback) {
        this.arrayGameBoard = arrayGameBoard;
        movement = new Movement(arrayGameBoard);
        this.messageCallback = messageCallback;
    }

    public void start(InputProvider input) {
        this.inputProvider = input;
        this.arrayGameBoard.getPlayer().setListener(arrayGameBoard);
        this.arrayGameBoard.getPlayer().setPosition(this.arrayGameBoard.getInitialPlayerPosition());
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
            messageCallback.send(String.format("%s was killed by %s.", player.getName(),enemy.getName()));
            messageCallback.send("you lost.");
            SoundPlayer.playSound("game_over.wav");
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return true;
        }
        return false;
    }


    // here, the player is the attacker. returns true if the enemy is dead. otherwise, false.
    public boolean attack(Player player, Enemy enemy){
        SoundPlayer.playSound("attack.wav");
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
            String message = player.gainExperience(enemy.getExperience());
            if(!message.isEmpty()){
                messageCallback.send(message);
            }

            messageCallback.send(String.format("%s died.", enemy.getName()));
            messageCallback.send(String.format("%s gained %d experience.", player.getName(), enemy.getExperience()));
            SoundPlayer.playSound("enemy_death.wav");
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
        Player player = arrayGameBoard.getPlayer();

        Position newPosition = getNewPosition(arrayGameBoard.getPlayer().getPosition(), action);
        if(!inBounds(newPosition)){
            messageCallback.send(String.format("%s can't move in direction %c.", player.getName(), action));
        }
        player.gameTick();
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

    private boolean inBounds(Position position){
        return position.getX()>0 && position.getX()<arrayGameBoard.getBoard().length && position.getY()>0 && position.getY()<arrayGameBoard.getBoard()[0].length;
    }

    private boolean canMove(Enemy enemy){
        int i = arrayGameBoard.getBoard().length;
        int j = arrayGameBoard.getBoard()[0].length;
        Position position = enemy.getPosition();
        boolean ans = false;
        if (position.getX() < i-1)
            ans = arrayGameBoard.getBoard()[position.getX()+1][position.getY()].accept(enemy);
        if (!ans && position.getX() < j-1)
            ans = arrayGameBoard.getBoard()[position.getX()][position.getY()+1].accept(enemy);
        if (!ans && position.getX() > 0)
            ans = arrayGameBoard.getBoard()[position.getX()-1][position.getY()].accept(enemy);
        if (!ans && position.getX() > 0)
            ans = arrayGameBoard.getBoard()[position.getX()][position.getY()-1].accept(enemy);
        return ans;
    }

    public void MonsterMove(Monster monster){
        Position newPosition;
        boolean isEmpty;
        char move;

        if(!canMove(monster))
            return;

        do {
            move = movement.MonsterChooseMove(monster);
            newPosition = getNewPosition(monster.getPosition(), move);
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
        messageCallback.send(arrayGameBoard.getPlayer().describe());
        char move = inputProvider.inputQuery();
        if (movement.Contains(move)) {
            playerMove(move);
        }
        else if (move == 'e') {
            castAbility(arrayGameBoard.getPlayer());

        }

        List<Enemy> enemies= arrayGameBoard.getEnemies();
        for(Enemy e: enemies)
        {
            if(arrayGameBoard.getPlayer().isAlive()){
                EnemyMove(e);
            }
        }
        return arrayGameBoard.getPlayer().isAlive();
    }

    public void castAbility(Player player) {
        String canCast = arrayGameBoard.getPlayer().canCastability();
        if(canCast.isEmpty()) {
            SoundPlayer.playSound("cast_ability.wav");
            player.castAbility(this);
        }
        else{
            messageCallback.send(canCast);
        }
    }

    public void EnemyMove(Enemy e) {
        e.Move(this);
    }


    public void WarriorAttack(Warrior warrior, String message){
        messageCallback.send(message);
        Random rnd = new Random();
        int i = 0;
        List<Enemy> list = SelectEnemyInRange();
        if (list.isEmpty()) {
            return;
        }
        i = rnd.nextInt(list.size());
        Enemy e = list.get(i);
        e.reciveDamage(warrior.getMaxHp()/10);
        // the enemy will try to defend itself?
        // need to insert callback?
        if (!e.isAlive()) {
            messageCallback.send(String.format("%s died %s gained %d experience", e.getName(), warrior.getName() ,e.getExperience()));
            warrior.gainExperience(e.getExperience());
            arrayGameBoard.RemoveEnemy(e);
            arrayGameBoard.setTile(new Empty(e.getPosition()), e.getPosition());
            SoundPlayer.playSound("enemy_death.wav");
        }

    }

    public void MageAttack(Mage mage, String message){
        messageCallback.send(message);
        Random rnd = new Random();
        int i = 0;
        List<Enemy> list = SelectEnemyInRange();
        if (list.isEmpty()) {
            return;
        }

        int hits = 0;
        while (hits < mage.getHitscnt()) {
            i = rnd.nextInt(list.size());
            Enemy e = list.get(i);
            int defenseRoll = (int) (Math.random() * e.getDefense());
            e.reciveDamage(mage.getSpellpower()-defenseRoll);
            messageCallback.send(e.getName()+ " rolled "+defenseRoll+" defense points.");
            messageCallback.send(mage.getName() + " hit " + e.getName() + " for " + (mage.getSpellpower()-defenseRoll) + " ability damage.");

            if (!e.isAlive()) {
                messageCallback.send(String.format("%s died. %s gained %d experience.", e.getName(), mage.getName() ,e.getExperience()));
                mage.gainExperience(e.getExperience());
                arrayGameBoard.RemoveEnemy(e);
                arrayGameBoard.setTile(new Empty(e.getPosition()), e.getPosition());
                SoundPlayer.playSound("enemy_death.wav");
            }
            hits++;
        }
    }

    public void RogueAttack(Rogue rogue, String message){
        messageCallback.send(message);
        List<Enemy> EnemyInRange = this.SelectEnemyInRange();
        if (EnemyInRange.isEmpty()){
            return;
        }

        for (Enemy e : EnemyInRange){
            int defenseRoll = (int) (Math.random() * e.getDefense());
            if (rogue.getAttack() > defenseRoll)
                e.reciveDamage(rogue.getAttack() - defenseRoll);
            messageCallback.send(e.getName()+ " rolled "+defenseRoll+" defense points.");
            messageCallback.send(rogue.getName() + " hit " + e.getName() + " for " + (rogue.getAttack() - defenseRoll)+" ability damage.");

            if (!e.isAlive()) {
                messageCallback.send(String.format("%s died %s gained %d experience", e.getName(), rogue.getName() ,e.getExperience()));
                rogue.gainExperience(e.getExperience());
                arrayGameBoard.RemoveEnemy(e);
                arrayGameBoard.setTile(new Empty(e.getPosition()), e.getPosition());
                SoundPlayer.playSound("enemy_death.wav");
            }
        }
    }


    public void HunterAttack(Hunter hunter, String message) {
        List<Enemy> list = SelectEnemyInRange();
        if(list.isEmpty()){
            return;
        }
        Enemy closeste = list.getFirst();
        double range = hunter.getPosition().Range(closeste.getPosition());
        for (Enemy e : list) {
            if (e.getPosition().Range(hunter.getPosition()) < range) {
                closeste = e;
                range = e.getPosition().Range(hunter.getPosition());
            }
        }
        messageCallback.send(message+closeste.getName());
        int defenseRoll = (int) (Math.random() * closeste.getDefense());
        if (hunter.getAttack() > defenseRoll)
            closeste.reciveDamage(hunter.getAttack() - defenseRoll);
        if (!closeste.isAlive()) {
            messageCallback.send(String.format("%s died %s gained %d experience", closeste.getName(), hunter.getName() ,closeste.getExperience()));
            hunter.gainExperience(closeste.getExperience());
            arrayGameBoard.RemoveEnemy(closeste);
            arrayGameBoard.setTile(new Empty(closeste.getPosition()), closeste.getPosition());
            SoundPlayer.playSound("enemy_death.wav");
        }
    }

    public void BossMove(Boss boss) {
        Position newPosition;
        boolean isEmpty;
        char move;

        if(boss.InRange(arrayGameBoard.getPlayer().getPosition()) && boss.canCast()){
            boss.castAbility(this);
        }
        else{ // else, make a move
            if(!canMove(boss))
                return;

            if(boss.InRange(arrayGameBoard.getPlayer().getPosition())){
                boss.setCombatTicks(boss.getCombatTicks() + 1);
            }
            else{
                boss.setCombatTicks(0);
            }

            do {
                move = movement.BossChooseMove(boss);
                if(move == 'n'){
                    return;
                }
                newPosition = getNewPosition(boss.getPosition(), move);
            } while (!inBounds(newPosition));


            isEmpty = arrayGameBoard.getBoard()[newPosition.getX()][newPosition.getY()].accept(boss);
            if(isEmpty){
                movement.makeMove(move, boss);
            }
        }

    }

    public void BossAttack(Boss boss) {
        int defenseRoll = (int) (Math.random() * arrayGameBoard.getPlayer().getDefense());
        arrayGameBoard.getPlayer().reciveDamage(Math.max(boss.getAttack() - defenseRoll,0));

        messageCallback.send(String.format("%s shoots %s for %s damage.", boss.getName(),arrayGameBoard.getPlayer().getName(), boss.getAttack()));
        messageCallback.send(String.format("%s rolled %s defense points.", arrayGameBoard.getPlayer().getName(), defenseRoll));
        messageCallback.send(String.format("%s hit %s for %s ability damage.", boss.getName(), arrayGameBoard.getPlayer().getName(), boss.getAttack()-defenseRoll));

        if(!arrayGameBoard.getPlayer().isAlive()){
            SoundPlayer.playSound("game_over.wav");
            arrayGameBoard.KillPlayer();
            messageCallback.send(String.format("%s was killed by %s.", arrayGameBoard.getPlayer().getName(),boss.getName()));
            messageCallback.send("you lost.");
        }
    }

    public void RandomKillerAttack(RandomKiller randomKiller, String message) {
        List<Enemy> list = arrayGameBoard.getEnemies();
        if(list.isEmpty()){
            return;
        }
        int i = (int) (Math.random() * list.size());
        Enemy e = list.get(i);
        messageCallback.send(message+e.getName());
        messageCallback.send(String.format("%s died %s gained %d experience", e.getName(), randomKiller.getName() ,e.getExperience()));
        randomKiller.gainExperience(e.getExperience());
        arrayGameBoard.RemoveEnemy(e);
        arrayGameBoard.setTile(new Empty(e.getPosition()), e.getPosition());
        SoundPlayer.playSound("enemy_death.wav");
    }

    public void HealerAttack(Healer healer, String message) {
        List<Enemy> list = arrayGameBoard.getEnemies();
        List<Enemy> dead = new ArrayList<>();
        if(list.isEmpty()){
            return;
        }
        int sum = 0;
        messageCallback.send(String.format("%s dealt %d damage to all enemies.", healer.getName(), healer.getAttack()/10));
        for (Enemy e : list) {
            sum += 1;
            e.reciveDamage(healer.getAttack()/10);
            if (!e.isAlive()){
                messageCallback.send(String.format("%s died %s gained %d experience", e.getName(), healer.getName() ,e.getExperience()));
                healer.gainExperience(e.getExperience());
                dead.add(e);
                arrayGameBoard.setTile(new Empty(e.getPosition()), e.getPosition());
                SoundPlayer.playSound("enemy_death.wav");
            }
        }
        for (Enemy e : dead)
            arrayGameBoard.RemoveEnemy(e);
        healer.setHp(healer.getHp()+ sum);
    }
}
