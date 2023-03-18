import java.util.ArrayList;

public class Hand {
    protected ArrayList<Card> hand = new ArrayList<Card>();
    protected boolean bust = false;
    protected boolean blackjack = false;
    protected boolean charlie = false;

    // Adds given card to the hand
    public void addCard(Card card) {
        hand.add(card);
    }

    // Remove card
    public void removeCard(int index) {
        hand.remove(index);
    }

    // Adds up the value of the hand and returns it
    public int checkHandValue() {
        int value = 0;
        ArrayList<Integer> indexOfElevens = new ArrayList<Integer>();

        // Resetting value of all aces to be 11
        for (Card card : hand) {
            if (card.getValue() == 1) {
                card.setValue(11);
            }
        }

        // Getting index of each ace valued at 11
        for (int i = 0; i < hand.size(); i++) {
            value += hand.get(i).getValue();

            if (hand.get(i).getValue() == 11) {
                indexOfElevens.add(i);
            }
        }

        // Looping through all aces in the hand
        for (Integer indexOfEleven : indexOfElevens) {
            // If the value of the hand is greater than 21, the value of the ace is set to 1
            if (value > 21) {
                int index = indexOfEleven;
                hand.get(index).setValue(1);
                value -= 10;
            }
        }

        return value;
    }

    // Checks if hand is a bust
    public boolean isBust() {
        if (checkHandValue() > 21) {
            bust = true;
        }
        return bust;
    }

    // Checks if hand is a blackjack
    public boolean isBlackjack() {
        if ((checkHandValue() == 21) && (hand.size() == 2)) {
            blackjack = true;
        }
        return blackjack;
    }

    // Checks if hand is a ten-card Charlie
    public boolean isTenCardCharlie() {
        if ((hand.size() == 10) && !bust) {
            charlie = true;
        }
        return charlie;
    }

    // Removes all cards in the hand and sets variables to their default values
    public void clearHand() {
        hand.clear();
        bust = false;
        blackjack = false;
        charlie = false;
    }

    // Printing the hand in the console
    public String toString() {
        StringBuilder cardList = new StringBuilder();
        for(Card card : hand) {
            cardList.append("\n     ").append(card.toString());
        }
        return cardList.toString();
    }

    // Displaying hand with a hidden upCard
    public String toString(boolean hideUpCard) {
        StringBuilder cardList = new StringBuilder();
        cardList.append("\n     *********");
        for (int i = 1; i < hand.size(); i++) {
            cardList.append("\n     ").append(hand.get(i).toString());
        }
        return cardList.toString();
    }

    // Getter
    public ArrayList<Card> getHand() {
        return hand;
    }
}
