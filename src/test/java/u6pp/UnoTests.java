package u6pp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UnoTests {

    private Uno uno;
    private Uno reversedUno;
    private ArrayList<Player> players;
    private Player p1, p2, p3;
    private CardStack deck;
    private CardStack discard;

    @BeforeEach
    public void beforeEach() {
        p1 = new Player("p1");
        p2 = new Player("p2");
        p3 = new Player("p3");

        p1.getHand().add(new Card(Card.RED, Card.ZERO));
        p1.getHand().add(new Card(Card.RED, Card.ONE));
        p1.getHand().add(new Card(Card.BLUE, Card.ZERO));
        p1.getHand().add(new Card(Card.WILD, Card.WILD));

        p2.getHand().add(new Card(Card.RED, Card.TWO));

        p3.getHand().add(new Card(Card.RED, Card.THREE));

        players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        deck = new CardStack();
        deck.push(new Card(Card.GREEN, Card.ONE));
        deck.push(new Card(Card.GREEN, Card.ZERO));
        discard = new CardStack();
        discard.push(new Card(Card.RED, Card.ZERO));
        discard.push(new Card(Card.RED, Card.ZERO));
        discard.push(new Card(Card.RED, Card.ZERO));
        discard.push(new Card(Card.RED, Card.ZERO));
        discard.push(new Card(Card.RED, Card.ZERO));
        discard.push(new Card(Card.RED, Card.ZERO));
        uno = new Uno(players, deck, discard, 0, false);
        reversedUno = new Uno(players, deck, discard, 0, true);
    }

    @Test
    public void getCurrentPlayer_index0_correctPlayer() {
        assertTrue(players.get(0) == uno.getCurrentPlayer());
    }

    @Test
    public void getCurrentPlayer_index1_correctPlayer() {
        uno = new Uno(players, deck, discard, 1, true);

        assertTrue(players.get(1) == uno.getCurrentPlayer());
    }

    @Test
    public void getNextPlayer_notReversed_correctPlayer() {
        assertTrue(players.get(1) == uno.getNextPlayer());
    }

    @Test
    public void getNextPlayer_notReversedWrapAround_correctPlayer() {
        uno = new Uno(players, deck, discard, players.size() - 1, false);

        assertTrue(players.get(0) == uno.getNextPlayer());
    }

    @Test
    public void getNextPlayer_reversed_correctPlayer() {
        uno = new Uno(players, deck, discard, 1, true);

        assertTrue(players.get(0) == uno.getNextPlayer());
    }

    @Test
    public void getNextPlayer_reversedWrapAround_correctPlayer() {
        uno = new Uno(players, deck, discard, 0, true);

        assertTrue(players.get(players.size() - 1) == uno.getNextPlayer());
    }

    @Test
    public void getTopDiscard_returnsCorrectCard() {
        Card c = new Card(Card.WILD, Card.WILD_DRAW_4);
        discard.push(c);
        assertTrue(c == uno.getTopDiscard());
    }

    @Test
    public void getWinner_winnerExists_returnsCorrectPlayer() {
        p2.getHand().clear();

        assertTrue(p2 == uno.getWinner());
    }

    @Test
    public void getWinner_winnerDoesNotExist_returnsNull() {
        assertNull(uno.getWinner());
    }

    @Test
    public void playCard_CardNotInHand_returnsFalse() {
        assertFalse(uno.playCard(new Card(Card.RED, Card.ZERO), ""));
    }

    @Test
    public void playCard_allowedPlay_returnsTrue() {
        discard.push(new Card(Card.RED, Card.ZERO));
        Card playerCard = new Card(Card.RED, Card.ZERO);
        uno.getCurrentPlayer().getHand().set(0, playerCard);
        assertTrue(uno.playCard(playerCard, ""));
    }

    @Test
    public void playCard_disallowedPlay_returnsFalse() {
        discard.push(new Card(Card.BLUE, Card.NINE));
        Card playerCard = new Card(Card.RED, Card.ZERO);
        uno.getCurrentPlayer().getHand().set(0, playerCard);
        assertFalse(uno.playCard(playerCard, ""));
    }

    @Test
    public void playCard_callsCardCanPlayAtLeastOnce() {
        // the spy thing is just so that I can check how many times a certain function
        // is called
        Card spyCard = spy(new Card(Card.RED, Card.ZERO));
        Card discardTop = uno.getTopDiscard();

        uno.getCurrentPlayer().getHand().set(0, spyCard);
        uno.playCard(spyCard, "");

        verify(spyCard, atLeastOnce()).canPlayOn(discardTop);
    }

    @Test
    public void playCard_whenCanPlayTrue_removesCardFromHand() {
        Card cardToRemove = uno.getCurrentPlayer().getHand().get(0);
        uno.playCard(cardToRemove, "");
        assertFalse(uno.getCurrentPlayer().getHand().contains(cardToRemove));
    }

    @Test
    public void playCard_whenCanPlayTrue_cardIsOnTopOfDiscard() {
        Card cardToPlay = uno.getCurrentPlayer().getHand().get(0);
        uno.playCard(cardToPlay, "");
        assertTrue(cardToPlay == uno.getTopDiscard());
    }

    @Test
    public void playCard_whenCanPlayFalse_cardStaysInHand() {
        Card cardToStayInHand = uno.getCurrentPlayer().getHand().get(0);
        discard.push(new Card(Card.GREEN, Card.FIVE));
        uno.playCard(cardToStayInHand, "");
        assertTrue(uno.getCurrentPlayer().getHand().contains(cardToStayInHand));
    }

    @Test
    public void playCard_whenCanPlayFalse_topDiscardIsUnchanged() {
        Card discardTop = new Card(Card.GREEN, Card.FIVE);
        discard.push(discardTop);
        Card cardToPlay = uno.getCurrentPlayer().getHand().get(0);
        uno.playCard(cardToPlay, "");
        assertTrue(discardTop == uno.getTopDiscard());
    }

    @Test
    public void playCard_normal_nextPlayerCorrect() {
        uno.playCard(uno.getCurrentPlayer().getHand().get(0), "");
        assertEquals(p2, uno.getCurrentPlayer());
    }

    @Test
    public void playCard_normalWhenReversed_nextPlayerCorrect() {
        reversedUno.playCard(reversedUno.getCurrentPlayer().getHand().get(0), "");
        assertEquals(p3, reversedUno.getCurrentPlayer());
    }

    @Test
    public void playCard_reverse_nextPlayerCorrect() {
        Card redReverse = new Card(Card.RED, Card.REVERSE);
        uno.getCurrentPlayer().getHand().set(0, redReverse);
        uno.playCard(redReverse, "");
        assertEquals(p3, uno.getCurrentPlayer());
    }

    @Test
    public void playCard_reverseWhenReversed_nextPlayerCorrect() {
        Card redReverse = new Card(Card.RED, Card.REVERSE);
        reversedUno.getCurrentPlayer().getHand().set(0, redReverse);
        reversedUno.playCard(redReverse, "");
        assertEquals(p2, reversedUno.getCurrentPlayer());
    }

    @Test
    public void playCardNull_drawsCard() {
        int p1HandSize = uno.getCurrentPlayer().getHand().size();
        uno.playCard(null, null);
        assertEquals(p1HandSize + 1, p1.getHand().size());
    }

    @Test
    public void playCardNull_correctNextPlayer() {

        uno.playCard(null, "ASDF");
        assertEquals(p2, uno.getCurrentPlayer());
    }

    @Test
    public void playCardNull_whenReversed_correctNextPlayer() {
        reversedUno.playCard(null, "");
        assertEquals(p3, reversedUno.getCurrentPlayer());
    }

    @Test
    public void playCardNull_deckNewTopCard() {
        Card origTopCard = deck.peek();
        uno.playCard(null, null);
        assertNotEquals(origTopCard, deck.peek());
    }

    @Test
    public void playCardNull_deck1CardLeft_deckNewTopCardNotNull() {
        deck.pop();
        uno.playCard(null, null);
        assertNotNull(deck.peek());
    }

    @Test
    public void playCardNull_deck1CardLeft_deckNewTopCard() {
        deck.pop();
        Card origTopCard = deck.peek();
        uno.playCard(null, null);
        assertNotEquals(origTopCard, deck.peek());
    }

    @Test
    public void playCardNull_deck1CardLeft_discardSame() {
        deck.pop();
        Card origDiscardTop = discard.peek();
        uno.playCard(null, null);
        assertEquals(origDiscardTop, discard.peek());
    }

    @Test
    public void playCardNull_deck1CardLeft_shuffleCalledAtLeastOnce() {
        deck = spy(deck);
        deck.pop();
        uno = new Uno(players, deck, discard, 0, false);
        uno.playCard(null, null);
        verify(deck, atLeastOnce()).shuffle();
    }

    @Test
    public void playCard_redSkip_skipsPlayer() {
        Card redSkip = new Card(Card.RED, Card.SKIP);
        uno.getCurrentPlayer().getHand().set(0, redSkip);
        uno.playCard(redSkip, "");

        assertEquals(p3, uno.getCurrentPlayer());
    }

    @Test
    public void playCard_redSkipReverse_skipsPlayer() {
        Card redSkip = new Card(Card.RED, Card.SKIP);
        reversedUno.getCurrentPlayer().getHand().set(0, redSkip);
        reversedUno.playCard(redSkip, "");

        assertEquals(p2, reversedUno.getCurrentPlayer());
    }

    @Test
    public void playCard_redSkip_skippedPlayerHandSizeNoChange() {
        int p2HandSizeOriginal = p2.getHand().size();
        Card redSkip = new Card(Card.RED, Card.SKIP);
        uno.getCurrentPlayer().getHand().set(0, redSkip);
        uno.playCard(redSkip, "");
        assertEquals(p2HandSizeOriginal, p2.getHand().size());
    }

    @Test
    public void playCard_redSkipReverse_skippedPlayerHandSizeNoChange() {
        int p3HandSizeOriginal = p3.getHand().size();
        Card redSkip = new Card(Card.RED, Card.SKIP);
        reversedUno.getCurrentPlayer().getHand().set(0, redSkip);
        reversedUno.playCard(redSkip, "");
        assertEquals(p3HandSizeOriginal, p3.getHand().size());
    }

    @Test
    public void playCard_redDraw2_skipsPlayer() {
        Card redDraw2 = new Card(Card.RED, Card.DRAW_2);
        uno.getCurrentPlayer().getHand().set(0, redDraw2);
        uno.playCard(redDraw2, "");

        assertEquals(p3, uno.getCurrentPlayer());
    }

    @Test
    public void playCard_redDraw2Reverse_skipsPlayer() {
        Card redDraw2 = new Card(Card.RED, Card.DRAW_2);
        reversedUno.getCurrentPlayer().getHand().set(0, redDraw2);
        reversedUno.playCard(redDraw2, "");

        assertEquals(p2, reversedUno.getCurrentPlayer());
    }

    @Test
    public void playCard_redDraw2_skippedPlayerHandSizePlusTwo() {
        int p2HandSizeOriginal = p2.getHand().size();
        Card redDraw2 = new Card(Card.RED, Card.DRAW_2);
        uno.getCurrentPlayer().getHand().set(0, redDraw2);
        uno.playCard(redDraw2, "");
        assertEquals(p2HandSizeOriginal + 2, p2.getHand().size());
    }

    @Test
    public void playCard_redDraw2Reversed_skippedPlayerHandSizePlusTwo() {
        int p3HandSizeOriginal = p3.getHand().size();
        Card redDraw2 = new Card(Card.RED, Card.DRAW_2);
        reversedUno.getCurrentPlayer().getHand().set(0, redDraw2);
        reversedUno.playCard(redDraw2, "");
        assertEquals(p3HandSizeOriginal + 2, p3.getHand().size());
    }

    @Test
    public void playCard_draw4_skipsPlayer() {
        Card draw4 = new Card(Card.WILD, Card.WILD_DRAW_4);
        uno.getCurrentPlayer().getHand().set(0, draw4);
        uno.playCard(draw4, Card.GREEN);

        assertEquals(p3, uno.getCurrentPlayer());
    }

    @Test
    public void playCard_draw4Reverse_skipsPlayer() {
        Card draw4 = new Card(Card.WILD, Card.WILD_DRAW_4);
        reversedUno.getCurrentPlayer().getHand().set(0, draw4);
        reversedUno.playCard(draw4, Card.GREEN);

        assertEquals(p2, reversedUno.getCurrentPlayer());
    }

    @Test
    public void playCard_draw4_skippedPlayerHandSizePlusFour() {
        int p2HandSizeOriginal = p2.getHand().size();
        Card draw4 = new Card(Card.WILD, Card.WILD_DRAW_4);
        uno.getCurrentPlayer().getHand().set(0, draw4);
        uno.playCard(draw4, Card.GREEN);
        assertEquals(p2HandSizeOriginal + 4, p2.getHand().size());
    }

    @Test
    public void playCard_draw4Reversed_skippedPlayerHandSizePlusFour() {
        int p3HandSizeOriginal = p3.getHand().size();
        Card draw4 = new Card(Card.WILD, Card.WILD_DRAW_4);
        reversedUno.getCurrentPlayer().getHand().set(0, draw4);
        reversedUno.playCard(draw4, Card.GREEN);
        assertEquals(p3HandSizeOriginal + 4, p3.getHand().size());
    }

    @Test
    public void playCard_wildGreen_setsPlayableColorCorrectly() {
        Card wild = new Card(Card.WILD, Card.WILD);
        uno.getCurrentPlayer().getHand().set(0, wild);
        uno.playCard(wild, Card.GREEN);
        assertEquals(Card.GREEN, uno.getTopDiscard().getColor());
    }

    @Test
    public void playCard_wildDraw4Green_setsPlayableColorCorrectly() {
        Card wild = new Card(Card.WILD_DRAW_4, Card.WILD);
        uno.getCurrentPlayer().getHand().set(0, wild);
        uno.playCard(wild, Card.GREEN);
        assertEquals(Card.GREEN, uno.getTopDiscard().getColor());
    }

    @Test
    public void simpleConstructor_4Players_correctNumberOfPlayers() {
        uno = new Uno(4);
        assertEquals(4, uno.getPlayers().size());
    }

    @Test
    public void simpleConstructor_7Players_correctNumberOfPlayers() {
        uno = new Uno(7);
        assertEquals(7, uno.getPlayers().size());
    }

    @Test
    public void simpleConstructor_4Players_7CardsInHands() {
        uno = new Uno(4);
        assertEquals(7, uno.getCurrentPlayer().getHand().size());
        assertEquals(7, uno.getNextPlayer().getHand().size());
    }

    @Test
    public void simpleConstructor_4Players_discardNotEmpty() {
        uno = new Uno(4);
        assertNotNull(uno.getTopDiscard());
    }

}
