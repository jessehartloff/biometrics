package system.allcommonclasses.modalities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Comparator;


public class Minutia implements Comparable<Minutia>, Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long x;
	private Long y;
	private Long theta;
	private Long confidence; 
	private Long index;
	
	
	public class MinutiaComparator implements Comparator<Minutia>{

		Minutia referencePoint;
		
		public MinutiaComparator(Minutia referencePoint){
			this.referencePoint = referencePoint;			
		}
		
		@Override
		public int compare(Minutia m0, Minutia m1) {
			Double d0 = referencePoint.distanceTo(m0);
			Double d1 = referencePoint.distanceTo(m1);
			return d0.compareTo(d1);
		}
		
	}
	
	public MinutiaComparator getComparator(){
		return new MinutiaComparator(this);
	}

	
	public Minutia(){
		
	}
	
	public Minutia(long x, long y, long theta){
		this.setX(x);
		this.setY(y);
		this.setTheta(theta);
	}
		
	
	public Minutia(long x, long y, long theta, long confidence){
		this.setX(x);
		this.setY(y);
		this.setTheta(theta);
		this.setConfidence(confidence);
	}
	
	
	public Double distanceTo(Minutia that){
		Double distance;
		Long dx = this.x - that.x;
		Long dy = this.y - that.y;
		distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return distance;
	}
	
	@Override
	public int compareTo(Minutia that) {
		int compareX = this.x.compareTo(that.x);
		return compareX == 0 ? this.y.compareTo(that.y) : compareX;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null){
	    	return false;
	    }
	    if (other == this){
	    	return true;
	    }
	    if (!(other instanceof Minutia)){
	    	return false;
	    }
	    
	    Minutia otherMinutia = (Minutia)other;
	    
	    return this.x.equals(otherMinutia.x) && 
	    		this.y.equals(otherMinutia.y) && 
	    		this.theta.equals(otherMinutia.theta);
	}
	
	@Override
	public String toString(){
		return "(" + this.x + ", " + this.y + ", " + this.theta + ")";
	}


	//getters and setters
	public Long getX() {
		return x;
	}


	public void setX(Long x) {
		this.x = x;
	}


	public Long getY() {
		return y;
	}


	public void setY(Long y) {
		this.y = y;
	}


	public Long getTheta() {
		return theta;
	}

/**
 * sets theta to the value in [0,359] corresponding to the input.
 * 
 * @param theta value in degrees. can be any long as this setter will change it to a values from 0 to 359
 */
	public void setTheta(Long theta) {
		while(theta >= 360){theta -= 360;}
	    while(theta < 0)  {theta += 360;}
		this.theta = theta;
	}


	public Long getConfidence() {
		return confidence;
	}


	public void setConfidence(Long confidence) {
		this.confidence = confidence;
	}


	public Long getIndex() {
		return index;
	}


	public void setIndex(Long index) {
		this.index = index;
	}
	
	// m1 is the center point
	public static Double computeInsideAngle(Minutia m0, Minutia m1, Minutia m2){
		// variables names follow the law of cosines equation
		Double a = m0.distanceTo(m1);
		Double b = m1.distanceTo(m2);
		Double c = m2.distanceTo(m0);
		Double inRadians = Math.acos( (a*a + b*b - c*c)/(2*a*b) );
		return (inRadians * 180.0)/Math.PI;
	}
	
	public static Double computeInsideAngle(Minutia m0, Double px, Double py, Minutia m2){
		// variables names follow the law of cosines equation
		Double a = distance(m0.getX().doubleValue(), m0.getY().doubleValue(), px, py);
		
		Double b = distance(m0.getX().doubleValue(), m0.getY().doubleValue(),
							m2.getX().doubleValue(), m2.getY().doubleValue());
		
		Double c = distance(px, py, m2.getX().doubleValue(), m2.getY().doubleValue());
		Double inRadians = Math.acos( (a*a + b*b - c*c)/(2*a*b) );
		return (inRadians * 180.0)/Math.PI;
	}
	
	public static Double distance(Double x1, Double y1, Double x2, Double y2){
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}	

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
	    in.defaultReadObject();

//	    System.out.println("Read a Minutia Point");
	}
	
	
}