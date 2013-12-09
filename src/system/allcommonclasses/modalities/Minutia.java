package system.allcommonclasses.modalities;

import java.util.Comparator;


public class Minutia implements Comparable<Minutia>{

	public Long x;
	public Long y;
	public Long theta;
	public Long confidence; 
	public Long index;
	
	
	public class MinutiaComparator implements Comparator<Minutia>{

		Minutia referencePoint;
		
		public MinutiaComparator(Minutia referencePoint){
			this.referencePoint = referencePoint;			
		}
		
		@Override
		public int compare(Minutia m0, Minutia m1) {
			Double d0 = referencePoint.distanceTo(m0);
			Double d1 = referencePoint.distanceTo(m1);
			{}// TODO test minutia sorting
			return d0.compareTo(d1);
		}
		
	}
	
	public MinutiaComparator getComparator(){
		return new MinutiaComparator(this);
	}

	
	public Minutia(){
		
	}
	
	public Minutia(long x, long y, long theta){
		this.x = x;
		this.y = y;
		this.theta = theta;
	}
		
	
	public Minutia(Long x, Long y, Long theta, Long confidence){
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.confidence = confidence;
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
}