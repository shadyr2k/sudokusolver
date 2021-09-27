public class HintHelper {

    public HintHelper(int[][] grid){
        SudokuHint hint = new SudokuHint(grid);
        hint.printMap();
        hint.getNotes(new Coordinate(1, 1));

        String[] pointingPairs = hint.checkPointingPair(grid);
        if(pointingPairs.length > 0){
            System.out.println("Pointing pair of value " + pointingPairs[2] +
                    " at " + pointingPairs[0] +
                    " and " + pointingPairs[1]);
        }

    }

}
