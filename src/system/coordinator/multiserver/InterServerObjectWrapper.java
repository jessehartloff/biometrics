package system.coordinator.multiserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class InterServerObjectWrapper implements Serializable{


	protected String origin;
	private Object contents;
	protected boolean testing;
	protected boolean enrolling;
	protected Long userID;
	
	public InterServerObjectWrapper(){
		
	}
	
	protected void writeObject(ObjectOutputStream out) throws IOException {	
		out.writeBoolean(testing);
		out.writeBoolean(enrolling);
		out.writeLong(userID);
		out.writeObject(origin);
		out.writeObject(contents);
	}
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		testing = in.readBoolean();
		enrolling = in.readBoolean();
		userID = in.readLong();
		origin = (String) in.readObject();
		System.out.println("My ID "+userID);
		contents = in.readObject();
	}
	
	
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
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
