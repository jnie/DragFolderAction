package dk.jnie.junit;

import java.io.File;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class use ClassFinder and TestCaseLoader to find the TestCase classes in the folder specified by the 
 * System property "root_folder"
 * Another System property that needs to be set i "test_type=ALL|UNIT"
 * All TestCase classes must contain a property of 'public final static String TEST_ALL_TEST_TYPE = "ALL|UNIT"' 
 * 
 * @author jniels23
 *
 */
public class TestAll extends TestCase {

	   private static int addAllTests(final TestSuite suite, final Vector<Class<TestCase>> classes)
	   throws java.io.IOException {
	      int testClassCount = 0;
	      for (Class<TestCase> testCaseClass:classes) {
	         suite.addTest (new TestSuite (testCaseClass));
	         System.out.println ("Loaded test case: " + testCaseClass.getName ());
	         testClassCount++;
	      }
	      return testClassCount;
	   }

	   public static Test suite()
	   throws Throwable {
	      try {
	         String classRootString = System.getProperty("class_root");
	         if (classRootString == null) throw new IllegalArgumentException ("System property class_root must be set.");
	         String testType = System.getProperty("test_type");
	         if (testType == null) throw new IllegalArgumentException ("System property test_type must be set.");
	         File classRoot = new File(classRootString);
	         ClassFinder classFinder = new ClassFinder (classRoot);
	         TestCaseLoader testCaseLoader = new TestCaseLoader (testType);
	         testCaseLoader.loadTestCases (classFinder.getClasses ());
	         TestSuite suite = new TestSuite();
	         int numberOfTests = addAllTests (suite, testCaseLoader.getClasses ());
	         System.out.println("Number of test classes found: " + numberOfTests);
	         return suite;
	      } catch (Throwable t) {
	         // This ensures we have extra information. Otherwise we get a "Could not invoke the suite method." message.
	         t.printStackTrace ();
	         throw t;
	      }
	   }
	  /**
	   * Basic constructor - called by the test runners.
	   */
	   public TestAll(String s) {
	      super(s);
	   }

}
