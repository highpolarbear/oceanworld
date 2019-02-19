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
    
    /**
     * Constructor for objects of class SeaWeed
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
        //field.place(this, location);
        alive = true;
        age = 0;
        MAX_AGE = 20;
    } 
    
    public int spreadProbability(){
        return rand.nextInt(1);
    }
    
    private Location selectRandomLocation(){
        Field field = getField();

        Location randomLocation;
        
        int row = rand.nextInt(field.getDepth());
        int column = rand.nextInt(field.getWidth());
        
        randomLocation = new Location (row, column);
        
        return randomLocation;
    }
    
    public void makeNewPlant(List<Organism> newPlants){
        
        if (Time.isDay()){
            Field field = getField();
            spreadRatio = rand.nextInt(2);
        
            for (int i = 0 ; i < spreadRatio; i++){
                Location newLocation = getField().freeAdjacentLocation(getLocation());
                if (newLocation == null){
                
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                if(newLocation != null) {
                    Plant youngPlant = new Plant(field, newLocation);
                    newPlants.add(youngPlant);
                }
                else {
                    setDead();
                }      
            
            }
        }
    }
    
    public void act(List<Organism> newPlant){
        incrementAge();
        
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
    }
}
