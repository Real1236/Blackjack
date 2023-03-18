public class playerHand extends Hand {
    private int bet;
    private boolean stand = false;
    private boolean doubleDown = false;
    private boolean softTotal = false;

    // Removes all cards in the hand and sets variables to their default values
    public void clearHand() {
        hand.clear();
        bust = false;
        blackjack = false;
        charlie = false;
        bet = 0;
        stand = false;
        doubleDown = false;
        softTotal = false;
    }

    // Checks if hand is a soft total
    public boolean checkSoftTotal() {
        // definition of a soft hand which is any hand in blackjack that contains an ace counted as 11
        for (Card card : hand) {
            if (card.getValue() == 11) {
                softTotal = true;
                break;
            }
            softTotal = false;
        }
        return softTotal;
    }

    // Getters and setters
    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public boolean isStand() {
        return stand;
    }

    public void setStand(boolean stand) {
        this.stand = stand;
    }

    public boolean isDoubleDown() {
        return doubleDown;
    }

    public void setDoubleDown(boolean doubleDown) {
        this.doubleDown = doubleDown;
    }
}
