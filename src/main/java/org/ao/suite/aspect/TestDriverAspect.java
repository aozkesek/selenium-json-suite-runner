package org.ao.suite.aspect;

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
                
                testDriver.getLogger().debug("BEFORE-ASPECT: {}", jp.getTarget());
                
                testDriver.getLogger().debug("BEFORE-ASPECT: {} is starting.", testDriver.getName());
                if (testDriver.getSuiteDriver().isNeededCommaTest())
                       	testDriver.getSuiteDriver().getReportWriter().print(", ");
                       
                testDriver.getSuiteDriver().getReportWriter()
                       	.printf("{ \"name\": \"%1$s\", \"commands\": [\n"
                        			, testDriver.getName());
                        
                testDriver.getSuiteDriver().setNeededCommaTest(true);
                testDriver.getSuiteDriver().setNeededCommaCommand(false);
                
                
        }
        
        @AfterReturning("testRun()")
        public void reportTestRunSuccessEnd(JoinPoint jp) {
                TestDriver testDriver = (TestDriver)jp.getTarget();
                
                testDriver.getLogger().debug("AFTERRETURNING-ASPECT: {}", jp.getTarget());
                
                testDriver.getSuiteDriver().getReportWriter().println("] , \"isSuccessful\": true }");
                testDriver.getLogger().debug("AFTERRETURNING-ASPECT: {} is finished.", testDriver.getName());
                
                
        }
	
        @AfterThrowing("testRun()")
        public void reportTestRunEnd(JoinPoint jp) {
                TestDriver testDriver = (TestDriver)jp.getTarget();
                
                testDriver.getLogger().debug("AFTERTHROWING-ASPECT: {}", jp.getTarget());
                
                testDriver.getSuiteDriver().getReportWriter().println("] , \"isSuccessful\": false }");
                testDriver.getLogger().debug("AFTERTHROWING-ASPECT: {} is finished.", testDriver.getName());
                        
                
        }
        
}
