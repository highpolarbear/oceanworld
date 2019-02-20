import java.util.Random;
import java.util.List;

/**
 * Write a description of class Weather here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weather
{
    // instance variables - replace the example below with your own
    private static String weather; // if not sunny, it's cloudy
    private static int temperature; // in celsius
    private static Random rand = Randomizer.getRandom();
    private static String[] weathers = {"sunny","clear","cloudy","overcast"};

    /**
     * Constructor for objects of class Weather - UNUSED SO FAR
     */
    public Weather()
    {
        // initialise instance variables
        setWeather();
        setTemperature();
    }
    
    private static void setWeather()
    {
        if(Time.isDay()) {
            weather = weathers[rand.nextInt(4)];
        }
        else {
            weather = "overcast";
        }
    }
    
    private static void setTemperature()
    {
        if(weather.equals("sunny")) {
            temperature = rand.nextInt(6) + 25;
        }
        else if(weather.equals("clear")) {
            temperature = rand.nextInt(6) + 20;
        }
        else if(weather.equals("cloudy")) {
            temperature = rand.nextInt(6) + 10;
        }
        else {
            temperature = rand.nextInt(11);
        }
    }
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public static String getWeather() 
    {
        return weather;
    }
    
    public static int getTemperature()
    {
        return temperature;
    }
    
    public static void updateWeather(int hour)
    {
        if(hour == 7 || hour == 13 || hour == 19 || hour == 1) {
            setWeather();
            setTemperature();
        }
    }
}