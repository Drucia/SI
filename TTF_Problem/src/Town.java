public class Town
{
    private int index;
    private double x;
    private double y;

    public Town(int idx, double x, double y)
    {
        index = idx;
        this.x = x;
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int calcDistance(Town other)
    {
        return (int) (Math.sqrt(Math.pow((x - other.x), 2) + Math.pow((y - other.y), 2)));
    }

    @Override
    public String toString() {
        return "Town(" + index + ", " + x + ", " + y + ")";
    }
}
