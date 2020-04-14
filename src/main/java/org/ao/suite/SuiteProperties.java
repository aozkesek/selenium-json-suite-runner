package org.ao.suite;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("suite")
public class SuiteProperties {
    
    public String format;
    public String path;
    public String testsPath;
    public String objectsPath;
    public String reportsPath;
        
    public boolean isParallel;
	public int threadCount;

    public String driver;
    public int timeout;

    public String remoteUrl;

    public List<String> suites;

    public SuiteProperties() { }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTestsPath(String testsPath) {
        this.testsPath = testsPath;
    }

    public void setObjectsPath(String objectsPath) {
        this.objectsPath = objectsPath;
    }

    public void setReportsPath(String reportsPath) {
        this.reportsPath = reportsPath;
    }

    public void setParallel(boolean isParallel) {
        this.isParallel = isParallel;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public void setSuites(List<String> suites) {
        this.suites = suites;
    }
    
	
}
