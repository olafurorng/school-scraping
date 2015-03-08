package core;

import core.logic.Scraper;
import core.miscellaneous.ErrorManager;
import junit.framework.TestCase;

import static org.mockito.Mockito.*;

public class MainProgramTest extends TestCore {

    /*
    We are using both Junit and Mockito libraries to create tests.
     With Mockito we can mock up dummy objects and use that to
     verify what is happening in the method instead of just looking
     at ‘if the input is this, then the output should be this’,
     and you can do a lot of other cool stuff with this library
     e.g. spy on real objects and use argument captor.
     */

    private MainProgram program;

    public void setUp() throws Exception {
        super.setUp();

        program = spy(new MainProgram());



    }

    public void tearDown() throws Exception {
        program = null;
        MainProgram.instance = null;
    }

    public void testMain() throws Exception {
        MainProgram.main(any(String[].class));

        assertEquals(isA(MainProgram.class), MainProgram.instance);
        assertEquals(isA(ErrorManager.class), MainProgram.instance.errorManager);
        assertEquals(isA(Scraper.class), MainProgram.instance.scraper);
        verify(eq(MainProgram.instance)).run();
    }
}