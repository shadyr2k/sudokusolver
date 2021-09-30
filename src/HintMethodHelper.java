import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class HintMethodHelper {

    HashMap<Coordinate, ArrayList<Integer>> sHash;
    int[][] gameGrid;

    HintMethodHelper(HashMap<Coordinate, ArrayList<Integer>> sHash, int[][] gameGrid){
        this.sHash = sHash;
        this.gameGrid = gameGrid;
    }

    //checks if coord list are all in same row
    boolean inSameRow(ArrayList<Coordinate> coords){
        if(coords.isEmpty()) return false;
        int yCoord = coords.get(0).getY();
        for(Coordinate coord : coords){
            if(coord.getY() != yCoord)
                return false;
        }
        return true;
    }

    //checks if coord list are all in same column
    boolean inSameCol(ArrayList<Coordinate> coords){
        if(coords.isEmpty()) return false;
        int xCoord = coords.get(0).getX();
        for(Coordinate coord : coords){
            if(coord.getX() != xCoord)
                return false;
        }
        return true;
    }

    //checks if coord list are all in same house
    boolean inSameHouse(ArrayList<Coordinate> coords){
        if(coords.isEmpty()) return false;
        Coordinate houseStart = new Coordinate(coords.get(0).getX() - coords.get(0).getX()%3, coords.get(0).getY() - coords.get(0).getY()%3);
        for(Coordinate coord : coords){
            Coordinate start = new Coordinate(coord.getX() - coord.getX()%3, coord.getY() - coord.getY()%3);
            if(!start.equals(houseStart))
                return false;
        }
        return true;
    }

    //get notes in a cell
    ArrayList<Integer> getNotes(Coordinate c){
        return sHash.get(c);
    }

    //assigns coordinate to key value in hash
    void getNoteHash(HashMap<Integer, ArrayList<Coordinate>> hash, ArrayList<Coordinate> blacklist, Coordinate c){
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
        }
    }

    //gets notes for houses
    void checkHouse(HashMap<Integer, ArrayList<Coordinate>> hash, int rowOffset, int colOffset, ArrayList<Coordinate> blacklisted) {
        for(int row = rowOffset; row < rowOffset + SudokuGrid.HOUSE_LIMIT; ++row){
            for(int col = colOffset; col < colOffset + SudokuGrid.HOUSE_LIMIT; ++col){
                Coordinate c = new Coordinate(row + 1, SudokuGrid.BOARD_LIMIT - col);
                getNoteHash(hash, blacklisted, c);
            }
        }
    }

    //prints grid with highlighted markings of certain cells
    void printGridWithHighlights(ArrayList<Coordinate> coords){
        int[][] tempBoard = Arrays.stream(gameGrid).map(int[]::clone).toArray($ -> gameGrid.clone());
        for(Coordinate coord : coords)
            tempBoard[coord.convertRow(coord.getY())][coord.convertCol(coord.getX())] = -1;
        SudokuGrid tempGrid = new SudokuGrid(tempBoard);
        tempGrid.printGrid(true);
    }

}
