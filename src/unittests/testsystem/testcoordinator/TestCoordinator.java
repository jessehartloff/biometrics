package unittests.testsystem.testcoordinator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	unittests.testsystem.testcoordinator.testmultiserver.TestMultiserver.class,
//	unittests.testsystem.testcoordinator.testtestgenerators.TestTestGenreator.class
	})

public class TestCoordinator {

}
