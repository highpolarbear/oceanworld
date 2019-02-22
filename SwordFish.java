import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a sword fish.
 * Sword fish can get infected, age, move, reproduce, eat mackerel, shrimp and squid and die.
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */

public class SwordFish extends Fish
{
    // The age at which this fish can start to breed.
    private int BREEDING_AGE = 16;
    // The likelihood of this organism breeding.
    private double BREEDING_PROBABILITY = 0.08;
    // random generator
    private Random rand = Randomizer.getRandom();

    // The age to which this organism can live.
    private int MAX_AGE;

    private Field field;
    private Field plantationField;
    
    // The food value of food. In effect, this is the
    // number of steps a fish can go before it has to eat again.
    private int PLANT_FOOD_VALUE = 50;
    private Character gender;
    
    /**
      * Create this organism at location in field.
     * 
     */
    public SwordFish(Field field, Location location, Field plantationField)
    {
        super(field, location, plantationField);
        this.plantationField = plantationField;
        age = 0;
        MAX_AGE = 25 + rand.nextInt(30);
        foodLevel = rand.nextInt(25);
        gender = genders[rand.nextInt(2)];
    }

   /**
     * This is what this organism does most of the time: it hunts for
     * food. In the process, it might get infected, breed, die of hunger,
     * or die of old age.
     */
    public void act(List<Organism> newSwordFish)
    {
        if(hasInfection) {
            respondToInfection();
        }
        
        incrementAge();
        incrementHunger();
        
        if (isAlive() && Time.isDay()){
            if(isFemale() && mateFound()) {
                giveBirth(newSwordFish);
            }
            //Location newLocation = getField().freeAdjacentLocation(getLocation());
            Location newLocation = findFood();
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    
    }
    
    /**
     * Check whether or not this organism is to give birth at this step.
     * New births will be made into free adjacent locations.
     */
    public void giveBirth(List<Organism> newSwordFish){
        
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        Random rand = new Random();
        int births = breed();
        
        for(int i = 0; i < births && free.size() > 0; i++) {
            Location loc = free.remove(0);
            SwordFish young = new SwordFish(field, loc, plantationField);
            newSwordFish.add(young);
        }
        
        int probability = rand.nextInt(101);
        
        if (probability > 85 && free.size() > 0){
            Location loc = free.remove(0);
            Egg sFishEgg = new Egg(plantationField, loc, field, this);
            newSwordFish.add(sFishEgg);
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    public int breed()
    {
        int births = 0;
        
        int probability = (int) (rand.nextInt(100) * BREEDING_PROBABILITY);
        
        births = probability;
        
        return births;
    }
    
    /**
     * Increase the age.
     * This could result in the organism's death.
     */
    public void incrementAge()
        {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Make this organism more hungry. This could result in the organism's death.
     */
    public void incrementHunger()
    {
        foodLevel--;
        if (foodLevel <= 0){
            setDead();
        }
    }
    
    /**
     * this organism can breed if it has reached the breeding age.
     * @return true if it can breed, false otherwise.
     */
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
    
    /**
     * Look for a mate adjacent to the current location.
     * Only the first male is selected.
     * @return true if mate found, else false.
     */
    public boolean mateFound()
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
    
    public int decrementFoodLevel(){
        foodLevel--;
        return foodLevel;
    }
    
    /**
     * returns maximum age
     */
    public int getMaxAge(){
        return MAX_AGE;
    }
    
    /**
     * Look for food adjacent to the current location.
     * Only the first food is eaten.
     * @return Where food was found, or null if it wasn't.
     */
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
}