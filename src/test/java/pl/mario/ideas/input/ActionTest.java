package pl.mario.ideas.input;

import org.junit.jupiter.api.Test;
import pl.mario.ideas.Action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActionTest {

    @Test
    public void testActionOfList() {
        String action = "list";
        Action result = Action.of(action);
        assertEquals(Action.LIST, result);
    }

    @Test
    public void testActionOfAdd() {
        String action = "add";
        Action result = Action.of(action);
        assertEquals(Action.ADD, result);
    }

    @Test
    public void testActionUnknown() {
        String action = "unknown";
        assertThrows(IllegalArgumentException.class, () -> Action.of(action));
    }

    @Test
    public void testActionRemove() {
        String action = "remove";
        Action result = Action.of(action);
        assertEquals(Action.REMOVE, result);
    }
}
