import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class SudokuHint {

    private HashMap<Coordinate, ArrayList<Integer>> sHash = new HashMap<>();
    private ArrayList<Coordinate> blacklistedPointingPair = new ArrayList<>();
    private int[][] grid;

    SudokuHint(SudokuGrid sgrid){
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
    }

    private ArrayList<Integer> getNotes(Coordinate c){
        //System.out.println(c.toString() + " has notes " + sHash.get(c));
        return sHash.get(c);
    }

    String[] checkPointingPair(){
        HashMap<Integer, ArrayList<Coordinate>> pointHash = new HashMap<>();
        int rowOffset = 0;
        int colOffset = 0;

        while(colOffset < 9) {
            for (int row = rowOffset; row < rowOffset + SudokuGrid.HOUSE_LIMIT; ++row) {
                for (int col = colOffset; col < colOffset + SudokuGrid.HOUSE_LIMIT; ++col) {
                    Coordinate c = new Coordinate(row + 1, col + 1);
                    if(!blacklistedPointingPair.contains(c)) {
                        ArrayList<Integer> noteArr = getNotes(c);
                        if (!noteArr.contains(0)) {
                            for (Integer note : noteArr) {
                                if (pointHash.containsKey(note))
                                    pointHash.get(note).add(c);
                                else {
                                    ArrayList<Coordinate> coords = new ArrayList<>();
                                    coords.add(c);
                                    pointHash.put(note, coords);
                                }
                            }
                        }
                    }
                }
            }
            for (Integer key : pointHash.keySet()) {
                if (pointHash.get(key).size() == 2) {
                    Coordinate coord1 = pointHash.get(key).get(0);
                    Coordinate coord2 = pointHash.get(key).get(1);
                    if (coord1.getY() == coord2.getY() || coord1.getX() == coord2.getX()) {
                        blacklistedPointingPair.add(coord1);
                        blacklistedPointingPair.add(coord2);
                        printGridWithHighlights(new ArrayList<Coordinate>(Arrays.asList(coord1, coord2)));
                        return new String[]{coord1.toString(), coord2.toString(), key.toString()};
                    }
                }
            }
            rowOffset += 3;
            if(rowOffset >= 9){
                rowOffset = 0;
                colOffset += 3;
            }
        }
        return new String[]{};
    }

    void printMap(){
        System.out.println(sHash);
    }

    private void printGridWithHighlights(ArrayList<Coordinate> coords){
        int[][] tempBoard = Arrays.stream(grid).map(int[]::clone).toArray($ -> grid.clone());
        for(Coordinate coord : coords)
            tempBoard[coord.convertRow(coord.getY())][coord.convertCol(coord.getX())] = -1;
        SudokuGrid tempGrid = new SudokuGrid(tempBoard);
        tempGrid.printGrid(true);
    }

    void updateGrid(Coordinate c, int val){
        //go through coordinate row/col/house and eliminate notes of value, then eliminate the coordinate from hashmap altogether
    }
}
