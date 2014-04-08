package system.method.fingerprintmethods;

import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.fingerprintmethodsettings.NgonsSingleEnrollAllRotationsSettings;
import system.allcommonclasses.modalities.Fingerprint;
import system.method.fingerprintmethods.MinutiaeMethod;
import system.method.fingerprintmethods.Ngons;
import system.method.fingerprintmethods.NgonsAllRotations;
import system.method.fingerprintmethods.PathsMethod;
import system.method.fingerprintmethods.Triangles;
import system.method.fingerprintmethods.TriplesOfTriangles;
import system.method.fingerprintmethods.TriplesOfTrianglesAllRotations;

public class FingerprintMethodFactory{
	
	public static void makeFingerprintMethod(){

		System.out.println("Method: "+FingerprintEnumerator.valueOf(AllFingerprintMethodSettings.getFingerprintMethod()));

		switch(FingerprintEnumerator.valueOf(AllFingerprintMethodSettings.getFingerprintMethod())){
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
			case PRINTS:
				Fingerprint.setFingerprintMethod(new PRINTS());
				break;
			case NGONSSINGLEENROLLALLROTATIONS:
				Fingerprint.setFingerprintMethod(new NgonsSingleEnrollAllRotations());
				break;
//			case STARS:
//				Fingerprint.setFingerprintMethod(new Stars());
//				break;
			default:
				System.out.println("Hey, you didn't choose a fingerprint method");
				Fingerprint.setFingerprintMethod(new Triangles());
				break;
		}
	}
	
	public enum FingerprintEnumerator{
		MINUTIAEMETHOD,PATHSMETHOD,TRIANGLES,TRIPLESOFTRIANGLES,
		TRIPLESOFTRIANGLESALLROTATIONS,NGONS,NGONSALLROTATIONS, PRINTS,NGONSSINGLEENROLLALLROTATIONS,STARS; 
	}
	
}