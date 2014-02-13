package system.method.fingerprintmethods;

import settings.modalitysettings.methodsettings.fingerprintmethodsettings.FingerprintMethodSettings;
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
		switch(FingerPrintEnumerator.valueOf(FingerprintMethodSettings.getFingerprintMethod())){
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
			case NGONS: //TODO make method variable settings
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