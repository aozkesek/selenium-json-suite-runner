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
                File reportFile = new File(SuiteDriver.FullPathName(suiteProp.reportsHome, reportFileName)
                                .replaceAll(".json", ".html"));
                try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile));
                        suiteDriver.setReportWriter(bw);
                        
                        bw.write("<html>");
                        bw.newLine();
                        
                        bw.append("<body>");
                        bw.newLine();
                        
                        bw.append("<table>");
                        bw.newLine();
                        
                        bw.append("<tr><td colspan=\"2\">" + suiteDriver.SuiteId + "</td> </tr>");
                        bw.newLine();
                        
                } catch (IOException e) {
                        
                }
                
                
                  
                
        }
        
        @AfterReturning("suiteRunTests()")
        public void reportSuiteRunTestsSuccessEnd(JoinPoint jp) {
                SuiteDriver suiteDriver = (SuiteDriver)jp.getTarget();
                
                try {
                        BufferedWriter bw = suiteDriver.getReportWriter();
                        
                        bw.append("<tr><td>" + suiteDriver.SuiteId + "</td> <td>SUCCEEDED</td> </tr>");
                        bw.newLine();
                        
                        bw.append("</table>");
                        bw.newLine();
                        
                        bw.append("</body>");
                        bw.newLine();
                        
                        bw.append("</html>");
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
                        
                        bw.append("<tr><td>" + suiteDriver.SuiteId + "</td> <td>FAILED</td> </tr>");
                        bw.newLine();
                        
                        bw.append("</table>");
                        bw.newLine();
                        
                        bw.append("</body>");
                        bw.newLine();
                        
                        bw.append("</html>");
                        bw.newLine();
                                      
                        bw.close();
                        
                } catch (IOException e) {
                        
                }
        }
}
