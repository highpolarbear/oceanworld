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
        //this.field = field;
        alive = true;
        age = 0;
        MAX_AGE = 20;
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
            spreadRatio = 1; //rand.nextInt(2);
        
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
        
        /*
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
        
        }*/
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
