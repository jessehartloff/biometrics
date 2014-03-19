package system.coordinator;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingPrequantizedMultiThreadedSettings;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.testgenerators.TestGenerator;
import system.hasher.Hasher;

public class DefaultTestingPrequantizedMultiThreaded extends DefaultTestingPrequantized{

	public DefaultTestingPrequantizedMultiThreaded(Hasher hasher, Users users,
			TestGenerator testGenerator) {
		super(hasher, users, testGenerator);
	}
	
	@Override
	protected void prequantize(){
		Integer numberOfThreads = DefaultTestingPrequantizedMultiThreadedSettings.getInstance().numberOfThreads().getValue().intValue();
		Integer numberOfUsers = users.users.size();
		System.out.println(numberOfUsers);
		Integer usersPerThread = numberOfUsers/numberOfThreads;
		ArrayList<User> threadLocalUsers = new ArrayList<User>();
		ArrayList<Callable<DividedPrequantize>> threads = new ArrayList<Callable<DividedPrequantize>>();
		for(User user : users.users){
			int i = 0;
			if(i < usersPerThread){
				threadLocalUsers.add(user);
			} else{
                DividedPrequantize thread = new DividedPrequantize(threadLocalUsers);
                threads.add(thread);
                threadLocalUsers = new ArrayList<User>();
			}
			i++;
		}
		ExecutorService e = Executors.newFixedThreadPool(numberOfThreads);
		for(Callable<DividedPrequantize> t : threads){
			e.submit(t);
		}
		System.out.println("prequantized");
	}
	
	private class DividedPrequantize implements Callable<DividedPrequantize>{
		private ArrayList<User> users;
		public DividedPrequantize(ArrayList<User> users){
			this.users =  users;
		}

		@Override
		public DividedPrequantize call() throws Exception {
			quantizeUserSet(this.users, false);
			return this;
		}
	}
}
