package org.ao.suite.aspect;

import org.ao.suite.SuiteDriver;
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
        
        @Pointcut("execution(* org.ao.suite.SuiteDriver.RunTests(..))")
        public void suiteRunTests() {}

        @Before("suiteRunTests()")
        public void reportSuiteRunTestsStart(JoinPoint jp) {
                SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
                
                suiteDriver.openReportWriter();
				
		suiteDriver.getLogger()
			.debug("BEFORE-ASPECT: Report output file {} is opened.", suiteDriver.SuiteId);
				
		suiteDriver.getReportWriter()
			.printf("{ \"name\": \"%1$s\", \"tests\": [\n"
				, suiteDriver.SuiteId);
				
        }
        
        @AfterReturning("suiteRunTests()")
        public void reportSuiteRunTestsSuccessEnd(JoinPoint jp) {
                SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
                
                suiteDriver.getReportWriter()
                	.println("], \"isSuccessful\": true }");		
		suiteDriver.getReportWriter().close();
		
		suiteDriver.getLogger()
			.debug("AFTERRETURNING-ASPECT: Report output file {} is closed.", suiteDriver.SuiteId);
        }

        @AfterThrowing("suiteRunTests()")
        public void reportSuiteRunTestsFailureEnd(JoinPoint jp) {
                SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
                
                suiteDriver.getReportWriter()
                	.println("], \"isSuccessful\": false }");	              
		suiteDriver.getReportWriter().close();
		
		suiteDriver.getLogger()
			.debug("AFTERTHROWING-ASPECT: Report output file {} is closed.", suiteDriver.SuiteId);
        }
}
