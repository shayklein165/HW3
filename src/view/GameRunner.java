package view;

import game.Level;
import game.tiles.units.player.Player;
import view.input.CommandLineInput;
import view.input.InputProvider;
import view.parser.FileParser;
import view.parser.TileFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        FileParser parser = new FileParser(tileFactory, this::sendMessage, inputProvider, idx);
        File root = new File(levelsDirectory);
        levels = Arrays.stream(Objects.requireNonNull(root.listFiles())).map(parser::parseLevel).collect(Collectors.toList());
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
                sendMessage(String.format("%d. %s", i+1, playerList.get(i).describe()))
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
