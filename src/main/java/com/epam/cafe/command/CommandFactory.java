package com.epam.cafe.command;

public class CommandFactory {
    /**
     * Returns suitable command by it's string value.
     * @param commandName holds the value of command.
     */
    public Command createCommand(String commandName) {
        return CommandMap.defineCommandType(commandName);
    }
}
