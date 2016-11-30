package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
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
		
		acd.logger.debug("AROUND-ASPECT: executing {}", commandModel);
		
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
		
		acd.logger.debug("AROUND-ASPECT: replaced {}", repCommandModel);
		
		Object returnObject = pjp.proceed(new Object[]{repCommandModel, suiteDriver});
		
		acd.logger.debug("AROUND-ASPECT: returned {}", repCommandModel);
		
		if (commandModel.getValue() != null) {
			String value = commandModel.getValue().toString();
			if (suiteDriver.getObjectContainer().containsVariable(value)) {
				String varName = suiteDriver.getObjectContainer().getVariableName(value);
				suiteDriver.getObjectContainer().putVariable(varName, repCommandModel.getValue());
			}
		}
		
		acd.logger.debug("AROUND-ASPECT: executed {}", commandModel);
		
		return returnObject;
		
	}

}
