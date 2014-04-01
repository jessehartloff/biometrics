package unittests.testsystem.testhasher;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestStraightHashing.class,
	TestFuzzyVault.class
})

public class TestHasher {

}
