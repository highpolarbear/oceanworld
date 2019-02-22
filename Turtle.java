import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a turtle.
 * Turtle can get infected, age, move, reproduce, eat plants and die.
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */

public class Turtle extends Fish
{
    // The age at which this fish can start to breed.
    private int BREEDING_AGE = 4;
    // The likelihood of this organism breeding.
    private double BREEDING_PROBABILITY = 0.40;
    
    // The food value of food. In effect, this is the
    // number of steps a fish can go before it has to eat again.
    private int PLANT_FOOD_VALUE = 20;
    // random generator
    private Random rand = Randomizer.getRandom();
    
    private Field field;
    private Character gender;
    private Field plantationField;

    /**
     * Create this organism at location in field.
     * 
     */
    public Turtle(Field field, Location location, Field plantationField)
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
    public void act(List<Organism> newTurtle){
        if(hasInfection) {
            respondToInfection();
        }
        
        incrementAge();
        incrementHunger();
              
        if (isAlive()&& !Time.isDay()){
            if(isFemale() && mateFound()) {
                giveBirth(newTurtle);
            }
            /** weather*/
            if(!Weather.getWeather().equals("overcast")) {
                Location newLocation = findGrass();

                newLocation = getField().freeAdjacentLocation(getLocation());
                if (newLocation == null){
                
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                if(newLocation != null) {
                    newLocation = getField().freeAdjacentLocation(getLocation());
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
    public void giveBirth(List<Organism> newTurtle){
        
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        Random rand = new Random();
        int births = breed();
        
        
        for(int i = 0; i < births && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Turtle young = new Turtle(field, loc, plantationField);

            newTurtle.add(young);
        }
        
        int probability = rand.nextInt(101);
        
        if (probability > 40 && free.size() > 0){
            Location loc = free.remove(0);
            Egg turtleEgg = new Egg(plantationField, loc, field, this);
            newTurtle.add(turtleEgg);
        }
        
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
    
    /**
     * returns maximum age
     */
    public int getMaxAge(){
        return MAX_AGE;
    }
    
    public int decrementFoodLevel(){
        foodLevel--;
        return foodLevel;
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
            if(animal instanceof Turtle) {
                Turtle turtle = (Turtle) animal;
                if(!turtle.isFemale()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    //Private Methods 
    
    /**
     * Increase the age.
     * This could result in the organism's death.
     */
    private void incrementAge()
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
    private void incrementHunger()
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
     * Look for food adjacent to the current location.
     * Only the first food is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findGrass()
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
}
