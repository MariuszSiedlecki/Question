package pl.mario.ideas.input;

import org.junit.jupiter.api.Test;
import pl.mario.ideas.Action;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInputCommandTest {

    @Test
    void shouldBuildCorrectUserInputCommand() {
        String input = "category add CategoryName";
        UserInputCommand userInputCommand = new UserInputCommand(input);
        assertEquals("category", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertLinesMatch(List.of("CategoryName"), userInputCommand.getParam());
    }

    @Test
    void shouldBuildCorrectUserInputCommandWithMultiplesParam() {
        String input = "category add CategoryName1 CategoryName2";
        UserInputCommand userInputCommand = new UserInputCommand(input);
        assertEquals("category", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertEquals(List.of("CategoryName1", "CategoryName2"), userInputCommand.getParam());
    }

    @Test
    void shouldBuildCorrectUserInputCommandWithoutParam() {
        String input = "command add";
        UserInputCommand userInputCommand = new UserInputCommand(input);
        assertEquals("command", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertEquals(0, userInputCommand.getParam().size());
    }

    @Test
    void shouldThrowExceptionForUnknownAction() {
        String input = "command unknown";
        assertThrows(IllegalArgumentException.class, () -> new UserInputCommand(input));
    }
}
