
/**
 * Write a description of class Time here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Time
{
    // instance variables - replace the example below with your own
    private static int hours;
    private static boolean isDay;

    /**
     * Constructor for objects of class Time
     */
    public Time()
    {
        hours = 0;
        isDay = false;
    }

    /**
     * Increment hour every 20 steps.
     *
    public static void updateHours(int step)
    {
        if (step % 20 == 0) {
            hours = (hours + 1) % 24;
            toggleDay();
        }
    } */
    
    /**
     * Times from 7am to 6pm are daytime. Else it is nighttime.
     */
    private static void toggleDay()
    {
        if (hours >= 7 && hours < 19){
            isDay = true;
        }
        else{
            isDay = false;
        }
    }
    
    /**
     * Returns whether it is daytime or nighttime.
     */
    public static boolean isDay()
    {
        return isDay;
    }
    
    public static int getHours()
    {
        return hours;
    }
    
    public static int updateHours(int step){
        if (step % 1 == 0){
            hours++;
            hours = hours % 24;
            toggleDay();
        }

        return hours;
    }
}
