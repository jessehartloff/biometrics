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
	private Minutia centroid;

	public class Star extends Feature implements Comparable<Star> {
		
		private Double angle;
		protected Minutia centroid;
		private Long N;

		Star reference;
		
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
			settings = StarSettings.getInstance();
			settings.setAllNumberOfBins(); // initializes the method variable
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
			// return Quantizer.getQuantizer().getRandomBigInt();
			// FIXME - Matt: Why was this returning a random big int?
			//return featureBigInt;
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

		public Double getAngle() {
			return this.angle;
		}

		public void setAngle(Double angle) {
			this.angle = angle;
		}

		public int compareTo(Stars that) {
			int compareX = this.centroid.getX().compareTo(that.centroid.getX());
			return compareX == 0 ? this.centroid.getY().compareTo(that.centroid.getY())
					: compareX;
		}

		public double distanceBetweenCenters(Star arg1) {
			double distance;
			Long dx = this.centroid.getX() - arg1.centroid.getX();
			Long dy = this.centroid.getY() - arg1.centroid.getY();
			distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			return distance;
		}


		@Override
		public int compareTo(Star o) {
			// TODO Auto-generated method stub
			return 0;
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
		ArrayList<Stars> stars = this.fingerprintToStars(fingerprint);
		for (Stars star : stars) {
			template.getHashes().add(star.toBigInt());
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
		ArrayList<Stars> stars = this.fingerprintToStars(fingerprint);
		Template template = new Template();
		Template templatePlusOne = new Template();
		Template templateMinusOne = new Template();
		for (Stars star : stars) {

			BigInteger quantizedStar = star.toBigInt();
			Long regionNumber = Stars.getAppendedRegion(quantizedStars);
			BigInteger StarNoRegion = Stars.getPrintWithoutRegionNumber(quantizedStars);

			BigInteger StarwithRegionPlusOne = Stars.appendRegionNumber(StarNoRegion, (regionNumber + 1)
																		% settings.rotationRegions().getValue());
			BigInteger StarwithRegionMinusOne = Stars.appendRegionNumber(StarNoRegion, (regionNumber - 1)
																		% settings.rotationRegions().getValue());

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

			template.getHashes().add(quantizedStar);
			template.getHashes().add(StarwithRegionPlusOne);
			template.getHashes().add(StarwithRegionMinusOne);
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

		for (Minutia minutia : fingerprint.minutiae) {
			Collections.sort(minutiaeCopy, minutia.getComparator());
			int startingIndex;
			for (startingIndex = 0; minutiaeCopy.get(startingIndex).distanceTo(
					minutia) < 0.0001; startingIndex++)
				;
			ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
			minutiae.add(minutia); // <--- don't need this..... ???
			for (int i = 0; i < settings.kClosestMinutia().getValue(); i++) {
				minutiae.add(minutiaeCopy.get(startingIndex + i));
			}

			prints.addAll(this.makeAllPossibleStars(minutiae));
		}
		return prints;
	}

	private ArrayList<Star> makeAllPossibleStars(ArrayList<Minutia> minutiae) {
		return this.recursiveStarBuilder(minutiae, new ArrayList<Minutia>());
	}

	public ArrayList<Star> recursiveStarBuilder(ArrayList<Minutia> minutiae, ArrayList<Minutia> currentStar) {
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

		for (Minutia minutia : minutiaList) {
			returnStar.setCenterX(returnStar.getCenterX()
					+ minutia.getX().doubleValue());
			returnStar.setCenterY(returnStar.getCenterY()
					+ minutia.getY().doubleValue());
		}

		returnStar.setCenterX(returnStar.getCenterX()
				/ settings.n().getValue().doubleValue());
		returnStar.setCenterY(returnStar.getCenterY()
				/ settings.n().getValue().doubleValue());

		ArrayList<Double> distances = new ArrayList<Double>();
		// ArrayList<Minutia> minutiaToSortDistance = new ArrayList<Minutia>();
		LinkedHashMap<Double, Minutia> indexMinutiaByDistance = new LinkedHashMap<Double, Minutia>();

		Double cx = returnStar.getCenterX();
		Double cy = returnStar.getCenterY();

		for (int i = 0; i < minutiaList.size(); i++) {
			Minutia minutia = minutiaList.get(i);
			Double distFromCenter = Minutia.distance(cx, cy, minutia.getX().doubleValue(), minutia.getY().doubleValue());
			distances.add(distFromCenter);
			indexMinutiaByDistance.put(distFromCenter, minutia);
		}

		Collections.sort(distances);

		Minutia m0 = indexMinutiaByDistance.get(distances.get(0));
		// System.out.println("M_0: "+m0);
		// System.out.println("Center: "+cx+", "+cy);

		Double angle = Math.toDegrees(Math.atan2(m0.getY().doubleValue() - cy,m0.getX().doubleValue() - cx));
		angle = (angle + 180) % 360; // wtf?

		// System.out.println("center: "+cx+", "+cy);
		// System.out.println("X: "+(m0.getX().doubleValue()-cx));
		// System.out.println("Y: "+(m0.getY().doubleValue()-cy));
		// System.out.println("Minutia: "+m0);
		// System.out.println("Center: "+cx+", "+cy);
		// System.out.println("Angle: "+angle);
		returnStar.setAngle(angle);

		ArrayList<Double> absoluteInteriorAngles = new ArrayList<Double>();
		LinkedHashMap<Double, Minutia> indexMinutiaByInteriorAngle = new LinkedHashMap<Double, Minutia>();

		// ArrayList<Minutia> minutiaToSortAngles = new ArrayList<Minutia>();

		for (Minutia minutia : minutiaList) {
			// System.out.println(m0);
			// System.out.println(cx+", "+cy);
			// System.out.println(minutia+"\n");
			if (!m0.equals(minutia)) {
				Double intAngle = Minutia.computeInsideAngle(m0, cx, cy, minutia);
				if (!isLeft(cx, cy, m0, minutia)) { // this is probably an issue
					intAngle = 360 - intAngle;
				}
				absoluteInteriorAngles.add(intAngle);
				indexMinutiaByInteriorAngle.put(intAngle, minutia);
			} else {
				absoluteInteriorAngles.add(0.0);
				indexMinutiaByInteriorAngle.put(0.0, minutia);
			}
		}

		// for(Double d : absoluteInteriorAngles){
		// System.out.println("thing: " + d);
		// }
		Collections.sort(absoluteInteriorAngles);

		// for(Double d : absoluteInteriorAngles)
		// System.out.println(d);

		ArrayList<Minutia> sortedMinutia = new ArrayList<Minutia>();
		for (Double interiorAngle : absoluteInteriorAngles) {
			Minutia m = indexMinutiaByInteriorAngle.get(interiorAngle);
			sortedMinutia.add(m);
		}

		distances = new ArrayList<Double>();
		ArrayList<Double> sigmas = new ArrayList<Double>();
		ArrayList<Double> phis = new ArrayList<Double>();

		for (int i = 0; i < sortedMinutia.size(); i++) {
			Minutia m1 = sortedMinutia.get(i), m2 = sortedMinutia.get((i + 1)
					% sortedMinutia.size()); // going around counterclockwise...
			Double distance = Minutia.distance(cx, cy, m1.getX().doubleValue(),
					m1.getY().doubleValue());
			Double phi = Minutia.computeInsideAngle(m1, cx, cy, m2);

			if (!isLeft(cx, cy, m1, m2)) { // this is probably an issue
				phi = 360 - phi;
			}

			Double sigma = m2.getTheta().doubleValue()
					- m1.getTheta().doubleValue();

			// ith position in each list corresponds to the ith minutia point
			distances.add(distance);
			phis.add(phi);
			sigmas.add(sigma);
		}

		// for(int i = 0; i < sortedMinutia.size(); i++){
		// Double distance = distances.get(i);
		// Double sigma = sigmas.get(i);
		// Double phi = phis.get(i);
		//
		// System.out.println("Distance: "+distance);
		// System.out.println("Phi: "+phi);
		// System.out.println("Sigma: "+sigma+"\n");
		// }
		// System.exit(0);
		for (Long i = 0L; i < N; i++) {
			// worth thinking about overloading setPrequantizedValue if Double
			// has more bits than Long?
			returnStar.variables.get(makeKey("distance", i)).setPrequantizedValue(distances.get(i.intValue()).doubleValue());
			returnStar.variables.get(makeKey("sigma", i)).setPrequantizedValue(sigmas.get(i.intValue()).doubleValue());
			returnStar.variables.get(makeKey("phi", i)).setPrequantizedValue(phis.get(i.intValue()).doubleValue());
		}
		return returnStar;
		// return (PRINT) Quantizer.getQuantizer().getRandomFeature();
	}

	public static boolean isLeft(Minutia center, Minutia linePoint,Minutia pointToCheck) {
		return Star.isLeft(center.getX().doubleValue(), center.getY().doubleValue(), linePoint, pointToCheck);
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
