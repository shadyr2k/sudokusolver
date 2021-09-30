import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HintLockedCandidate extends HintMethodHelper {

    private ArrayList<Coordinate> blacklistedLockedCandidate = new ArrayList<>();

    HintLockedCandidate(HashMap<Coordinate, ArrayList<Integer>> sHash, int[][] gameGrid) {
        super(sHash, gameGrid);
    }

    String[] checkLockedCandidate(){
        HashMap<Integer, ArrayList<Coordinate>> lockedHash = new HashMap<>();

        for(int row = 0; row < SudokuGrid.BOARD_LIMIT; ++row){ //column-first
            for(int col = 0; col < SudokuGrid.BOARD_LIMIT; ++col){
                Coordinate c = new Coordinate(row + 1, SudokuGrid.BOARD_LIMIT - col);
                getNoteHash(lockedHash, blacklistedLockedCandidate, c);
                for(Integer key : lockedHash.keySet()){
                    if(inSameHouse(lockedHash.get(key))){
                        blacklistedLockedCandidate.addAll(lockedHash.get(key));

                        String retString;
                        retString = lockedHash.get(key).stream().map(Coordinate::toString).collect(Collectors.joining(", "));
                        return new String[]{retString, String.valueOf(key)};
                    }
                }
            }
        }
        return new String[]{};
    }
}
