package org.ao.aspect;

import org.ao.SuiteDriver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SuiteDriverAspect {
        
    @Pointcut("execution(* org.ao.SuiteDriver.runTests(..))")
    public void suiteRunTests() {}

    @Before("suiteRunTests()")
    public void reportSuiteRunTestsStart(JoinPoint jp) {
        
        SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
        
        suiteDriver.openReport();
                
        suiteDriver.report("{ \"name\": \"%1$s\", \"tests\": [\n", suiteDriver);
                
    }
    
    @AfterReturning("suiteRunTests()")
    public void reportSuiteRunTestsSuccessEnd(JoinPoint jp) {
        
        SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
            
        suiteDriver.report("], \"isSuccessful\": true }");		
        suiteDriver.closeReport();
    }

    @AfterThrowing("suiteRunTests()")
    public void reportSuiteRunTestsFailureEnd(JoinPoint jp) {
            
        SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
            
        suiteDriver.report("], \"isSuccessful\": false }");	              
        suiteDriver.closeReport();
    }
}
