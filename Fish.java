import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A class representing shared characteristics of fish.
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */
public abstract class Fish extends Organism
{
    // an array of characters representing genders for assigning
    protected Character[] genders = {'m', 'f'};
    // the fish's age
    protected int age;
    // maximum age before fish dies
    protected int MAX_AGE;
    // fish's food level which is increased by eating food
    protected int foodLevel;
    // fish's field
    protected Field field;
    // separate field on sea floor to find plants and lay eggs
    protected Field plantationField;
    // true if fish is infected else false
    protected boolean hasInfection;
    // probability of fish infecting another one
    protected final double INFECTING_PROBABILITY = 0.33;
    // variable for storing disease object 
    protected Disease disease;
    
    /**
     * Create a new fish at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
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
    
    /**
     * Make this fish act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Organism> newAnimals);
    
    /**
     * returns maximum age
     */
    abstract public int getMaxAge();
    
    /**
     * decreases food level by 1
     */
    abstract public int decrementFoodLevel();
    
    /**
     * returns true if mate is found for fish else false
     */
    abstract public boolean mateFound();
    
    /**
     * returns true if fish is of breeding age
     */
    abstract public boolean canBreed();
    
    /**
     * method for breeding. returns number of offspring to give birth to
     */
    abstract public int breed();
    
    /**
     * This method implements how an infection affects an animal
     * from the start of infection till it gets eliminated or kills the animal.
     * At certain steps after infection, the antibody will act or the animal
     * will infect another, or the infection will kill the animal.
     */
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
   
    /**
     * toggles whether or not animal is infected
     */
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
    
    /**
     * method for infecting another fish
     */
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
