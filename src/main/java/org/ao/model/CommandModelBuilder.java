package org.ao.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommandModelBuilder {
	private String command;
	private Object value;
	private List<String> args = new ArrayList<>();
    
    public static CommandModelBuilder builder() {
        return new CommandModelBuilder();
    }

    public CommandModelBuilder() {}

    public CommandModelBuilder(String command) {
        this.command = command;
    }

    public CommandModelBuilder(String command, Object value) {
        this.command = command;
        this.value = value;
    }

    public CommandModelBuilder(String command, Object value, List<String> args) {
        this.command = command;
        this.value = value;
        this.args.addAll(args);
    }

	public CommandModelBuilder setCommand(String command) {
		this.command = command;
		return this;
	}
	
	public CommandModelBuilder setValue(Object value) {
		this.value = value;
		return this;
	}
	
    public CommandModelBuilder setValue(Object value, 
                                        Function<String, Object> mapper) 
    {
		if (value == null) {
            return this;
        }

		if (value instanceof String) {
			this.value = mapper.apply(String.valueOf(value));
        } 
        else {
            this.value = value;
        }
		return this;
	}
	
	public CommandModelBuilder setArgs(List<String> args) {
		if (args == null) {
            return this;
        }
		
		this.args.addAll(args);
		return this;
	}
	
    public CommandModelBuilder setArgs(List<String> args, 
                                        Function<String, String> mapper) 
    {
        
        if (args == null) {
            return this;
        }
        
        this.args = new ArrayList<>();
        args.forEach(a -> this.args.add(mapper.apply(a)));
        
		return this;
	}
	
	public CommandModel build() {
		CommandModel commandModel = new CommandModel();
		commandModel.setArgs(args);
		commandModel.setCommand(command);
		commandModel.setValue(value);
		return commandModel;
	}
}
