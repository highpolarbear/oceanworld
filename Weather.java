import java.util.Random;
import java.util.List;

/**
 * Represents weather
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */
public class Weather
{
    // stores weather type
    private static String weather;
    // temperature in celsius
    private static int temperature; 
    private static Random rand = Randomizer.getRandom();
    // array of weathers
    private static String[] weathers = {"sunny","clear","cloudy","overcast"};

    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        setWeather();
        setTemperature();
    }
    
    /**
     * set the weather to one of sunny, clear, cloudy or overcast
     * summer or winter changes the probability of certain weather types
     */
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
    }
    
    /**
     * set temperature according to weather conditions. further change temperature
     * if it's summer or winter
     */
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
     * returns weather state
     */
    public static String getWeather() 
    {
        return weather;
    }
    
    /**
     * returns temperature
     */
    public static int getTemperature()
    {
        return temperature;
    }
    
    /**
     * every 6 hours, update weather and temperature
     */
    public static void updateWeather(int hour)
    {
        if(hour == 7 || hour == 13 || hour == 19 || hour == 1) {
            setWeather();
            setTemperature();
        }
    }
}