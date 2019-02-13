import java.util.List;
/**
 * Write a description of class Fish here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Carnivores extends Organism
{
    
    protected Character[] genders = {'m', 'f'};
    
    public Carnivores(Field field, Location location)
    {
        // initialise instance variables
        super(field, location);
    }

    public void act(List<Organism> newAnimals){}
}
