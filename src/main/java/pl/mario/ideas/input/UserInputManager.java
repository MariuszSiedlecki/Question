package pl.mario.ideas.input;

import java.io.InputStream;
import java.util.Scanner;

public class UserInputManager {

    private Scanner scanner;

    public UserInputManager(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public UserInputManager() {
        this(System.in);
    }

    public UserInputCommand nextCommand() {

        return new UserInputCommand(scanner.nextLine());
    }
}
