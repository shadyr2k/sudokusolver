import java.util.stream.IntStream;

class SudokuGrid {

    private int[][] grid;

    static final int HOUSE_LIMIT = 3;
    static final int BOARD_LIMIT = 9;
    static final int NO_VALUE = 0;

    SudokuGrid(int[][] grid){
        this.grid = grid;
    }

    boolean solve(int[][] grid){
        for(int row = 0; row < BOARD_LIMIT; ++row){
            for(int col = 0; col < BOARD_LIMIT; ++col){
                if(grid[row][col] == NO_VALUE){
                    for(int i = 1; i <= 9; ++i){
                        grid[row][col] = i;
                        if(isValid(grid, row, col) && solve(grid))
                            return true;
                        grid[row][col] = NO_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean inCol(int[][] grid, int col){
        boolean[] numArray = new boolean[BOARD_LIMIT];
        return IntStream.range(0, BOARD_LIMIT).allMatch(row -> checkNum(grid, row, col, numArray));
    }

    private boolean inRow(int[][] grid, int row){
        boolean[] numArray = new boolean[BOARD_LIMIT];
        return IntStream.range(0, BOARD_LIMIT).allMatch(col -> checkNum(grid, row, col, numArray));
    }

    private boolean inHouse(int[][] grid, int row, int col){
        boolean[] numArray = new boolean[BOARD_LIMIT];
        for(int row1 = row - row%3; row1 < row - row%3 + 3; ++row1){
            for(int col1 = col - col%3; col1 < col - col%3 + 3; ++col1){
                if(!checkNum(grid, row1, col1, numArray))
                    return false;
            }
        }
        return true;
    }

    private boolean checkNum(int[][] grid, int row, int col, boolean[] numArray){
        int index = grid[row][col] - 1;
        if(grid[row][col] != NO_VALUE){
            if(!numArray[index])
                numArray[index] = true;
            else
                return false;
        }
        return true;
    }

    boolean isValid(int[][] grid, int row, int col){
        return inRow(grid, row) && inCol(grid, col) && inHouse(grid, row, col);
    }

    static int[][] convertGrid(String[][] grid){
        int[][] numGrid = new int[BOARD_LIMIT][BOARD_LIMIT];
        for(int row = 0; row < BOARD_LIMIT; ++row){
            for(int col = 0; col < BOARD_LIMIT; ++col){
                if(grid[row][col].equals("."))
                    numGrid[row][col] = NO_VALUE;
                else
                    numGrid[row][col] = Integer.parseInt(grid[row][col]);
            }
        }
        return numGrid;
    }

    void printGrid(boolean string){
        for(int i = 0; i < BOARD_LIMIT; ++i){
            if(i % HOUSE_LIMIT == 0 && i != 0) {
                System.out.println("-------------------------------");
            }
            for (int j = 0; j < BOARD_LIMIT; ++j) {
                if (j % HOUSE_LIMIT == 0 && j != 0) {
                    System.out.print("|  ");
                }
                if(grid[i][j] == 0 && string)
                    System.out.print(".  ");
                else if(grid[i][j] == -1)
                    System.out.print("✗  ");
                else
                    System.out.print(grid[i][j] + "  ");
            }
            System.out.println();
        }
    }

    void editCell(Coordinate c, int val){
        grid[c.convertRow(c.getY())][c.convertCol(c.getX())] = val;
    }

    int[][] getGrid(){
        return grid;
    }
}
