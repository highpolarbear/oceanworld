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
        if(Time.getCurrentSeason().equals("summer")){
            if(rand.nextDouble() <= 0.4) {
                weather = "sunny";
            }
            else if (rand.nextDouble() <= 0.8) {
                weather = "clear";
            }
            else if (rand.nextDouble() <= 0.9) {
                weather = "cloudy";
            }
            else {
                weather = "overcast";
            }
        }
        else if (Time.getCurrentSeason().equals("winter")){
            if(rand.nextDouble() <= 0.4) {
                weather = "overcast";
            }
            else if (rand.nextDouble() <= 0.8) {
                weather = "cloudy";
            }
            else if (rand.nextDouble() <= 0.9) {
                weather = "clear";
            }
            else {
                weather = "sunny";
            }
        }
        else{
            weather = weathers[rand.nextInt(4)];
        }
        /*if(Time.isDay()) {
            weather = weathers[rand.nextInt(4)];
        }
        else {
            weather = "overcast";
        }*/
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
        
        if(Time.getCurrentSeason().equals("summer")){
            temperature += 15;
        }
        else if(Time.getCurrentSeason().equals("winter")){
            temperature -= 15;
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