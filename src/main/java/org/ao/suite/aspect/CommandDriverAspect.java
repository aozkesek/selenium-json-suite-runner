package org.ao.suite.aspect;

import org.ao.suite.SuiteDriver;
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
		
		commandDriver.getLogger().debug("AROUND-ASPECT: {} {} {}", jp.getTarget(),commandModel, suiteDriver);
		
		commandDriver.getLogger().debug("AROUND-ASPECT: executing {}", commandModel);
		
		CommandModel repCommandModel = new CommandModel(){
			{ 
				setCommand(commandModel.getCommand());
				setArgs(commandModel.getArgs());
				setValue(commandModel.getValue());
				}
			};
		if (commandModel.getArgs() != null)
			repCommandModel.setArgs(commandDriver.getArgs(commandModel, suiteDriver.getObjectContainer()));
		if (commandModel.getValue() != null)
			repCommandModel.setValue(commandDriver.getValue(commandModel, suiteDriver.getObjectContainer()));
		
		commandDriver.getLogger().debug("AROUND-ASPECT: command-parameter replaced {}", repCommandModel);
		
		Object returnObject = jp.proceed(new Object[]{repCommandModel, suiteDriver});
		
		commandDriver.getLogger().debug("AROUND-ASPECT: command-parameter returned {}", repCommandModel);
		
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
		
		commandDriver.getLogger().debug("AROUND-ASPECT: executed {}", commandModel);
		
		return returnObject;
		
	}
	
	@Before("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteStart(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
        
		
	        if (!commandModel.getCommand().equals("runTest"))
	                return;
	        AbstractCommandDriver commandDriver = (AbstractCommandDriver)jp.getTarget();
	        commandDriver.getLogger().debug("BEFORE-ASPECT: {} {} {}", jp.getTarget(),commandModel, suiteDriver);
			
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
        
	        AbstractCommandDriver commandDriver = (AbstractCommandDriver)jp.getTarget();
	        commandDriver.getLogger().debug("AFTERRETURNING-ASPECT: {} {} {}", jp.getTarget(),commandModel, suiteDriver);
		
	        if (commandModel.getCommand().equals("runTest"))
	        	suiteDriver.getReportWriter().print(", ");
	        else
                    if (suiteDriver.isNeededCommaCommand())
                    	suiteDriver.getReportWriter().print(", { ");
                    else
                    	suiteDriver.getReportWriter().print("{ ");
	        suiteDriver.getReportWriter().printf("\"command\": \"%1$s\", \"isSuccessful\": true }\n", commandModel.getCommand());
            
	        suiteDriver.setNeededCommaCommand(true);       
        }
	
	@AfterThrowing("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteFailureEnd(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
	        AbstractCommandDriver commandDriver = (AbstractCommandDriver)jp.getTarget();
	        commandDriver.getLogger().debug("AFTERTHROWING-ASPECT: {} {} {}", jp.getTarget(),commandModel, suiteDriver);
			
	        if (commandModel.getCommand().equals("runTest"))
            	suiteDriver.getReportWriter().print(", ");
            else
                    if (suiteDriver.isNeededCommaCommand())
                    	suiteDriver.getReportWriter().print(", { ");
                    else
                    	suiteDriver.getReportWriter().print("{ ");
            suiteDriver.getReportWriter().printf("\"command\": \"%1$s\", \"isSuccessful\": false }\n", commandModel.getCommand());
            
            suiteDriver.setNeededCommaCommand(true);
                
                
        }
}
