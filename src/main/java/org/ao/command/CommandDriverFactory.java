package org.ao.command;

import org.ao.command.exception.CommandNotFoundException;
import org.ao.model.CommandModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.TreeMap;

@Component
public class CommandDriverFactory {

	private static final Map<String, Method> seleniumCommands = new TreeMap<>();
	static {
		putMethods("org.openqa.selenium.WebElement", "element");
		putMethods("org.openqa.selenium.remote.RemoteWebDriver", "driver");
	}

	private static void putMethods(String className, String mapTo) {
		try {
			Class clazz = Class.forName(className);
			for (Method method : clazz.getDeclaredMethods()) {
				if (Modifier.isPublic(method.getModifiers())) {
					seleniumCommands.put(
						mapTo.concat(method.getName().substring(0,1).toUpperCase())
							.concat(method.getName().substring(1)),
						method);
				}
			}
		} catch (ClassNotFoundException e) {
			// stop running here
			throw new RuntimeException(e);
		}

	}

	@Autowired
	private ApplicationContext context;

	public ICommandDriver getCommandDriver(CommandModel commandModel)
			throws CommandNotFoundException {
		
		if (null == commandModel || null == commandModel.getCommand()) {
			throw new IllegalArgumentException("command-model");
		}

		if (!context.containsBean(commandModel.getCommand())) {
			throw new CommandNotFoundException(commandModel.getCommand());
		}

		return context.getBean(commandModel.getCommand(), ICommandDriver.class);
	}
	
	void findCommand(CommandModel commandModel) {




	}

}
