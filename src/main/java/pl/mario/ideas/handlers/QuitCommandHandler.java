package pl.mario.ideas.handlers;

import pl.mario.ideas.QuiteIdeaApplicationException;
import pl.mario.ideas.input.UserInputCommand;

public class QuitCommandHandler extends BaseCommandHandler {
    private static final String COMMAND_NAME = "quite";

    @Override
    public void handle(UserInputCommand command) {
        throw new QuiteIdeaApplicationException();
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
