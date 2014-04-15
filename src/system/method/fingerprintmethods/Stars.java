package system.method.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;

import system.allcommonclasses.modalities.Minutia;
import settings.fingerprintmethodsettings.StarSettings;
import settings.settingsvariables.SettingsMethodVariable;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.method.feature.DistanceVariable;
import system.method.feature.Feature;
import system.method.feature.PhiVariable;
import system.method.feature.SigmaVariable;
import system.method.fingerprintmethods.Ngons.Ngon;
import system.method.fingerprintmethods.PRINTS.PRINT;
import system.method.fingerprintmethods.PRINTS.PRINT.PRINTComparator;
import system.quantizer.Quantizer;

public class Stars extends FingerprintMethod {
	private StarSettings settings;
	/**
	 * Member variables
	 */
	protected Long N;

	public class Star extends Feature implements Comparable<Star> {
		
		private Double angle;
		protected Minutia centroid;
		private Long N;
		
		public class StarComparator implements Comparator<Star>{
			Star reference;
			
			public  StarComparator(Star referencePoint) {
				this.reference = referencePoint;
			}
	
			@Override
			public int compare(Star arg0, Star arg1) {
				Double d0 = this.reference.distanceBetweenCenters(arg0);
				Double d1 = this.reference.distanceBetweenCenters(arg1);
				return d0.compareTo(d1);
			}
		}

		public StarComparator getComparator() {
			return new StarComparator(this);
		}

		
		public Star() {
//			settings = StarSettings.getInstance();
//			settings.setAllNumberOfBins(); // initializes the method variable
													// settings (bins and bits)
			N = settings.n().getValue();
//			this.minutiaIndices = new HashSet<Long>();
			this.centroid = new Minutia(0L, 0L, 0L);
			for (Long i = 0L; i < N; i++) {
				variables.put(makeKey("distance", i), new DistanceVariable(settings.getMinutiaComponentVariable("distance", i)));
				variables.put(makeKey("sigma", i), new SigmaVariable(settings.getMinutiaComponentVariable("sigma", i)));
				variables.put(makeKey("phi", i), new PhiVariable(settings.getMinutiaComponentVariable("phi", i)));
			}
		}

		@Override
		public BigInteger toBigInt() {
			BigInteger featureBigInt = super.toBigInt();
			return Stars.appendRegionNumber(featureBigInt,
			this.getRegionNumber());
		}

		public Long getRegionNumber() {
			Double d = Math.floor((this.angle / 360.0)
					* settings.rotationRegions().getValue().doubleValue());
			return d.longValue();
		}

		public Long getCenterX() {
			return centroid.getX();
		}

		public void setCenterX(Long centerX) {
			centroid.setX(centerX);
		}

		public Long getCenterY() {
			return centroid.getY();		
		}

		public void setCenterY(Long centerY) {
			centroid.setY(centerY);
		}

		public Double getCenterAngle() {
			return centroid.getTheta().doubleValue();
		}

		public void setCenterAngle(Double angle) {
			centroid.setTheta(angle.longValue());
		}
		public Double getAngle() {
			return angle;
		}

		public void setAngle(Double angle) {
			this.angle = angle;
		}

		@Override
		public int compareTo(Star that) {
			int compareX = this.getCenterX().compareTo(that.getCenterX());
			return compareX == 0 ? this.getCenterY().compareTo(that.getCenterY())
					: compareX;
		}

		public Double distanceBetweenCenters(Star that) {
			Double distance;
			Long dx = this.centroid.getX() - that.centroid.getX();
			Long dy = this.centroid.getY() - that.centroid.getY();
			distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			return distance;
		}

	}
	
