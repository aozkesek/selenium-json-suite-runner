package org.ao.suite.aspect;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.TestDriver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestDriverAspect {
        
        @Pointcut("execution(* org.ao.suite.test.TestDriver.run(..))")
        public void testRun() {}

        @Before("testRun()")
        public void reportTestRunStart(JoinPoint jp) {
                TestDriver td = (TestDriver)jp.getTarget();
                String testName = td.getName();
                SuiteDriver suiteDriver = td.getSuiteDriver(); 
                
        }
        
        @After("testRun()")
        public void reportTestRunEnd(JoinPoint jp) {
                TestDriver td = (TestDriver)jp.getTarget();
                String testName = td.getName();
                SuiteDriver suiteDriver = td.getSuiteDriver();    
        }
	
}
