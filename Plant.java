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
    
    private Field currentField;
    
    private int MAX_AGE;
    
    /**
     * Constructor for objects of class SeaWeed
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
        field.place(this, location);
        alive = true;
        age = 0;
        MAX_AGE = 2;
        
        currentField = field;
    } 
    
    public long spreadProbability(){
        return rand.nextInt(2);
    }
    
    private Location selectRandomLocation(){

        Location randomLocation;
        
        int row = rand.nextInt(80);
        int column = rand.nextInt(120);
        
        randomLocation = new Location (row, column);
        
        return randomLocation;
    }
    
    public void makeNewPlant(List<Organism> newPlant){
        
        Field field = getField();
        
        Location location = selectRandomLocation();

        
        Plant newplant = new Plant(field, location);
        currentField.place(newplant,location);
        System.out.println("seaweed being placed");
        

       
        /**
        if ( field.getObjectAt(location) == null){
            Plant newplant = new Plant(field, location);
            field.place(newplant,location);
            System.out.println("seaweed being placed");
        }*/
       
    }
    
    public void act(List<Organism> newPlant){
        incrementAge();
        
        makeNewPlant(newPlant);
        
        
        /**Field field = currentField;
        
         spreadRatio = spreadProbability();
        
        //System.out.println("plant called, ratio : " + spreadRatio);
        
        for (int i = 0; i < 2; i++){
            Location location = selectRandomLocation();
        
            if ( field.getObjectAt(location) == null){
                Plant newplant = new Plant(field, location);
                field.place(newplant,location);
                System.out.println("seaweed being placed");
            } }*/
            
            
        
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
