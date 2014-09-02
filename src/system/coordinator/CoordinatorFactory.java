package system.coordinator;


import settings.coordinatorsettings.histogramcoordinatorsettings.AllHistogramCoordinatorSettings;
import settings.coordinatorsettings.indexingcoordinatorsettings.AllIndexingCoordinatorSettings;
import settings.coordinatorsettings.matchingcoordinatorsettings.AllMatchingCoordinatorSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.AllMultiserverCoordinatorSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.indexingstructure.RAMStructure;
import system.allcommonclasses.indexingstructure.SQLStructure;
import system.coordinator.multiserver.*;
import system.coordinator.testgenerators.TestGeneratorFactory;
import system.hasher.Hasher;
import system.hasher.HasherFactory;

//import system.allcommonclasses.indexingstructure.SQLStructure;
//import system.allcommonclasses.indexingstructure.SQLStructure;

public class CoordinatorFactory {
	 	
	public static Coordinator makeMultiserverCoordinator(Users users){
        //TODO refactor: get rid of all the global crap

		Hasher hasher = HasherFactory.makeHasher();
		Coordinator firstCoordinator = new CoordinatorFactory().new BaseCoordinator(hasher, users);
		
		switch(MultiserverCoordinatorEnumerator.valueOf(AllMultiserverCoordinatorSettings.getMultiserverCoordinator())){
			case NONE:
				return null;
			case SERVER1:
				return addToFront(new Server1(hasher, null), firstCoordinator);
			case SERVER2:
				return addToFront(new Server2(hasher, null), firstCoordinator);
			case CLIENT:
				return addToFront(new Client(), firstCoordinator);
			case SUPERTESTINGMETACLIENT:
				return addToFront(new SuperTestingMetaClient(hasher, users, TestGeneratorFactory.makeTestGenerator()), firstCoordinator);
			case TESTINGSTMC:
				return addToFront(new TestingSTMC(hasher, users), firstCoordinator);
			default:
				return null;
		}
		
	}
	
	public static Coordinator makeAllSingleServerCoordinators(Users users){
		
		Hasher hasher = HasherFactory.makeHasher();
		
		Coordinator firstCoordinator = new CoordinatorFactory().new BaseCoordinator(hasher, users);
		
		Coordinator currentCoordinator = null;
		
		currentCoordinator = CoordinatorFactory.makeMatchingCoordinator(users);
		if(currentCoordinator != null){
			firstCoordinator = addToFront(currentCoordinator, firstCoordinator);
		}
		
		currentCoordinator = CoordinatorFactory.makeIndexingCoordinator(users);
		if(currentCoordinator != null){
			firstCoordinator = addToFront(currentCoordinator, firstCoordinator);
		}
		
		currentCoordinator = CoordinatorFactory.makeHistogramCoordinator(users);
		if(currentCoordinator != null){
			firstCoordinator = addToFront(currentCoordinator, firstCoordinator);
		}
		
		
		
		
		
		return firstCoordinator;
	}
	
	
	public static Coordinator makeMatchingCoordinator(Users users){

		Hasher hasher = HasherFactory.makeHasher();
		
		switch(MatchingCoordinatorEnumerator.valueOf(AllMatchingCoordinatorSettings.getMatchingCoordinator())){
			case DEFAULTTESTING:
				return new DefaultTesting(hasher, users, TestGeneratorFactory.makeTestGenerator());		
			case DEFAULTTESTINGPREQUANTIZED:
				return new DefaultTestingPrequantized(hasher, users, TestGeneratorFactory.makeTestGenerator());
			case DEFAULTTESTINGPREQUANTIZEDMULTITHREADED:
				return new DefaultTestingPrequantizedMultiThreaded(hasher, users, TestGeneratorFactory.makeTestGenerator());
			case FEATURECOUNTER:
				return new FeatureCounter(hasher, users, TestGeneratorFactory.makeTestGenerator());
			case MULTIPLEENROLLMENT:
				return new MultipleEnrollment(hasher, users);
			case NONE:
				return null;				
			default:
				System.out.println("You didn't provide an appropriate matching coordinator");
				return null;
		}		
	
	}
	
	

	public static Coordinator makeIndexingCoordinator(Users users){
		
		Hasher hasher = HasherFactory.makeHasher();
		
		switch(IndexingCoordinatorEnumerator.valueOf(AllIndexingCoordinatorSettings.getIndexingCoordinator())){
		case RAMINDEXING:
			return new IndexTesting(hasher, users, new RAMStructure());
		case SQLINDEXING:
			return new IndexTesting(hasher, users, new SQLStructure());
		case NONE:
			return null;				
		default:
			System.out.println("You didn't provide an appropriate indexing coordinator");
			return null;
	}	
		
	}
	
	

	public static Coordinator makeHistogramCoordinator(Users users){

		Hasher hasher = HasherFactory.makeHasher();
		
		switch(HistogramCoordinatorEnumerator.valueOf(AllHistogramCoordinatorSettings.getHistogramCoordinator())){
		case HISTOGRAM:
			return new HistogramCoordinator(hasher, users);		
		case NONE:
			return null;				
		default:
			System.out.println("You didn't provide an appropriate histogram coordinator");
			return null;
	}	
	
		
	}
	
	private static Coordinator addToFront(Coordinator coordinatorToAdd, Coordinator frontCoordinator){
		coordinatorToAdd.nextCoordinator = frontCoordinator;
		return coordinatorToAdd;
	}
	
	public enum MatchingCoordinatorEnumerator{
		MULTIPLEENROLLMENT, DEFAULTTESTING, DEFAULTTESTINGPREQUANTIZED, DEFAULTTESTINGPREQUANTIZEDMULTITHREADED, FEATURECOUNTER, NONE;
	}
	
	public enum IndexingCoordinatorEnumerator{
		RAMINDEXING, SQLINDEXING, NONE;
	}
	
	public enum HistogramCoordinatorEnumerator{
		HISTOGRAM, NONE;
	}
	
	public enum MultiserverCoordinatorEnumerator{
		SUPERTESTINGMETACLIENT, TESTINGSTMC, SERVER1, SERVER2, CLIENT, NONE;
	}
	
	

	
	private class BaseCoordinator extends Coordinator{

		public BaseCoordinator(Hasher hasher, Users users) {
			super(hasher, users);
		}

		@Override
		public RawScores run() {
			RawScores rawScores = new RawScores();
			rawScores.numberOfUsers = this.users.users.size();
			return rawScores;
		}

	}
}
