package system.coordinator;


import settings.coordinatorsettings.HistogramCoordinatorSettings;
import settings.coordinatorsettings.IndexingCoordinatorSettings;
import settings.coordinatorsettings.MatchingCoordinatorSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.indexingstructure.RAMStructure;
import system.allcommonclasses.indexingstructure.SQLStructure;
import system.coordinator.Coordinator;
import system.coordinator.DefaultTesting;
import system.coordinator.DefaultTestingPrequantized;
import system.coordinator.HistogramCoordinator;
import system.coordinator.IndexTesting;
import system.coordinator.MultipleEnrollment;
import system.coordinator.testgenerators.TestGeneratorFactory;
import system.hasher.Hasher;
import system.hasher.HasherFactory;

public class CoordinatorFactory {
	 	
	public static Coordinator makeCoordinator(Users users){
		
		Hasher hasher = HasherFactory.makeHasher();
		
		Coordinator firstCoordinator = new CoordinatorFactory().new BaseCoordinator(hasher, users);
		
	
		switch(MatchingCoordinatorEnumerator.valueOf(MatchingCoordinatorSettings.getMatchingCoordinator())){
			case DEFAULTTESTING:
				firstCoordinator = addToFront(new DefaultTesting(hasher, users, TestGeneratorFactory.makeTestGenerator()), firstCoordinator);
				break;		
			case DEFAULTTESTINGPREQUANTIZED:
				firstCoordinator = addToFront(new DefaultTestingPrequantized(hasher, users, TestGeneratorFactory.makeTestGenerator()), firstCoordinator);
				break;
			case MULTIPLEENROLLMENT:
				firstCoordinator = addToFront(new MultipleEnrollment(hasher, users), firstCoordinator);
				break;
			case NONE:
				break;				
			default:
				System.out.println("You didn't provide an appropriate matching coordinator");
				break;
		}		
		
		switch(IndexingCoordinatorEnumerator.valueOf(IndexingCoordinatorSettings.getIndexingCoordinator())){
			case RAMINDEXING:
				firstCoordinator = addToFront(new IndexTesting(hasher, users, new RAMStructure()), firstCoordinator);
			break;	
			case SQLINDEXING:
				firstCoordinator = addToFront(new IndexTesting(hasher, users, new SQLStructure()), firstCoordinator);
			break;		
			case NONE:
				break;				
			default:
				System.out.println("You didn't provide an appropriate indexing coordinator");
				break;
		}	
		
		switch(HistogramCoordinatorEnumerator.valueOf(HistogramCoordinatorSettings.getHistogramCoordinator())){
			case HISTOGRAM:
				firstCoordinator = addToFront(new HistogramCoordinator(hasher, users), firstCoordinator);
				break;		
			case NONE:
				break;				
			default:
				System.out.println("You didn't provide an appropriate histogram coordinator");
				break;
		}	
		
		
		return firstCoordinator;
	}
	
	
	
	
	private static Coordinator addToFront(Coordinator coordinatorToAdd, Coordinator frontCoordinator){
		coordinatorToAdd.nextCoordinator = frontCoordinator;
		return coordinatorToAdd;
	}
	
	public enum MatchingCoordinatorEnumerator{
		MULTIPLEENROLLMENT, DEFAULTTESTING, DEFAULTTESTINGPREQUANTIZED, NONE;
	}
	
	public enum IndexingCoordinatorEnumerator{
		RAMINDEXING, SQLINDEXING, NONE;
	}
	
	public enum HistogramCoordinatorEnumerator{
		HISTOGRAM, NONE;
	}
	
	

	
	private class BaseCoordinator extends Coordinator{

		public BaseCoordinator(Hasher hasher, Users users) {
			super(hasher, users);
		}

		@Override
		public RawScores run() {
			return new RawScores();
		}

	}
}
