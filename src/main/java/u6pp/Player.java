package u6pp;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private String name;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public String toString() {
        return "Player: " + name + "; Hand size: " + hand.size();
    }
}
