package system.method;

import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import system.method.fingerprintmethods.*;

/**
 * Created by jessehartloff on 9/2/14.
 */
public class MethodFactory {


    public static Method makeMethod(String method){

        System.out.println("Method: " + MethodEnumerator.valueOf(method));

        switch(MethodEnumerator.valueOf(AllFingerprintMethodSettings.getFingerprintMethod())){
            case MINUTIAEMETHOD:
                return new MinutiaeMethod();
            case PATHSMETHOD:
                return new PathsMethod();
            case TRIANGLES:
                return new Triangles();
            case TRIPLESOFTRIANGLES:
                return new TriplesOfTriangles();
            case TRIPLESOFTRIANGLESALLROTATIONS:
                return new TriplesOfTrianglesAllRotations();
            case NGONS:
                return new Ngons();
            case NGONSALLROTATIONS:
                return new NgonsAllRotations();
            case PRINTS:
                return new PRINTS();
            case NGONSSINGLEENROLLALLROTATIONS:
                return new NgonsSingleEnrollAllRotations();
            case STARS:
                return new Stars();
            default:
                System.out.println("FingerprintMethodSettings says \"Hey, you didn't choose a fingerprint method\"");
                return new Triangles();
        }
    }

    public enum MethodEnumerator{
        MINUTIAEMETHOD,PATHSMETHOD,TRIANGLES,TRIPLESOFTRIANGLES,
        TRIPLESOFTRIANGLESALLROTATIONS,NGONS,NGONSALLROTATIONS,
        PRINTS,NGONSSINGLEENROLLALLROTATIONS,STARS;
    }

}