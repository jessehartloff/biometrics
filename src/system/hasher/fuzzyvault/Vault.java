package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;

import settings.hashersettings.FuzzyVaultSettings;
import system.allcommonclasses.commonstructures.Template;
import system.quantizer.Quantizer;

public class Vault {

	private ArrayList<FuzzyVaultPoint> vaultPoints;

	public Vault(){
		this.vaultPoints = new ArrayList<FuzzyVaultPoint>();
	}
	
	public Vault(Template lockedVault){
		this.vaultPoints = Vault.getVaultPoints(lockedVault);
	}

	public ArrayList<FuzzyVaultPoint> getVaultPoints(){
		return vaultPoints;
	}
	
	/**
	 * 
	 * @param enrollingTemplate set of BigIntegers representing z-values only
	 */
	public void lock(Template enrollingTemplate){

		this.vaultPoints = new ArrayList<FuzzyVaultPoint>(); // protect against making a vault more than once
		
		Long termsInPoly = FuzzyVaultSettings.getInstance().numberOfTermsInPolynomial().getValue();
		Long totalBits = Quantizer.getQuantizer().getTotalBits(); // FIXME this has to change to handle #bins that are not powers of 2
		
		SecretPolynomial secretPoly = new SecretPolynomial(termsInPoly, totalBits);

		
		//CRC on the end of polynomial
		
		// add the genuine points to vaultPoints
		for (BigInteger bigInt : enrollingTemplate.hashes) {
			FuzzyVaultPoint genuinePoint = new FuzzyVaultPoint();
			genuinePoint.setZ(bigInt);
			genuinePoint.setGamma(secretPoly.evaluateAt(bigInt));
			genuinePoint.setChaff(false);
			this.vaultPoints.add(genuinePoint);
		}
		
		// add chaff points
		for(int i=0; i < FuzzyVaultSettings.getInstance().numberOfChaffPoints().getValue().intValue(); i++){
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
	 * @param testTemplate set of BigIntegers representing z-values only
	 * @return did it unlock? determined by the CRC check
	 */
	public boolean unlock(Template testTemplate){
		
		//loop through vault and extract points with matching z-value
		
		// TODO FuzzyVault - unlock
		
		RSDecoder decoder = new RSDecoder();
		
		//BW
		//CRC
		return false;
	}
	

	/**
	 * assumes input is a locked vault which was locked according to the current settings
	 * 
	 * @param lockedVault a locked vault
	 * @return
	 */
	public static ArrayList<FuzzyVaultPoint> getVaultPoints(Template lockedVault){
		ArrayList<FuzzyVaultPoint> vaultPoints = new ArrayList<FuzzyVaultPoint>();
		for(BigInteger bigInt : lockedVault.hashes){
			vaultPoints.add(new FuzzyVaultPoint(bigInt));
		}
		return vaultPoints;
	}
	
	
	public Template toTemplate(){
		Template toReturn = new Template();
		for(FuzzyVaultPoint point : this.vaultPoints){
			toReturn.hashes.add(point.toBigInt());
		}
		return toReturn;
	}
	
}
