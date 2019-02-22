import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * Write a description of class Shrimp here.
 *
 * @author (your name)
 * @version (a version number or a date)!
 */

public class Shrimp extends Fish
{
    // instance variables - replace the example below with your own
    
    private int BREEDING_AGE = 1;
    private double BREEDING_PROBABILITY = 0.40;
    private Random rand = Randomizer.getRandom();
    //private int age;
    //private int MAX_AGE = 100;
    //private int foodLevel;
    private Field field;
    private int PLANT_FOOD_VALUE = 50;
    private Character gender;
    private int x;
    private Field plantationField;

    /**
     * Constructor for objects of class Shrimp
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
    
    private int breed()
    {
        int births = 0;
        
        int probability = (int) (rand.nextInt(100) * BREEDING_PROBABILITY);
        
        births = probability;
        
        return births;
    }
    
    public int decrementFoodLevel(){
        foodLevel--;
        return foodLevel;
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
    
    protected Location findGrass()
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
    private Location s()
    {
        //Field plantationField = getPlantation();
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
    
    private boolean mateFound()
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
    
    protected void incrementAge()
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
    
    protected void incrementHunger()
    {
        foodLevel = decrementFoodLevel();
        if (foodLevel <= 0){
            setDead();
            if(hasInfection){
                Disease.decrementCounter();
            }
        }
    }
}
