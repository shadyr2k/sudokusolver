import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        String[][] exampleGrid = {
                {"8",".","1",".",".","4",".","3","."},
                {".","3",".",".",".",".",".","5","."},
                {".",".","4",".","1",".",".",".","."},
                {"9",".",".","6",".","8",".",".","."},
                {".",".",".",".","5",".",".",".","."},
                {".",".",".","2",".","9",".",".","7"},
                {"6",".",".","5","9",".","8",".","."},
                {".","8",".",".",".",".",".",".","."},
                {".","1",".",".",".",".","6",".","3"}};
        String[][] inputGrid = grabInput();
        int[][] sudokuBoard = SudokuGrid.convertGrid(inputGrid);
        SudokuGrid sudokuGrid = new SudokuGrid(sudokuBoard);
        sudokuGrid.printGrid(true);
        System.out.println();

        HintHelper helper = new HintHelper(sudokuGrid);

        Scanner sc = new Scanner(System.in);
        int input;
        do {
            System.out.println();
            System.out.println("Andromeda's Sudoku Helper - 2021 Edition");
            System.out.println("1 - Get Hint");
            System.out.println("2 - Solve Board");
            System.out.println("3 - Edit Board");
            System.out.println("4 - Check Board");
            System.out.println("5 - Exit");
            System.out.println();
            input = sc.nextInt();
            switch(input) {
                case 1:
                    helper.getHints();
                    break;
                case 2:
                    long start = System.currentTimeMillis();
                    boolean solved = sudokuGrid.solve(sudokuBoard);
                    if(solved) {
                        long end = System.currentTimeMillis();
                        sudokuGrid.printGrid(false);
                        System.out.println("_________");
                        System.out.println("Solved in " + (end - start) + " ms");
                        System.exit(0);
                    } else {
                        System.out.println("Puzzle is invalid, please edit a cell or restart");
                        break;
                    }
                case 3:
                    System.out.print("Enter coordinate of cell to edit: ");
                    String coordInput = sc.next();
                    while(coordInput.replaceAll("[^\\s\\d,]", "").split(",").length != 2)
                        coordInput = sc.next();

                    String[] coordXY = coordInput.replaceAll("[^\\s\\d,]", "").split(",");
                    Coordinate toEdit = new Coordinate(Integer.parseInt(coordXY[0]), Integer.parseInt(coordXY[1]));
                    System.out.print("Enter replacement value (0 for empty): ");
                    int valInput = sc.nextInt();
                    while(valInput < 0 || valInput > 9)
                        valInput = sc.nextInt();
                    sudokuGrid.editCell(toEdit, valInput);
                    sudokuGrid.printGrid(true);
                    //helper.updateGrid(toEdit, valInput);
                    break;
                case 4:
                    int[][] tempBoard = Arrays.stream(sudokuBoard).map(int[]::clone).toArray($ -> sudokuBoard.clone());
                    if(sudokuGrid.solve(tempBoard))
                        System.out.print("This puzzle has a valid solution");
                    else
                        System.out.print("This puzzle is invalid and thus cannot be solved");
                    break;
            }
        } while(input < 5);
    }

    private static String[][] grabInput(){
        Scanner sc = new Scanner(System.in);
        String[][] input = new String[9][9];
        for(int i = 0; i < 9; ++i){
            String in = "";
            System.out.print("Row " + (i + 1) + ": ");
            while(in.length() != 9)
                in = sc.nextLine().replaceAll("[^\\s\\d]", "");
            for(int j = 0; j < 9; ++j)
                input[i][j] = in.split("")[j].isBlank() ? "." : in.split("")[j];
        }
        return input;
    }
}
