import java.util.List;
import java.util.Random;

/**
 * A simple model of a fish egg.
 * Eggs age, hatch and die.
 * 
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */

public class Egg extends Organism
{
    // organism's age
    private int age;
    // age at which egg matures
    private int MATURE_AGE = 4;
    // egg's field
    private Field field;
    // organism's location
    private Location location;
    // field of fishes where new eggs
    private Field fishField;
    private Class type;
    private Organism parent;

    /**
     * Create this organism at location in field.
     * 
     */
    public Egg(Field field, Location location, Field fishField, Organism organism)
    {
        super(field, location);
        
        this.field = field;
        this.fishField = fishField;
        
        type = organism.getClass();
        parent = organism;
        
        age = 0;
        setLocation(location);
    }
    
    /**
     * egg hatches, ages. or dies
     * @param newRabbits A list to return newly born eggs.
     */
    public void act(List<Organism> newEgg)
    {
        //checks if the egg is ready to hatch
        boolean canHatch = checkMaturity();
        // increment age
        age++;
        //if the egg can hatch, finds the closest location on the field of 
        //fishes and spawns a new fish of the same type as its parent.
        if (canHatch){
           
            List<Location> free = fishField.getFreeAdjacentLocations(getLocation());
            
            if (free.size() > 0){
                int births = 1;
                for (int i = 0; i < births; i++){
                    Location loc = free.remove(0);
                    Organism newOrganism = hatch(loc, field);
                    newEgg.add(newOrganism);
                }
                setDead(); //the fish is spawned, no need for the egg to
                           // still exist.
            }
        }
     
        
    }
    
    /**
     * hatch a new egg for each organism
     */
    public Organism hatch(Location location, Field field){
        Organism newOrganism = null;
        
        //Checks the type of egg it is depending on its parent,
        //spawns a new fish of the same type as its parent 
        //and passes it on.
                      
        if (parent instanceof Shrimp){
            newOrganism = new Shrimp(fishField, location, field);
        }
        else if (parent instanceof Turtle){
            newOrganism = new Turtle(fishField, location, field);
        }
        else if (parent instanceof Squid){
            newOrganism = new Squid(fishField, location, field);
        }

        else if (parent instanceof SwordFish){
            newOrganism = new SwordFish(fishField, location, field);
        }
        
        else if (parent instanceof Mackerel){
            newOrganism = new Mackerel(fishField, location, field);
        }
        
        else if (parent instanceof BabyShark){
            newOrganism = new BabyShark(fishField, location, field);
        }
    
        
        return newOrganism;
    }
    
    /**
     * return true if egg is above mature age else false
     */ 
    public boolean checkMaturity(){
        //checks if egg is ready to spawn
        if (age > MATURE_AGE){
            return true;
        }
        else{
            return false;
        }
    
    }
        
    /**
     * select a random location in the field
     */
    private Location selectRandomLocation(){
        Field field = getField();
        
        if (getField() != null){
            Location randomLocation;
            Random rand = new Random();
            
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