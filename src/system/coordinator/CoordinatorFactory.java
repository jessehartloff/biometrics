package system.coordinator;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class CoordinatorFactory {
	
	{} // TODO Jim - factories are cool!
	// start with a base coordinator. might be cleaner to have base coordinator be a subclass in here
	// maybe BaseCoordinator should be private
	// Separate enumerator for each type of coordinator? add NONE to all coordinator enumerators? 
	
	
	public class BaseCoordinator extends Coordinator{

		public BaseCoordinator(Hasher hasher, Users users) {
			super(hasher, users);
		}

		@Override
		public RawScores run() {
			return new RawScores();
		}

	}
}
