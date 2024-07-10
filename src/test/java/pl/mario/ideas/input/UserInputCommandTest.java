package pl.mario.ideas.input;

import org.junit.jupiter.api.Test;
import pl.mario.ideas.Action;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInputCommandTest {

    @Test
    void shouldBuildCorrectUserInputCommand() {
        //given
        String input = "category add CategoryName";
        //When
        UserInputCommand userInputCommand = new UserInputCommand(input);
        //Then
        assertEquals("category", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertLinesMatch(List.of("CategoryName"), userInputCommand.getParam());
    }

    @Test
    void shouldBuildCorrectUserInputCommandWithMultiplesParam() {
        //given
        String input = "category add CategoryName1 CategoryName2";
        // when
        UserInputCommand userInputCommand = new UserInputCommand(input);
        // then
        assertEquals("category", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertEquals(List.of("CategoryName1", "CategoryName2"), userInputCommand.getParam());
    }

    @Test
    void shouldBuildCorrectUserInputCommandWithoutParam() {
        //given
        String input = "command add";
        //When
        UserInputCommand userInputCommand = new UserInputCommand(input);
        //Then
        assertEquals("command", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertEquals(0, userInputCommand.getParam().size());
    }

    @Test
    void shouldThrowExceptionForUnknownAction() {
        //given
        String input = "command unknown";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> new UserInputCommand(input));
    }
}
