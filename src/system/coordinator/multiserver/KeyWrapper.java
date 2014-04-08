package system.coordinator.multiserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;

public class KeyWrapper extends InterServerObjectWrapper{

	protected PrivateKey key;
	
	protected void writeObject(ObjectOutputStream out) throws IOException {	
		super.writeObject(out);
		out.writeObject(key);
	}
		
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		super.readObject(in);
		key = (PrivateKey) in.readObject();
		System.out.println("My read origin is "+origin);
//		contents = in.readObject();
	}

	public PrivateKey getKey() {
		return key;
	}

	public void setKey(PrivateKey key) {
		this.key = key;
	}
	
	
	
}
