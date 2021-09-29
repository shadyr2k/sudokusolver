import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

class SudokuHint {

    private HashMap<Coordinate, ArrayList<Integer>> sHash = new HashMap<>();
    private ArrayList<Coordinate> blacklistedLoneSingle = new ArrayList<>();
    private ArrayList<Coordinate> blacklistedHiddenSingle = new ArrayList<>();
    private ArrayList<Coordinate> blacklistedPointingPair = new ArrayList<>();

    private int[][] grid;
    private SudokuGrid sgrid;

    SudokuHint(SudokuGrid sgrid){
        this.sgrid = sgrid;
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

    String[] checkHiddenSingle(){
        HashMap<Integer, ArrayList<Coordinate>> hiddenHash = new HashMap<>();
        for(int row = 0; row < SudokuGrid.BOARD_LIMIT; ++row){
            for(int col = 0; col < SudokuGrid.BOARD_LIMIT; ++col){
                Coordinate c = new Coordinate(row + 1, SudokuGrid.BOARD_LIMIT - col);
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
                Coordinate c = new Coordinate(row + 1, SudokuGrid.BOARD_LIMIT - col);
                getNoteHash(hiddenHash, blacklistedHiddenSingle, c);
            }
            for(Integer key : hiddenHash.keySet()){
                if(hiddenHash.get(key).size() == 1){
                    printGridWithHighlights(new ArrayList<>(Collections.singletonList(hiddenHash.get(key).get(0))));
                    return new String[]{hiddenHash.get(key).get(0).toString(), key.toString(), "row"};
                }
            }
            hiddenHash.clear();
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
                    ArrayList<Coordinate> coordArr = new ArrayList<>(Arrays.asList(coord1, coord2));
                    int determiner = inSameCol(coordArr) ? 0 : inSameRow(coordArr) ? 1 : -1;
                    int num1 = sHash.get(coord1).get(0);
                    int num2 = sHash.get(coord1).get(1);
                    if(determiner >= 0){
                        if(determiner == 0){
                            for(int i = 0; i < SudokuGrid.BOARD_LIMIT; ++i){
                                if(i == coord1.getY() || i == coord2.getY()) continue;
                                Coordinate c = new Coordinate(coord1.getX(), i);
                                if(!getNotes(c).contains(0)){
                                    if(sHash.get(c).contains(num1) || sHash.get(c).contains(num2))
                                        break; //todo check
                                }

                            }
                        }
                        blacklistedPointingPair.add(coord1);
                        blacklistedPointingPair.add(coord2);
                        printGridWithHighlights(coordArr);
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

    /*
     * shortened methods to repeat less code
     */

    private void checkHouse(HashMap<Integer, ArrayList<Coordinate>> hash, int rowOffset, int colOffset, ArrayList<Coordinate> blacklisted) {
        for(int row = rowOffset; row < rowOffset + SudokuGrid.HOUSE_LIMIT; ++row){
            for(int col = colOffset; col < colOffset + SudokuGrid.HOUSE_LIMIT; ++col){
                Coordinate c = new Coordinate(row + 1, SudokuGrid.BOARD_LIMIT - col);
                getNoteHash(hash, blacklisted, c);
            }
        }
    }

    private void getNoteHash(HashMap<Integer, ArrayList<Coordinate>> hash, ArrayList<Coordinate> blacklist, Coordinate c){
        if(!blacklist.contains(c)) {
            ArrayList<Integer> noteArr = getNotes(c);
            if (!noteArr.contains(0)) {
                for (Integer note : noteArr) {
                    if (hash.containsKey(note))
                        hash.get(note).add(c);
                    else {
                        ArrayList<Coordinate> coords = new ArrayList<>();
                        coords.add(c);
                        hash.put(note, coords);
                    }
                }
            }
            //System.out.println(hash);
        }
    }

    /*
     * print methods
     */

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

    /*
     * check/update methods
     */

    void updateGrid(Coordinate c, int val){
        sgrid.editCell(c, val);
        sHash.remove(c);
    }

    void updateNote(ArrayList<Coordinate> coords, ArrayList<Integer> values){
        for(Coordinate coord : coords){
            for(Integer value : values)
                sHash.get(coord).remove(value);
        }

    }

    private boolean inSameRow(ArrayList<Coordinate> coords){
        if(coords.isEmpty()) return false;
        int yCoord = coords.get(0).getY();
        for(Coordinate coord : coords){
            if(coord.getY() != yCoord)
                return false;
        }
        return true;
    }

    private boolean inSameCol(ArrayList<Coordinate> coords){
        if(coords.isEmpty()) return false;
        int xCoord = coords.get(0).getX();
        for(Coordinate coord : coords){
            if(coord.getX() != xCoord)
                return false;
        }
        return true;
    }

    private boolean inSameHouse(ArrayList<Coordinate> coords){
        if(coords.isEmpty()) return false;
        Coordinate houseStart = new Coordinate(coords.get(0).getX() - coords.get(0).getX()%3, coords.get(0).getY() - coords.get(0).getY()%3);
        for(Coordinate coord : coords){
            Coordinate start = new Coordinate(coord.getX() - coord.getX()%3, coord.getY() - coord.getY()%3);
            if(!start.equals(houseStart))
                return false;
        }
        return true;
    }
}
