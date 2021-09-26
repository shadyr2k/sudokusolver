public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
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
