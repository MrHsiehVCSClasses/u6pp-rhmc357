package u6pp;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to u6pp! :)\nGood luck!");

        //UnoFrontend game = new UnoFrontend();
        //game.play();

        // use this space to test your code :)
        Card myCard = new Card(Card.WILD, Card.EIGHT);
        myCard.trySetColor("bfdjhrhds");
        myCard.trySetColor(Card.WILD);
        myCard.trySetColor(null);
        myCard.trySetColor(Card.BLUE);
        myCard.trySetColor(Card.RED);

    }
}