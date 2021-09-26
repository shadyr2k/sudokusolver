class SudokuGrid {

    private String[][] grid;
    private int[][] intGrid;

    SudokuGrid(String[][] grid){
        this.grid = grid;
        convertGrid();
    }

    private boolean solve(){

        return false;
    }

    private boolean puzzleFilled(){
        for(int row = 0; row < 9; ++row){
            for(int col = 1; col < 9; ++col){
                if(intGrid[row][col] == 0)
                    return true;
            }
        }
        return false;
    }

    private boolean inCol(int col, int n){
        for(int row = 0; row < 9; ++row){
            if(intGrid[row][col] == n)
                return true;
        }
        return false;
    }

    private boolean inRow(int row, int n){
        for(int col = 0; col < 9; ++col){
            if(intGrid[row][col] == n)
                return true;
        }
        return false;
    }

    private boolean inHouse(int startRow, int startCol, int n){
        for(int row = 0; row < 3; ++row){
            for(int col = 0; col < 3; ++col){
                if(intGrid[startRow + row][startCol + col] == n)
                    return true;
            }
        }
        return false;
    }

    private boolean isValid(int row, int col, int n){
        return !inCol(col, n) && !inRow(row, n) && !inHouse(row - row%3, col - col%3, n);
    }

    private void convertGrid(){
        intGrid = new int[9][9];
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                if(grid[i][j].equals("."))
                    intGrid[i][j] = 0;
                else
                    intGrid[i][j] = Integer.parseInt(grid[i][j]);
            }
        }
    }

    void printGrid(){
        for(int i = 0; i < 9; ++i){
            if(i % 3 == 0) {
                System.out.println("-------------------------------");
            }
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("|  ");
                }
                System.out.print(grid[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
