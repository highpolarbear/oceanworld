import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Write a description of class Herbivores here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Fish extends Organism
{
    
    protected Character[] genders = {'m', 'f'};
    
    protected int age;
    protected int MAX_AGE;
    protected int foodLevel;
    
    protected Field field;
    protected Field plantationField;
    
    protected boolean hasInfection;
    protected final double INFECTING_PROBABILITY = 0.33;
    protected Disease disease;
    
    public Fish(Field field, Location location, Field plantationField)
    {
        super(field, location);
        age = 0;
        this.field = field;
        this.plantationField = plantationField;
        hasInfection = false;
        disease = new Disease(); // this disease object is 'dormant'. 
        // its methods are only called when the animal is infected
    }
    
    abstract public void act(List<Organism> newAnimals);
    
    abstract public int getMaxAge();
    
    abstract public int decrementFoodLevel();
    
    abstract public boolean mateFound();
    
    abstract public boolean canBreed();
    
    abstract public int breed();
    
    // This method implements how an infection affects an animal
    // from the start of infection till it gets eliminated or kills the animal.
    // At certain steps after infection, the antibody will act or the animal
    // will infect another, or the infection will kill the animal.
    protected void respondToInfection()
    {
        int step = Disease.getCurrentStep();
        int infectionStart = disease.getInfectionStart();
        int antibodyActs = disease.getAntibodyDelay() + infectionStart;
            
        if(step == antibodyActs) {
            toggleInfection();
            //System.out.println("saved by antibodies, hasInfection =" + hasInfection);
        }
        else if(step == infectionStart + Disease.getSpreadDelay()) {
           infectAnother();
           //System.out.println("spread");
        }
        else if(step == infectionStart + Disease.getInfectionDuration()) {
            setDead();
            //System.out.println("killed by disease");
            Disease.decrementCounter();
            //System.out.println("decrement");
        }
    }
   
    // toggles whether or not animal is infected
    protected void toggleInfection()
    {
        hasInfection = !hasInfection;
        
        // if animal has been infected, record the step where infection started
        // and increment the total infected animals
        if(hasInfection) {
            disease.setInfectionStart();
            Disease.incrementCounter();
        }
        else {
            Disease.decrementCounter();
            System.out.println("decrement");
        }
    }
    
    protected void infectAnother()
    {
        /** null pointer? */
        if(getLocation() != null){
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            Random rand = Randomizer.getRandom();
            
            while(it.hasNext()) {
                Location where = it.next();
                Object organism = field.getObjectAt(where);
                if(organism instanceof Fish) {
                    Fish fish = (Fish) organism;
                    if(fish.isAlive()) { 
                        if(rand.nextDouble() <= INFECTING_PROBABILITY) {
                            fish.toggleInfection();
                        }
                    }
                }
            }
        }
    }
}
