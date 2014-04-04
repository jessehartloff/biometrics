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
	private BigInteger hashOfPolynomial;

	public Vault() {
		this.vaultPoints = new ArrayList<FuzzyVaultPoint>();
		this.termsInPoly = FuzzyVaultSettings.getInstance().numberOfTermsInPolynomial().getValue();
		this.totalBits = Quantizer.getQuantizer().getTotalBits();
	}

	public Vault(Template lockedVault) {
		this.vaultPoints = Vault.getVaultPoints(lockedVault);
		this.termsInPoly = FuzzyVaultSettings.getInstance().numberOfTermsInPolynomial().getValue();
		this.totalBits = Quantizer.getQuantizer().getTotalBits();
		this.hashOfPolynomial = Vault.getPolynomialHash(lockedVault);
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
		this.hashOfPolynomial = secretPoly.computeHash();
		
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

//		for (FuzzyVaultPoint point : vaultPoints) {
//			for (BigInteger hash : testTemplate.getHashes()) {
//				System.out.println("z:"+point.getZ());
//				System.out.println("hash:"+hash);
//
//				if (point.getZ().equals(hash)) {
//					System.out.println("z:"+point.getZ());
//					hashesInFuzzyVault.add(point);
//					break;
//				}
//			}
//		}
		for (FuzzyVaultPoint point : vaultPoints) {
//			System.out.println("z:"+point.getZ());
				if(testTemplate.getHashes().contains(point.getZ())) {
//					System.out.println("z:"+point.getZ());
					hashesInFuzzyVault.add(point);
				}
		}
		// loop through vault and extract points with matching z-value

//		return hashesInFuzzyVault.size() >= this.termsInPoly.intValue() ? true : false;
//		
		RSDecoder decoder = new BerlekampWelchWrapper();
		
		long startMillis = System.currentTimeMillis();
		SecretPolynomial secret = decoder.decode(hashesInFuzzyVault, this.termsInPoly.intValue(), FieldSizeMap.getPrime(this.totalBits));
		long endMillis = System.currentTimeMillis();
		
		System.out.println("Decoded in " + (endMillis - startMillis)/1000.0 + " seconds with " + hashesInFuzzyVault.size() + " points as input");
		
		if(secret == null){
			return false;
		}
		
		String output = secret.computeHash().equals(this.hashOfPolynomial) ? "unlocked!!" : "failure";
		
		System.out.println(output);

		return secret.computeHash().equals(this.hashOfPolynomial) ? true : false;
		
//		return hashesInFuzzyVault.size() >= this.termsInPoly.intValue() ? true : false;
//		// BWDecoder decoder = new
//		// BWDecoder(...);//http://nssl.eew.technion.ac.il/files/Projects/thresholddsaimporvement/doc/javadoc/BWDecoder.html
//		// for details
//
//		// BW
//		
//		if(secret == null){
//			return false;
//		}
//		
//		// CRC
//
////		CRCPolynomial crcPoly = new CRCPolynomial();
////		crcPoly = CRCPolynomial.createIrreducible(this.termsInPoly.intValue());
////		System.out.println("Checking if CRC's return 0");
////		System.out.println(CRCPolyMap.getCRCPoly(this.termsInPoly.intValue()));
////		System.out.println(CRC.CheckCRC(secret.getPolynomialTerms(), CRCPolyMap.getCRCPoly(this.termsInPoly.intValue())));
//		return CRC.CheckCRC(secret.getPolynomialTerms(), CRCPolyMap.getCRCPoly(this.termsInPoly.intValue()));
////		return true;
	}

	/**
	 * assumes input is a locked vault which was locked according to the current
	 * settings
	 * @param lockedVault a template representing a locked vault
	 * @return
	 */
	public static ArrayList<FuzzyVaultPoint> getVaultPoints(Template lockedVault) {
		ArrayList<FuzzyVaultPoint> vaultPoints = new ArrayList<FuzzyVaultPoint>();
		for (BigInteger bigInt : lockedVault.getHashes()) {
			vaultPoints.add(new FuzzyVaultPoint(bigInt));
		}
		return vaultPoints;
	}

	public static BigInteger getPolynomialHash(Template lockedVault) {
		return lockedVault.getExtraHash();
	}
	
	public Template toTemplate() {
		Template toReturn = new Template();
		for (FuzzyVaultPoint point : this.vaultPoints) {
			toReturn.getHashes().add(point.toBigInt());
		}
		toReturn.setExtraHash(this.hashOfPolynomial);
		return toReturn;
	}

	
	public BigInteger getHashOfPolynomial() {
		return hashOfPolynomial;
	}
	
}
