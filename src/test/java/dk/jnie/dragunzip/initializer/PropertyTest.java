package dk.jnie.dragunzip.initializer;

import static org.junit.Assert.*;

import org.junit.Test;

import dk.csc.util.properties.SortedProperties;

public class PropertyTest {

	@Test
	public void testGetProps() {
		SortedProperties sp = Property.getProps();
		assertNotNull(sp);
		
		boolean test01 = sp.containsKey(Property.TIMER);
		assertTrue(test01);
		
	}

}
