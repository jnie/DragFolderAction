package dk.jnie.dragunzip.model;

import junit.framework.TestCase;

import org.junit.Test;

import dk.csc.util.properties.SortedProperties;

public class PropertyTest extends TestCase {

	public final static String TEST_ALL_TEST_TYPE = "UNIT";
	
	@Test
	public void testGetProps() {
		SortedProperties sp = Property.getProps();
		assertNotNull(sp);
		
		boolean test01 = sp.containsKey(Property.TIMER);
		assertTrue(test01);
		
	}

}
