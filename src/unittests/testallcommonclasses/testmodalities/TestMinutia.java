package unittests.testallcommonclasses.testmodalities;

import org.junit.Test;
import system.allcommonclasses.modalities.Minutia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class TestMinutia {

	@Test
	public void testMinutiaSorting() {
		boolean result = true;

		Minutia reference = new Minutia(20,150, 359);
		Minutia m0 = new Minutia(0,0,210); // distance to reference: 151.3
		Minutia m1 = new Minutia(41, 299, 21); // distance to reference: 150.5
		Minutia m2 = new Minutia(50,50,0); // distance to reference: 104.4
		Minutia m3 = new Minutia(20, 150, 5); // distance to reference: 0.0
		Minutia m4 = new Minutia(21, 150, 27); // distance to reference: 1.0
		Minutia m5 = new Minutia(20, 148, 6); // distance to reference: 1.4
		Minutia m6 = new Minutia(-10, -10, 55); // distance to reference: 162.8
		Minutia m7 = new Minutia(1000, 1000, 12); // distance to reference: 1297.3
		Minutia m8 = new Minutia(100, 140, 400); // distance to reference: 80.6

		ArrayList<Minutia> expectedPoints = 
				new ArrayList<Minutia>(Arrays.asList(m3,m4,m5,m8,m2,m1,m0,m6,m7));
		ArrayList<Minutia> computedPoints = 
				new ArrayList<Minutia>(Arrays.asList(m0,m1,m2,m3,m4,m5,m6,m7,m8));
		
		Collections.sort(computedPoints, reference.getComparator());
		
		for(int i=0; i < expectedPoints.size(); i++){
			result &= (expectedPoints.get(i) == computedPoints.get(i) );
		}
		
		assertTrue("\nExpected: " + expectedPoints + "\nComputed: " + computedPoints,
				result);
	}
	
	public void testThetaSetter(Long setTo, Long expected) {
		Minutia m0 = new Minutia();
		m0.setTheta(setTo);
		Long computed = m0.getTheta();
		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
				expected.equals(computed));
	}
	@org.junit.Test public void thetaSetter0(){this.testThetaSetter(360L, 0L);}
	@org.junit.Test public void thetaSetter1(){this.testThetaSetter(4340930L, 50L);}
	@org.junit.Test public void thetaSetter2(){this.testThetaSetter(0L, 0L);}
	@org.junit.Test public void thetaSetter4(){this.testThetaSetter(-50L, 310L);}
	@org.junit.Test public void thetaSetter5(){this.testThetaSetter(183L, 183L);}
	@org.junit.Test public void thetaSetter6(){this.testThetaSetter(-9770L, 310L);}
	
	
	
	
}
