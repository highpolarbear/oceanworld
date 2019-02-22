import java.util.List;
import java.util.Random;

/**
 * Write a description of class Disease here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Disease
{
    // Methods and fields with 'disease' refer to the general epidemic happening. 'infection'
    // refers to the individual disease caught by an organism.
    
    /** static fields about the disease in general */ 
    private static int currentStep; // tracks current step as simulator runs;
    private static List<Organism> allOrganisms; // keeps updated list of organisms in simulator;
    private static int diseaseStart = 0; // tracks the step where the most recent disease started;
    private static int diseaseEnd = 0; // tracks the step where the most recent disease ended;
    private static final int STEP_DELAY = 97; // only start a disease after a delay;
    private static final int INFECTION_DURATION = 11; // number of steps infection lasts before it
    // kills the animal
    private static final int SPREAD_DELAY = 3; // number of steps to delay before animal can infect
    // another animal
    private static int infectionCounter; // tracks the total number of infected animals
    private static Random rand = Randomizer.getRandom();
    
    /** private fields to be different in each infection case of each animal */
    private int infectionStart; // stores the step where infection of particular animal started
    private int antibodyDelay; // number of steps delayed before an animal's antibody eliminates the
    // infection. randomly generated for each animal
    
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
    
    private void setAntibodyDelay() {
        antibodyDelay = rand.nextInt(INFECTION_DURATION * 3); 
        // there's a third of a chance the antibody won't eliminate the infection
        // before it kills the host
    }
    
    public static void incrementCounter()
    {
        infectionCounter++;
    }
    
    public static void decrementCounter()
    {
        infectionCounter--;
        if(infectionCounter == 0) {
            diseaseEnd = currentStep;
            System.out.println("disease ended");
        }
    }
    
    public static void resetCounter()
    {
        infectionCounter = 0;
    }
    
    public static int getInfectionDuration()
    {
        return INFECTION_DURATION;
    }
    
    public static int getSpreadDelay()
    {
        return SPREAD_DELAY;
    }
    
    public static int getCurrentStep()
    {
        return currentStep;
    }
    
    public static int getInfectionCounter()
    {
        return infectionCounter;
    }
    
    public void setInfectionStart()
    {
        infectionStart = currentStep;
    }
    
    public int getInfectionStart()
    {
        return infectionStart;
    }
    
    public int getAntibodyDelay()
    {
        return antibodyDelay;
    }
   
    // private methods
    
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
