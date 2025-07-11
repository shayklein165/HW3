package game;

import game.tiles.units.player.Player;
import game.utils.SoundPlayer;
import view.input.CommandLineInput;
import view.input.InputProvider;
import view.parser.FileParser;
import game.board.TileFactory;

import java.io.File;
import java.util.*;

public class GameRunner {
    private Scanner scanner;
    private InputProvider inputProvider;
    private TileFactory tileFactory;

    private List<Level> levels;

    public GameRunner(){
        scanner = new Scanner(System.in);
        tileFactory = new TileFactory();
    }

    public void initialize(String levelsDirectory) {
        int idx = choosePlayer();
        Player player = tileFactory.listPlayers().get(idx);

        FileParser parser = new FileParser(player, this::send);
        File root = new File(levelsDirectory);
        File[] files = root.listFiles();
        if(files == null || files.length == 0){
            System.out.println("No levels found.");
            return;
        }
        Arrays.sort(files, Comparator.comparing(File::getName)); // sorting by the levels

        levels = new ArrayList<>();
        for(File file: files){
            parser.parseLevel(file);
            levels.add(parser.interpret());
        }
    }

    public void start() {
        for(Level currentLevel: levels){
            SoundPlayer.playSound("game_start.wav");
            inputProvider = new CommandLineInput(scanner);
            currentLevel.start(this.inputProvider);
            while(!currentLevel.won()) {
                if(!currentLevel.processRound()){
                    currentLevel.gameDisplay();
                    System.out.println(currentLevel.getArrayGameBoard().getPlayer().describe());
                    System.out.println("Game Over");
                    return;
                }
            }
        }
        SoundPlayer.playSound("victory.wav");
        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
        }
        finally {
            System.out.println("You won!");
        }
    }



    private int choosePlayer(){
        while(true){
            send("Select player: ");
            List<Player> playerList = tileFactory.listPlayers();
            for(int i=0; i<playerList.size();i++){
                send(String.format("%d. %s", i+1, playerList.get(i).describe()));
            }

            try {
                int selected = Integer.parseInt(scanner.next()) - 1;
                if (0 <= selected && selected < playerList.size()) {
                    send(String.format("You have selected: \n %s", playerList.get(selected).getName()));
                    return selected;
                }
            }
            catch (NumberFormatException e){
                System.out.println("Not a number.");
            }
        }
    }

    private void send(String message){
        System.out.println(message);
    }
}
