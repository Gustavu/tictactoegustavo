import java.io.Serializable;

class Board implements Serializable {

    private Cell[][] board;
    private Integer size;

    Board() {
        this.board = null;
    }

    Board init(Integer sizeBoard) {
        board = new Cell[sizeBoard][sizeBoard];
        size = sizeBoard;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }

        return this;
    }

    void print() {
        System.out.println();
        if(size == 10){
            System.out.println("  1 2 3 4 5 6 7 8 9 10");
        }else{
            System.out.println("  1 2 3");
        }

        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");

            for (int j = 0; j < size; j++) {
                System.out.print(this.board[i][j].getValue() + " ");
            }

            System.out.println();
        }
    }

    void setTile(Tile tile) {
        this.board[tile.getY()][tile.getX()].setValue(tile.getSymbol());
    }

    boolean cellEmpty(Integer x, Integer y) {
        return this.board[y][x].empty();
    }

    public boolean checkRowWin(Cell[][] gameBoard, String symbolPlayer) { //checks for three pieces in a row to decide if the game has been won
        for(int i = 0; i < gameBoard.length; i++) {
            int countForBoard10 = 0;
            boolean rowWin = true;
            Cell[] tempArray = gameBoard[i];
            for(int j = 0; j < tempArray.length; j++) {
                if(!tempArray[j].getValue().equals(symbolPlayer)){
                    rowWin = false;
                    countForBoard10 =0;
                }else{
                    countForBoard10++;
                }
            }
            if(rowWin || countForBoard10 == 5) {
                return true;
            }
        }
        return false;
    }

    public  boolean checkColoumnWin(Cell[][] gameBoard, String symbolPlayer) { //checks for three pieces in a coloumn to decide if the game has been won
        for(int i = 0; i < gameBoard.length; i++) {
            int countForBoard10 = 0;
            boolean coloumnWin = true;
            for(int j = 0; j < gameBoard.length; j++) {
                if(!gameBoard[j][i].getValue().equals(symbolPlayer)) {
                    coloumnWin = false;
                    countForBoard10 = 0;
                }else {
                    countForBoard10++;
                }
            }

            if(coloumnWin || countForBoard10 == 5) {
                return true;
            }
        }
        return false;
    }
    public  boolean checkDiagonalWin(Cell[][] gameBoard, String symbolPlayer) { //checks for three pieces in a diagonal of the board to decide if the game has been won
        boolean diagonalWin = true;
        int countForBoard10 = 0;
        for(int i = 0; i < gameBoard.length; i++) {
            if(!gameBoard[i][i].getValue().equals(symbolPlayer)){
                diagonalWin = false;
                countForBoard10 = 0;
            }else {
                countForBoard10++;
            }
        }

        if(diagonalWin || countForBoard10 == 5) {
            return true;
        }


        countForBoard10 = 0;
        for(int j = 0; j < gameBoard.length; j++) {
            diagonalWin = true;
            int row = j;
            int coloumn = gameBoard.length-1-j;
            if(!gameBoard[row][coloumn].getValue().equals(symbolPlayer)){
                diagonalWin = false;
            }else {
                countForBoard10++;
            }
        }

        if(diagonalWin || countForBoard10 == 5){
            return true;
        }

        return false;
    }

    public  boolean checkTie(Cell[][] gameBoard) {
        boolean test = true;
        for(int i = 0; i < gameBoard.length; i++) {
            for(int j = 0; j < gameBoard.length; j++) {
                if(gameBoard[i][j].getValue().equals(" ")) {
                    test = false;
                }
            }
        }
        return test;
    }

    public Cell[][] getBoard() {
        return board;
    }
}


