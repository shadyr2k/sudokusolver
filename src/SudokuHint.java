import java.util.ArrayList;
import java.util.HashMap;

public class SudokuHint {

    private HashMap<Coordinate, ArrayList<Integer>> sHash = new HashMap<>();

    public SudokuHint(int[][] grid){

        SudokuGrid sgrid = new SudokuGrid(grid);
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

    }

    public ArrayList<Integer> getNotes(Coordinate c){
        System.out.println(sHash.get(c));
        return sHash.get(c);
    }

    public String[] checkPointingPair(){
        //check square for only two of a candidate, then check if in same row/col

        return new String[]{};
    }

    public void printMap(){
        System.out.println(sHash);
    }
}
