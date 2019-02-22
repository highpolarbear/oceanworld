import java.util.List;
import java.util.Random;

public class Egg extends Organism
{
    private int age;
    private int MATURE_AGE = 4;
    private Field field;
    private Location location;
    private Field fishField;
    private Class type;
    private Organism parent;

    /**
     * Constructor for objects of class Egg
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
    
    public void act(List<Organism> newEgg)
    {
        boolean canHatch = checkMaturity();
        age++;

        if (canHatch){
            
            List<Location> free = fishField.getFreeAdjacentLocations(getLocation());
            
            if (free.size() > 0){
                int births = 1;
                for (int i = 0; i < births; i++){
                    Location loc = free.remove(0);
                    Organism newOrganism = hatch(loc, field);
                    newEgg.add(newOrganism);
                }
                setDead();
            }
        }
     
        
    }
    
    public Organism hatch(Location location, Field field){
        Organism newOrganism = null;
        
                      
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
    
    public int getAge()
    {
        return age;
    }
    
    public boolean checkMaturity(){
    
        if (age > MATURE_AGE){
            return true;
        }
        else{
            return false;
        }
    
    }
        
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