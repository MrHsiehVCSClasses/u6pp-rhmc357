package u6pp;

import java.util.ArrayList;

public class Player {
    String name;
    ArrayList<Card> hand = new ArrayList<Card>();
    Player(String pName){
        name = pName;
    }
    String getName(){
        return name;
    }
    ArrayList<Card> getHand(){
        return hand;
    }
}