import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.*;
import junit.runner.*;


@RunWith(Suite.class)
@SuiteClasses({BunnyTest.class, CoordTest.class, GameTest.class, TextTileTest.class, FoxTest.class})
public class AllTests{
	
}
