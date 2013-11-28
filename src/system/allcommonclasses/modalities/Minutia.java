package system.allcommonclasses.modalities;


public class Minutia implements Comparable<Minutia>{

	public Long x;
	public Long y;
	public Long theta;
	public long confidence; 
	public long index;
	
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
	    
	    return this.x == otherMinutia.x && 
	    		this.y == otherMinutia.y && 
	    		this.theta == otherMinutia.theta;
	}
	
	@Override
	public String toString(){
		return "(" + this.x + ", " + this.y + ", " + this.theta + ")";
	}
}