package system.method.irismethods;

import settings.irismethodsettings.AllIrisMethodSettings;
import system.allcommonclasses.modalities.Iris;

public class IrisMethodFactory {

	public static void makeFingerprintMethod() {
		switch (IrisEnumerator.valueOf(AllIrisMethodSettings.getIrisMethod())) {
		case IRISSEGMENTATION:
			Iris.setIrisMethod(new IrisSegmentation());
			break;
		}
	}

	public enum IrisEnumerator {
		IRISSEGMENTATION;
	}

}