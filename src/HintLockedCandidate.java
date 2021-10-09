import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HintLockedCandidate extends HintMethodHelper {

    private HashMap<ArrayList<Coordinate>, Integer> blacklistedLockedCandidate = new HashMap<>();

    HintLockedCandidate(HashMap<Coordinate, ArrayList<Integer>> sHash, int[][] gameGrid) {
        super(sHash, gameGrid);
    }

    String[] checkLockedCandidate(){
        HashMap<Integer, ArrayList<Coordinate>> lockedHash = new HashMap<>();

        String[] rowFirstCheck = lockedRoutine(lockedHash, false);
        if(rowFirstCheck.length < 1) //row-first
            return lockedRoutine(lockedHash, true); //col-first
        else return rowFirstCheck;
    }

    private String[] lockedRoutine(HashMap<Integer, ArrayList<Coordinate>> hash, boolean colFirst){
        for(int row = 0; row < SudokuGrid.BOARD_LIMIT; ++row){
            for(int col = 0; col < SudokuGrid.BOARD_LIMIT; ++col){
                Coordinate c = colFirst ? convertCoordinate(row, col) : convertCoordinate(col, row);
                getNoteHash(hash, new ArrayList<>(), c);
            }
            for(Integer key : hash.keySet()){
                if(inSameHouse(hash.get(key)) && lockedCandidateCheckUse(hash.get(key), key)){
                    blacklistedLockedCandidate.put(hash.get(key), key);
                    printGridWithHighlights(hash.get(key));

                    String retString = hash.get(key).stream().map(Coordinate::toString).collect(Collectors.joining(", "));
                    String retRow = colFirst ? "column" : "row";
                    return new String[]{String.valueOf(key), retString, retRow};
                }
            }
            hash.clear();
        }
        return new String[]{};
    }

    private boolean lockedCandidateCheckUse(ArrayList<Coordinate> coordArr, int value){
        if(blacklistedLockedCandidate.containsKey(coordArr) && blacklistedLockedCandidate.get(coordArr).equals(value)) return false;

        List<Coordinate> convertedCoords = coordArr.stream().map(Coordinate::convert).collect(Collectors.toList());
        int startX = convertedCoords.get(0).getX() - convertedCoords.get(0).getX()%3;
        int startY = convertedCoords.get(0).getY() - convertedCoords.get(0).getY()%3;

        for(int row = startX; row < startX + SudokuGrid.HOUSE_LIMIT; ++row){
            for(int col = startY; col < startY + SudokuGrid.HOUSE_LIMIT; ++col){
                Coordinate c = convertCoordinate(row, col);
                if(!coordArr.contains(c) && sHash.containsKey(c)){
                    if(sHash.get(c).contains(value))
                        return true;
                }
            }
        }
        return false;
    }

}
