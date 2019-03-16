package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import game.MineSweeper.CellState;

public class MineSweeperTest {
    MineSweeper mineSweeper;

    @Test
    void Canary() {
        assertTrue(true);
    }

    @BeforeEach
    void setUp() {
        mineSweeper = new MineSweeper();
    }

    @Test
    void exposeUnexposedCell() {
        mineSweeper.exposeCell(1, 1);

        assertEquals(CellState.EXPOSED, mineSweeper.getCellState(1, 1));
    }

    @Test
    void exposeAnExposedCell() {
        mineSweeper.exposeCell(1, 1);
        mineSweeper.exposeCell(1, 1);

        assertEquals(CellState.EXPOSED, mineSweeper.getCellState(1, 1));
    }

    @Test
    void cellOutOfRowRange() {
        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class, () -> mineSweeper.exposeCell(11, 2)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> mineSweeper.exposeCell(-2, 5))
        );
    }

    @Test
    void cellOutOfColumnRange() {
        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class, () -> mineSweeper.exposeCell(2, 11)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> mineSweeper.exposeCell(3, -4))
        );
    }

    class MineSweeperExposeNeighborsOfReplaced {
        List<Integer> exposeNeighborsOfCalledWith = new ArrayList<>();

        MineSweeper mineSweeperTest = new MineSweeper() {
            @Override
            public void exposeNeighborsOf(int row, int column) {
                if (!mined[row][column]) {
                    exposeNeighborsOfCalledWith.add(row);
                    exposeNeighborsOfCalledWith.add(column);
                }
            }
        };
    }

    @Test
    void exposeCellCallsExposeNeighborOf() {
        MineSweeperExposeNeighborsOfReplaced mineSweeper = new MineSweeperExposeNeighborsOfReplaced();

        mineSweeper.mineSweeperTest.exposeCell(4, 4);
        assertEquals(Arrays.asList(4, 4), mineSweeper.exposeNeighborsOfCalledWith);
    }

    @Test
    void exposeCellTwiceCallExposeNeighborOfOnce() {
        MineSweeperExposeNeighborsOfReplaced mineSweeper = new MineSweeperExposeNeighborsOfReplaced();

        mineSweeper.mineSweeperTest.exposeCell(4, 4);
        mineSweeper.mineSweeperTest.exposeCell(4, 4);
        List<Integer> expectedCellsExposed = Arrays.asList(4, 4);

        assertEquals(expectedCellsExposed, mineSweeper.exposeNeighborsOfCalledWith);
    }

    class MineSweeperExposeCellReplaced {
        List<Integer> actualCellsExposed = new ArrayList<>();

        MineSweeper mineSweeper = new MineSweeper() {
            @Override
            public void exposeCell(int row, int column) {
                if (cells[row][column] == CellState.UNEXPOSED) {
                    cells[row][column] = MineSweeper.CellState.EXPOSED;
                    actualCellsExposed.add(row);
                    actualCellsExposed.add(column);
                }
            }
        };
    }

    @Test
    void exposeNeighborCells() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(4, 4);
        List<Integer> expectedCellsExposed = Arrays.asList(3, 3, 3, 4, 3, 5, 4, 3, 4, 5, 5, 3, 5, 4, 5, 5);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }

    @Test
    void exposeTopLeftCell() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(0, 0);
        List<Integer> expectedCellsExposed = Arrays.asList(0, 1, 1, 0, 1, 1);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }
    @Test
    void exposeTopCell() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(0, 5);
        List<Integer> expectedCellsExposed = Arrays.asList(0, 4, 0, 6, 1, 4, 1, 5, 1, 6);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }

    @Test
    void exposeBottomCell() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(9, 5);
        List<Integer> expectedCellsExposed = Arrays.asList(8, 4, 8, 5, 8, 6, 9, 4, 9, 6);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }
    @Test
    void exposeLeftCell() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(5, 0);
        List<Integer> expectedCellsExposed = Arrays.asList(4, 0, 4, 1, 5, 1, 6, 0, 6, 1);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }
    @Test
    void exposeRightCell() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(5, 9);
        List<Integer> expectedCellsExposed = Arrays.asList(4, 8, 4, 9, 5, 8, 6, 8, 6, 9);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }

    @Test
    void exposeTopRightCell() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(0, 9);
        List<Integer> expectedCellsExposed = Arrays.asList(0, 8, 1, 8, 1, 9);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }

    @Test
    void exposeBottomLeftCell() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(9, 0);
        List<Integer> expectedCellsExposed = Arrays.asList(8, 0, 8, 1, 9, 1);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }

    @Test
    void exposeBottomRightCell() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.exposeNeighborsOf(9, 9);
        List<Integer> expectedCellsExposed = Arrays.asList(8, 8, 8, 9, 9, 8);

        assertEquals(expectedCellsExposed, mineSweeper.actualCellsExposed);
    }

    @Test
    void sealingCell() {
        mineSweeper.toggleSeal(0, 1);

        assertEquals(CellState.SEALED, mineSweeper.getCellState(0, 1));
    }

    @Test
    void unsealingCell() {
        mineSweeper.toggleSeal(2, 2);
        mineSweeper.toggleSeal(2, 2);

        assertEquals(CellState.UNEXPOSED, mineSweeper.getCellState(2, 2));
    }

    @Test
    void sealExposedCell() {
        mineSweeper.exposeCell(2, 3);
        mineSweeper.toggleSeal(2, 3);

        assertEquals(CellState.EXPOSED, mineSweeper.getCellState(2, 3));
    }

    @Test
    void exposeSealedCell() {
        mineSweeper.toggleSeal(5, 5);
        mineSweeper.exposeCell(5, 5);

        assertEquals(CellState.SEALED, mineSweeper.getCellState(5, 5));
    }

    @Test
    void exposedSealCellNotAffectingNeighborCells() {
        MineSweeperExposeCellReplaced mineSweeper = new MineSweeperExposeCellReplaced();

        mineSweeper.mineSweeper.toggleSeal(4, 4);
        mineSweeper.mineSweeper.exposeCell(4, 4);

        assertEquals(Arrays.asList(), mineSweeper.actualCellsExposed);
    }

    @Test
    void setMine() {
        mineSweeper.mined[4][4] = true;

        assertTrue(mineSweeper.mined[4][4]);
    }

    @Test
    void adjacentCellNextToAMinedCell() {
        mineSweeper.mined[4][4] = true;

        assertEquals(1, mineSweeper.countNumberOfAdjacentCells(4, 3));
    }

    @Test
    void minedCellIsConsideredAdjacentCell() {
        mineSweeper.mined[6][6] = true;

        assertEquals(0, mineSweeper.countNumberOfAdjacentCells(6, 6));
    }

    @Test
    void mineCellAdjacentToAMineConsideredAnAdjacentCell() {
        mineSweeper.mined[5][5] = true;
        mineSweeper.mined[4][5] = true;

        assertEquals(1, mineSweeper.countNumberOfAdjacentCells(5, 5));
    }

    @Test
    void exposeMinedCellNotExposeNeighborCells() {
        MineSweeperExposeNeighborsOfReplaced mineSweeper = new MineSweeperExposeNeighborsOfReplaced();

        mineSweeper.mineSweeperTest.mined[5][5] = true;
        mineSweeper.mineSweeperTest.exposeCell(5, 5);

        assertEquals(Arrays.asList(), mineSweeper.exposeNeighborsOfCalledWith);
    }

    @Test
    void exposeAdjacentCellNotExposeNeighborCells() {
        MineSweeperExposeNeighborsOfReplaced mineSweeper = new MineSweeperExposeNeighborsOfReplaced();

        mineSweeper.mineSweeperTest.mined[5][6] = true;
        mineSweeper.mineSweeperTest.exposeCell(5, 5);

        assertEquals(Arrays.asList(), mineSweeper.exposeNeighborsOfCalledWith);
    }

    @Test
    void startGameStatus(){
        assertEquals(MineSweeper.GameStatus.INPROGRESS, mineSweeper.gameStatus());
    }

    @Test
    void mineExposedGameStatus() {
        mineSweeper.mined[5][6] = true;
        mineSweeper.exposeCell(5, 6);

        assertEquals(MineSweeper.GameStatus.LOSS, mineSweeper.gameStatus());
    }

    @Test
    void allSealedAllExposedGameStatus() {
        for (int i = 0; i < 10; i++) {
            mineSweeper.mined[i][i] = true;
            mineSweeper.toggleSeal(i, i);
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mineSweeper.exposeCell(i, j);
            }
        }

        assertEquals(MineSweeper.GameStatus.WIN, mineSweeper.gameStatus());
    }

    @Test
    void gameInProgressWithUnexposedCells() {
        for (int i = 0; i < 10; i++) {
            mineSweeper.mined[i][i] = true;
            mineSweeper.toggleSeal(i, i);
        }

        assertEquals(MineSweeper.GameStatus.INPROGRESS, mineSweeper.gameStatus());
    }


    @Test
    void tenMinesPlacedWhenCreated(){
        mineSweeper.setMines();

        int count = 0;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (mineSweeper.mined[i][j])
                    count++;
            }
        }
        assertEquals(10, count);
    }


    @Test
    void mineLocationsAreRandom(){
        MineSweeper mineSweeper2 = new MineSweeper();
        mineSweeper.setMines();
        mineSweeper2.setMines();
        boolean minesAreRandom = false;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if(mineSweeper.mined[i][j]!=mineSweeper2.mined[i][j])
                    minesAreRandom=true;
            }}

        assertEquals(true,minesAreRandom);
    }

    @Test
    void checkCellStateEnum(){
        List<CellState> cellStateEnum = new ArrayList<>();
        List<CellState> expectedCellStateEnum = Arrays.asList(CellState.values());

        cellStateEnum.add(CellState.valueOf("EXPOSED"));
        cellStateEnum.add(CellState.valueOf("UNEXPOSED"));
        cellStateEnum.add(CellState.valueOf("SEALED"));

        assertEquals(expectedCellStateEnum, cellStateEnum);
    }

    @Test
    void checkGameStatusEnum(){
        List<MineSweeper.GameStatus> gameStatusEnum = new ArrayList<>();
        List<MineSweeper.GameStatus> expectedGameStateEnum = Arrays.asList(MineSweeper.GameStatus.values());

        gameStatusEnum.add(MineSweeper.GameStatus.valueOf("INPROGRESS"));
        gameStatusEnum.add(MineSweeper.GameStatus.valueOf("LOSS"));
        gameStatusEnum.add(MineSweeper.GameStatus.valueOf("WIN"));

        assertEquals(expectedGameStateEnum, gameStatusEnum);
    }

    @Test
    void tenCellsSealedNoWin(){
        for (int i = 0; i < 10; i++){
            mineSweeper.mined[i][i] = true;
        }
        for (int j = 0; j <10; j++){
            mineSweeper.cells[j][j] = CellState.SEALED;
        }

        mineSweeper.cells[9][9] = CellState.UNEXPOSED;
        mineSweeper.cells[9][8] = CellState.SEALED;


        assertEquals(MineSweeper.GameStatus.INPROGRESS, mineSweeper.gameStatus());
    }
}
