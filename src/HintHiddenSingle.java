import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class HintHiddenSingle extends HintMethodHelper {

    private ArrayList<Coordinate> blacklistedHiddenSingle = new ArrayList<>();

    HintHiddenSingle(HashMap<Coordinate, ArrayList<Integer>> sHash, int[][] gameGrid) {
        super(sHash, gameGrid);
    }

    String[] checkHiddenSingle(){
        HashMap<Integer, ArrayList<Coordinate>> hiddenHash = new HashMap<>();
        for(int row = 0; row < SudokuGrid.BOARD_LIMIT; ++row){
            for(int col = 0; col < SudokuGrid.BOARD_LIMIT; ++col){
                Coordinate c = convertCoordinate(row, col);
                getNoteHash(hiddenHash, blacklistedHiddenSingle, c);
            }
            for(Integer key : hiddenHash.keySet()){
                if(hiddenHash.get(key).size() == 1){
                    printGridWithHighlights(new ArrayList<>(Collections.singletonList(hiddenHash.get(key).get(0))));
                    return new String[]{hiddenHash.get(key).get(0).toString(), key.toString(), "column"};
                }
            }
            hiddenHash.clear();
        }
        for(int col = 0; col < SudokuGrid.BOARD_LIMIT; ++col){
            for(int row = 0; row < SudokuGrid.BOARD_LIMIT; ++row){
                Coordinate c = convertCoordinate(row, col);
                getNoteHash(hiddenHash, blacklistedHiddenSingle, c);
            }
            for(Integer key : hiddenHash.keySet()){
                if(hiddenHash.get(key).size() == 1){
                    printGridWithHighlights(new ArrayList<>(Collections.singletonList(hiddenHash.get(key).get(0))));
                    return new String[]{hiddenHash.get(key).get(0).toString(), key.toString(), "row"};
                }
            }
            hiddenHash.clear();

            //todo clear number from every row and column and house anyways
        }
        int rowOffset = 0;
        int colOffset = 0;

        while(colOffset < 9) {
            checkHouse(hiddenHash, rowOffset, colOffset, blacklistedHiddenSingle);
            for(Integer key : hiddenHash.keySet()){
                if(hiddenHash.get(key).size() == 1){
                    printGridWithHighlights(new ArrayList<>(Collections.singletonList(hiddenHash.get(key).get(0))));
                    return new String[]{hiddenHash.get(key).get(0).toString(), key.toString(), "house"};
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
}
