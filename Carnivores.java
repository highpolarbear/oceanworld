import java.util.List;
/**
 * Write a description of class Fish here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Carnivores extends Organism
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Fish
     */
    public Carnivores(Field field, Location location)
    {
        // initialise instance variables
        super(field, location);
    }


    public void act(List<Organism> newAnimals){}
}
