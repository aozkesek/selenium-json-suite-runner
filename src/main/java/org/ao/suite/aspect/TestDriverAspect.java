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
                        bw.append("<table><tr><td><ul>");
                        bw.newLine();
                        
                }
                catch(Exception ex) {
                        
                }
                
                
        }
        
        @AfterReturning("testRun()")
        public void reportTestRunSuccessEnd(JoinPoint jp) {
                TestDriver td = (TestDriver)jp.getTarget();
                String testName = td.getName();
                SuiteDriver suiteDriver = td.getSuiteDriver();  

                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        bw.append("</ul></td></tr><tr><th>" + testName + "</th> <th>SUCCEEDED</th> </tr>");
                        bw.newLine();
                        
                        bw.append("</table>");
                        bw.newLine();
                        
                }
                catch(Exception ex) {
                        
                }
        }
	
        @AfterThrowing("testRun()")
        public void reportTestRunEnd(JoinPoint jp) {
                TestDriver td = (TestDriver)jp.getTarget();
                String testName = td.getName();
                SuiteDriver suiteDriver = td.getSuiteDriver();    
                
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        bw.append("</ul></td></tr><tr><th>" + testName + "</th> <th>FAILED</th> </tr>");
                        bw.newLine();
                        bw.append("</table>");
                        bw.newLine();
                        
                }
                catch(Exception ex) {
                        
                }
        }
        
}
