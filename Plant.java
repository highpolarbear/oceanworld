import java.util.Random;
import java.util.List;

/**
 * Write a description of class Plants here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Plant extends Organism
{
    private int age;
    
    private Location location;
    
    private long spreadRatio;
    
    private Random rand = new Random();
    
    private boolean alive;
    
    private Field field;
    
    private int MAX_AGE;
    
    private boolean mature;
    
    private static double BREEDING_PROBABILITY = 0.15;
    private static final int MAX_LITTER_SIZE = 6;
    
    /**
     * Constructor for objects of class SeaWeed
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
        //field.place(this, location);
        //this.field = field;
        alive = true;
        age = 0;
        MAX_AGE = 140;
        mature = false;
    } 
    
    public int spreadProbability(){
        return rand.nextInt(1);
    }
    
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
    
    public void makeNewPlant(List<Organism> newPlants){
        
        if (! Time.isDay() || Time.isDay()){
            Field field = getField();
            spreadRatio = 1;
            
            List<Location> free = field.getFreeAdjacentLocations(getLocation());
            int births = breed();
            for(int b = 0; b < births && free.size() > 0; b++) {
                Location loc = free.remove(0);
                Plant young = new Plant(field, loc);
                newPlants.add(young);
            }
        
        } 
        
        if(Weather.getWeather().equals("sunny")) {
            for (int i = 0 ; i < spreadRatio; i++){
                Field field = getField();
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
    
    private int breed()
    {
        int births = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    /** end of changes*/
    
    public void act(List<Organism> newPlant){
        incrementAge();
        updateBP();
        if (isAlive() ){//&& Time.isDay()){
            makeNewPlant(newPlant);
        }
        else {
                setDead();
        }
        
            
        
    }
    
    public int getAge(){
        return age;
    }
    
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
    
    /** weather*/
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
    
   
    public boolean isMature(){
        return mature;
    }
    /**
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            System.out.println("field loc cleared");
            location = null;
            field = null;
        }
    }*/
}
