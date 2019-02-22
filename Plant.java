import java.util.Random;
import java.util.List;

/**
 * A simple model of a plant.
 * Plants age, reproduce, and die.
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */
public class Plant extends Organism
{
    // The age to which this organism can live.
    private int MAX_AGE;
    // The likelihood of this organism breeding.
    private static double BREEDING_PROBABILITY = 0.15;
    // The maximum number of offspring.
    private static final int MAX_OFFSPRING_SIZE = 6;
    // organism's age
    private int age;
    // organism's location
    private Location location;
    private long spreadRatio;
    // random generator
    private Random rand = new Random();
    
    private boolean alive;
    // 
    private boolean mature;
    
    private Field field;
    /**
     * Create this organism at location in field.
     * 
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
        alive = true;
        age = 0;
        MAX_AGE = 140;
        mature = false;
    } 
    
    public int spreadProbability(){
        return rand.nextInt(1);
    }
    
    /**
     * Generate new plants. If weather is sunny, generate more plants in
     * random locations
     */
    public void makeNewPlant(List<Organism> newPlants){
        
        Field field = getField();
        spreadRatio = 1;
            
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Plant young = new Plant(field, loc);
            newPlants.add(young);
        }
        
        
        if(Weather.getWeather().equals("sunny")) {
            for (int i = 0 ; i < spreadRatio; i++){
                Location newLocation = selectRandomLocation();
                if(newLocation != null && field.getObjectAt(newLocation) == null) {
                    Plant youngPlant = new Plant(field, newLocation);
                    newPlants.add(youngPlant);
                }
                else {
                    setDead();
                }      
        
            }
        }
    }
    
    /**
     * Plant ages, gets breeding probability affected, and dies 
     */
    public void act(List<Organism> newPlant){
        incrementAge();
        updateBP();
        if (isAlive() ){
            makeNewPlant(newPlant);
        }
        else {
                setDead();
        }
    }
    
    
    public boolean isMature(){
        return mature;
    }
    
    /**
     * Increase the age.
     * This could result in the organism's death.
     */
    private void incrementAge()
        {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
        if (age > 1){
            mature = true;
        }
    }
    
    //private methods
    
    /** 
     * Change the breeding probability according to weather conditions
     */
    private void updateBP()
    {
        if(Weather.getWeather().equals("clear")) {
            BREEDING_PROBABILITY = 0.13;
        }
        if(Weather.getWeather().equals("cloudy")) {
            BREEDING_PROBABILITY = 0.09;
        }
        if(Weather.getWeather().equals("overcast")) {
            BREEDING_PROBABILITY = 0;
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_OFFSPRING_SIZE) + 1;
        }
        return births;
    }
        
    /**
     * select a random location in the field
     */
    private Location selectRandomLocation(){
        Field field = getField();
        
        if (getField() != null){
            Location randomLocation;
            
            int row = rand.nextInt(field.getDepth());
            int column = rand.nextInt(field.getWidth());
            
            randomLocation = new Location (row, column);
            
            return randomLocation;
        }
        else{
            return null;
        }
    }
}
