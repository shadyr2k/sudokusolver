import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class HintLoneSingle extends HintMethodHelper{

    private ArrayList<Coordinate> blacklistedLoneSingle = new ArrayList<>();

    HintLoneSingle(HashMap<Coordinate, ArrayList<Integer>> sHash, int[][] gameGrid) {
        super(sHash, gameGrid);
    }

    String[] checkLoneSingle(){
        for(int row = 0; row < SudokuGrid.BOARD_LIMIT; ++row){
            for(int col = 0; col < SudokuGrid.BOARD_LIMIT; ++col){
                Coordinate c = new Coordinate(row + 1, SudokuGrid.BOARD_LIMIT - col);
                if(!blacklistedLoneSingle.contains(c)){
                    ArrayList<Integer> noteArr = getNotes(c);
                    if(!noteArr.contains(0)){
                        if(noteArr.size() <= 1){
                            blacklistedLoneSingle.add(c);
                            printGridWithHighlights(new ArrayList<>(Collections.singletonList(c)));
                            return new String[]{c.toString(), String.valueOf(noteArr.get(0))};
                        }
                    }
                }
            }
        }
        return new String[]{};
    }
}

