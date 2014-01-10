package system.allcommonclasses.indexingstructure;

import java.math.BigInteger;

public class IndexingPoint {

	private BigInteger value;
	private Long userID;
	
	
	// getters and setters
	public BigInteger getValue() {
		return value;
	}
	public void setValue(BigInteger value) {
		this.value = value;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	
	
}
