import java.util.List;
import java.util.Random;

/**
 * Write a description of class Shrimp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class Shrimp extends Herbivores
{
    // instance variables - replace the example below with your own
    
    int BREEDING_AGE = 1;
    double BREEDING_PROBABILITY = 0.02;
    Random rand = Randomizer.getRandom();
    int age;
    int MAX_AGE;

    /**
     * Constructor for objects of class Shrimp
     */
    public Shrimp(Field field, Location location)
    {
        super(field, location);
        age = 0;
        MAX_AGE = 10;
    }

    public void act(List<Organism> newShrimp){
        
        incrementAge();
        
        if (isAlive()){
            giveBirth(newShrimp);
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    
    }
    
    public void giveBirth(List<Organism> newShrimp){
        
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        
        for(int i = 0; i < births && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Shrimp young = new Shrimp(field, loc);
            newShrimp.add(young);
        }
        
    }
    
    private int breed()
    {
        int births = 0;
        
        int probability = (int) (rand.nextInt(100) * BREEDING_PROBABILITY);
        System.out.println(probability);
        
        births = probability;
        
        return births;
    }
    
    private void incrementAge()
        {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    private boolean canBreed(){
        boolean returnValue;
        
        if (age >= BREEDING_AGE){
            returnValue = true;
        }
        else{
            returnValue = false;
        }
        
        return returnValue;
    }
    
    public int getAge(){
        return age;
    }
}
