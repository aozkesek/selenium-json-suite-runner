package org.ao.command;

import org.ao.SuiteDriver;
import org.ao.model.CommandModel;

public interface ICommandDriver {
	
	void execute(CommandModel commandModel, SuiteDriver suiteDriver) throws RuntimeException;

}
