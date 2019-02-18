import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * Write a description of class SwordFish here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class SwordFish extends Carnivores
{
    // instance variables - replace the example below with your own
    
    private int BREEDING_AGE = 1;
    private double BREEDING_PROBABILITY = 0.01;
    private Random rand = Randomizer.getRandom();
    private int age;
    private int MAX_AGE;
    private int foodLevel;
    private Field field;
    private int PLANT_FOOD_VALUE = 3;
    private Character gender;
    private int x;

    /**
     * Constructor for objects of class SwordFish
     */
    public SwordFish(Field field, Location location)
    {
        super(field, location);
        age = 0;
        MAX_AGE = 200;
        foodLevel = 3000;
        gender = genders[rand.nextInt(2)];
    }

    public void act(List<Organism> newSwordFish){
        
        incrementAge();
        incrementHunger();
        
        if (isAlive()){//&& Time.isDay()){
            if(isFemale() && mateFound()) {
                giveBirth(newSwordFish);
            }
            //Location newLocation = getField().freeAdjacentLocation(getLocation());
            Location newLocation = findFood();
            if (newLocation == null){
                
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    
    }
    
    public void giveBirth(List<Organism> newSwordFish){
        
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        
        for(int i = 0; i < births && free.size() > 0; i++) {
            Location loc = free.remove(0);
            SwordFish young = new SwordFish(field, loc);
            newSwordFish.add(young);
        }
        
    }
    
    private int breed()
    {
        int births = 0;
        
        int probability = (int) (rand.nextInt(100) * BREEDING_PROBABILITY);
        
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
    
    private void incrementHunger()
    {
        foodLevel--;
        if (foodLevel <= 0){
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
    
        private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object organism = field.getObjectAt(where);
            if(organism instanceof Mackerel) {
                Mackerel mackerel = (Mackerel) organism;
                if(mackerel.isAlive()) { 
                    mackerel.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                }
            }
            else if(organism instanceof Shrimp) {
                Shrimp shrimp = (Shrimp) organism;
                if(shrimp.isAlive()) { 
                    shrimp.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                }
            }
            else if(organism instanceof Squid) {
                Squid squid = (Squid) organism;
                if(squid.isAlive()) { 
                    squid.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    private boolean mateFound()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof SwordFish) {
                SwordFish swordFish = (SwordFish) animal;
                if(!swordFish.isFemale()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if it is female
     */
    public boolean isFemale()
    {
        if (gender.equals('f')) { 
            return true;
        }
        else {
            return false;
        }
    }

}