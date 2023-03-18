import java.util.ArrayList;
import java.util.Collections;

public class Shoe {
    private ArrayList<Card> shoe = new ArrayList<Card>();
    private int decks;  // Initial number of decks
    private double numOfDecks;  // Running count of the number of decks remaining

    // Constructor, populates shoe with a given number of decks
    public Shoe(int numOfDecks) {
        this.numOfDecks = decks = numOfDecks;
        // shuffle();
        testing();
    }

    // Resets and randomizes the order of the cards in the shoe
    public void shuffle() {
        // Populating shoe with decks
        Deck deck = new Deck();
        for (int i = 0; i < numOfDecks; i++) {
            shoe.addAll(deck.getDeck());
        }

        Collections.shuffle(shoe);
    }

    // Removes first card in shoe and returns it
    public Card drawCard() {
        // Removing the first card in the shoe
        Card temp = shoe.get(0);
        shoe.remove(0);

        // Updating count of cards and decks
        numOfDecks = (double) shoe.size() / 52;

        // Returning first card
        return temp;
    }

    public int getDecks() {
        return decks;
    }

    public double getNumOfDecks() {
        return numOfDecks;
    }

    /* Testing */
    public void testing() {
        // Testing
//        testingSplits();
//        testSoftTotals();
//        testHardTotals();
        Card three = new Card("Three", "", 3);
        Card ace = new Card("Ace", "", 11);

        // Player hand
        shoe.add(ace);
        shoe.add(ace);

        // Dealer hand
        shoe.add(three);
        shoe.add(three);

        // Hand 1
        shoe.add(three);
        shoe.add(three);

        // Populating shoe with decks
        Deck deck = new Deck();
        for (int i = 0; i < numOfDecks; i++) {
            shoe.addAll(deck.getDeck());
        }
    }

