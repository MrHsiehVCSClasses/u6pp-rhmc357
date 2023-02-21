package u6pp;

import java.util.ArrayList;
import java.util.Random;



public class CardStack{
    ArrayList<Card> deck = new ArrayList<Card>();
    public CardStack() {
        
    }
    boolean isEmpty(){
        if (deck.size() == 0){
            return true;
        }
        return false;
    }
    Card peek(){
        if (deck.isEmpty() == true){
            return null;
        }
        return deck.get(deck.size()-1);
    }
    public void push(Card card) {
        deck.add(card);
    }
    int getSize(){
        return deck.size() ;
    }
    Card pop(){
        Card temp = deck.get(deck.size()-1);
        deck.remove(deck.get(deck.size()-1));
        return temp;
    }
    void clear(){
        while(deck.isEmpty() == false){
            deck.remove(0);
        }
    }
    void addAll(CardStack stack){
        if(deck .equals(stack.deck)){
            return;
        }
        while(stack.isEmpty() == false){
            deck.add(stack.deck.get(0));
            stack.deck.remove(0);
        }
    }
    void shuffle(){
        for (int i = 0; i < deck.size(); i++){
            Random myRandom = new Random();
            int ran;
            
            ran = myRandom.nextInt(deck.size());
            
            Card temp;
            temp = deck.get(ran);
            deck.set(ran, deck.get(i));
            deck.set(i, temp);
        }
    }
}
