package view.parser;

import game.Level;
import game.board.ArrayGameBoard;
import game.tiles.units.player.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;


public class FileParser {
    private List<String> lines;
    private TileFactory tileFactory;

    public FileParser(TileFactory fileFactory) {
        this.tileFactory = fileFactory;
    }

    public void readFile(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return;
        }
        if (lines != null) {
            lines.clear();
        }
        else {
            lines = new java.util.ArrayList<>();
        }
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String next;
            while ((next = reader.readLine()) != null) {
                lines.add(next);
            }
        }
        catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public Level interpret(Player player) {
        if(lines == null || lines.isEmpty()) {
            return null;
        }
        if(lines.get(0).length() == 0) {
            return null;
        }
        if(lines.size() < 2) {
            return null;
        }
        int hight=lines.size();
        int width=lines.get(0).length();
        char[][] board = new char[width][hight];
        for (int y = 0; y < lines.size(); y++) {
            for(int x = 0; x < lines.get(y).length(); x++) {
                char c = lines.get(y).charAt(x);
                board[x][y] = c;
            }
        }
        ArrayGameBoard arrayGameBoard = new ArrayGameBoard(new char[width][hight],player);
        Level level = new Level(arrayGameBoard);
        return level;
    }
}

