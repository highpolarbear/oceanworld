import java.util.List;
import java.util.Random;

/**
 * Represents a disease that can infect a fish and kill it after a certain period of time.
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */
public class Disease
{
    // Methods and fields with 'disease' refer to the general epidemic happening. 'infection'
    // refers to the individual disease caught by an organism.
    
    /** static fields about the disease in general */ 
    // tracks current step as simulator runs;
    private static int currentStep; 
    // keeps updated list of organisms in simulator;
    private static List<Organism> allOrganisms; 
    // tracks the step where the most recent disease started;
    private static int diseaseStart = 0; 
    // tracks the step where the most recent disease ended;
    private static int diseaseEnd = 0;
    // only start a disease after a delay;
    private static final int STEP_DELAY = 97; 
    // number of steps infection lasts before it
    // kills the animal
    private static final int INFECTION_DURATION = 11;
    // number of steps to delay before animal can infect
    // another animal
    private static final int SPREAD_DELAY = 3; 
     // tracks the total number of infected animals
    private static int infectionCounter;
    // random generator
    private static Random rand = Randomizer.getRandom();
    
    /** private fields to be different in each infection case of each animal */
     // stores the step where infection of particular animal started
    private int infectionStart; 
    // number of steps delayed before an animal's antibody eliminates the
    // infection. randomly generated for each animal
    private int antibodyDelay;
    
    
    /**
     * Constructor for objects of class Disease
     */
    public Disease()
    {
        // initialise instance variables
        setAntibodyDelay();
    }
    
    /**
     * Tracks each step as simulator runs to decide whether or not to start a new disease epidemic.
     */
    public static void act(int step, List<Organism> organisms)
    {
        currentStep = step; 
        allOrganisms = organisms;
        
        // start a new disease after a delay after simulator starts or 
        // after the last disease has ended;
        if (currentStep == diseaseEnd + STEP_DELAY) { 
            startDisease();
            System.out.println("disease started");
            diseaseStart = step;
        }
    }
    
    /**
     * randomly select a step where antibody will act
     */
    private void setAntibodyDelay() {
        antibodyDelay = rand.nextInt(INFECTION_DURATION * 3); 
        // there's a third of a chance the antibody won't eliminate the infection
        // before it kills the host
    }
    
    /**
     * increase infection count
     */
    public static void incrementCounter()
    {
        infectionCounter++;
    }
    
    /**
     * decrease infection count
     */
    public static void decrementCounter()
    {
        infectionCounter--;
        if(infectionCounter == 0) {
            diseaseEnd = currentStep;
            System.out.println("disease ended");
        }
    }
    
    /**
     * reset infection count
     */
    public static void resetCounter()
    {
        infectionCounter = 0;
    }
    
    /**
     * return infection duration
     */
    public static int getInfectionDuration()
    {
        return INFECTION_DURATION;
    }
    
    /**
     * return spread delay
     */
    public static int getSpreadDelay()
    {
        return SPREAD_DELAY;
    }
    
    /**
     * return current step of simulation
     */
    public static int getCurrentStep()
    {
        return currentStep;
    }
    
    /**
     * return infection count
     */
    public static int getInfectionCounter()
    {
        return infectionCounter;
    }
    
    /**
     * store the step at which infection started
     */
    public void setInfectionStart()
    {
        infectionStart = currentStep;
    }
    
    /**
     * returns the step at which infection of particular fish started
     */
    public int getInfectionStart()
    {
        return infectionStart;
    }
    
    /**
     * returns antibody delay
     */
    public int getAntibodyDelay()
    {
        return antibodyDelay;
    }
   
    // private methods
    
    /**
     * randomly select a fish to infect
     */
    private static void startDisease() 
    {
        
        
        int orgSize = allOrganisms.size();
        Organism org;
        
        // select a random animal to infect
        for(int i = 0; i < orgSize; i++) {
            org = allOrganisms.get(rand.nextInt(orgSize));
            if (org instanceof Fish) {
                Fish fish = (Fish) org;
                fish.toggleInfection();
                return;
            }
        }
        
    }
}
