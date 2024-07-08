package pl.mario.ideas.heandlers;

import pl.mario.ideas.dao.CategoryDao;
import pl.mario.ideas.dao.QuestionDao;
import pl.mario.ideas.input.UserInputCommand;
import pl.mario.ideas.model.Category;
import pl.mario.ideas.model.Question;

import java.util.List;
import java.util.logging.Logger;

public class QuestionCommandHandler extends BaseCommandHandler {
    private static final Logger LOG = Logger.getLogger(QuitCommandHandler.class.getName());
    private static final String COMMAND_NAME = "question";
    private final QuestionDao questionDao;
    private final CategoryDao categoryDao;


    public QuestionCommandHandler() {
        questionDao = new QuestionDao();
        categoryDao = new CategoryDao();
    }


    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }


    @Override
    public void handle(UserInputCommand command) {
        if (command.getAction() == null) {
            throw new IllegalArgumentException("command can't be null");
        }
        switch (command.getAction()) {
            case LIST -> {
                LOG.info("list of questions...");
                if (!command.getParam().isEmpty()) {
                    throw new IllegalArgumentException("question list doesn't support any additional params");
                }
                List<Question> questions = questionDao.findAll();
                questions.forEach(System.out::println);
            }
            case ADD -> {
                LOG.info("Add question");
                if (command.getParam().size() != 2) {
                    throw new IllegalArgumentException("wrong command format. Check help for mor information");
                }
                String categoryName = command.getParam().get(0);
                String questionName = command.getParam().get(1);
                Category category = categoryDao.findOne(categoryName)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found" + categoryName));
                questionDao.add(new Question(questionName, category));
            }
            default -> {
                throw new IllegalArgumentException(String.format("Unknown action: %s from command %s",
                        command.getAction(), command.getCommand()));
            }
        }
    }
}
