import java.util.List;
/**
 * Write a description of class Shrimp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Shrimp extends Herbivores
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Shrimp
     */
    public Shrimp(Field field, Location location)
    {
        super(field, location);
    }

    public void act(List<Animal> newShrimp){
    
        if (isAlive()){
            giveBirth();
        }
    
    }
    
    public void giveBirth(){
    
        //give birth = new shrimp.
        
    }
}
