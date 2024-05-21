package pl.mario.ideas;

import java.util.Arrays;
import java.util.Objects;

public enum Action {
    LIST("list"),
    ADD("add");


    Action(String value) {
        this.value = value;
    }

    private final String value;

    public static Action of(String inputValue) {
        return Arrays.stream(values())
                .filter(action -> Objects.equals(action.value, inputValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknow actions " + inputValue));
    }
}