    // Pair splitting
    public void testingSplits() {
        Card ace = new Card("Ace", "", 11);
        Card two = new Card("Two", "", 2);
        Card three = new Card("Three", "", 3);
        Card four = new Card("Four", "", 4);
        Card five = new Card("Five", "", 5);
        Card six = new Card("Six", "", 6);
        Card seven = new Card("Seven", "", 7);
        Card eight = new Card("Eight", "", 8);
        Card nine = new Card("Nine", "", 9);
        Card ten = new Card("Ten", "", 10);

        // Array of cards
        Card[] cardArray = new Card[] { two, three, four, five, six, seven, eight, nine, ten, ace };

        // Aces
        for (int j = 0; j < 10; j++) {
            shoe.add(ace);
            shoe.add(ace);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            shoe.add(cardArray[j]);
            shoe.add(ten);
            shoe.add(ten);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Tens
        for (int j = 0; j < 10; j++) {
            shoe.add(ten);
            shoe.add(ten);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Nines
        for (int j = 0; j < 10; j++) {
            shoe.add(nine);
            shoe.add(nine);

            // Down card
            if (j < 5) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(ten);
            } else if (j == 5) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
            } else if (j == 6) {
                shoe.add(eight);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(ten);
            } else if (j == 7) {
                shoe.add(seven);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(ten);
            } else if (j == 8) {
                shoe.add(six);
                shoe.add(cardArray[j]);
            } else {
                shoe.add(five);
                shoe.add(cardArray[j]);
            }

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Eights
        for (int j = 0; j < 10; j++) {
            shoe.add(eight);
            shoe.add(eight);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            shoe.add(cardArray[j]);
            shoe.add(ten);
            shoe.add(ten);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Sevens
        for (int j = 0; j < 10; j++) {
            shoe.add(seven);
            shoe.add(seven);

            // Down card
            if (j < 6) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(ten);
            } else if (j == 6) {
                shoe.add(eight);
                shoe.add(cardArray[j]);
                shoe.add(six);
            } else if (j == 7) {
                shoe.add(seven);
                shoe.add(cardArray[j]);
                shoe.add(six);
            } else if (j == 8) {
                shoe.add(six);
                shoe.add(cardArray[j]);
                shoe.add(six);
            } else {
                shoe.add(five);
                shoe.add(cardArray[j]);
                shoe.add(six);
            }

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Sixes
        for (int j = 0; j < 10; j++) {
            shoe.add(six);
            shoe.add(six);

            // Down card
            if (j < 5) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(ten);
            } else if (j == 5) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(six);
            } else if (j == 6) {
                shoe.add(eight);
                shoe.add(cardArray[j]);
                shoe.add(six);
            } else if (j == 7) {
                shoe.add(seven);
                shoe.add(cardArray[j]);
                shoe.add(six);
            } else if (j == 8) {
                shoe.add(six);
                shoe.add(cardArray[j]);
                shoe.add(six);
            } else {
                shoe.add(five);
                shoe.add(cardArray[j]);
                shoe.add(six);
            }

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Fives
        for (int j = 0; j < 10; j++) {
            shoe.add(five);
            shoe.add(five);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);
            shoe.add(ten);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Fours
        for (int j = 0; j < 10; j++) {
            shoe.add(four);
            shoe.add(four);

            // Down card
            if ((j < 3) || (j == 5)) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
            } else if ((j == 3) || (j == 4)) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
            } else if (j == 6) {
                shoe.add(eight);
                shoe.add(cardArray[j]);
            } else if (j == 7) {
                shoe.add(seven);
                shoe.add(cardArray[j]);
            } else if (j == 8) {
                shoe.add(six);
                shoe.add(cardArray[j]);
            } else {
                shoe.add(five);
                shoe.add(cardArray[j]);
            }

            // Up card
            shoe.add(ten);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Threes
        for (int j = 0; j < 10; j++) {
            shoe.add(three);
            shoe.add(three);

            // Down card
            if (j < 5) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(ten);
            } else if (j == 5) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(seven);
                shoe.add(ten);
                shoe.add(seven);
            } else if (j == 6) {
                shoe.add(eight);
                shoe.add(cardArray[j]);
                shoe.add(two);
                shoe.add(ten);
            } else if (j == 7) {
                shoe.add(seven);
                shoe.add(cardArray[j]);
                shoe.add(two);
                shoe.add(ten);
            } else if (j == 8) {
                shoe.add(six);
                shoe.add(cardArray[j]);
                shoe.add(two);
                shoe.add(ten);
            } else {
                shoe.add(five);
                shoe.add(cardArray[j]);
                shoe.add(two);
                shoe.add(ten);
            }

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Twos
        for (int j = 0; j < 10; j++) {
            shoe.add(two);
            shoe.add(two);

            // Down card
            if (j < 2) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(seven);
                shoe.add(ten);
                shoe.add(seven);
            } else if (j < 5) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(ten);
            } else if (j == 5) {
                shoe.add(nine);
                shoe.add(cardArray[j]);
                shoe.add(ten);
                shoe.add(seven);
                shoe.add(ten);
                shoe.add(seven);
            } else if (j == 6) {
                shoe.add(eight);
                shoe.add(cardArray[j]);
                shoe.add(three);
                shoe.add(ten);
            } else if (j == 7) {
                shoe.add(seven);
                shoe.add(cardArray[j]);
                shoe.add(three);
                shoe.add(ten);
            } else if (j == 8) {
                shoe.add(six);
                shoe.add(cardArray[j]);
                shoe.add(three);
                shoe.add(ten);
            } else {
                shoe.add(five);
                shoe.add(cardArray[j]);
                shoe.add(three);
                shoe.add(ten);
            }

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }
    }

    private void dealDealersLastCard(Card five, Card six, Card seven, Card eight, Card nine, Card ten, int j) {
        if (j == 0)
            shoe.add(ten);
        else if (j == 1)
            shoe.add(nine);
        else if (j == 2)
            shoe.add(eight);
        else if (j == 3)
            shoe.add(seven);
        else if (j == 4)
            shoe.add(six);
        else
            shoe.add(five);
    }

    // Soft Totals
    public void testSoftTotals() {
        Card ace = new Card("Ace", "", 11);
        Card two = new Card("Two", "", 2);
        Card three = new Card("Three", "", 3);
        Card four = new Card("Four", "", 4);
        Card five = new Card("Five", "", 5);
        Card six = new Card("Six", "", 6);
        Card seven = new Card("Seven", "", 7);
        Card eight = new Card("Eight", "", 8);
        Card nine = new Card("Nine", "", 9);
        Card ten = new Card("Ten", "", 10);

        // Array of cards
        Card[] cardArray = new Card[] { two, three, four, five, six, seven, eight, nine, ten, ace };

        // Two - Seven
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                shoe.add(ace);
                shoe.add(cardArray[i]);

                // Down card
                if (j < 6)
                    shoe.add(nine);
                else if (j == 6)
                    shoe.add(eight);
                else if (j == 7)
                    shoe.add(seven);
                else if (j == 8)
                    shoe.add(six);
                else
                    shoe.add(five);

                // Up card
                shoe.add(cardArray[j]);

                // Hand
                if (cardArray[i].getRank().equals("Six"))
                    shoe.add(ace);
                else if (cardArray[i].getRank().equals("Seven")) {
                    if ((j != 5) && (j != 6)) {
                        shoe.add(ace);
                    }
                }
                else {
                    int lastCard = 9 - cardArray[i].getValue(); // Finding difference to make soft 19
                    lastCard -= 2;  // adjusting for array
                    shoe.add(cardArray[lastCard]);
                }

                // Dealer's last card
                dealDealersLastCard(five, six, seven, eight, nine, ten, j);
            }
        }

        // Seven - doubling down
        for (int j = 0; j < 5; j++) {
            shoe.add(three);
            shoe.add(four);

            // Dealers card
            shoe.add(nine);
            shoe.add(cardArray[j]);

            // Hand
            shoe.add(ace);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Eight
        for (int j = 0; j < 10; j++) {
            shoe.add(ace);
            shoe.add(eight);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Hand
            if (j == 4)
                shoe.add(ace);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Eight - doubling down
        shoe.add(two);
        shoe.add(six);

        shoe.add(nine); // Dealer cards
        shoe.add(six);

        shoe.add(ace);  // Hand

        shoe.add(six);  // Dealer's last card

        // Nine
        for (int j = 0; j < 10; j++) {
            shoe.add(ace);
            shoe.add(nine);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }
    }

    // Hard Totals
    public void testHardTotals() {
        Card ace = new Card("Ace", "", 11);
        Card two = new Card("Two", "", 2);
        Card three = new Card("Three", "", 3);
        Card four = new Card("Four", "", 4);
        Card five = new Card("Five", "", 5);
        Card six = new Card("Six", "", 6);
        Card seven = new Card("Seven", "", 7);
        Card eight = new Card("Eight", "", 8);
        Card nine = new Card("Nine", "", 9);
        Card ten = new Card("Ten", "", 10);

        // Array of cards
        Card[] cardArray = new Card[] { two, three, four, five, six, seven, eight, nine, ten, ace };

        // Eight
        for (int j = 0; j < 10; j++) {
            shoe.add(two);
            shoe.add(six);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Hand
            shoe.add(nine);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Nine
        for (int j = 0; j < 10; j++) {
            shoe.add(two);
            shoe.add(seven);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Hand
            shoe.add(nine);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Nine - hitting instead of doubling down
        for (int j = 0; j < 4; j++) {
            shoe.add(two);
            shoe.add(three);

            // Down card
            shoe.add(nine);

            // Up card
            shoe.add(cardArray[j+1]);

            // Hand
            shoe.add(four);
            shoe.add(nine);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j+1);
        }

        // Ten
        for (int j = 0; j < 10; j++) {
            shoe.add(two);
            shoe.add(eight);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Hand
            shoe.add(nine);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Ten - hitting instead of doubling down
        for (int j = 0; j < 8; j++) {
            shoe.add(two);
            shoe.add(four);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else
                shoe.add(seven);

            // Up card
            shoe.add(cardArray[j]);

            // Hand
            shoe.add(four);
            shoe.add(nine);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Eleven
        for (int j = 0; j < 10; j++) {
            shoe.add(two);
            shoe.add(nine);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Hand
            shoe.add(nine);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Eleven - hitting instead of doubling down
        for (int j = 0; j < 10; j++) {
            shoe.add(two);
            shoe.add(five);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Hand
            shoe.add(four);
            shoe.add(nine);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Twelve
        for (int j = 0; j < 10; j++) {
            shoe.add(two);
            shoe.add(ten);

            // Down card
            if (j < 6)
                shoe.add(nine);
            else if (j == 6)
                shoe.add(eight);
            else if (j == 7)
                shoe.add(seven);
            else if (j == 8)
                shoe.add(six);
            else
                shoe.add(five);

            // Up card
            shoe.add(cardArray[j]);

            // Hand
            if ((j < 2) || (j > 4))
                shoe.add(five);

            // Dealer's last card
            dealDealersLastCard(five, six, seven, eight, nine, ten, j);
        }

        // Thirteen to Sixteen
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                shoe.add(cardArray[i+1]);
                shoe.add(ten);

                // Down card
                if (j < 6)
                    shoe.add(nine);
                else if (j == 6)
                    shoe.add(eight);
                else if (j == 7)
                    shoe.add(seven);
                else if (j == 8)
                    shoe.add(six);
                else
                    shoe.add(five);

                // Up card
                shoe.add(cardArray[j]);

                // Hand
                if ((i == 3) && (j > 4))
                    shoe.add(four);
                else if (j > 4)
                    shoe.add(five);

                // Dealer's last card
                dealDealersLastCard(five, six, seven, eight, nine, ten, j);
            }
        }
    }
}
