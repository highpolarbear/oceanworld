import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * Write a description of class Turtle here.
 *
 * @author (your name)
 * @version (a version number or a date)!
 */

public class Turtle extends Fish
{
    private int BREEDING_AGE = 4;
    private double BREEDING_PROBABILITY = 0.40;
    private int PLANT_FOOD_VALUE = 20;
    private Random rand = Randomizer.getRandom();
    
    private Field field;
    private Character gender;
    private Field plantationField;

    /**
     * Constructor for objects of class Turtle
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
