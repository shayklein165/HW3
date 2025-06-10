package view.input;

import game.tiles.units.actions.Movement;
import game.tiles.units.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class CommandLineInput {
    private static Map<String, Function<Player, Action>> actionMap = new HashMap<String, Function<Player,Action>>(){
        {
            put("w", Movement.Up::new);
            put("a", Movement.Left::new);
            put("s", Movement.Down::new);
            put("d", Movement.Right::new);
            put("q", Movement.NoOperation::new);
        }
    };

    private final Scanner scanner;

    public CommandLineInput(Scanner scanner){
        this.scanner = scanner;
    }

    public Function<Player, Action> inputQuery(){
        String input = scanner.next().toLowerCase();
        while(!actionMap.containsKey(input)){
            input = scanner.next().toLowerCase();
        }

        return actionMap.get(input);
    }
}
