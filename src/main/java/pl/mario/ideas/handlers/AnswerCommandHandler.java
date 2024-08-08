package pl.mario.ideas.handlers;

import pl.mario.ideas.dao.AnswerDao;
import pl.mario.ideas.dao.QuestionDao;
import pl.mario.ideas.input.UserInputCommand;
import pl.mario.ideas.model.Answer;
import pl.mario.ideas.model.Question;

import java.util.logging.Logger;

public class AnswerCommandHandler extends BaseCommandHandler {
    private static Logger LOG = Logger.getLogger(AnswerCommandHandler.class.getName());
    private static final String COMMAND_NAME = "answer";
    private final AnswerDao answerDao;
    private QuestionDao questionDao;


    public AnswerCommandHandler() {
        this.answerDao = new AnswerDao();
        this.questionDao = new QuestionDao();
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
            case LIST:
                LOG.info("list of answer...");
                if (command.getParam().size() != 1) {
                    throw new IllegalArgumentException("Answer list requires one parameter (question name)");
                }
                String questionName = command.getParam().get(0);
                Question question = questionDao.findOne(questionName)
                        .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionName));
                displayQuestion(question);
                break;
            case ADD:
                LOG.info("Add answer");
                if (command.getParam().size() != 2) {
                    throw new IllegalArgumentException("Wrong command format. Check help for mor information");
                }
                questionName = command.getParam().get(0);
                String answerName = command.getParam().get(1);
                question = questionDao.findOne(questionName)
                        .orElseThrow(() -> new IllegalArgumentException("Question not found" + questionName));
                answerDao.add(new Answer(answerName));
                questionDao.addAnswer(question, new Answer(answerName));
                break;
            default: {
                throw new IllegalArgumentException(String.format("Unknown action: %s from command %s",
                        command.getAction(), command.getCommand()));
            }
        }
    }

    private void displayQuestion(Question question) {
        System.out.println(question.getName());
        question.getAnswers().forEach(System.out::println);
    }
}
