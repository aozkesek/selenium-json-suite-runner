package org.ao.suite.aspect;

import java.io.BufferedWriter;

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
import org.springframework.core.annotation.Order;
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
		
		AbstractCommandDriver acd = (AbstractCommandDriver)jp.getTarget();
		
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
		
		Object returnObject = jp.proceed(new Object[]{repCommandModel, suiteDriver});
		
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
	
	@Before("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteStart(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
        
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        bw.append("<li>");
                        bw.newLine();
                        
                }
                catch(Exception ex) {
                                                   
                }
                
        }

	@AfterReturning("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteSuccessEnd(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
        
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        bw.append(commandModel.getCommand() + " SUCCEEDED</li>");
                        bw.newLine();
                        
                }
                catch(Exception ex) {
                                                   
                }
                
        }
	
	@AfterThrowing("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteFailureEnd(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
        
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        bw.append(commandModel.getCommand() + " FAILED</li>");
                        bw.newLine();
                        
                }
                catch(Exception ex) {
                                                   
                }
                
        }
}
