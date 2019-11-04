import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Class with a main method to run runner the suite of tests 
 * 
 * @author Matthew Harris,
 * 			101 073 502
 */
public class TestSuiteRunner {
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(AllTests.class);
		for (Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
		if(result.wasSuccessful()){
			System.out.println("All Tests Passed Successfully!");	
		}
		
	}
}
