package dk.jnie.dragunzip.monitor;

import static org.junit.Assert.*;
import dk.jnie.dragunzip.monitor.Monitor;

import org.junit.Test;

public class MonitorTest {

	@Test
	public void testGetInstance() {
		Monitor mon = Monitor.getInstance();
		
		System.out.println(mon.isDoRun());
	}

}
