package view;

public class Program {
    public static void main(String[] args){
        System.out.println("Welcome to the game!");
        if(args.length == 0){
            System.out.println("Please enter the game levels you want to play!");
            return;
        }

        String gameLevels = args[0];
        if(gameLevels.isEmpty()){
            System.out.println("levels directory cannot be empty");
        }

        GameRunner gameRunner = new GameRunner();
        try{
            gameRunner.initialize(gameLevels);
            gameRunner.start();
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
