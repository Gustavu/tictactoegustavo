import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeTest {

    private Game game;
    private Cell[][] cell;
    private Player pl1;
    private Player pl2;

    @Before
    public void init() {
        game = new Game();
        game.initBoard(3);
        cell = game.getBoard().getBoard();
        pl1 = new Player();
        pl1.setName("pl1");
        pl1.setSymbol("X");

        pl2 = new Player();
        pl2.setName("pl2");
        pl2.setSymbol("O");
    }

    @Test
    public void allCellsAreEmptyInANewGame() {
        assertBoardIs(cell);
    }

    @Test
    public void boardHasToBeMinimum3x3Grid() {
        new Board().init(3);
    }

    @Test
    public void firstPlayersMoveMarks_X_OnTheBoard() {
        assertTrue(game.move(pl1, 1, 1));
        assertEquals(game.getBoard().getBoard()[1][1].getValue(), "X");
        assertBoardIs(cell);
    }

    @Test
    public void secondPlayersMoveMarks_O_OnTheBoard() {
        assertTrue(game.move(pl2, 2, 2));
        assertEquals(game.getBoard().getBoard()[2][2].getValue(), "O");
        assertBoardIs(cell);
    }

    @Test
    public void playerCanOnlyMoveToAnEmptyCell() {
        game.move(pl1, 1,1);
        assertTrue(!game.move(pl2, 1,1));
    }

    @Test
    public void firstPlayerWithAllSymbolsInOneRowWins() {
        game.move(pl1, 0,0);
        game.move(pl1, 1,1);
        game.move(pl1, 2,2);
        assertTrue(game.gameHasEnded(pl1));
    }

    @Test
    public void firstPlayerWithAllSymbolsInOneColumnWins() {
        game.move(pl1, 0,0);
        game.move(pl1, 0,1);
        game.move(pl1, 0,2);
        assertTrue(game.gameHasEnded(pl1));
    }

    @Test
    public void firstPlayerWithAllSymbolsInPrincipalDiagonalWins() {
        game.move(pl1,0, 0);
        game.move(pl1,1, 1);
        game.move(pl1,2, 2);
        assertTrue(game.gameHasEnded(pl1));
    }

    @Test
    public void firstPlayerWithAllSymbolsInMinorDiagonalWins() {
        game.move(pl1,2, 0);
        game.move(pl1,1, 1);
        game.move(pl1,0, 2);
        assertTrue(game.gameHasEnded(pl1));
    }

    private void assertBoardIs(Cell[][] expectedBoard) {
        assertArrayEquals(expectedBoard, game.getBoard().getBoard());
    }

}
