package system.coordinator.multiserver;

import java.io.Serializable;

public class InterServerObjectWrapper implements Serializable{

	private Object contents;
	private boolean testing;
	private boolean enrolling;
	private Long userID;
	
	public InterServerObjectWrapper(){
		
	}
	public Object getContents() {
		return contents;
	}

	public void setContents(Object contents) {
		this.contents = contents;
	}

	public boolean isTesting() {
		return testing;
	}

	public void setTesting(boolean testing) {
		this.testing = testing;
	}

	public boolean isEnrolling() {
		return enrolling;
	}

	public void setEnrolling(boolean enrolling) {
		this.enrolling = enrolling;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	

}
