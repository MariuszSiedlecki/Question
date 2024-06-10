package pl.mario.ideas;

import pl.mario.ideas.heandlers.AnswerCommandHandler;
import pl.mario.ideas.heandlers.*;
import pl.mario.ideas.input.UserInputCommand;
import pl.mario.ideas.input.UserInputManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IdeasApplication {

    private static Logger LOG = Logger.getLogger(IdeasApplication.class.getName());
    public static void main(String[] args) {
        new IdeasApplication().start();
    }

    private void start() {

        LOG.info("Start.app..");
        System.out.println("klops");

        boolean applicationLoop = true;

        UserInputManager userInputManager = new UserInputManager();
        List<CommandHandler> handlers = new ArrayList<>();
        handlers.add(new HelpCommandHandlers());
        handlers.add(new QuitCommandHandler());
        handlers.add(new CategoryCommandHandler());
        handlers.add(new QuestionCommandHandler());
        handlers.add(new AnswerCommandHandler());


        while ( applicationLoop ) {
            try {
                UserInputCommand userInputCommand = userInputManager.nextCommand();
                LOG.info(userInputCommand.toString());
                Optional<CommandHandler> currentHandler = Optional.empty();
                for (CommandHandler handler : handlers) {
                    if (handler.supports(userInputCommand.getCommand())) {
                        currentHandler = Optional.of(handler);
                        break;
                    }
                }
                currentHandler
                        .orElseThrow(() -> new IllegalArgumentException("Unknown handler: " + userInputCommand.getCommand()))
                        .handle(userInputCommand);
            } catch (QuiteIdeaApplicationException e) {
               LOG.info("Quite...");
                applicationLoop = false;

            } catch (IllegalArgumentException e) {
                LOG.log(Level.WARNING, "Validation Exception " + e.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
                LOG.log(Level.SEVERE, "Unknown error ", e);
            }

        }

    }
}
