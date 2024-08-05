package pl.mario.ideas.heandlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.mario.ideas.dao.CategoryDao;
import pl.mario.ideas.dao.QuestionDao;
import pl.mario.ideas.input.UserInputCommand;
import pl.mario.ideas.model.Category;
import pl.mario.ideas.model.Question;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuestionCommandHandlerTest {

    private QuestionCommandHandler handler;
    private QuestionDao questionDao;
    private CategoryDao categoryDao;

    @BeforeEach
    public void setUp() {
        questionDao = mock(QuestionDao.class);
        categoryDao = mock(CategoryDao.class);
        handler = new QuestionCommandHandler(questionDao, categoryDao);
    }
    @Test
    void shouldAddQuestion() {
        UserInputCommand command = new UserInputCommand("question ADD categoryName questionName");
        Category mockCategory = new Category("categoryName");

        when(categoryDao.findOne(anyString())).thenReturn(Optional.of(mockCategory));

        handler.handle(command);

        ArgumentCaptor<Question> questionCaptor = ArgumentCaptor.forClass(Question.class);
        verify(questionDao).add(questionCaptor.capture());

        Question captureQuestion = questionCaptor.getValue();
        assertEquals("questionName",captureQuestion.getName());
        assertEquals(mockCategory,captureQuestion.getCategory());
    }

    @Test
    void shouldRemoveQuestion() {
        UserInputCommand command = new UserInputCommand("question REMOVE questionName");
        handler.handle(command);
        verify(questionDao, times(1)).remove("questionName");
    }

}
