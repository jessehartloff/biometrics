package settings.irismethodsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.fingerprintmethodsettings.NgonSettings;

public class IrisSegmentationSettings extends IrisMethodSettings {

	@Override
	protected void addSettings() {

	}

	private static final long serialVersionUID = 1L;

	private static IrisSegmentationSettings instance;

	protected IrisSegmentationSettings() {
	}

	public static IrisSegmentationSettings getInstance() {
		if (instance == null) {
			instance = new IrisSegmentationSettings();
		}
		return instance;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(instance.settingsVariables);
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in
				.readObject();
	}

	@Override
	public String getMethodString() {
		return "IRISSEGMENTATION";
	}

	@Override
	public String getLabel() {
		return "Iris Segmentation";
	}

}
