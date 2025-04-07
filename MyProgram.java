public class MyProgram
{
    public static void main(String[] args)
    {
        int numberOfPlayers = 10;
        Game game = new Game(numberOfPlayers);
        game.playGame();
    }
}
import java.util.*;

public class Deck 
{
    private ArrayList<Card> deck;
    
    public Deck() {
        deck = new ArrayList<>(); 
        createDeck();
        shuffleDeck();
    }
    
    // used for grader, do not modify
    public Deck(ArrayList<Card> cards) {
        this.deck = cards;
    }
    
    private void createDeck() {
        
        for (int i = 0; i < 52; i++) {
            deck.add(new Card(i));
        }
    }
    public Card dealCard(){
             if(deck.size() == 0){
         return null;
     }
     Card card = deck.remove(0);

     return card;
     
    }
    public int getDeckSize(){
        return deck.size();
    }
    
    public void shuffleDeck() {
        
        for (int i = 0; i < deck.size(); i++) {
            int random = (int) (Math.random() * deck.size());
            
            Card swap = deck.get(i);
            deck.set(i, deck.get(random));
            deck.set(random, swap);
        }
    }
    
    public void show() {
        
        for (int i = 0; i < deck.size(); i++) {
            if (i != 0 && i % 13 == 0) {
                System.out.println();
            }
            System.out.print(deck.get(i) + " ");
        }
        System.out.println();
    }
} 
public class Card implements Comparable<Card> {

    private int value;   
    private String face;
    private String suit;
    private String symbol;

    public Card(int n) {
        String[] faces   = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits   = {"S", "C", "H", "D"};
        String[] symbols = {"\u2660", "\u2663", "\u2665", "\u2666"};

        value  = n % 13 + 1;
        face   = faces[n % 13];
        suit   = suits[n / 13 % 4];
        symbol = symbols[n / 13 % 4];
        
        if (value > 10) {
            value = 10;
        } else if (value == 1) {
            value = 11;
        }
    }
    
    public int compareTo(Card other) {
        if (this.value == 10 && other.value == 10) {
            if (this.face.equals("Q") && other.face.equals("K")) {
                return -1;
            } else if (this.face.equals("K") && other.face.equals("Q")) {
                return 1;
            } else {
                return this.face.compareTo(other.face);
            }
        } else {
            return this.value - other.value;
        }
    }     
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int newValue) {
        value = newValue;
    }
    
    public String getSuit() {
        return suit;
    }
    
    public String toString() {
        String result = face + symbol;
        if (result.length() == 2){
            result = " " + result;
        }
        
        if (suit.equals("H") || suit.equals("D")) {
            return "\u001B[31m" + result + "\u001B[0m";
        } else {
            return result;
        }
    }
}
import java.util.*;

public class Player {

    private static int totalPlayers = 0;
    private int playerNum, handValue;
    private String outcome;
    private ArrayList<Card> hand;
    private Card faceUpCard;
    
    public Player() {
        playerNum = totalPlayers++;
        handValue = 0;
        outcome = "";
        hand = new ArrayList<>();
    }
    
    public void addCard(Card c) {
        hand.add(c);
        handValue += c.getValue();
        if(hand.size() == 1){
        faceUpCard = c; 
        }
        Collections.sort(hand);
        if(handValue > 21 && hand.get(hand.size() - 1).getValue() == 11){
            hand.get(hand.size() - 1).setValue(1);
                   handValue -= 10; 
                   hand.add(0, hand.remove(hand.size() - 1));
                }
            }
    
    public Card getFaceUpCard(){
        return faceUpCard;
    }
    public boolean hit(Card dealersFaceUpCard){
       if(playerNum == 0){
           return handValue < 17;
       }else{
           int val = dealersFaceUpCard.getValue();
          if( handValue < 12){
               return true;
           }
           if(handValue == 12 && val < 4){
               return true;
           }
           if(handValue > 16){
               return false;
           }
           if(val > 6){
               return true;
           }
       }
       return false;
    }
    
    public int getHandValue() {
        return handValue;
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public static void resetTotalPlayers() {
        totalPlayers = 0;
    }
    public void setOutcome(String outcome){
        this.outcome = outcome;
    }
    public String getOutcome(){
        return outcome;
    }
    public boolean hasBlackJack(){
        return hand.size() == 2 && handValue == 21;
    }
    
    public String toString() {
        if (playerNum == 0) {
            return "Dealer  : " + handValue + " " + hand;
        } else {
            return "Player " + playerNum + ": " + handValue + " " + hand;
        }
    }
}
public class Game {
    
    private Deck deck;
    private Player[] players;
    
    public Game(int numPlayers) {
        deck = new Deck();
        players = new Player[numPlayers];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
    }
    
    // used for grader only, do not modify
    public Game (Deck deck, Player[] players) {
        this.deck = deck;
        this.players = players;
    }
    
    public void playGame() {
        
    for(int i = 0; i < players.length; i++){
        players[i].addCard(deck.dealCard());
    }    
    for(int i = 0; i < players.length; i++){
        players[i].addCard(deck.dealCard());
    }
    
    
   for( int i = 0; i < players.length; i++){
        while(players[i].hit(players[0].getFaceUpCard())){
            players[i].addCard(deck.dealCard());
        }
    }
    
    
    for(int i = 1; i < players.length; i++){
        if(players[0].hasBlackJack() && !(players[i].hasBlackJack())){
            players[i].setOutcome("Lose");
        }else if(!(players[0].hasBlackJack()) && players[i].hasBlackJack()){
            players[i].setOutcome("Win");
        }
        else if(players[i].getHandValue() > 21){
            players[i].setOutcome("Lose");
        }
            else if(players[0].getHandValue() > 21){
                players[i].setOutcome("Win");
            }
        else if(players[i].getHandValue() == players[0].getHandValue()){
            players[i].setOutcome("Tie");
        }else if(players[i].getHandValue() > players[0].getHandValue() && players[i].getHandValue() <= 21){
            players[i].setOutcome("Win");
        }
            else{
            players[i].setOutcome("Lose");
        }
    }
    System.out.println("\t" + players[0]);
    for(int i = 1; i < players.length; i++){
        System.out.println(players[i].toString() + " " + players[i].getOutcome());
    }
    }

}
