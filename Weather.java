import java.util.Random;

/**
 * Write a description of class Weather here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weather
{
    // instance variables - replace the example below with your own
    private static boolean isSunny; // if not sunny, it's cloudy
    private static int temperature; // in celsius
    private static Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        // initialise instance variables
        setSun();
        setTemperature();
    }
    
    private static void setSun()
    {
        if(Time.isDay()) {
            isSunny = rand.nextBoolean();
        }
        else {
            isSunny = false;
        }
    }
    
    private static void setTemperature()
    {
        if(isSunny) {
            temperature = rand.nextInt(25) + 25;
        }
        else {
            temperature = rand.nextInt(25);
        }
    }
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public static boolean isSunny()
    {
        return isSunny;
    }
    
    public static int getTemperature()
    {
        return temperature;
    }
    
    public static void updateWeather(int hour)
    {
        if(hour == 7 || hour == 13 || hour == 19 || hour == 1) {
            setSun();
            setTemperature();
        }
    }
}