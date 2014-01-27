package system.makefeaturevector.fingerprintmethods;

import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.settings.GlobalSettings;

public class FingerprintMethodFactory{
	
	public static void makeFingerprintMethod(){
		switch(FingerPrintEnumerator.valueOf(GlobalSettings.getInstance().getFingerprintMethod())){
			case MINUTIAEMETHOD:
				Fingerprint.setFingerprintMethod(new MinutiaeMethod());
				break;
			case PATHSMETHOD:
				Fingerprint.setFingerprintMethod(new PathsMethod());
				break;
			case TRIANGLES:
				Fingerprint.setFingerprintMethod(new Triangles());
				break;
			case TRIPLESOFTRIANGLES:
				Fingerprint.setFingerprintMethod(new TriplesOfTriangles());
				break;
			case TRIPLESOFTRIANGLESALLROTATIONS:
				Fingerprint.setFingerprintMethod(new TriplesOfTrianglesAllRotations());
				break;
			case NGONS:
				Fingerprint.setFingerprintMethod(new Ngons());
				break;
			case NGONSALLROTATIONS:
				Fingerprint.setFingerprintMethod(new NgonsAllRotations());
				break;
			default:
				System.out.println("Hey, you didn't choose a fingerprint method");
				Fingerprint.setFingerprintMethod(new Triangles());
				break;
		}
	}
	
	public enum FingerPrintEnumerator{
		MINUTIAEMETHOD,PATHSMETHOD,TRIANGLES,TRIPLESOFTRIANGLES,
		TRIPLESOFTRIANGLESALLROTATIONS,NGONS,NGONSALLROTATIONS; 
	}
	
}