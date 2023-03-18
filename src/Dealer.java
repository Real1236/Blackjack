public class Dealer {
    private Hand dealerHand = new Hand();

    // Adding a card to the dealer's  hand
    public void addCardDealer(Card card) {
        dealerHand.addCard(card);
    }

    // Deals initial hand
    public void dealHand(Shoe shoe) {
        addCardDealer(shoe.drawCard());
        addCardDealer(shoe.drawCard());
    }

    // Drawing cards until hitting a hand value above 16
    public void dealDealerHand(Shoe shoe) {
        while (dealerHand.checkHandValue() < 17) {
            Card cardDrawn = shoe.drawCard();
            dealerHand.addCard(cardDrawn);

            if (dealerHand.getHand().size() == 3)
                System.out.println("\nDealer draws: " + cardDrawn.toString());
            else
                System.out.println("Dealer draws: " + cardDrawn.toString());
        }
    }

    // Resetting round
    public void reset() {
        dealerHand.clearHand();
    }

    // Getters
    public Hand getDealerHand() {
        return dealerHand;
    }
}
