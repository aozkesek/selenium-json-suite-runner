package org.ao.suite.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ReportAspect {
        
        
        @Pointcut("execution(* org.ao.suite.SuiteDriver.RunTests(..))")
        public void suiteRunTests() {}

        @Before("suiteRunTests()")
        public void reportSuiteRunTestsStart(JoinPoint jp) {
                System.out.println("report suite.runtests start");    
                
        }
              
        @Pointcut("execution(* org.ao.suite.test.command.*.execute(..))")
        public void commandExecute() {}
        

	@AfterReturning(pointcut="commandExecute()")
	public void reportSuccessCommand(JoinPoint jp) {
		
		System.out.println("report success");
		
	}

	@AfterThrowing(pointcut="commandExecute()",
	                throwing="ex")
        public void reportFailedCommand(JoinPoint jp, Exception ex) {
                
	        System.out.println("report failure");
                
        }

	
}
