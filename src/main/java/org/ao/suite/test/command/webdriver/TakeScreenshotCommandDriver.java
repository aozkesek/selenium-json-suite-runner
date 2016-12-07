package org.ao.suite.test.command.webdriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.ao.suite.SuiteDriver;
import org.ao.suite.SuiteProperty;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("takeScreenshot")
public class TakeScreenshotCommandDriver extends AbstractCommandDriver {

	@Autowired
	private SuiteProperty suiteProp;
	
	public TakeScreenshotCommandDriver() {
		super(LoggerFactory.getLogger(TakeScreenshotCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		WebDriver augmentedDriver = new Augmenter().augment(suiteDriver.getWebDriver());
        File tempScreenshot = ((TakesScreenshot)augmentedDriver).
                            getScreenshotAs(OutputType.FILE);
        logger.debug("temporary screenshot {} is copying here {}"
        		, tempScreenshot.getAbsolutePath()
        		, FileSystems.getDefault().getPath(suiteProp.screenshotsHome, commandModel.getArgs()));
        
        try {
			Files.move(tempScreenshot.toPath(), 
					FileSystems.getDefault().getPath(suiteProp.screenshotsHome, commandModel.getArgs()), 
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
       
	}

}