	public Stars() {
		settings = StarSettings.getInstance();
		this.settings.setAllNumberOfBins();
		this.N = this.settings.n().getValue();
	}

	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return this.StarsQuantizeOne(fingerprint);
	}

	// used for enrollment
	private Template StarsQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Star> stars = this.fingerprintToStars(fingerprint);
		for (Star star : stars) {
			BigInteger quantizedStar = star.toBigInt();
			Long regionNumber = Stars.getAppendedRegion(quantizedStar);
			ArrayList<BigInteger> regionStars = new ArrayList<BigInteger>();
			BigInteger starNoRegion = Stars.getPrintWithoutRegionNumber(quantizedStar);
			BigInteger starWithRegion = Stars.appendRegionNumber(starNoRegion, regionNumber );

			regionStars.add(starWithRegion);
//			System.out.println(starWithRegion);


			for(int region = 1; region <= settings.getInstance().neighboringRegions().getValue(); region++) {
				BigInteger starwithRegionPlus = Stars.appendRegionNumber(starNoRegion, (regionNumber + region)
																			% settings.rotationRegions().getValue());
				BigInteger starwithRegionMinus = Stars.appendRegionNumber(starNoRegion, (regionNumber - region)
						                                                    % settings.rotationRegions().getValue());
				regionStars.add(starwithRegionPlus);
				regionStars.add(starwithRegionMinus);

//				System.out.println(starwithRegionPlus);
//				System.out.println(starwithRegionMinus);

			}
			template.getHashes().addAll(regionStars);
		}
		return template;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return this.StarsQuantizeAll(fingerprint);
	}

	// used for matching
	private ArrayList<Template> StarsQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>();
		ArrayList<Star> stars = this.fingerprintToStars(fingerprint);
		Template template = new Template();
		Template templatePlusOne = new Template();
		Template templateMinusOne = new Template();
		for (Star star : stars) {

			BigInteger quantizedStar = star.toBigInt();
			Long regionNumber = Stars.getAppendedRegion(quantizedStar);
			ArrayList<BigInteger> regionStars = new ArrayList<BigInteger>();
			BigInteger starNoRegion = Stars.getPrintWithoutRegionNumber(quantizedStar);
			BigInteger starWithRegion = Stars.appendRegionNumber(starNoRegion, regionNumber );

			regionStars.add(starWithRegion);
//			System.out.println(starWithRegion);


			for(int region = 1; region <= settings.getInstance().neighboringRegions().getValue(); region++) {
				BigInteger starwithRegionPlus = Stars.appendRegionNumber(starNoRegion, (regionNumber + region)
																			% settings.rotationRegions().getValue());
				BigInteger starwithRegionMinus = Stars.appendRegionNumber(starNoRegion, (regionNumber - region)
						                                                    % settings.rotationRegions().getValue());
				regionStars.add(starwithRegionPlus);
				regionStars.add(starwithRegionMinus);

//				System.out.println(starwithRegionPlus);
//				System.out.println(starwithRegionMinus);

			}
			
			// BigInteger nextRegion = regionNumber.add(BigInteger.valueOf(1))
			// .mod(BigInteger.valueOf(settings.rotationRegions().getValue()));
			// BigInteger previousRegion =
			// regionNumber.subtract(BigInteger.valueOf(1))
			// .mod(BigInteger.valueOf(settings.rotationRegions().getValue()));

			// BigInteger PRINTwithRegionPlusOne =
			// PRINTNoRegion.add(nextRegion);
			// BigInteger PRINTwithRegionMinusOne =
			// PRINTNoRegion.add(previousRegion);
			// System.out.println("Adjacent regions: "+quantizedPRINT+", "+PRINTwithRegionPlusOne+", "+PRINTwithRegionMinusOne);

			// template.hashes.add(quantizedPRINT);
			// templatePlusOne.hashes.add(PRINTwithRegionPlusOne);
			// templateMinusOne.hashes.add(PRINTwithRegionMinusOne);

			template.getHashes().addAll(regionStars);
			// template.hashes.add(PRINTwithRegionPlusOne);
			// template.hashes.add(PRINTwithRegionMinusOne);
			// 0.14138669183781966
			// 3 k closest : 0.19514218446549275
			// System.out.println("Adjacent regions: "+quantizedPRINT+", "+PRINTwithRegionPlusOne+", "+PRINTwithRegionMinusOne);
		}

		templates.add(template);
		// templates.add(templatePlusOne);
		// templates.add(templateMinusOne);

		return templates;
	}

	public static BigInteger appendRegionNumber(BigInteger star,
			Long regionNumber) {
		BigInteger toReturn = star.shiftLeft(SettingsMethodVariable.binsToBits(StarSettings.getInstance().rotationRegions().getValue()).intValue());
		return toReturn.add(BigInteger.valueOf(regionNumber));
	}

	public static Long getAppendedRegion(BigInteger appendedStar) {
		Long numberOfBits = SettingsMethodVariable.binsToBits(StarSettings.getInstance().rotationRegions().getValue());
		return appendedStar.and(BigInteger.valueOf(2L).pow(numberOfBits.intValue()).subtract(BigInteger.ONE)).longValue();
	}

	public static BigInteger getPrintWithoutRegionNumber(BigInteger appendedStar) {
		return appendedStar.shiftRight(SettingsMethodVariable.binsToBits(StarSettings.getInstance().rotationRegions().getValue()).intValue());
	}

	// public BigInteger getRegionBigInteger(BigInteger integer){
	// though I love this code... it's gotta go.
	// String regionNumberDigits =
	// this.settings.rotationRegions().getValue().toString();
	// BigInteger regionNumber = integer.remainder(new
	// BigDecimal(Math.pow(10,regionNumberDigits.length()-1)).toBigInteger());
	// return regionNumber;
	// }

	private ArrayList<Star> fingerprintToStars(Fingerprint fingerprint) {
		ArrayList<Star> prints = new ArrayList<Star>();
		ArrayList<Minutia> minutiaeCopy = new ArrayList<Minutia>();

		for (Minutia minutia : fingerprint.minutiae) {
			minutiaeCopy.add(minutia);
		}

		int count=0;
		for (Minutia minutia : fingerprint.minutiae) {
			Collections.sort(minutiaeCopy, minutia.getComparator());
			int startingIndex;
			for (startingIndex = 0; minutiaeCopy.get(startingIndex).distanceTo(minutia) < 0.0001; startingIndex++);
			ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
			minutiae.add(minutia); // <--- don't need this..... ???
			for (int i = 0; i < settings.kClosestMinutia().getValue(); i++) {
				minutiae.add(minutiaeCopy.get(startingIndex + i));
			}
//			System.out.println("minutia "+count);
			count++;
			prints.addAll(this.makeAllPossibleStars(minutiae));
		}
		return prints;
	}

	private ArrayList<Star> makeAllPossibleStars(ArrayList<Minutia> minutiae) {
		return this.recursiveStarBuilder(minutiae, new ArrayList<Minutia>());
	}

	public ArrayList<Star> recursiveStarBuilder(ArrayList<Minutia> minutiae, ArrayList<Minutia> currentStar) {
//		System.out.println(currentStar.size());
		if (currentStar.size() == settings.n().getValue()) {
			ArrayList<Star> baseCaseList = new ArrayList<Star>();
			baseCaseList.add(this.makeStar(currentStar));
			return baseCaseList;
		} else {
			ArrayList<Star> intermediaryStars = new ArrayList<Star>();
			for (Minutia minutia : minutiae) {
				if (currentStar.contains(minutia)) {
					return intermediaryStars;
				} else {
					currentStar.add(minutia);
					intermediaryStars.addAll(recursiveStarBuilder(minutiae,currentStar));
					currentStar.remove(currentStar.size() - 1);
				}
			}
			return intermediaryStars;
		}
	}

	private Star makeStar(ArrayList<Minutia> minutiaList) {
		Star returnStar = new Star();
		//calculate centroid minutia
		for (Minutia minutia : minutiaList) {
			returnStar.setCenterX(returnStar.getCenterX()
					+ minutia.getX());
			returnStar.setCenterY(returnStar.getCenterY()
					+ minutia.getY());
			returnStar.setCenterAngle(returnStar.getCenterAngle()+minutia.getTheta().doubleValue());
		}
		returnStar.setCenterX(returnStar.getCenterX()
				/ settings.n().getValue());
		returnStar.setCenterY(returnStar.getCenterY()
				/ settings.n().getValue());
		returnStar.setCenterAngle(returnStar.getCenterAngle()
				/ settings.n().getValue().doubleValue());

		//we'll order by distance from center
		ArrayList<Double> distances = new ArrayList<Double>();

		LinkedHashMap<Double, Minutia> indexMinutiaByDistance = new LinkedHashMap<Double, Minutia>();

		Long cx = returnStar.getCenterX();
		Long cy = returnStar.getCenterY();

		for (int i = 0; i < minutiaList.size(); i++) {
			Minutia minutia = minutiaList.get(i);
			Double distFromCenter = Minutia.distance(cx.doubleValue(), cy.doubleValue(), minutia.getX().doubleValue(), minutia.getY().doubleValue());
			distances.add(distFromCenter);
			indexMinutiaByDistance.put(distFromCenter, minutia);
		}

		Collections.sort(distances);
		
		//mark Star angle
		Minutia m0 = indexMinutiaByDistance.get(distances.get(0));
		Double angle = Math.toDegrees(Math.atan2(m0.getY().doubleValue() - cy,m0.getX().doubleValue() - cx));
		returnStar.setAngle(angle);

		//now do actual value calculations
		for (int i = 0; i < N; i++) {
//			//calc d = distance from center (easy since we index by them)
			Double d = distances.get(i);
//
//			//calc sigma = inner angle between x,y and centroid
			Minutia m = indexMinutiaByDistance.get(distances.get(i));
			Double sigma = Math.toDegrees(Math.atan2(m.getY().doubleValue() - cy, m.getX().doubleValue() - cx));
//			//calc sigma = inner angle between x,y and centroid
			Double phi = returnStar.getCenterAngle() - m.getTheta();
			
			//for the moment let d=x, sigma=y, phi stays as is
//			Double d = cx -m.getX().doubleValue();
//			Double sigma = cy - m.getY().doubleValue();
			

			//add them to the star
			returnStar.variables.get(makeKey("distance",new Long(i))).setPrequantizedValue(d);
			returnStar.variables.get(makeKey("sigma",new Long(i))).setPrequantizedValue(sigma);
			returnStar.variables.get(makeKey("phi",new Long(i))).setPrequantizedValue(phi);
		}

		return returnStar;

	}

	public static boolean isLeft(Minutia center, Minutia linePoint,Minutia pointToCheck) {
		return Stars.isLeft(center.getX().doubleValue(), center.getY().doubleValue(), linePoint, pointToCheck);
		// returns true if pointToCheck is left of the line made by linePointA
		// and linePointB
		// note: returns true if pointTCheck is above the horizontal line made
		// by A and B
	}

	public static boolean isLeft(Double centerX, Double centerY,Minutia linePoint, Minutia pointToCheck) {
		return ((linePoint.getX() - centerX) * (pointToCheck.getY() - centerY) - (linePoint.getY() - centerY) * (pointToCheck.getX() - centerX)) > 0;
		// returns true if pointToCheck is left of the line made by linePointA
		// and linePointB
		// note: returns true if pointTCheck is above the horizontal line made
		// by A and B
	}

	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint) {
		return new ArrayList<Feature>(this.fingerprintToStars(fingerprint));
	}

	public static String makeKey(String component, Long i) {
		return component + i.toString();
	}

	@Override
	public Feature getBlankFeatureForTraining() {
		return new Star();
	}
}
