import java.util.List;
/**
 * Write a description of class Herbivores here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Herbivores extends Organism
{
    
    protected Character[] genders = {'m', 'f'};
    
    public Herbivores(Field field, Location location)
    {
        super(field, location);
    }
    
    abstract public void act(List<Organism> newAnimals);
}
