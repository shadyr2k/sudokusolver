import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

class SudokuHint {

    private HashMap<Coordinate, ArrayList<Integer>> sHash = new HashMap<>();

    private int[][] grid;
    private SudokuGrid sgrid;

    private HintLoneSingle hintLoneSingle;
    private HintHiddenSingle hintHiddenSingle;
    private HintPointingPair hintPointingPair;
    private HintLockedCandidate hintLockedCandidate;

    SudokuHint(SudokuGrid sgrid){
        this.sgrid = sgrid;
        grid = sgrid.getGrid();
        for(int row = 0; row < SudokuGrid.BOARD_LIMIT; ++row){
            for(int col = 0; col < SudokuGrid.BOARD_LIMIT; ++col){
                ArrayList<Integer> notes = new ArrayList<>();
                if(grid[row][col] == SudokuGrid.NO_VALUE) {
                    for (int i = 1; i <= SudokuGrid.BOARD_LIMIT; ++i) {
                        int temp = grid[row][col];
                        grid[row][col] = i;
                        if (sgrid.isValid(grid, row, col))
                            notes.add(i);
                        grid[row][col] = temp;
                    }
                } else {
                    notes.add(0);
                }
                sHash.put(new Coordinate(col + 1, SudokuGrid.BOARD_LIMIT - row), notes);
            }
        }

        hintLoneSingle = new HintLoneSingle(sHash, grid);
        hintHiddenSingle = new HintHiddenSingle(sHash, grid);
        hintPointingPair = new HintPointingPair(sHash, grid);
        hintLockedCandidate = new HintLockedCandidate(sHash, grid);
    }

    String[] checkLoneSingle(){
        return hintLoneSingle.checkLoneSingle();
    }

    String[] checkHiddenSingle(){
        return hintHiddenSingle.checkHiddenSingle();
    }

    String[] checkPointingPair(){
        return hintPointingPair.checkPointingPair();
    }

    String[] checkLockedCandidate(){
        return hintLockedCandidate.checkLockedCandidate();
    }


    /*
     * print methods
     */

    void printMap(){
        System.out.println(sHash);
    }

    /*
     * check/update methods
     */

    void updateGrid(Coordinate c, int val){
        sgrid.editCell(c, val);
        sHash.remove(c);
    }

    void updateNote(ArrayList<Coordinate> coords, ArrayList<Integer> values){
        for(Coordinate coord : coords){
            for(Integer value : values)
                sHash.get(coord).remove(value);
        }
    }
}
