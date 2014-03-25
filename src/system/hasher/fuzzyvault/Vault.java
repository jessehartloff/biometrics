package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;

import settings.hashersettings.FuzzyVaultSettings;
import system.allcommonclasses.commonstructures.Template;
import system.quantizer.Quantizer;

public class Vault {

	private ArrayList<FuzzyVaultPoint> vaultPoints;
	private Long termsInPoly;
	private Long totalBits;

	public Vault() {
		this.vaultPoints = new ArrayList<FuzzyVaultPoint>();
		System.out.println();
		
		this.termsInPoly = FuzzyVaultSettings.getInstance().numberOfTermsInPolynomial().getValue();
		this.totalBits = Quantizer.getQuantizer().getTotalBits();
	}

	public Vault(Template lockedVault) {
		this.vaultPoints = Vault.getVaultPoints(lockedVault);
		this.termsInPoly = FuzzyVaultSettings.getInstance()
				.numberOfTermsInPolynomial().getValue();
		this.totalBits = Quantizer.getQuantizer().getTotalBits();
	}

	public ArrayList<FuzzyVaultPoint> getVaultPoints() {
		return vaultPoints;
	}

	/**
	 * 
	 * @param enrollingTemplate
	 *            set of BigIntegers representing z-values only
	 */
	public void lock(Template enrollingTemplate) {
		this.vaultPoints = new ArrayList<FuzzyVaultPoint>(); // protect against
																// making a
																// vault more
																// than once
		SecretPolynomial secretPoly = new SecretPolynomial(termsInPoly, totalBits);

		// CRC on the end of polynomial
		// add the genuine points to vaultPoints
		for (BigInteger bigInt : enrollingTemplate.getHashes()) {
			FuzzyVaultPoint genuinePoint = new FuzzyVaultPoint();
			genuinePoint.setZ(bigInt);
			genuinePoint.setGamma(secretPoly.evaluateAt(bigInt));
			genuinePoint.setChaff(false);
			this.vaultPoints.add(genuinePoint);
		}

		// add chaff points

		for (int i = 0; i < FuzzyVaultSettings.getInstance().numberOfChaffPoints().getValue().intValue(); i++) {

			FuzzyVaultPoint chaffPoint = new FuzzyVaultPoint();

			Double randomZ = Math.random() * totalBits.doubleValue();
			chaffPoint.setZ(BigInteger.valueOf(randomZ.longValue()));

			Double randomGamma = Math.random() * totalBits.doubleValue();
			chaffPoint.setGamma(BigInteger.valueOf(randomGamma.longValue()));

			chaffPoint.setChaff(true);
			this.vaultPoints.add(chaffPoint);
		}

	}

	/**
	 * 
	 * @param testTemplate
	 *            set of BigIntegers representing z-values only
	 * @return did it unlock? determined by the CRC check
	 */
	public boolean unlock(Template testTemplate) {
		ArrayList<FuzzyVaultPoint> hashesInFuzzyVault = new ArrayList<FuzzyVaultPoint>();

		for (FuzzyVaultPoint point : vaultPoints) {
			for (BigInteger hash : testTemplate.getHashes()) {
				if (point.getZ().equals(hash)) {
					hashesInFuzzyVault.add(point);
				}
			}
		}

		// loop through vault and extract points with matching z-value

		RSDecoder decoder = new BerlekampWelchWrapper();
		SecretPolynomial secret = decoder.decode(hashesInFuzzyVault, this.termsInPoly.intValue(), BigInteger.valueOf(new Double(Math.pow(2, this.totalBits)).intValue()));
		// BWDecoder decoder = new
		// BWDecoder(...);//http://nssl.eew.technion.ac.il/files/Projects/thresholddsaimporvement/doc/javadoc/BWDecoder.html
		// for details

		// BW
		// CRC

		if(secret == null){
			System.out.println("not enough points to try");
			return false;
		}
		
		return true;
	}

	/**
	 * assumes input is a locked vault which was locked according to the current
	 * settings
	 * 
<<<<<<< Vault.java
	 * @param lockedVault a template representing a locked vault
=======
	 * @param lockedVault
	 *            a locked vault
>>>>>>> 1.8
	 * @return
	 */
	public static ArrayList<FuzzyVaultPoint> getVaultPoints(Template lockedVault) {
		ArrayList<FuzzyVaultPoint> vaultPoints = new ArrayList<FuzzyVaultPoint>();
		for (BigInteger bigInt : lockedVault.getHashes()) {
			vaultPoints.add(new FuzzyVaultPoint(bigInt));
		}
		return vaultPoints;
	}

	public Template toTemplate() {
		Template toReturn = new Template();
		for (FuzzyVaultPoint point : this.vaultPoints) {
			toReturn.getHashes().add(point.toBigInt());
		}
		return toReturn;
	}

}
