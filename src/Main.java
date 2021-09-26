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
        //String[][] inputGrid = grabInput();
        SudokuGrid sudokuGrid = new SudokuGrid(exampleGrid);
        sudokuGrid.printGrid();
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
