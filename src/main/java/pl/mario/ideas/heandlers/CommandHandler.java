package pl.mario.ideas.heandlers;

import pl.mario.ideas.input.UserInputCommand;

public interface CommandHandler {
    void handle(UserInputCommand command);
    boolean supports(String name);
}
