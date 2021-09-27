import java.util.ArrayList;

class HintHelper {

    private SudokuHint hint;

    HintHelper(SudokuGrid sgrid){
        hint = new SudokuHint(sgrid);
        //hint.printMap();
        //hint.getNotes(new Coordinate(1, 1));
    }

    void getHints(){

        String[] pointingPairs = hint.checkPointingPair();
        if(pointingPairs.length > 0){
            System.out.println("Pointing pair of value " + pointingPairs[2] +
                    " at " + pointingPairs[0] +
                    " and " + pointingPairs[1]);
        }
    }

    void updateGrid(Coordinate c, int val){
        hint.updateGrid(c, val);
    }
}
