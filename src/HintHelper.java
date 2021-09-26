public class HintHelper {

    public HintHelper(int[][] grid){
        SudokuHint hint = new SudokuHint(grid);
        hint.printMap();
        //hint.getNotes(new Coordinate(1, 1));
    }

}
