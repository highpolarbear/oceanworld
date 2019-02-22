import java.util.Random;
/**
 * Represents time
 *
 * @author Cherry Lim Siang Sue, David Yin and Terry Phung
 * @version 22.02.2019
 */
public class Time
{
    // hours that have passed
    private int hours;
    // boolean of whether or not it is day time 
    private static boolean isDay;
    // array of seasons
    private static String [] seasons = {"spring", "summer", "autumn", "winter"};
    // current season
    private static String currentSeason;
    // tracks the cycle of seasons, with spring as the first
    private static int seasonCounter;
    private Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Time
     */
    public Time()
    {
        hours = 0;
        isDay = false;
        setRandomSeason();
    }
    
    /**
     * Returns whether it is daytime or nighttime.
     */
    public static boolean isDay()
    {
        return isDay;
    }
    
    /**
     * returns hours passed
     */
    public int getHours()
    {
        return hours;
    }
    
    /**
     * returns current season
     */
    public static String getCurrentSeason()
    {
        return currentSeason;
    }
    
    /**
     * randomly select a season
     */
    public void setRandomSeason()
    {
        seasonCounter = rand.nextInt(4);
        currentSeason = seasons[seasonCounter];
    }
    
    /**
     * update the hour as step increments. 1 hour per step.
     * resets when hours reach 24, according to 24 hour clock
     */
    public void updateHours(int step){
        if (step % 1 == 0){
            hours++;
            hours = hours % 24;
            toggleDay();
        }
    }
    
    /**
     * updates the season as step increments
     * cycles through the four seasons
     */
    public void updateSeason(int step) {
        if (step == 1 || step % 120 == 0){
            seasonCounter++;
            seasonCounter = seasonCounter % 4;
            currentSeason = seasons[seasonCounter];
        }
    }
    
    /**
      * Times from 7am to 6pm are daytime. Else it is nighttime.
      */
    private void toggleDay()
    {
        if (hours >= 7 && hours < 19){
            isDay = true;
        }
        else{
            isDay = false;
        }
    }

}
