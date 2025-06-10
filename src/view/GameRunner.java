package view;

import game.Level;
import game.tiles.units.player.Player;
import view.input.CommandLineInput;
import view.input.InputProvider;
import view.parser.FileParser;
import view.parser.TileFactory;

import java.io.File;
import java.util.*;

public class GameRunner {
    private Scanner scanner;
    private InputProvider inputProvider;
    private TileFactory tileFactory;

    private List<Level> levels;

    public GameRunner(){
        scanner = new Scanner(System.in);
        inputProvider = new CommandLineInput(scanner);
        tileFactory = new TileFactory();
    }

    public void initialize(String levelsDirectory){
        int idx = choosePlayer();
        Player player = tileFactory.listPlayers().get(idx); // need to be changed.

        FileParser parser = new FileParser(player);
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

    public void start(){
        for(Level currentLevel: levels){
            currentLevel.start();
            while(!currentLevel.won()){
                System.out.println(currentLevel);
                if(!currentLevel.processRound()){
                    System.out.println(currentLevel);
                    System.out.println("Game Over");
                    return;
                }
            }
        }
        System.out.println("You won!");
    }



    private int choosePlayer(){
        while(true){
            sendMessage("Select player: ");
            List<Player> playerList = tileFactory.listPlayers();
            for(int i=0; i<playerList.size();i++){
                sendMessage(String.format("%d. %s", i+1, playerList.get(i).describe()));
            }

            try {
                int selected = Integer.parseInt(scanner.next()) - 1;
                if (0 <= selected && selected < playerList.size()) {
                    sendMessage(String.format("You have selected: \n %s", playerList.get(selected).getName()));
                    return selected;
                }
            }
            catch (NumberFormatException e){
                System.out.println("Not a number.");
            }
        }
    }

    private void sendMessage(String message){
        System.out.println(message);
    }
}
