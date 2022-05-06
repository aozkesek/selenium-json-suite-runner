package org.ao.aspect;

import org.ao.SuiteDriver;
import org.ao.model.VariableModel;
import org.ao.model.CommandModel;
import org.ao.model.CommandModelBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CommandDriverAspect {
        
    @Pointcut("execution(* org.ao.suite.test.command.*.execute(..))")
    public void commandExecute() {}

	@Around("commandExecute() && args(commandModel, suiteDriver)")
    public Object replaceCommandModelObject(ProceedingJoinPoint jp, 
                                            CommandModel commandModel, 
	                                        SuiteDriver suiteDriver) 
	throws Throwable {
		
		suiteDriver.logDebug("AROUND-ASPECT: {} is being executed...", commandModel);
		
		CommandModel repCommandModel = CommandModelBuilder.builder()
				.setCommand(commandModel.getCommand())
				.setValue(commandModel.getValue(), suiteDriver::getReplacedVariable)
				.setArgs(commandModel.getArgs(), suiteDriver::getReplacedVariable)
				.build();
		
        suiteDriver.logDebug("AROUND-ASPECT: command-parameter is replaced by {}", 
            repCommandModel);
		
		Object returnObject = jp.proceed(new Object[]{repCommandModel, suiteDriver});
		
        suiteDriver.logDebug("AROUND-ASPECT: command-parameter {} is returned back.", 
            repCommandModel);
		
		if (commandModel.getValue() != null) {
			String value = commandModel.getValue().toString();
			if (VariableModel.contains(value)) {
				String ref = VariableModel.toVariable(value);
				// value can only a variable name if we want to save the returned value
				if (value.equals("${" + ref + "}")) {
					suiteDriver.putVariable(ref, repCommandModel.getValue().toString());	
                }
			}
		}
		
		suiteDriver.logDebug("AROUND-ASPECT: {} is executed.", commandModel);
		
		return returnObject;
		
	}
	
	@Before("commandExecute() && args(commandModel, suiteDriver)")
    public void reportCommandExecuteStart(  JoinPoint jp, 
                                            CommandModel commandModel, 
                                            SuiteDriver suiteDriver) 
    throws Throwable {
    
        if (commandModel.getCommand().equals("runTest")) {
            suiteDriver.reportCommandStart();
        }
            
    }
        
	@AfterReturning("commandExecute() && args(commandModel, suiteDriver)")
    public void reportCommandExecuteSuccessEnd( JoinPoint jp, 
                                                CommandModel commandModel, 
                                                SuiteDriver suiteDriver) 
    throws Throwable {
    
        suiteDriver.reportCommandEnd(commandModel.getCommand(), true);
    }
	
	@AfterThrowing("commandExecute() && args(commandModel, suiteDriver)")
    public void reportCommandExecuteFailureEnd( JoinPoint jp, 
                                                CommandModel commandModel, 
                                                SuiteDriver suiteDriver) 
    throws Throwable {
        
        suiteDriver.reportCommandEnd(commandModel.getCommand(), false);
            
    }
}
