import java.util.List;
/**
 * Write a description of class Herbivores here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Fish extends Organism
{
    
    protected Character[] genders = {'m', 'f'};
    
    int age;
    int MAX_AGE;
    int foodLevel;
    
    public Fish(Field field, Location location, Field plantationField)
    {
        super(field, location);
        age = 0;
    }
    
    abstract public void act(List<Organism> newAnimals);
    
    abstract public int getMaxAge();
    
    abstract public int decrementFoodLevel();
    
    protected void incrementAge()
        {
        age++;
        MAX_AGE = getMaxAge();
       
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    protected void incrementHunger()
    {
        foodLevel = decrementFoodLevel();
        if (foodLevel <= 0){
            setDead();
        }
    }
    
}
