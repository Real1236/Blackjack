public class Card {
    private String rank;
    private String suit;
    private int value;

    // Constructor
    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }

    // For printing a card in the console
    public String toString() {
        return rank + "-" + suit;
    }

    // Getters
    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
