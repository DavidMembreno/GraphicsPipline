package Models;

public class Color extends ColorAbstract
{
    public Color(double r, double g, double b) throws IllegalArgumentException
    {
        if(r < 0.0 || r > 1.0 || g < 0.0 || g > 1.0 || b < 0.0 || b > 1.0)//Makes sure RGB values stay within range
            throw new IllegalArgumentException("value out of range");
        this.r = r;
        this.g = g;
        this.b = b;
    }

    //Returns array of scaled RGB values
    @Override
    public int[] scale(int s)
    {
        int[]scaleArray =new int [3];
        scaleArray[0] = (int) r*s;
        scaleArray[1] = (int) g*s;
        scaleArray[2] = (int) b*s;
        return scaleArray;
    }
}
