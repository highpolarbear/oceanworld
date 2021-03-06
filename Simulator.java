import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.awt.Color;
import java.util.Date;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)!
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a plant will be created in the plantation field.
    private static final double PLANT_CREATION_PROBABILITY = 0.32;
    /////////
    private static final double SHRIMP_CREATION_PROBABILITY = 0.32;

    private static final double TURTLE_CREATION_PROBABILITY = 0.12;

    private static final double SQUID_CREATION_PROBABILITY = 0.16;

    private static final double MACKEREL_CREATION_PROBABILITY = 0.08;

    private static final double SWORDFISH_CREATION_PROBABILITY = 0.08;

    private static final double BABYSHARK_CREATION_PROBABILITY = 0.04;

    // List of animals in the field.
    private List<Organism> organisms;
    
    private HashMap stats;
    
    private List<Egg> eggs;
    
    private List<Plant> plants;
    // The current state of the field.
    private Field field;
    
    private Field plantationField;
    
    private Time time;
    
    private Weather weather;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view, plantationView;
    // The current hour in simulation.
    private int hour;
    //The current season in simulation.
    private String season;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        organisms = new ArrayList<>();
        plants = new ArrayList<>();
        eggs = new ArrayList<>();
        plantationField = new Field(depth, width);
        
        field = new Field(depth, width);
        time = new Time();
        weather = new Weather();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Plant.class, Color.GREEN);
        view.setColor(Shrimp.class, Color.YELLOW);
        view.setColor(Squid.class, Color.CYAN);
        view.setColor(SwordFish.class, Color.MAGENTA);
        view.setColor(BabyShark.class, Color.BLACK);
        view.setColor(Mackerel.class, Color.ORANGE);
        view.setColor(Turtle.class, Color.RED);
        
        plantationView = new SimulatorView(depth, width);
        plantationView.setColor(Plant.class, Color.GREEN);
        plantationView.setColor(Egg.class, Color.GRAY);
        
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    public void runSlowSimulation()
    {
        for (int i = 0; i < 5000; i++)
        {
            simulate(1);
            delay(50);
        }
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }

    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        time.updateHours(step);
        hour = time.getHours();
        time.updateSeason(step);
        season = time.getCurrentSeason();
        
        Weather.updateWeather(hour); 
        
        Disease.act(step, organisms);

        // Provide space for newborn animals.
        List<Organism> newOrganisms = new ArrayList<>();

        // Let all organisms act.
        for(Iterator<Organism> it = organisms.iterator(); it.hasNext(); ) {
            Organism organism = it.next();
            organism.act(newOrganisms);

            if(! organism.isAlive()) 
            {
                it.remove();
            }
        }

        // Add the newly born foxes and rabbits to the main lists.
        organisms.addAll(newOrganisms);

        view.showStatus(step, field, hour, season);
        plantationView.showStatus(step, plantationField, hour, season);
    }

    
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        hour = 0;
        organisms.clear();
        plants.clear();
        populate();
        
        time.setRandomSeason();
        Disease.resetCounter();
        
        // Show the starting state in the view.
        view.showStatus(step, field, hour, season);
        plantationView.showStatus(step, plantationField, hour, season);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        plantationField.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
               
                if (rand.nextDouble() <= SHRIMP_CREATION_PROBABILITY){
                    Location location = new Location(row, col);
                    Shrimp shrimp = new Shrimp (field, location, plantationField);
                    organisms.add(shrimp);
                }
                else if (rand.nextDouble() <= PLANT_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    Plant plant = new Plant(plantationField, location);
                    organisms.add(plant);
                }
                else if (rand.nextDouble() <= TURTLE_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    Turtle turtle = new Turtle (field, location, plantationField);
                    organisms.add(turtle);
                }
                else if (rand.nextDouble() <= SQUID_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    Squid squid = new Squid (field, location, plantationField);
                    organisms.add(squid);
                }
                
                else if (rand.nextDouble() <= MACKEREL_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    Mackerel mackerel = new Mackerel(field, location, plantationField);
                    organisms.add(mackerel);
                    
                }
                else if (rand.nextDouble() <= SWORDFISH_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    SwordFish swordFish = new SwordFish(field, location, plantationField);
                    organisms.add(swordFish);
                    
                }
                else if (rand.nextDouble() <= BABYSHARK_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    BabyShark babyShark = new BabyShark(field, location, plantationField);
                    organisms.add(babyShark);
                    
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
}
