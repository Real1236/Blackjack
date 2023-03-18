import java.util.ArrayList;

public class Player {
    private int bankroll;
    private int insuranceBet;
    private boolean insurance;
    private boolean split;
    private ArrayList<playerHand> playerHands = new ArrayList<playerHand>();

    // Constructor, creates one hand in ArrayList
    public Player(int bankroll) {
        playerHands.add(new playerHand());
        this.bankroll = bankroll;
    }

    // Sets the "bet" variable of the respective hand, deducts the bet from player's bankroll
    public void bet (int betAmount) {
        bet(betAmount, 0);
    }
    public void bet(int betAmount, int hand) {
        playerHands.get(hand).setBet(betAmount);
        bankroll -= betAmount;
    }

    // Deals initial hand
    public void dealHand(Shoe shoe) {
        hit(shoe.drawCard());
        hit(shoe.drawCard());
    }

    // Adds a card to the respective hand
    public void hit(Card card) {
        hit(card, 0);
    }
    public void hit(Card card, int hand) {
        playerHands.get(hand).addCard(card);

        // Standing if hand value reaches 21
        if (playerHands.get(hand).checkHandValue() == 21) {
            stand(hand);
        }
    }

    // Setting the "stand" variable of a given hand to "true"
    public void stand(int hand) {
        playerHands.get(hand).setStand(true);
    }

    // Doubles the bet, deducts from bankroll, hits one more time, and stands
    public void doubleDown(Card card, int hand) {
        // Betting
        int betAmount = playerHands.get(hand).getBet();
        playerHands.get(hand).setBet(betAmount * 2);
        bankroll -= betAmount;

        // Hitting and standing
        playerHands.get(hand).setDoubleDown(true);
        hit(card, hand);
        stand(hand);
    }

    // Creates new hand in ArrayList, set bets, deal cards
    public void split(int hand) {
        // Creating new hands
        playerHands.add(hand+1, new playerHand());
        playerHands.get(hand+1).addCard(playerHands.get(hand).getHand().get(1));    // Assigning 2nd card to new hand
        playerHands.get(hand).removeCard(1);   // Removing 2nd card

        // Resetting the value of aces
        if (playerHands.get(0).getHand().get(0).getRank().equals("Ace")) {
            playerHands.get(0).getHand().get(0).setValue(11);
            playerHands.get(1).getHand().get(0).setValue(11);
        }

        bet(playerHands.get(hand).getBet(), hand+1);    // Setting bets
        split = true;   // Setting boolean variable
    }

    // Insurance
    public void insurance() {
        insuranceBet = playerHands.get(0).getBet() / 2;
        bankroll -= insuranceBet;
    }

    // Checking if player has blackjack
    public boolean blackjack(int hand) {
        return playerHands.get(hand).isBlackjack();
    }

    // Checking if specific hand busted
    public boolean bust(int hand) {
        return playerHands.get(hand).isBust();
    }

    // Checking if player has ten cards, stands if player gets ten cards and doesn't bust
    public void tenCardCharlie(int hand) {
        if (playerHands.get(hand).isTenCardCharlie())
            stand(hand);
        playerHands.get(hand).isTenCardCharlie();
    }

    // Push
    public void push(int hand) {
        bankroll += playerHands.get(hand).getBet();
    }

    // Winning the bet
    public void winBet(double multiplier, int hand) {
        bankroll += playerHands.get(hand).getBet() * multiplier;
    }

    // Winning insurance
    public void winInsurance() {
        bankroll += insuranceBet * 3;
    }

    // Resetting round
    public void reset() {
        playerHands.clear();
        playerHands.add(new playerHand());
        insuranceBet = 0;
        insurance = false;
        split = false;
    }

    // Getters
    public int getBankroll() {
        return bankroll;
    }

    public ArrayList<playerHand> getPlayerHands() {
        return playerHands;
    }

    public int getInsuranceBet() {
        return insuranceBet;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public boolean isSplit() {
        return split;
    }
}
