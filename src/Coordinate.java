public class Coordinate {

    private int x;
    private int y;

    Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

    int convertCol(int x){
        return x - 1;
    }

    int convertRow(int y){
        return SudokuGrid.BOARD_LIMIT - y;
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Coordinate)) return false;
        Coordinate c = (Coordinate) obj;
        return x == c.getX() && y == c.getY();
    }

    @Override
    public int hashCode(){
        return x + y;
    }
}
