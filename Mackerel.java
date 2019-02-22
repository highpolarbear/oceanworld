import java.util.List;
import java.util.Random;
import java.util.Iterator;


/**
 * Write a description of class Mackerel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class Mackerel extends Fish
{
    // instance variables - replace the example below with your own
    
    private int BREEDING_AGE = 8;
    private double BREEDING_PROBABILITY = 0.08;
    private Random rand = Randomizer.getRandom();
    private int age;
    private int MAX_AGE;
    private int foodLevel;
    private Field field;
    private Field plantationField;
    private int MACKEREL_FOOD_VALUE = 50;
    private Character gender;
    private int x;
    public int animal_food_value = 40;

    /**
     * Constructor for objects of class Mackerel
     */
    public Mackerel(Field field, Location location, Field plantationField)
    {
        super(field, location, plantationField);
        this.plantationField = plantationField;
        age = 0;
        MAX_AGE = 25 + rand.nextInt(30);
        foodLevel = rand.nextInt(25);
        gender = genders[rand.nextInt(2)];
    }

    public void act(List<Organism> newMackerel){
        
        if(hasInfection) {
            respondToInfection();
        }
        
        incrementAge();
        incrementHunger();
        updateBP(); /** weather*/
        
        if (isAlive()&&  Time.isDay()){
            if(isFemale() && mateFound()) {
                giveBirth(newMackerel);
            }
            //Location newLocation = getField().freeAdjacentLocation(getLocation());
            Location newLocation = findFood();
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                //Overcrowding.
                setDead();
            }
        }
    
    }
    
    public void giveBirth(List<Organism> newMackerel){
        
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        
        
        Random rand = new Random();
        int births = breed();
        
        for(int i = 0; i < births && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Mackerel young = new Mackerel(field, loc, plantationField);

            newMackerel.add(young);
        }
        
        int probability = rand.nextInt(101);
        
        if (probability > 60 && free.size() > 0){
            Location loc = free.remove(0);
            Egg mackerelEgg = new Egg(plantationField, loc, field, this);
            newMackerel.add(mackerelEgg);
        }
    }
    
    private int breed()
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
            if(organism instanceof SwordFish) {
                SwordFish swordFish = (SwordFish) organism;
                if(swordFish.isAlive()) { 
                    swordFish.setDead();
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
            /*
            else if(organism instanceof Turtle) {
                Turtle turtle = (Turtle) organism;
                if(turtle.isAlive()) { 
                    turtle.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                }
            */
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
            if(animal instanceof Mackerel) {
                Mackerel mackerel = (Mackerel) animal;
                if(!mackerel.isFemale()) {
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
    
    /** weather*/
    private void updateBP()
    {
        if(Weather.getTemperature() > 25) {
            BREEDING_PROBABILITY = 0;
        }
        else if(Weather.getTemperature() > 20) {
            BREEDING_PROBABILITY = 0.005;
        }
        else if(Weather.getTemperature() > 10) {
            BREEDING_PROBABILITY = 0.01;
        }
    }
    
    public int decrementFoodLevel(){
        foodLevel--;
        return foodLevel;
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