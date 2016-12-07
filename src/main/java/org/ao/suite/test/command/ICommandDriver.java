package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.model.CommandModel;

public interface ICommandDriver {
	
	void execute(CommandModel commandModel, SuiteDriver suiteDriver) throws RuntimeException;

}
