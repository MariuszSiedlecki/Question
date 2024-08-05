package pl.mario.ideas.heandlers;

import pl.mario.ideas.input.UserInputCommand;

public class HelpCommandHandlers extends BaseCommandHandler {
    private static final String COMMAND_NAME = "help";

    @Override
    public void handle(UserInputCommand userInputCommand) {
        System.out.println("Help...");
        System.out.println("Allowed command: help, quite, category, question, answer");
        System.out.println("Command pattern: <command> <action> <param1> <param2>");
        System.out.println("Example command:\n " +
                "'category list',\n " +
                "'category add + (name)',\n " +
                "'category remove + (name)',\n " +
                "'category update + (old category) + (new category) ',\n " +
                "'question list',\n " +
                "'question add + (category) + (question)',\n " +
                "'question remove + (name)'\n " +
                "'answer add + (questionName) + (answer)',\n ");
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
