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
        
	        if (!commandModel.getCommand().equals("runTest"))
	                return;
	        
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        if (suiteDriver.isNeededCommaCommand())
                                bw.append(", { \"test\": ");
                        else
                                bw.append("{ \"test\": ");
                        suiteDriver.setNeededCommaTest(false);
                        suiteDriver.setNeededCommaCommand(false);
                        bw.newLine();
                        
                        
                }
                catch(Exception ex) {
                                                   
                }
                
        }
        
	@AfterReturning("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteSuccessEnd(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
        
	        boolean isRunTest = commandModel.getCommand().equals("runTest");
	        
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        if (isRunTest)
                                bw.append(", \"command\": \"" + commandModel.getCommand() + "\", \"isSuccessful\": true }");
                        else
                                if (suiteDriver.isNeededCommaCommand())
                                        bw.append(", { \"command\": \"" + commandModel.getCommand() + "\", \"isSuccessful\": true }");
                                else
                                        bw.append("{ \"command\": \"" + commandModel.getCommand() + "\", \"isSuccessful\": true }");
                        bw.newLine();
                        
                        suiteDriver.setNeededCommaCommand(true);
                        
                }
                catch(Exception ex) {
                                                   
                }
                
        }
	
	@AfterThrowing("commandExecute() && args(commandModel, suiteDriver)")
        public void reportCommandExecuteFailureEnd(JoinPoint jp, CommandModel commandModel, SuiteDriver suiteDriver) 
                        throws Throwable {
	        boolean isRunTest = commandModel.getCommand().equals("runTest");
        
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        if (isRunTest)
                                bw.append(", \"command\": \"" + commandModel.getCommand() + "\", \"isSuccessful\": false }");
                        else
                                if (suiteDriver.isNeededCommaCommand())
                                        bw.append(", { \"command\": \"" + commandModel.getCommand() + "\", \"isSuccessful\": false }");
                                else
                                        bw.append("{ \"command\": \"" + commandModel.getCommand() + "\", \"isSuccessful\": false }");
                        bw.newLine();
                        
                        suiteDriver.setNeededCommaCommand(true);
                }
                catch(Exception ex) {
                                                   
                }
                
        }
}
