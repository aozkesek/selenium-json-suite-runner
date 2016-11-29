package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;

public interface ICommandDriver {
	
	void execute(CommandModel commandModel, SuiteDriver suiteDriver) throws ElementNotFoundException;

}
