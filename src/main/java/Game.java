import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Random;

class Game implements Serializable {

    private static BufferedReader in;

    private Player player1;
    private Player player2;
    private Player playerIA;
    private Board board;
    private Player winner;
    private Integer sizeBoard;
    private Player lastPlayer;

    Game() {
        in = new BufferedReader(new InputStreamReader(System.in));

        this.player1 = null;
        this.player2 = null;
        this.playerIA = null;
        this.board = null;
        this.winner = null;
        this.sizeBoard = null;
        this.lastPlayer = null;

        //boot();
    }

    public void init(){
        boot();
    }

    private void boot() {
        printWelcomeScreen();
        playerNamesDialog();
        printStartScreen();
        initBoard(sizeBoard);
        start();
        printEndScreen();
    }

    private void printWelcomeScreen() {
        System.out.println("Welcome to this TicTacToe game!");
        System.out.println("This game can currently only be played by two players.");
        System.out.println("Please enter your names in order to start the game.");
        System.out.println();
    }

    private void playerNamesDialog() {
        String player1 = "X";
        String player2 = "O";

        System.out.print("Please choose 1 to 3x3 board and 2 to board 10x10 > ");
        try {
            if (sizeBoard != null && in.readLine().trim().equals("2")) {
                sizeBoard = 10;
            } else {
                sizeBoard = 3;
            }
        } catch (IOException e) {
            sizeBoard = 3;
            System.out.println("Something went wrong processing your entered board size. Your board default is 3x3.");
        }


        System.out.print("Player \"1\", please enter your symbol > ");

        try {
                player1 = in.readLine().trim();
        } catch (IOException e) {
            System.out.println("Something went wrong processing your entered name. Your name stays \"X\".");
        }

        System.out.print("Player \"2\", please enter your symbol > ");

        try {
                player2 = in.readLine().trim();
        } catch (IOException e) {
            System.out.println("Something went wrong processing your entered name. Your name stays \"O\".");
        }

        if ((player1 == null || player2 == null) || player1.equals(player2)) {
            player1 = "X";
            player2 = "O";
            System.out.println("You entered with the same symbol for both player so player1 is \"X\" and player2 is \"O\"");
        }

        createPlayers(player1, player2);
    }

    private void createPlayers(String player1, String player2) {
        this.player1 = (new Player()).setName("1").setSymbol(player1);
        this.player2 = (new Player()).setName("2").setSymbol(player2);
        if (sizeBoard == 10) {
            this.playerIA = (new Player()).setName("IA").setSymbol("IA");
            this.playerIA.setIa(true);
        }
    }

    private void printStartScreen() {
        System.out.println();
        System.out.println("Alright, " + this.player1.getName() + " and " + this.player2.getName() + ". Have fun!");
        System.out.println("The game is starting now.");
    }

    public void initBoard(Integer sizeBoard) {
        this.board = (new Board()).init(sizeBoard);
    }

    private void start() {
        this.board.print();
        System.out.println();

        play();

        restartDialog();
    }

    private void play() {
        Player currentPlayer = choseFirstMove();
        this.lastPlayer = this.player1;

        while (!gameHasEnded(lastPlayer)) {
            boolean playCorrect = move(currentPlayer, null, null);
            this.lastPlayer = currentPlayer;
            this.board.print();
            System.out.println();

            if (playCorrect) {
                currentPlayer = switchPlayer(currentPlayer);
            }
        }

        if (this.winner != null) {
            System.out.println("Congratulations! " + this.winner.getName() + " has won the game.");
            return;
        }

        System.out.println("Game is over. Nobody has won the game.");
    }

    private Player choseFirstMove() {
        Random r = new Random();
        Player player = null;
        int intg = sizeBoard == 10 ? r.nextInt(3) : r.nextInt(2);

        switch (intg) {
            case 0:
                player = this.player1;
                break;
            case 1:
                player = this.player2;
                break;
            default:
                player = this.playerIA;
                break;
        }
        return player;
    }

    public boolean gameHasEnded(Player player) {
        boolean gameWon;

        gameWon = board.checkRowWin(board.getBoard(), player.getSymbol())
                || board.checkColoumnWin(board.getBoard(), player.getSymbol())
                || board.checkDiagonalWin(board.getBoard(), player.getSymbol())
                || board.checkTie(board.getBoard());

        this.winner = player;

        return gameWon;
    }

    public boolean move(Player player, Integer x, Integer y) {
        Tile tile = new Tile();

        if (player != null && player.isIa()) {
            Random random = new Random();
            x = random.nextInt(sizeBoard);
            y = random.nextInt(sizeBoard);
        } else {
            if(x == null){
                do {
                    System.out.print(player.getName() + ", please enter the x-coordinate (1-" + sizeBoard + ") > ");
                } while ((x = tile.readCoordinate()) == null);
            }

            if(y == null){
                do {
                    System.out.print(player.getName() + ", please enter the y-coordinate (1-" + sizeBoard + ") > ");
                } while ((y = tile.readCoordinate()) == null);
            }
        }

        tile.setX(x);
        tile.setY(y);

        return setTile(tile, player);
    }

    private boolean setTile(Tile tile, Player player) {
        if (tile.getX() < 0 || tile.getX() > 9 || tile.getY() < 0 || tile.getY() > 9 || !this.board.cellEmpty(tile.getX(), tile.getY())) {
            System.out.println("The entered coordinates are not valid. Reenter the coordinates please.");
            return false;
        }

        tile.setSymbol(player.getSymbol());

        this.board.setTile(tile);
        return true;
    }

    private Player switchPlayer(Player player) {
        switch (player.getName()) {
            case "1":
                return this.player2;
            case "2":
                if (sizeBoard == 3) {
                    return player1;
                }
                return this.playerIA;
            default:
                return this.player1;
        }
    }

    private void restartDialog() {
        System.out.println();
        System.out.print("Would you like to restart the game (write yes/y or no/n)? > ");

        boolean restart;

        try {
            String input = in.readLine();
            restart = input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y");
        } catch (IOException e) {
            System.out.println("Something went wrong processing your answer. The game is not restarted.");
            return;
        }

        if (restart) {
            restart();
        }
    }

    private void restart() {
        System.out.println();
        System.out.println("The game is restarted. Have fun!");

        clearOldGameSettings();
        initBoard(sizeBoard);
        start();
    }

    private void clearOldGameSettings() {
        this.board = null;
        this.winner = null;
        this.lastPlayer = null;
    }

    private void printEndScreen() {
        System.out.println();
        System.out.println("Thanks for playing. See you!");
    }

    public Board getBoard() {
        return board;
    }

}
