package system.method.fingerprintmethods;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;

import settings.fingerprintmethodsettings.PRINTSettings;
import settings.settingsvariables.SettingsMethodVariable;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;
import system.method.feature.DistanceVariable;
import system.method.feature.Feature;
import system.method.feature.PhiVariable;
import system.method.feature.SigmaVariable;
import system.method.fingerprintmethods.Ngons.Ngon;
import system.quantizer.Quantizer;

public class PRINTS extends FingerprintMethod {

	private PRINTSettings settings;
	protected Long N;

	public class PRINT extends Feature implements Comparable<PRINT> {
		public HashSet<Long> minutiaIndices;

		private Double centerX;
		private Double centerY;
		private Long N;
		private Double angle;

		public class PRINTComparator implements Comparator<PRINT> {
			PRINT reference;

			public PRINTComparator(PRINT referencePoint) {
				this.reference = referencePoint;
			}

			@Override
			public int compare(PRINT arg0, PRINT arg1) {
				Double d0 = this.reference.distanceBetweenCenters(arg0);
				Double d1 = this.reference.distanceBetweenCenters(arg1);
				return d0.compareTo(d1);
			}
		}

		public PRINTComparator getComparator() {
			return new PRINTComparator(this);
		}

		public PRINT() {
			this.N = settings.n().getValue();
			this.minutiaIndices = new HashSet<Long>();
			this.setCenterX(0.0);
			this.setCenterY(0.0);
			for (Long i = 0L; i < N; i++) {
				variables.put(makeKey("distance", i), new DistanceVariable(settings.getMinutiaComponentVariable("distance", i)));
				variables.put(makeKey("sigma", i), new SigmaVariable(settings.getMinutiaComponentVariable("sigma", i)));
				variables.put(makeKey("phi", i), new PhiVariable(settings.getMinutiaComponentVariable("phi", i)));
			}
		}

		@Override
		public BigInteger toBigInt() {
			BigInteger featureBigInt = super.toBigInt();
			return PRINTS.appendRegionNumber(featureBigInt,
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

		public Double getCenterX() {
			return centerX;
		}

		public void setCenterX(Double centerX) {
			this.centerX = centerX;
		}

		public Double getCenterY() {
			return centerY;
		}

		public void setCenterY(Double centerY) {
			this.centerY = centerY;
		}

		public Double getAngle() {
			return this.angle;
		}

		public void setAngle(Double angle) {
			this.angle = angle;
		}

		@Override
		public int compareTo(PRINT that) {
			int compareX = this.centerX.compareTo(that.centerX);
			return compareX == 0 ? this.centerY.compareTo(that.centerY)
					: compareX;
		}

		public Double distanceBetweenCenters(PRINT that) {
			Double distance;
			Double dx = this.getCenterX() - that.getCenterX();
			Double dy = this.getCenterY() - that.getCenterY();
			distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			return distance;
		}

	}

	public PRINTS() {
		settings = PRINTSettings.getInstance();
		this.settings.setAllNumberOfBins(); // initializes the method variable
											// settings (bins and bits)
		this.N = this.settings.n().getValue();

	}

	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return this.PRINTSQuantizeOne(fingerprint);
	}

	// used for enrollment
	private Template PRINTSQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<PRINT> prints = this.fingerprintToPRINTS(fingerprint);
		for (PRINT print : prints) {
			template.getHashes().add(print.toBigInt());
		}
		return template;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return this.PRINTSQuantizeAll(fingerprint);
	}

