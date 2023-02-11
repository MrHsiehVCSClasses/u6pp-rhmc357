package u6pp;

import java.util.Arrays;
import java.util.Scanner;

public class UnoFrontend {
    private Uno uno;
    private Scanner scanner = new Scanner(System.in);

    public void play() {
        System.out.println("--------------------------------");

        System.out.println("Welcome to Uno!");
        boolean shouldKeepPlaying = true;
        while (shouldKeepPlaying) {
            int numPlayers = InputHelper.getIntegerInput(scanner, "How many players (2 to 10)? ", 2, 10);
            uno = new Uno(numPlayers);

            System.out.println("--------------------------------");

            while (uno.getWinner() == null) {
                // print status
                String prompt = getStatus(uno.getCurrentPlayer()) + "Please select an option: ";

                // get action
                int optionSelection = InputHelper.getIntegerInput(scanner, prompt, 0,
                        uno.getCurrentPlayer().getHand().size());
                System.out.println();

                // do action
                boolean actionSuccess;
                if (optionSelection == 0) {
                    actionSuccess = uno.playCard(null, null);
                    System.out.println("> skipping turn and drawing a card");
                } else {
                    Card currCard = uno.getCurrentPlayer().getHand().get(optionSelection - 1);
                    Player nextPlayer = uno.getNextPlayer();
                    if (currCard.getColor().equals(Card.WILD) || currCard.getColor().equals(Card.WILD_DRAW_4)) {
                        String color = InputHelper.getInputInList(scanner, "What color?",
                                Arrays.asList((Object[]) Card.COLORS));
                        actionSuccess = uno.playCard(currCard, color);
                    } else {
                        actionSuccess = uno.playCard(currCard, "");
                    }
                    System.out.println("> playing card " + currCard);

                    if (currCard.getValue().equals(Card.SKIP)) {
                        System.out.println("> attempting to skip " + nextPlayer);
                    } else if (currCard.getValue().equals(Card.DRAW_2)) {
                        System.out.println("> attempting to force a draw 2 and turn skip on " + nextPlayer);
                    } else if (currCard.getValue().equals(Card.REVERSE)) {
                        System.out.println("> attempting to reverse");
                    } else if (currCard.getValue().equals(Card.WILD_DRAW_4)) {
                        System.out.println("> attempting to force a draw 4 and turn skip on " + nextPlayer);
                    }
                }

                // print action result
                if (actionSuccess) {
                    System.out.println("> action succeeded :) ");
                } else {
                    System.out.println("> action not allowed :(");
                }
                System.out.println("--------------------------------");

            }
            shouldKeepPlaying = InputHelper.getYesNoInput(scanner, "Keep playing? (Y/N): ");
        }
        System.out.println("Thanks for playing! :)");
    }

    private String getStatus(Player player) {
        String output = "";
        output += "Current Player: " + player.getName();
        output += "\n";
        output += "top of discard pile: " + uno.getTopDiscard() + "\n";
        output += "Options: \n";
        output += "[0]: skip turn & draw a card\n";
        for (int i = 0; i < player.getHand().size(); i++) {
            output += String.format("[%s]: %s", i + 1, player.getHand().get(i));
            output += "\n";
        }
        return output;
    }
}
