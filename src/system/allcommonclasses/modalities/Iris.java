package system.allcommonclasses.modalities;

public class Iris extends Biometric {

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (!(other instanceof Iris)) {
			return false;
		}
		Iris otherIris = (Iris) other;

		// code for equals

		return true;
	}

	@Override
	public String toString() {
		return "Iris.toString()";
	}


	@Override
	public boolean isFailure() {
		// used for failure to capture
		return false;
	}

}
