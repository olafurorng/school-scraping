import Miscellaneous.ErrorManager;
import sun.applet.Main;

public class MainProgram
{
    public ErrorManager errorManager;
    private static MainProgram instance;

    public static void main(String[] args)
    {
        System.out.println("Running");
        MainProgram program = new MainProgram();
        program.run();
    }

    public MainProgram()
    {

    }

    private void run()
    {
        instance = new MainProgram();
        instance.errorManager = new ErrorManager();
    }

    public static MainProgram getProgram()
    {
        return instance;
    }

}
