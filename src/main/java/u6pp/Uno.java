package u6pp;

import java.util.ArrayList;

public class Uno {
    ArrayList<Player> player;
    CardStack deck;
    CardStack discard;
    int playerIndex;
    boolean reversed;
    Uno(ArrayList<Player> inPlayer, CardStack inDeck, CardStack inDiscard, int inPlayerIndex, boolean inReversed){
        player = inPlayer;
        deck = inDeck;
        discard = inDiscard;
        playerIndex = inPlayerIndex;
        reversed = inReversed;
    }
    public Uno(int PI) {
        playerIndex = PI;
        reversed = false;
        for (int i = 0; i < playerIndex; i++){
            Player myPlayer = new Player("" + i);
            player.add(myPlayer);
        }
    }
    Player getCurrentPlayer(){
        return player.get(playerIndex);
    }
    Player getNextPlayer(){
        int add = 1;
        if (reversed == true){
            add *= -1;
        }
        if (playerIndex + add >= player.size()){
            return player.get(0);
        }
        else if (playerIndex + add < 0){
            return player.get(player.size() - 1);
        }
        return player.get(playerIndex + add);
    }
    Card getTopDiscard(){
        return discard.deck.get(discard.deck.size()-1);
    }
    Player getWinner(){
        for (Player p : player){
            if (p.getHand().isEmpty() == true){
                return p;
            }
        }
        return null;
    }
    boolean playCard(Card c, String s){
        boolean temp = false;
        if (c == null){
            getCurrentPlayer().getHand().add(deck.pop());
            deck.shuffle();
            if(deck.peek() == null){
                deck.shuffle();
            }
            int add = 1;
            if (reversed == true){
                add *= -1;
            }
            if (playerIndex + add >= player.size()){
                playerIndex = 0;
            }
            else if (playerIndex + add < 0){
                playerIndex = player.size() - 1;
            }
            else{
                playerIndex = playerIndex + add;
            }
            deck.shuffle();
            // nextPlayer(c);
        }
        for (Card h : getCurrentPlayer().getHand()){
            if (c == h){
                temp = true;
            }
        }
        if (temp == false){
            return false;
        }
        
        if (c.canPlayOn(discard.peek())){
            discard.push(c);
            getCurrentPlayer().getHand().remove(c);
            nextPlayer(c);
            
            return true; 
        }
        
            return false;
    }


    void nextPlayer(Card c){
        int add = 1;
        if (c.getValue() == Card.REVERSE && reversed == true){
            add = 1;
        }
        else if (c.getValue() == Card.REVERSE || reversed == true){
            add *= -1;
        }
        if (c.getValue() == Card.SKIP && reversed == true){
            add = 2;
        }
        if (playerIndex + add >= player.size()){
            playerIndex = 0;
        }
        else if (playerIndex + add < 0){
            playerIndex = player.size() - 1;
        }
        else{
            playerIndex = playerIndex + add;
        }
    }
    public ArrayList<Player> getPlayers() {
        return player;
    }
}