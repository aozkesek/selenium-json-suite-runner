package org.ao.suite.aspect;

import org.ao.suite.SuiteDriver;
import org.ao.suite.model.VariableModel;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CommandDriverAspect {
        
        @Pointcut("execution(* org.ao.suite.test.command.*.execute(..))")
        public void commandExecute() {}

	@Around("commandExecute() && args(commandModel, suiteDriver)")
	public Object replaceCommandModelObject(ProceedingJoinPoint jp, CommandModel commandModel, 
	                SuiteDriver suiteDriver) 
	                throws Throwable {
		
		AbstractCommandDriver commandDriver = (AbstractCommandDriver)jp.getTarget();
		Logger logger = commandDriver.getLogger();
		
		logger.debug("AROUND-ASPECT: executing {}", commandModel);
		
		CommandModel repCommandModel = commandModel.clone();
		
		if (repCommandModel.getArgs() != null) {
			for(int i = 0; i < repCommandModel.getArgs().length; i++)
				repCommandModel.getArgs()[i] = suiteDriver
								.getObjectContainer()
								.getReplacedVariable(commandModel.getArgs()[i]);
		}
		
		if (repCommandModel.getValue() != null) {
			
			repCommandModel.setValue(suiteDriver
							.getObjectContainer()
							.getReplacedVariable(repCommandModel.getValue().toString()));
		}
		
		logger.debug("AROUND-ASPECT: command-parameter is replaced by {}", repCommandModel);
		
		Object returnObject = jp.proceed(new Object[]{repCommandModel, suiteDriver});
		
		logger.debug("AROUND-ASPECT: command-parameter {} is returned back.", repCommandModel);
		
		if (commandModel.getValue() != null) {
			String value = commandModel.getValue().toString();
			if (VariableModel.containsVariableName(value)) {
				String ref = VariableModel.VariableName(value);
				// value can only a variable name if we want to save the returned value
				if (value.equals("${" + ref + "}"))
					suiteDriver
						.getObjectContainer()
						.putVariable(ref, repCommandModel.getValue().toString());	
			}
		}
		
		logger.debug("AROUND-ASPECT: {} is executed.", commandModel);
		
		return returnObject;
		
	}
	
	@Before("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteStart(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
		
	        if (!commandModel.getCommand().equals("runTest"))
	                return;
	     	
                if (suiteDriver.isNeededCommaCommand())
                       	suiteDriver.getReportWriter().println(", { \"test\": ");
                else
                      	suiteDriver.getReportWriter().println("{ \"test\": ");
                
                suiteDriver.setNeededCommaTest(false);
                suiteDriver.setNeededCommaCommand(false);
                        
                
        }
        
	@AfterReturning("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteSuccessEnd(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
        
	        if (commandModel.getCommand().equals("runTest"))
	        	suiteDriver.getReportWriter().print(", ");
	        else
                    if (suiteDriver.isNeededCommaCommand())
                    	suiteDriver.getReportWriter().print(", { ");
                    else
                    	suiteDriver.getReportWriter().print("{ ");
	        
	        suiteDriver.getReportWriter()
	        	.printf("\"command\": \"%1$s\", \"isSuccessful\": true }\n", commandModel.getCommand());
            
	        suiteDriver.setNeededCommaCommand(true);       
        }
	
	@AfterThrowing("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteFailureEnd(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
	        
		if (commandModel.getCommand().equals("runTest"))
			suiteDriver.getReportWriter().print(", ");
		else
			if (suiteDriver.isNeededCommaCommand())
				suiteDriver.getReportWriter().print(", { ");
			else
				suiteDriver.getReportWriter().print("{ ");
            
		suiteDriver.getReportWriter()
			.printf("\"command\": \"%1$s\", \"isSuccessful\": false }\n", commandModel.getCommand());
            
		suiteDriver.setNeededCommaCommand(true);
                
                
        }
}
