import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * Write a description of class Squid here.
 *
 * @author (your name)
 * @version (a version number or a date)!
 */

public class Squid extends Fish
{

    
    private int BREEDING_AGE = 2;
    private double BREEDING_PROBABILITY = 0.16;
    private int PLANT_FOOD_VALUE = 50;
    private Random rand = Randomizer.getRandom();

    private Field field;
    private Field plantationField;

    private Character gender;

    /**
     * Constructor for objects of class Squid
     */
    public Squid(Field field, Location location, Field plantationField)
    {
        super(field, location, plantationField);
        this.plantationField = plantationField;
        age = 0;
        MAX_AGE = 50;
        foodLevel = 30 + rand.nextInt(30);
        gender = genders[rand.nextInt(2)];
    }

    // public methods
    
    public void act(List<Organism> newShrimp){
        
        if(hasInfection) {
            respondToInfection();
        }
        
        incrementAge();
        incrementHunger();
        
        Random rand = new Random();
        int choice = rand.nextInt(2);
        
        if (isAlive()&& !Time.isDay()){
            if(isFemale() && mateFound()) {
                giveBirth(newShrimp);
            }
            //Location newLocation = getField().freeAdjacentLocation(getLocation());
            Location newLocation;
            if (choice == 0){
                newLocation = findGrass();
            }
            else {
                newLocation = findFood();
            }
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

    public void giveBirth(List<Organism> newSquid){
        
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        
        for(int i = 0; i < births && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Squid young = new Squid(field, loc, plantationField);

            newSquid.add(young);
        }
        
        int probability = rand.nextInt(101);
        
        if (probability > 60 && free.size() > 0){
            Location loc = free.remove(0);
            Egg squidEgg = new Egg(plantationField, loc, field, this);
            newSquid.add(squidEgg);
        }
        
        
    }
    
    public Location findGrass()
    {
        List<Location> adjacent = plantationField.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object organism = plantationField.getObjectAt(where);
            if(organism instanceof Plant) {
                Plant plant = (Plant) organism;
                if(plant.isAlive() && plant.isMature()) { 
                    plant.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                    
                }
            }
        }
        return null;
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

    public int getMaxAge(){
        return MAX_AGE;
    }
    
    public int getAge(){
        return age;
    }
    
    public int decrementFoodLevel(){
        foodLevel--;
        return foodLevel;
    }
    
    public void incrementAge()
        {
        age++;
        MAX_AGE = getMaxAge();
       
        if(age > MAX_AGE) {
            setDead();
            if(hasInfection){
                Disease.decrementCounter();
            }
        }
    }
    
    public void incrementHunger()
    {
        foodLevel = decrementFoodLevel();
        if (foodLevel <= 0){
            setDead();
            if(hasInfection){
                Disease.decrementCounter();
            }
        }
    }
    
    public boolean mateFound()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Squid) {
                Squid shrimp = (Squid) animal;
                if(!shrimp.isFemale()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int breed()
    {
        int births = 0;
        
        int probability = (int) (rand.nextInt(100) * BREEDING_PROBABILITY);
        
        births = probability;
        
        return births;
    }

    public boolean canBreed(){
        boolean returnValue;
        
        if (age >= BREEDING_AGE){
            returnValue = true;
        }
        else{
            returnValue = false;
        }
        
        return returnValue;
    }
    //private methods
    
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object organism = field.getObjectAt(where);
            if(organism instanceof Shrimp) {
                Shrimp shrimp = (Shrimp) organism;
                if(shrimp.isAlive()) { 
                    shrimp.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    } 
}
