package pl.mario.ideas;

import java.util.Arrays;

public enum Action {
    LIST("list"),
    ADD("add");


    Action(String value) {
        this.value = value;
    }

    private final String value;

    public static Action of(String action) {
        System.out.println("Trying to find action: " + action);
        return Arrays.stream(values())
                .filter(a -> a.name().equalsIgnoreCase(action))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown action: " + action));
    }
}
