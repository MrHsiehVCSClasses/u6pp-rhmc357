package u6pp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardStackTests {

    private CardStack redDeck = new CardStack();
    private CardStack greenDeck = new CardStack();

    private Card redEight = new Card(Card.RED, Card.EIGHT);
    private Card redNine = new Card(Card.RED, Card.NINE);
    private Card greenEight = new Card(Card.GREEN, Card.EIGHT);
    private Card greenNine = new Card(Card.GREEN, Card.NINE);

    @BeforeEach
    public void beforeEach() {
        redDeck.push(redNine);
        redDeck.push(redEight);  // redEight is now at the top of the deck
        greenDeck.push(greenNine);
        greenDeck.push(greenEight); // greenEight is now at the top of the deck
    }

    @Test
    public void isEmpty_emptyDeck_returnsTrue() {
        assertTrue(new CardStack().isEmpty());
    }

    @Test
    public void isEmpty_filledDeck_returnsFalse() {
        assertFalse(redDeck.isEmpty());
    }

    @Test
    public void peek_normalDeck_getsCard() {
        assertEquals(redEight, redDeck.peek());
    }

    @Test
    public void peek_emptyDeck_returnsNull() {
        assertNull(new CardStack().peek());
    }

    @Test
    public void getSize_correctValue() {
        assertEquals(2, redDeck.getSize());
    }

    @Test
    public void popGetSize_shrinksDeck() {
        redDeck.pop();
        assertEquals(1, redDeck.getSize());
    }

    @Test
    public void pop_returnsCorrectCard() {
        assertEquals(redEight, redDeck.pop());
    }

    @Test
    public void clear_emptiesDeck() {
        redDeck.clear();
        assertTrue(redDeck.isEmpty());
    }

    @Test
    public void addAll_mergesDecks() {
        redDeck.addAll(greenDeck);
        assertEquals(4, redDeck.getSize());
    }

    @Test
    public void addAll_sameDeck_doesNothing() {
        redDeck.addAll(redDeck);
        assertEquals(2, redDeck.getSize());
    }

    @Test
    public void addAll_emptyDeck_doesNothing() {
        redDeck.addAll(new CardStack());
        assertEquals(2, redDeck.getSize());
    }

    @Test
    public void addAll_clearsOtherDeck() {
        redDeck.addAll(greenDeck);
        assertEquals(0, greenDeck.getSize());
    }

    @Test
    public void addAll_preservesOrder() {
        redDeck.addAll(greenDeck);
        assertEquals(greenEight, redDeck.pop());
        assertEquals(greenNine, redDeck.pop());
        assertEquals(redEight, redDeck.pop());
        assertEquals(redNine, redDeck.pop());
    }

    @Test
    public void shuffle_changesOrder() {
        redDeck.addAll(greenDeck);
        redDeck.push(new Card(Card.YELLOW, Card.ZERO));
        redDeck.push(new Card(Card.YELLOW, Card.ONE));
        redDeck.push(new Card(Card.YELLOW, Card.TWO));
        redDeck.push(new Card(Card.YELLOW, Card.THREE));
        redDeck.push(new Card(Card.BLUE, Card.ZERO));
        redDeck.push(new Card(Card.BLUE, Card.ONE));
        redDeck.push(new Card(Card.BLUE, Card.TWO));
        redDeck.push(new Card(Card.BLUE, Card.THREE));
        redDeck.push(new Card(Card.BLUE, Card.FOUR));
        redDeck.push(new Card(Card.BLUE, Card.FIVE));
        redDeck.push(new Card(Card.BLUE, Card.SIX));
        redDeck.push(new Card(Card.BLUE, Card.SEVEN));
        redDeck.push(new Card(Card.BLUE, Card.EIGHT));
        redDeck.push(new Card(Card.BLUE, Card.NINE));
        redDeck.push(new Card(Card.BLUE, Card.REVERSE));
        redDeck.push(new Card(Card.BLUE, Card.SKIP));
        redDeck.push(new Card(Card.BLUE, Card.DRAW_2));
        redDeck.push(new Card(Card.WILD, Card.WILD));
        redDeck.push(new Card(Card.WILD, Card.WILD_DRAW_4));

        // create 2 copies of the deck
        CardStack stack1 = new CardStack();
        CardStack stack2 = new CardStack();

        while (!redDeck.isEmpty()) {
            Card c = redDeck.pop();
            stack1.push(c);
            stack2.push(c);
        }

        // shuffle only stack 2
        stack2.shuffle();

        // compare the 2 stacks - same number of cards, different order
        int numCards = stack1.getSize();
        assertEquals(stack1.getSize(), stack2.getSize());

        int differentCards = 0;
        while (!stack1.isEmpty()) {
            if (!stack1.pop().equals(stack2.pop())) {
                differentCards++;
            }
        }

        assertTrue(differentCards > numCards - 7);
    }
}
