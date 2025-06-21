package view.input;

import game.tiles.units.actions.Movement;
import game.tiles.units.player.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class CommandLineInput implements InputProvider {
    private HashSet<Character> validInputs = new HashSet<Character>();

    private final Scanner scanner;

    public CommandLineInput(Scanner scanner){
        this.scanner = scanner;
        validInputs.add('w');
        validInputs.add('a');
        validInputs.add('s');
        validInputs.add('d');
        validInputs.add('q');
        validInputs.add('e');
    }

    public char inputQuery() {
        while (true) {
            String input = "";
            while(input.isEmpty())
                input = scanner.nextLine().trim().toLowerCase();

            if (input.length() == 1 && validInputs.contains(input.charAt(0))) {
                return input.charAt(0);
            }
            else {
                System.out.println("Invalid input");
            }
        }
    }
}
