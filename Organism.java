import java.util.List;

/**
 * A class representing shared characteristics of organisms (living things).
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */
public abstract class Organism
{
    // Whether the organism is alive or not.
    private boolean alive;
    // The organism's field.
    private Field field;
    // The organism's position in the field.
    private Location location;
    
    /**
     * Constructor for objects of class Organism
     */
    public Organism(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this organism act - that is: make it do
     * whatever it wants/needs to do.
     * @param newOrganisms A list to receive newly born organisms.
     */
    abstract public void act(List<Organism> newOrganisms);
    
    /**
     * Check whether the organism is alive or not.
     * @return true if the organism is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Indicate that the organism is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
         alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the organism's location.
     * @return The organism's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the organism at the new location in the given field.
     * @param newLocation The organism's new location.
     */
    protected void setLocation(Location newLocation, Field nextfield)
    {
        if(location != null) {
            nextfield.clear(location);
        }
        location = newLocation;
        nextfield.place(this, newLocation);
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The organism's new location.
     */
    protected void setLocation(Location newLocation){
        setLocation(newLocation, field);
    }
    
    /**
     * Return the organism's field.
     * @return The organism's field.
     */
    protected Field getField()
    {
        return field;
    }
}
