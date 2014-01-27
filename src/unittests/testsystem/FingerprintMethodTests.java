package unittests.testsystem;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import system.allcommonclasses.modalities.Minutia;
import system.allcommonclasses.settings.NgonSettings;
import system.makefeaturevector.feature.Variable;
import system.makefeaturevector.fingerprintmethods.Ngons;
import system.makefeaturevector.fingerprintmethods.Ngons.Ngon;


public class FingerprintMethodTests {
	@Test
	public void testRecursiveConstructor(){
		ArrayList<Ngon> ngonList = new ArrayList<Ngon>();
		NgonSettings settings = NgonSettings.getInstance();
		settings.setN(4L);
		Ngons ngons = new Ngons();
		//Method method = Ngons.getClass
		ArrayList<Minutia> points = new ArrayList<Minutia>();
		
		points.add(new Minutia(102,  -75, 360));
		points.add(new Minutia(52, 106, 721));
		points.add(new Minutia(52, -10, 2));
		points.add(new Minutia(54,50, 4));
		Ngon tempNgon = ngons.makeNgon(points);
		ArrayList<Ngon> ngonListTest = ngons.recursiveNgonBuilder(points, new ArrayList<Minutia>());
		ngonList.add(tempNgon);
		System.out.println("actual ngon:");
		for(Variable var : tempNgon.variables.values()){
			System.out.println(var.getPrequantizedValue().toString());
		}
		System.out.println("\nTestNgons:");
		for(int i = 0; i< ngonListTest.size(); i++){
			for(Variable var : ngonListTest.get(i).variables.values()){
				System.out.print(var.getPrequantizedValue().toString()+", ");
			} 
			System.out.println();
		}
		System.out.println(tempNgon.variables.values().toString());
		System.out.println(ngonList.get(0).variables.values());
		assertTrue("",ngonListTest.get(0).variables.values().contains(tempNgon.variables.values()));
		
	}
}
