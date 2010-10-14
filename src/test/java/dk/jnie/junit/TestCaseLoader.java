package dk.jnie.junit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Vector;

import junit.framework.TestCase;

/**
 * This class will add all testcase class files to a list
 * @author jniels23
 *
 */
public class TestCaseLoader {

	private final Vector<Class<TestCase>> testCaseClassList = new Vector<Class<TestCase>>();
	private final String requiredType;
	
	public TestCaseLoader(String requiredType) {
		if (requiredType == null) {
			throw new IllegalArgumentException("Argument requiredType should not be null");
		}
		this.requiredType = requiredType;
	}
	
	private void addIfTestCase(Class testCaseClass) {
		if (isTestCaseOfCorrectType(testCaseClass)) {
			testCaseClassList.add(testCaseClass);
		}
	}

	/**
	 * Test the class to determine if it is a TestCase, and has a field of "Public Static Final String" 
	 * called TEST_ALL_TEST_TYPE
	 * @param testCaseClass
	 * @return
	 */
	private boolean isTestCaseOfCorrectType(Class testCaseClass) {
		boolean isCorrectType = false;

		if (TestCase.class.isAssignableFrom(testCaseClass)) {
			try {
	            Field testAllIgnoreThisField = testCaseClass.getDeclaredField("TEST_ALL_TEST_TYPE");
	            final int EXPECTED_MODIFIERS = Modifier.STATIC | Modifier.PUBLIC | Modifier.FINAL;
	            if (((testAllIgnoreThisField.getModifiers() & EXPECTED_MODIFIERS) != EXPECTED_MODIFIERS) ||
	               (testAllIgnoreThisField.getType() != String.class)) {
	               throw new IllegalArgumentException ("TEST_ALL_TEST_TYPE should be private static final String");
	            }
	            String testType = (String)testAllIgnoreThisField.get(testCaseClass);
	            isCorrectType = requiredType.equals (testType);
	         } catch (NoSuchFieldException e) {
	         } catch (IllegalAccessException e) {
	            throw new IllegalArgumentException ("The field " + testCaseClass.getName () + ".TEST_ALL_TEST_TYPE is not accessible.");
	         }

		}
		
		return isCorrectType;
	}
	
	public void loadTestCases(Vector<String> classNames) {
		if (classNames == null) {
			throw new IllegalArgumentException("Argument should contain a className list.");
		}
		for (String className:classNames) {
			try {
				Class candidateClass = Class.forName (className);
	            addIfTestCase(candidateClass);

			} catch(ClassNotFoundException e) {
				System.err.println ("Cannot load class: " + className);
			}
		}
	}
	
	public Vector<Class<TestCase>> getClasses() {
		return testCaseClassList;
	}
	
}
