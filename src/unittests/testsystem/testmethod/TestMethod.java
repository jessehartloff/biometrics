package unittests.testsystem.testmethod;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
//	unittests.testsystem.testmethod.testfacemethods.TestFaceMethods.class,
//	unittests.testsystem.testmethod.testfeature.TestFeature.class,
	unittests.testsystem.testmethod.testfingerprintmethods.TestFingerprintMethods.class,
	unittests.testsystem.testmethod.testfeature.TestFeature.class
//	unittests.testsystem.testmethod.testirismethods.TestIrisMethods.class
	})

public class TestMethod {

}
