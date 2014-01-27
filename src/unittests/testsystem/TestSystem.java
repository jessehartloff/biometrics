package unittests.testsystem;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	unittests.testsystem.testallcommonclasses.TestAllCommonClasses.class, 
//	unittests.testsystem.testbase.TestBase.class, 
	unittests.testsystem.testcoordinator.TestCoordinator.class, 
//	unittests.testsystem.testhasher.TestHasher.class, 
	unittests.testsystem.testmethod.TestMethod.class, 
//	unittests.testsystem.testvectordistance.TestVectorDistance.class, 
	})


public class TestSystem {

}