	// used for matching
	private ArrayList<Template> PRINTSQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>();
		ArrayList<PRINT> prints = this.fingerprintToPRINTS(fingerprint);
		Template template = new Template();
		Template templatePlusOne = new Template();
		Template templateMinusOne = new Template();
		for (PRINT print : prints) {

			BigInteger quantizedPRINT = print.toBigInt();
			Long regionNumber = PRINTS.getAppendedRegion(quantizedPRINT);
			BigInteger PRINTNoRegion = PRINTS.getPrintWithoutRegionNumber(quantizedPRINT);

			BigInteger PRINTwithRegionPlusOne = PRINTS.appendRegionNumber(PRINTNoRegion, (regionNumber + 1)
																		% settings.rotationRegions().getValue());
			BigInteger PRINTwithRegionMinusOne = PRINTS.appendRegionNumber(PRINTNoRegion, (regionNumber - 1)
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

			template.getHashes().add(quantizedPRINT);
			template.getHashes().add(PRINTwithRegionPlusOne);
			template.getHashes().add(PRINTwithRegionMinusOne);
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

	public static BigInteger appendRegionNumber(BigInteger print,
			Long regionNumber) {
		BigInteger toReturn = print.shiftLeft(SettingsMethodVariable.binsToBits(PRINTSettings.getInstance().rotationRegions().getValue()).intValue());
		return toReturn.add(BigInteger.valueOf(regionNumber));
	}

	public static Long getAppendedRegion(BigInteger appendedPrint) {
		Long numberOfBits = SettingsMethodVariable.binsToBits(PRINTSettings.getInstance().rotationRegions().getValue());
		return appendedPrint.and(BigInteger.valueOf(2L).pow(numberOfBits.intValue()).subtract(BigInteger.ONE)).longValue();
	}

	public static BigInteger getPrintWithoutRegionNumber(BigInteger appendedPrint) {
		return appendedPrint.shiftRight(SettingsMethodVariable.binsToBits(PRINTSettings.getInstance().rotationRegions().getValue()).intValue());
	}

	// public BigInteger getRegionBigInteger(BigInteger integer){
	// though I love this code... it's gotta go.
	// String regionNumberDigits =
	// this.settings.rotationRegions().getValue().toString();
	// BigInteger regionNumber = integer.remainder(new
	// BigDecimal(Math.pow(10,regionNumberDigits.length()-1)).toBigInteger());
	// return regionNumber;
	// }

	private ArrayList<PRINT> fingerprintToPRINTS(Fingerprint fingerprint) {
		ArrayList<PRINT> prints = new ArrayList<PRINT>();
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

			prints.addAll(this.makeAllPossiblePRINTs(minutiae));
		}
		return prints;
	}

	private ArrayList<PRINT> makeAllPossiblePRINTs(ArrayList<Minutia> minutiae) {
		return this.recursivePRINTBuilder(minutiae, new ArrayList<Minutia>());
	}

	public ArrayList<PRINT> recursivePRINTBuilder(ArrayList<Minutia> minutiae, ArrayList<Minutia> currentPRINT) {
		if (currentPRINT.size() == settings.n().getValue()) {
			ArrayList<PRINT> baseCaseList = new ArrayList<PRINT>();
			baseCaseList.add(this.makePRINT(currentPRINT));
			return baseCaseList;
		} else {
			ArrayList<PRINT> intermediaryPRINTs = new ArrayList<PRINT>();
			for (Minutia minutia : minutiae) {
				if (currentPRINT.contains(minutia)) {
					return intermediaryPRINTs;
				} else {
					currentPRINT.add(minutia);
					intermediaryPRINTs.addAll(recursivePRINTBuilder(minutiae,currentPRINT));
					currentPRINT.remove(currentPRINT.size() - 1);
				}
			}
			return intermediaryPRINTs;
		}
	}

	private PRINT makePRINT(ArrayList<Minutia> minutiaList) {
		PRINT returnPRINT = new PRINT();

		for (Minutia minutia : minutiaList) {
			returnPRINT.setCenterX(returnPRINT.getCenterX()
					+ minutia.getX().doubleValue());
			returnPRINT.setCenterY(returnPRINT.getCenterY()
					+ minutia.getY().doubleValue());
		}

		returnPRINT.setCenterX(returnPRINT.getCenterX()
				/ settings.n().getValue().doubleValue());
		returnPRINT.setCenterY(returnPRINT.getCenterY()
				/ settings.n().getValue().doubleValue());

		ArrayList<Double> distances = new ArrayList<Double>();
		// ArrayList<Minutia> minutiaToSortDistance = new ArrayList<Minutia>();
		LinkedHashMap<Double, Minutia> indexMinutiaByDistance = new LinkedHashMap<Double, Minutia>();

		Double cx = returnPRINT.getCenterX();
		Double cy = returnPRINT.getCenterY();

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
		returnPRINT.setAngle(angle);

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
			returnPRINT.variables.get(makeKey("distance", i)).setPrequantizedValue(distances.get(i.intValue()).doubleValue());
			returnPRINT.variables.get(makeKey("sigma", i)).setPrequantizedValue(sigmas.get(i.intValue()).doubleValue());
			returnPRINT.variables.get(makeKey("phi", i)).setPrequantizedValue(phis.get(i.intValue()).doubleValue());
		}
		return returnPRINT;
		// return (PRINT) Quantizer.getQuantizer().getRandomFeature();
	}

	public static boolean isLeft(Minutia center, Minutia linePoint,Minutia pointToCheck) {
		return PRINTS.isLeft(center.getX().doubleValue(), center.getY().doubleValue(), linePoint, pointToCheck);
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
		return new ArrayList<Feature>(this.fingerprintToPRINTS(fingerprint));
	}

	public static String makeKey(String component, Long i) {
		return component + i.toString();
	}

	@Override
	public Feature getBlankFeatureForTraining() {
		return new PRINT();
	}

}
