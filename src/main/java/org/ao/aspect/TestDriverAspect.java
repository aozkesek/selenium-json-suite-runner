package org.ao.aspect;

import org.ao.TestDriver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestDriverAspect {
        
    @Pointcut("execution(* org.ao.TestDriver.run(..))")
    public void testRun() {}

    @Before("testRun()")
    public void reportTestRunStart(JoinPoint jp) {
        
        TestDriver testDriver = (TestDriver)jp.getTarget(); 
        
        testDriver.getSuiteDriver()
            .reportTestStart(testDriver.getName());            
    }
    
    @AfterReturning("testRun()")
    public void reportTestRunSuccessEnd(JoinPoint jp) {

        TestDriver testDriver = (TestDriver)jp.getTarget();
            
        testDriver.getSuiteDriver()
            .report("] , \"isSuccessful\": true }");            
    }

    @AfterThrowing("testRun()")
    public void reportTestRunEnd(JoinPoint jp) {

        TestDriver testDriver = (TestDriver)jp.getTarget();
            
        testDriver.getSuiteDriver()
            .report("] , \"isSuccessful\": false }");
    }
        
}
