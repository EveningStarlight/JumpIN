import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.*;
import junit.runner.*;

/**
 * Class to hold the classes of the tests for the suite
 * 
 * @author Matthew Harris,
 * 			101 073 502
 */
@RunWith(Suite.class)
@SuiteClasses({BunnyTest.class, CoordTest.class, GameTest.class, TextTileTest.class, FoxTest.class})
public class AllTests{
	
}
