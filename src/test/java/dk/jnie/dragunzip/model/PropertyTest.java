package dk.jnie.dragunzip.model;

import static org.junit.Assert.*;

import org.junit.Test;

import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.model.Property;

public class PropertyTest {

	@Test
	public void testGetProps() {
		SortedProperties sp = Property.getProps();
		assertNotNull(sp);
		
		boolean test01 = sp.containsKey(Property.TIMER);
		assertTrue(test01);
		
	}

}
