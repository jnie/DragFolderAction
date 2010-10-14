package dk.jnie.dragunzip.monitor;

import junit.framework.TestCase;

import org.junit.Test;

public class MonitorTest extends TestCase {
	
	public final static String TEST_ALL_TEST_TYPE = "UNIT";
	
	@Test
	public void testGetInstance() {
		Monitor mon = Monitor.getInstance();
		
		System.out.println(mon.isDoRun());
	}

}
