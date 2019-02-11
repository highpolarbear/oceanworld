import java.util.List;
/**
 * Write a description of class Fish here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Fish extends Animal
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Fish
     */
    public Fish(Field field, Location location)
    {
        // initialise instance variables
        super(field, location);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
