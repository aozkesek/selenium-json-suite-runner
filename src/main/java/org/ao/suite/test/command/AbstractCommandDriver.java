package org.ao.suite.test.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ao.suite.test.TestContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommandDriver implements ICommandDriver {
	
	protected Logger logger = LoggerFactory.getLogger(AbstractCommandDriver.class);
	protected static Pattern VariablePattern = Pattern.compile("\\$\\{[A-Z,a-z,_][A-Z,a-z,0-9,.,_,\\[,\\]]+\\}");
	
	protected CommandModel commandModel;
	private CommandModel orgCommandModel;
	protected WebDriver webDriver;
	protected TestContainer testContainer;	
	
	public AbstractCommandDriver(TestContainer testContainer, WebDriver webDriver, CommandModel commandModel) {
		this.testContainer = testContainer;
		this.commandModel = commandModel;
		this.orgCommandModel = commandModel;
		this.webDriver = webDriver;

	}
	
	@Override
	public void execute() throws ElementNotFoundException {
		// put back the original model with include variable name
		// so if the test that contain this needed to run again,
		// command driver re-calculate the variables  
		commandModel = orgCommandModel;
		
	}
	
	protected Logger getLogger() {
		return logger;
	}
	
	protected WebElement findElement() throws ElementNotFoundException {
		
		getLogger().debug("find element for {}", commandModel);
		
		By by = parseArguments();
		if (by == null)
			throw new ElementNotFoundException(commandModel.getArgs());
		
		new WebDriverWait(webDriver, 10000).until(
				new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(by);
					}
				});
			
		WebElement webElement = webDriver.findElement(by);
		
		if (containsVariable(commandModel.getValue())) 
			commandModel.setValue(replaceVariables(commandModel.getValue()));
		
		return webElement;
		
	}
	
	protected List<WebElement> findElements() throws ElementNotFoundException {
		
		getLogger().debug("find element for {}", commandModel);
		
		By by = parseArguments();
		if (by == null)
			throw new ElementNotFoundException(commandModel.getArgs());
			
		new WebDriverWait(webDriver, 10000).until(
				new ExpectedCondition<List<WebElement>>() {
					public List<WebElement> apply(WebDriver d) {
						return d.findElements(by);
					}
				});
		
		List<WebElement> webElements = webDriver.findElements(by);

		if (containsVariable(commandModel.getValue())) 
			commandModel.setValue(replaceVariables(commandModel.getValue()));

		return webElements;
		
	}
	
	protected void storeValue(Object value) {
		
		
		
	}
	
	public boolean containsVariable(String input) {
		
		if (input == null)
			return false;
		
		logger.debug("checking for variable: {}", input);
		
		Matcher matcher = VariablePattern.matcher(input);
		return matcher.find(0);
	}
	
	protected String replaceVariables(String input) {
		if (input == null)
			return null;
		
		Matcher matcher = VariablePattern.matcher(input);
		boolean isFound = matcher.find(0);
		
		if (!isFound)
			return input;
		
		HashMap<String, String> kvPairs = new HashMap<String, String>();
		
		while (isFound) {
			String varName = input.substring(matcher.start() + 2, matcher.end() - 1);
			Object varValue = testContainer.getVariable(varName);
			kvPairs.put(varName, varValue.toString());
			logger.debug("{} will replace by {}", varName, varValue);
			isFound = matcher.find();
		}
		
		for (Entry<String, String> pair: kvPairs.entrySet())
			input = input.replaceAll("\\$\\{" + pair.getKey() + "\\}", pair.getValue());	
		
		logger.debug("replaced by variable: {}", input);
		return input;
	}
	
	protected By parseArguments() {
		
		String args = commandModel.getArgs();
		
		if (containsVariable(args)) {
			args = replaceVariables(args);
			commandModel.setArgs(args);
		}
				
		if (args.startsWith("id="))
			return new ById(args.substring(3));
		else if (args.startsWith("name="))
			return new ByName(args.substring(5));
		else if (args.startsWith("className="))
			return new ByClassName(args.substring(10));
		else if (args.startsWith("cssSelector="))
			return new ByCssSelector(args.substring(10));
		else if (args.startsWith("tagName="))
			return new ByTagName(args.substring(8));
		else if (args.startsWith("xPath="))
			return new ByXPath(args.substring(6));
		else if (args.startsWith("//"))
			return new ByXPath(args);
		else if (args.startsWith("linkText="))
			return new ByLinkText(args.substring(9));
		else if (args.startsWith("partialLinkText="))
			return new ByPartialLinkText(args.substring(16));
		
		return null;
		
	}
	
	

}
