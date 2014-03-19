package system.coordinator.multiserver;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.coordinator.Coordinator;
import system.hasher.Hasher;

public class Client extends Coordinator {
	//extends server 
	
	public Client(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
	}

	@Override
	public RawScores run() {
		return null;
	}
	
	
	public void enroll(Biometric biometric){
		Template quantized = biometric.quantizeOne();
		//1.) generate key pair
		//2.) encrypt Template with public key e(u) [public key] from 1.)
		//2a.) generate UUID for verification
		//3.) send d(u) [private key] to Server_1
		//4.) sent e(Template)  to Server_2
		//wait for server 1's response
	}
	
	public Double test(Biometric biometric){
		Template quantized = biometric.quantizeOne();
		//1.) generate key pair
		//2.) encrypt Template with public key e(u) [public key] from 1.)
		//2a.) generate UUID for verification
		//3.) send d(u) [private key] to Server_1
		//4.) sent e(Template)  to Server_2
		//wait for server 1's response
		return null;
	}

}
