package u6pp;

import java.util.ArrayList;
import java.util.Random;

public class CardStack {
    private ArrayList<Card> cards = new ArrayList<>();
    private Random rand = new Random();

    public static CardStack createUnoDeck() {
        CardStack stack = new CardStack();
        for (String color : Card.COLORS) {
            if (color.equalsIgnoreCase(Card.WILD)) {
                continue;
            }

            for (String value : Card.VALUES) {
                if (value.equals(Card.WILD) || value.equals(Card.WILD_DRAW_4)) {
                    continue;
                }
                stack.push(new Card(color, value));
                if (!value.equalsIgnoreCase(Card.ZERO)) {
                    stack.push(new Card(color, value));
                }
            }
        }

        for (int i = 0; i < 40; i++) {
            stack.push(new Card(Card.WILD, Card.WILD));
            stack.push(new Card(Card.WILD, Card.WILD_DRAW_4));
        }

        return stack;
    }

    public Card peek() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(cards.size() - 1);
    }

    public Card pop() {
        if (isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    public void push(Card card) {
        cards.add(card);
    }

    public boolean isEmpty() {
        return cards.size() == 0;
    }

    public int getSize() {
        return cards.size();
    }

    public void clear() {
        cards.clear();
    }

    // clears the other Stack
    public void addAll(CardStack other) {
        if (other == this) {
            return;
        }
        this.cards.addAll(other.cards);
        other.clear();
    }

    public void shuffle() {
        // fisher-yates
        for (int i = 0; i < cards.size(); i++) {
            int randIndex = rand.nextInt(i, cards.size());
            if (randIndex == i) {
                continue; // if we select the current card, do nothing
            }
            Card temp = cards.get(i);
            cards.set(i, cards.get(randIndex));
            cards.set(randIndex, temp);
        }
    }

}
