package u6pp;

import java.util.List;
import java.util.Scanner;

public class InputHelper {

    public static int getIntegerInput(Scanner scanner, String prompt, int min, int max) {
        String input = "";

        boolean isInputValid = false;
        int output = 0;

        do {

            System.out.println(prompt);
            input = scanner.nextLine().trim();

            if (input.length() != 0) {
                boolean isAllNumbers = true;
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (i == 0 && c == '-') {
                        continue; // starting with a '-' is okay
                    }
                    if (c < '0' || c > '9') {
                        isAllNumbers = false;
                        break;
                    }
                }

                boolean isInBounds = false;
                if (isAllNumbers) {
                    output = Integer.parseInt(input);
                    if (min <= output && output <= max) {
                        isInBounds = true;
                    }
                }

                isInputValid = isAllNumbers && isInBounds;
            }

            if (!isInputValid) {
                System.out.println("Invalid input, please input an integer.");
            }

        } while (!isInputValid);

        return output;
    }

    public static boolean getYesNoInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            input = input.replaceAll("\\s", "").toLowerCase();
            if (!input.isBlank()) {
                char firstLetter = input.charAt(0);
                if (firstLetter == 'y') {
                    return true;
                }
                if (firstLetter == 'n') {
                    return false;
                }
            }
            System.out.println("Sorry, invalid input. Please answer with either \'yes\'' or \'no\'.");
        }
    }

    public static String getInputInList(Scanner scanner, String prompt, List<Object> list) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            input = input.replaceAll("\\s", "");
            if (!input.isBlank()) {
                if (list.contains(input)) {
                    return input;
                }
            }
            System.out.println("Sorry, invalid input. Please answer with one of the following: " + list);
        }
    }
}
