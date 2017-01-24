package org.ao.suite.aspect;

import java.io.BufferedWriter;

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
                TestDriver td = (TestDriver)jp.getTarget();
                String testName = td.getName();
                SuiteDriver suiteDriver = td.getSuiteDriver(); 
                
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        if (suiteDriver.isNeededCommaTest())
                                bw.append(", { \"name\": \"" + testName + "\"");
                        else
                                bw.append("{ \"name\": \"" + testName + "\"");
                        bw.append(", \"commands\": [");
                        bw.newLine();
                        
                        suiteDriver.setNeededCommaTest(true);
                        suiteDriver.setNeededCommaCommand(false);
                }
                catch(Exception ex) {
                        
                }
                
                
        }
        
        @AfterReturning("testRun()")
        public void reportTestRunSuccessEnd(JoinPoint jp) {
                TestDriver td = (TestDriver)jp.getTarget();
                SuiteDriver suiteDriver = td.getSuiteDriver();  

                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        bw.append("] , \"isSuccessful\": true }");
                        bw.newLine();
                        
                }
                catch(Exception ex) {
                        
                }
        }
	
        @AfterThrowing("testRun()")
        public void reportTestRunEnd(JoinPoint jp) {
                TestDriver td = (TestDriver)jp.getTarget();
                SuiteDriver suiteDriver = td.getSuiteDriver();    
                
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        bw.append("] , \"isSuccessful\": false }");
                        bw.newLine();
                        
                }
                catch(Exception ex) {
                        
                }
        }
        
}
