import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class HintPointingPair extends HintMethodHelper {

    private ArrayList<Coordinate> blacklistedPointingPair = new ArrayList<>();

    HintPointingPair(HashMap<Coordinate, ArrayList<Integer>> sHash, int[][] gameGrid) {
        super(sHash, gameGrid);
    }

    String[] checkPointingPair(){
        HashMap<Integer, ArrayList<Coordinate>> pointHash = new HashMap<>();
        int rowOffset = 0;
        int colOffset = 0;

        while(colOffset < 9) {
            checkHouse(pointHash, rowOffset, colOffset, blacklistedPointingPair);
            for(Integer key : pointHash.keySet()){
                if(pointHash.get(key).size() == 2){
                    Coordinate coord1 = pointHash.get(key).get(0);
                    Coordinate coord2 = pointHash.get(key).get(1);
                    System.out.println(coord1.toString() + " " + coord2.toString());
                    ArrayList<Coordinate> coordArr = new ArrayList<>(Arrays.asList(coord1, coord2));
                    int determiner = inSameCol(coordArr) ? 0 : inSameRow(coordArr) ? 1 : -1;
                    int num = key;

                    boolean useful = determiner == 0 ? pointingPairCheckUse(coord1, coord2, false, num) : determiner == 1 && pointingPairCheckUse(coord1, coord2, true, num);
                    blacklistedPointingPair.add(coord1);
                    blacklistedPointingPair.add(coord2);
                    if(useful){
                        printGridWithHighlights(coordArr);
                        return new String[]{coord1.toString(), coord2.toString(), key.toString()};
                    }
                }
            }
            pointHash.clear();
            rowOffset += 3;
            if(rowOffset >= 9){
                rowOffset = 0;
                colOffset += 3;
            }
        }
        return new String[]{};
    }

    private boolean pointingPairCheckUse(Coordinate coord1, Coordinate coord2, boolean row, int value){
        for(int i = 1; i <= SudokuGrid.BOARD_LIMIT; ++i){
            Coordinate c = row ? new Coordinate(i, coord1.getY()) : new Coordinate(coord1.getX(), i);
            if(!c.equals(coord1) && !c.equals(coord2)){
                if(!getNotes(c).contains(0)){
                    if(sHash.get(c).contains(value))
                        return true;
                }
            }
        }
        return false;
    }
}
