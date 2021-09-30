import java.util.ArrayList;

class HintHelper {

    private SudokuHint hint;

    HintHelper(SudokuGrid sgrid){
        hint = new SudokuHint(sgrid);
        //hint.printMap();
        //hint.getNotes(new Coordinate(1, 1));
    }

    void getHints(){

        String[] loneSingles = hint.checkLoneSingle();
        if(loneSingles.length > 0){
            System.out.println("Lone single of value " + loneSingles[1] +
                    " at " + loneSingles[0]);
            return;
        }

        String[] hiddenSingles = hint.checkHiddenSingle();
        if(hiddenSingles.length > 0){
            System.out.println("Hidden single of value " + hiddenSingles[1] +
                    " at " + hiddenSingles[0] +
                    " - it is the only " + hiddenSingles[1] +
                    " in its " + hiddenSingles[2]);
            return;
        }

        String[] pointingPairs = hint.checkPointingPair();
        if(pointingPairs.length > 0){
            System.out.println("Pointing pair of value " + pointingPairs[2] +
                    " at " + pointingPairs[0] +
                    " and " + pointingPairs[1]);
            return;
        }
        System.out.println("No Hint Available");
    }

    void updateGrid(Coordinate c, int val){
        hint.updateGrid(c, val);
    }
}
