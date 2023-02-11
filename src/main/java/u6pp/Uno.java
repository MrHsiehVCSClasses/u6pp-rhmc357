package u6pp;

import java.util.ArrayList;

public class Uno {

    private ArrayList<Player> players;
    private CardStack deck;
    private CardStack discard;
    private int currentActivePlayerIndex = 0;
    private boolean reversed = false;

    public Uno(int numPlayers) {
        // init new game
        players = new ArrayList<Player>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        deck = CardStack.createUnoDeck();
        deck.shuffle();
        discard = new CardStack();
        discard.push(deck.pop());
        for (int i = 0; i < 7; i++) {
            for (Player p : players) {
                p.getHand().add(deck.pop());
            }
        }
    }

    public Uno(ArrayList<Player> players,
            CardStack deck,
            CardStack discard,
            int currentActivePlayerIndex,
            boolean reversed) {
        this.players = players;
        this.deck = deck;
        this.discard = discard;
        this.currentActivePlayerIndex = currentActivePlayerIndex;
        this.reversed = reversed;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Card getTopDiscard() {
        if (discard.getSize() > 0) {
            return discard.peek();
        }
        return null;
    }

    public boolean playCard(Card cardToPlay, String wildColor) {

        if (cardToPlay == null) {
            drawCards(1);
            endCurrentTurn();
            return true;
        }

        if (!getCurrentPlayer().getHand().contains(cardToPlay)) {
            return false;
        }
        if (!cardToPlay.canPlayOn(getTopDiscard())) {
            return false;
        }

        if (cardToPlay.getValue().equals(Card.WILD) ||
                cardToPlay.getValue().equals(Card.WILD_DRAW_4)) {
            if (!cardToPlay.trySetColor(wildColor)) {
                return false;
            }
        }

        // do the playing of the card
        getCurrentPlayer().getHand().remove(cardToPlay);
        discard.push(cardToPlay);

        // apply card effects (current player effects)
        if (cardToPlay.getValue().equals(Card.REVERSE)) {
            reversed = !reversed;
        }

        // end current player turn
        endCurrentTurn(); // end current player turn

        // apply card effects (next player effects)
        if (cardToPlay.getValue().equals(Card.SKIP)) {
            endCurrentTurn(); // do the skip
        } else if (cardToPlay.getValue().equals(Card.DRAW_2)) {
            drawCards(2);
            endCurrentTurn(); // skip
        } else if (cardToPlay.getValue().equals(Card.WILD_DRAW_4)) {
            drawCards(4);
            endCurrentTurn(); // skip
        }

        return true;
    }

    private void drawCards(int numCards) {
        for (int i = 0; i < numCards; i++) {
            getCurrentPlayer().getHand().add(deck.pop());
            if (deck.isEmpty()) {
                Card discardTop = discard.pop();

                while (!discard.isEmpty()) {
                    deck.push(discard.pop());
                }
                deck.shuffle();
                discard.push(discardTop);
            }
        }
    }

    private void endCurrentTurn() {
        if (reversed) {
            currentActivePlayerIndex -= 1;
            if (currentActivePlayerIndex < 0) {
                currentActivePlayerIndex = players.size() - 1;
            }
        } else {
            currentActivePlayerIndex += 1;
            currentActivePlayerIndex %= players.size();
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentActivePlayerIndex);
    }

    public Player getNextPlayer() {
        int nextPlayerIndex;
        if (reversed) {
            nextPlayerIndex = currentActivePlayerIndex - 1;
            if (nextPlayerIndex < 0) {
                nextPlayerIndex = players.size() - 1;
            }
        } else {
            nextPlayerIndex = currentActivePlayerIndex + 1;
            nextPlayerIndex %= players.size();
        }

        return players.get(nextPlayerIndex);
    }

    public Player getWinner() {
        for (Player p : players) {
            if (p.getHand().size() == 0) {
                return p;
            }
        }
        return null;
    }

}