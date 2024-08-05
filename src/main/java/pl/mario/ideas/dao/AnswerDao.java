package pl.mario.ideas.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.mario.ideas.model.Answer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnswerDao {
    private static final Logger LOG = Logger.getLogger(AnswerDao.class.getName());
    private final ObjectMapper objectMapper;


    public AnswerDao() {
        this.objectMapper = new ObjectMapper();
    }

    private List<Answer> getAnswers() {
        Path path = Paths.get("./answers.txt");
        if (Files.notExists(path)) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(Files.readString(path), new TypeReference<>() {
            });
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error reading answers file", e);
            return new ArrayList<>();
        }
    }

    public List<Answer> findAll() {
        return getAnswers();
    }

    public void add(Answer answer) {
        List<Answer> answers = getAnswers();
        answers.add(answer);
        try {
            Files.writeString(Paths.get("./answers.txt"), objectMapper.writeValueAsString(answers));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error writing answers file", e);
        }
    }
}
