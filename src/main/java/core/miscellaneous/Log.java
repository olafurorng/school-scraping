package core.miscellaneous;

/**
 * Created by olafurorn on 1/21/15.
 */
public class Log
{
    // for debugging
    public static void d(String message)
    {
        System.out.println("DEBUG >>>  " + message);
    }

    // for information
    public static void i(String message)
    {
        System.out.println("INFO >>> " + message);
    }


    // for error
    public static void err(String message)
    {
        System.out.println("ERROR >>> " + message);
    }

    // for exceptions
    public static void ex(String message, Exception ex)
    {
        System.out.println("==== EXCEPTION ====");
        System.out.println("ERROR >>> " + message);
        ex.printStackTrace();
        System.out.println("===================");
    }
}
