package org.ao.suite.aspect;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CommandDriverAspect {

	@Around("execution(* org.ao.suite.test.command.*.execute(..))")
	public Object replaceCommandModelObject(ProceedingJoinPoint pjp) throws Throwable {
		
		AbstractCommandDriver acd = (AbstractCommandDriver)pjp.getTarget();
		CommandModel commandModel = (CommandModel)pjp.getArgs()[0];
		SuiteDriver suiteDriver = (SuiteDriver)pjp.getArgs()[1];
		
		acd.getLogger().debug("AROUND-ASPECT: executing {}", commandModel);
		
		CommandModel repCommandModel = new CommandModel(){
			{ 
				setCommand(commandModel.getCommand());
				setArgs(commandModel.getArgs());
				setValue(commandModel.getValue());
				}
			};
		if (commandModel.getArgs() != null)
			repCommandModel.setArgs(acd.getArgs(commandModel));
		if (commandModel.getValue() != null)
			repCommandModel.setValue(acd.getValue(commandModel));
		
		acd.getLogger().debug("AROUND-ASPECT: command-parameter replaced {}", repCommandModel);
		
		Object returnObject = pjp.proceed(new Object[]{repCommandModel, suiteDriver});
		
		acd.getLogger().debug("AROUND-ASPECT: command-parameter returned {}", repCommandModel);
		
		if (commandModel.getValue() != null) {
			String value = commandModel.getValue().toString();
			if (suiteDriver.getObjectContainer().containsVariable(value)) {
				String varName = suiteDriver.getObjectContainer().getVariableName(value);
				// value can only a variable name if we want to save the returned value
				if (value.equals("${" + varName + "}")) { 
					suiteDriver.getObjectContainer().putVariable(varName, repCommandModel.getValue());	
				}
			}
		}
		
		acd.getLogger().debug("AROUND-ASPECT: executed {}", commandModel);
		
		return returnObject;
		
	}

}
