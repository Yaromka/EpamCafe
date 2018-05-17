package com.epam.cafe.command;

public class CommandFactory {
    public Command createCommand(String commandName) {
        return CommandMap.defineCommandType(commandName);
    }
}
