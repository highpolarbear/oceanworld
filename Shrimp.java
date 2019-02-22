import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a shrimp.
 * Shrimp can get infected, age, move, reproduce, eat plants and die.
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */

public class Shrimp extends Fish
{
    // The age at which this fish can start to breed.
    private int BREEDING_AGE = 1;
    // The likelihood of this organism breeding.
    private double BREEDING_PROBABILITY = 0.40;
    
    // The food value of food. In effect, this is the
    // number of steps a fish can go before it has to eat again.
    private int PLANT_FOOD_VALUE = 50;
    // random generator
    private Random rand = Randomizer.getRandom();

    private Field field;
    private Field plantationField;
    
    private Character gender;
    
    /**
     * Create this organism at location in field.
     * 
     */
    public Shrimp(Field field, Location location, Field plantationField)
    {
        super(field, location, plantationField);
        
        this.plantationField = plantationField;
        age = 0;
        MAX_AGE = 30;
        foodLevel = 30 + rand.nextInt(30);
        gender = genders[rand.nextInt(2)];
       
    }

    //public methods
    
    /**
     * This is what this organism does most of the time: it hunts for
     * food. In the process, it might get infected, breed, die of hunger,
     * or die of old age.
     */
    public void act(List<Organism> newShrimp){
        
        if(hasInfection) {
            respondToInfection();
        }
        
        incrementAge();
        incrementHunger();
        
        if (isAlive()&& !Time.isDay()){
            if(isFemale() && mateFound()) {
                giveBirth(newShrimp);
            }
            //Location newLocation = getField().freeAdjacentLocation(getLocation());
            
            /** weather*/
            if(!Weather.getWeather().equals("overcast")) {
                Location newLocation = findGrass();
        
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
    
    }
    
    /**
     * Check whether or not this organism is to give birth at this step.
     * New births will be made into free adjacent locations.
     */
    public void giveBirth(List<Organism> newShrimp){
        
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        //int births = 4;//breed();
        Random rand = new Random();
        int births = breed();
        
        for(int i = 0; i < births && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Shrimp young = new Shrimp(field, loc, plantationField);

            newShrimp.add(young);
        }
        
        int probability = rand.nextInt(101);
        
        if (probability > 60 && free.size() > 0){
            Location loc = free.remove(0);
            Egg shrimpEgg = new Egg(plantationField, loc, field, this);
            newShrimp.add(shrimpEgg);
        }
        
        
    }
    
    /**
     * Look for food adjacent to the current location.
     * Only the first food is eaten.
     * @return Where food was found, or null if it wasn't.
     */
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
     * Increase the age.
     * This could result in the organism's death.
     */
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
    
    /**
     * Make this organism more hungry. This could result in the organism's death.
     */
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
            if(animal instanceof Shrimp) {
                Shrimp shrimp = (Shrimp) animal;
                if(!shrimp.isFemale()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //private Methods
    
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
}
