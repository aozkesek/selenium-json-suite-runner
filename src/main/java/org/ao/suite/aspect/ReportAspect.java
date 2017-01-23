package org.ao.suite.aspect;

import org.ao.suite.test.TestDriver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
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
        
        @After("suiteRunTests()")
        public void reportSuiteRunTestsEnd(JoinPoint jp) {
                System.out.println("report suite.runtests end");    
                
        }
        
        @Pointcut("execution(* org.ao.suite.test.TestDriver.run(..))")
        public void testRun() {}

        @Before("testRun()")
        public void reportTestRunStart(JoinPoint jp) {
                System.out.println("report test.run start");    
                TestDriver td = (TestDriver)jp.getTarget();
                System.out.println("report test.run " + td.getName());    
                
        }
        
        @After("testRun()")
        public void reportTestRunEnd(JoinPoint jp) {
                System.out.println("report test.run end");    
                TestDriver td = (TestDriver)jp.getTarget();
                System.out.println("report test.run " + td.getName());    
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
