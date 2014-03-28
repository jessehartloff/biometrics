package system.coordinator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingPrequantizedSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.transformations.SHA2;
import system.coordinator.testgenerators.Test;
import system.coordinator.testgenerators.TestGenerator;
import system.hasher.Hasher;

public class DefaultTestingPrequantized extends DefaultTesting{

	// TODO Jen/Jim - SQL prequantizing
	
	public DefaultTestingPrequantized(Hasher hasher, Users users, TestGenerator testGenerator) {
		super(hasher, users, testGenerator);
		this.prequantize();
	}
	
	protected void prequantize(){
		quantizeUserSet(users.users, true);
	}
	
	public void quantizeUserSet(ArrayList<User> users, boolean print){	

		SHA2 sha = new SHA2();
		Long total = 0L;
		Long completed = 0L;
		Long totalNumHashes;
		Double progress;
		System.out.println("Prequantizing...");
		for(User user : users){
			total += user.readings.size();
		}
		for(User user : users){
			
			int keySize = DefaultTestingPrequantizedSettings.getInstance().keySize().getValue().intValue();
			BigInteger userKey = new BigInteger(keySize, new Random());
			
			int numberOfReadings = user.readings.size();
			for(int i=0; i<numberOfReadings; i++){
				Template enroll = hasher.makeEnrollTemplate(user.readings.get(i));
				ArrayList<Template> test = hasher.makeTestTemplates(user.readings.get(i));
				
				if(keySize != 0){
					//keyed system
					Template enrollKeyed = new Template();
					ArrayList<Template> testKeyed = new ArrayList<Template>();
					for(BigInteger templatePoint : enroll.getHashes()){
						enrollKeyed.getHashes().add(sha.transform(templatePoint.shiftLeft(keySize).add(userKey)));
					}
					for(Template currentTemplate : test){
						Template t = new Template();
						for(BigInteger templatePoint : currentTemplate.getHashes()){
							t.getHashes().add(sha.transform(templatePoint.shiftLeft(keySize).add(userKey)));
						}
						testKeyed.add(t);
					}
					user.prequantizedEnrolledTemplates.add(enrollKeyed);
					user.prequantizedTestTemplates.add(testKeyed);
				}else{
					//non-keyed system
					user.prequantizedEnrolledTemplates.add(enroll);
					user.prequantizedTestTemplates.add(test);
				}
			
				
				completed++;
				progress = (completed.doubleValue()/total.doubleValue())*100.0;

//				System.out.format("prequantizing: %5.2f%%%n", progress);
			}
			progress = (completed.doubleValue()/total.doubleValue())*100.0;
					System.out.format("prequantizing: %5.2f%%%n", progress);
		}
	}
	
	
	@Override
	protected Double runTest(Test test){
		Template enrolledTemplate = users.users.get(test.enrolledUserID.intValue()).prequantizedEnrolledTemplates.get(test.enrolledReadingNumber.intValue());
		ArrayList<Template> testTemplates = users.users.get(test.testUserID.intValue()).prequantizedTestTemplates.get(test.testReadingNumber.intValue());
		
		Double score = hasher.compareTemplates(enrolledTemplate, testTemplates);
		
		return score; 
	}
	
}
