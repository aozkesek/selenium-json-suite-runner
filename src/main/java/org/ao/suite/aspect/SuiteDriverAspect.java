package org.ao.suite.aspect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.ao.suite.SuiteDriver;
import org.ao.suite.SuiteProperty;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SuiteDriverAspect {
        @Autowired
        private SuiteProperty suiteProp;
        
        @Pointcut("execution(* org.ao.suite.SuiteDriver.RunTests(..))")
        public void suiteRunTests() {}

        @Before("suiteRunTests()")
        public void reportSuiteRunTestsStart(JoinPoint jp) {
                SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
                String reportFileName = suiteDriver.SuiteId
                                .replace('/', '_')
                                .replace('\\', '_');
                File reportFile = new File(SuiteDriver.FullPathName(suiteProp.reportsHome, reportFileName));
                try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile));
                        suiteDriver.setReportWriter(bw);
                        
                        bw.write("{ \"name\": \"" + suiteDriver.SuiteId + "\"");
                        bw.write(", \"threadId\": " + String.valueOf(Thread.currentThread().getId()));
                        bw.write(", \"tests\": [");
                        
                        bw.newLine();
                        
                } catch (IOException e) {
                        
                }
                
                
                  
                
        }
        
        @AfterReturning("suiteRunTests()")
        public void reportSuiteRunTestsSuccessEnd(JoinPoint jp) {
                SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
                
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        
                        bw.append("], \"isSuccessful\": true }");
                        bw.newLine();
                        
                        bw.close();
                        
                } catch (IOException e) {
                        
                }
        }

        @AfterThrowing("suiteRunTests()")
        public void reportSuiteRunTestsFailureEnd(JoinPoint jp) {
                SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
                
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        
                        bw.append("], \"isSuccessful\": false }");
                        bw.newLine();
                                      
                        bw.close();
                        
                } catch (IOException e) {
                        
                }
        }
}
