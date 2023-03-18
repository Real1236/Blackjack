import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();

    // Constructor, populates deck with 52 cards
    public Deck() {
        // Ranks and suits
        String[] rank =
                {
                        "Two", "Three", "Four", "Five",
                        "Six", "Seven", "Eight", "Nine", "Ten",
                        "Jack", "Queen", "King", "Ace"
                };
        String[] suit = { "Spades", "Hearts", "Clubs", "Diamonds"  };

        // Populating cards 2 - 9
        for(int i = 0; i < 8; i++) {
            for (String s : suit) {
                deck.add(new Card(rank[i], s, i + 2));
            }
        }

        // Populating cards 10 - King
        for(int i = 8; i < rank.length-1; i++){
            for (String s : suit) {
                deck.add(new Card(rank[i], s, 10));
            }
        }

        // Populating aces
        for (String s : suit) {
            deck.add(new Card(rank[rank.length - 1], s, 11));
        }
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
