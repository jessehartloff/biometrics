package system.method.fingerprintmethods;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.method.feature.Feature;

import java.util.ArrayList;

/**
 * Created by jessehartloff on 8/26/14.
 */
public class CompositeFingerprintMethod extends FingerprintMethod{

    //TODO might have to give each method a quantizer. not sure how else to do binning without changing everything

    @Override
    public Template quantizeOne(Fingerprint fingerprint) {
        return null;
    }

    @Override
    public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
        return null;
    }

    @Override
    public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint) {
        return null;
    }

    @Override
    public Feature getBlankFeatureForTraining() {
        return null;
    }

}
