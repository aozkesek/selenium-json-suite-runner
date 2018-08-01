package org.ao.suite.aspect;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.TestDriver;
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
        
        @Pointcut("execution(* org.ao.suite.test.TestDriver.run(..))")
        public void testRun() {}

        @Before("testRun()")
        public void reportTestRunStart(JoinPoint jp) {
                TestDriver testDriver = (TestDriver)jp.getTarget(); 
                SuiteDriver suiteDriver = testDriver.getSuiteDriver();
                
                if (suiteDriver.isNeededCommaTest())
                       	suiteDriver.getReportWriter().print(", ");
                       
                suiteDriver.getReportWriter()
                       	.printf("{ \"name\": \"%1$s\", \"commands\": [\n"
                        			, testDriver.getName());
                        
                suiteDriver.setNeededCommaTest(true);
                suiteDriver.setNeededCommaCommand(false);
                
                
        }
        
        @AfterReturning("testRun()")
        public void reportTestRunSuccessEnd(JoinPoint jp) {
                TestDriver testDriver = (TestDriver)jp.getTarget();
                
                testDriver.getSuiteDriver().getReportWriter()
                	.println("] , \"isSuccessful\": true }");
                
                
        }
	
        @AfterThrowing("testRun()")
        public void reportTestRunEnd(JoinPoint jp) {
                TestDriver testDriver = (TestDriver)jp.getTarget();
                
                testDriver.getSuiteDriver().getReportWriter()
                	.println("] , \"isSuccessful\": false }");
                        
                
        }
        
}
