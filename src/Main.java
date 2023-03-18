import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Welcome message
        System.out.println("Welcome to Blackjack!");

        //Scanner for user input
        Scanner input = new Scanner(System.in);

        // Simulation or play?
        System.out.println("\nDo you want to (1) play or (2) simulate basic strategy?");
        int play = input.nextInt();

        // Asking number of decks and size of bankroll
        System.out.println("\nHow many decks do you want to play with?");
        int numOfDecks = input.nextInt();

        System.out.println("\nWhat size do you want your bankroll to be?");
        int bankroll = input.nextInt();

        // Creating stat tracker workbook
        StatsTracker workbook = new StatsTracker(bankroll);

        // Creating dealer, player, and shoe
        Dealer dealer = new Dealer();
        Player player = new Player(bankroll);
        Shoe shoe = new Shoe(numOfDecks);

        // Loop rounds while player has money
        while((player.getBankroll() > 0) && (workbook.getGameNumber() < 50000)) {
            // Playing manually
            if (play == 1) {
                startRound(player, dealer, shoe, workbook);    // Starting round

                // Checking for blackjack and insurance
                player.blackjack(0);
                insurance(player, dealer);

                // Player's play
                int hand = 0;
                while (hand < player.getPlayerHands().size()) { // Dealing with splits
                    if (!split(player, dealer, shoe, hand)) {
                        play(player, dealer, shoe, hand);
                        hand++;
                    }
                }
            }

            // Basic Strategy
            else {
                startRoundBS(player, dealer, shoe, workbook);   // Starting round

                player.blackjack(0);    // Checking for blackjack

                int hand = 0;
                while (hand < player.getPlayerHands().size()) { // Dealing with splits
                    if (!splitBS(player, dealer, shoe, hand)) {
                        playBS(player, dealer, shoe, hand);
                        hand++;
                    }
                }
            }

            // Checking winnings
            checkWinnings(player, dealer, shoe, workbook);

            // Resetting for next round
            reset(player, dealer, shoe, workbook);
        }

        //Game is over
        System.out.println("\nYou lost all your money. Game over.");

        //Close Scanner
        input.close();
    }

    /* Manually Playing */
    // Starting the turn, taking bets, dealing hands
    public static void startRound(Player player, Dealer dealer, Shoe shoe, StatsTracker workbook) {
        //Scanner for user input
        Scanner input = new Scanner(System.in);

        // Separating turns by printing a line
        System.out.println("\n---------------------------------------------------------");

        // Taking bet
        System.out.println("\nYou have $" + player.getBankroll() + ", how much would you like to bet?");
        int bet = input.nextInt();
        player.bet(bet);
        System.out.println("\nYou have $" + player.getBankroll());

        // Recording in Excel
        workbook.addGame(bet);

        // Dealing hands
        player.dealHand(shoe);
        dealer.dealHand(shoe);
    }

    // Splitting
    public static boolean split(Player player, Dealer dealer, Shoe shoe, int hand) {
        // If split, deal extra card until hand has a length of 2
        while (player.getPlayerHands().get(hand).getHand().size() < 2) {
            System.out.println("\nHAND NUMBER " + (hand+1) + "\n---------------------------");
            hit(player, shoe, hand);
        }

        // Player can't split a split hand
        if (player.getPlayerHands().size() < 2) {
            // Checking if two cards in hands are the same value or if they're both aces
            if ((player.getPlayerHands().get(hand).getHand().get(0).getValue()
                    == player.getPlayerHands().get(hand).getHand().get(1).getValue())
                    || ((player.getPlayerHands().get(hand).getHand().get(0).getRank().equals("Ace"))
                    && (player.getPlayerHands().get(hand).getHand().get(1).getRank().equals("Ace")))) {

                //Scanner for user input
                Scanner input = new Scanner(System.in);

                // Asking for input
                displayHands(player, dealer, true, hand);
                System.out.println("\nWould you like to split? (1)Yes or (2)No");
                int response = input.nextInt();

                // Split
                if (response == 1) {
                    player.split(hand);
                    return true;
                }
            }
        }

        return false;
    }

    // Rest of player's play, returns true if another play is needed
    public static void play(Player player, Dealer dealer, Shoe shoe, int hand) {
        // If player insures and dealer has blackjack or if player has blackjack, skip code
        if (!(player.isInsurance() && dealer.getDealerHand().isBlackjack()) && !player.blackjack(hand)) {
            //Scanner for user input
            Scanner input = new Scanner(System.in);

            // Asking for input
            displayHands(player, dealer, true, hand);

            // Only one card is drawn to split aces
            if ((player.getPlayerHands().size() == 2) && player.getPlayerHands().get(hand).getHand().get(0).getRank().equals("Ace")) {
                player.stand(hand);
            } else {
                System.out.println("\nWould you like to (1)Hit, (2)Stand, or (3)Double Down");
                int response = input.nextInt();

                // They hit
                if (response == 1) {
                    hit(player, shoe, hand);
                }

                // Stand
                else if (response == 2) {
                    player.stand(hand);
                }

                // Double down
                else if (response == 3) {
                    doubleDown(player, shoe, hand);
                    System.out.println("\nYou have $" + player.getBankroll());
                }

                // Looping until player stands or busts
                while (!player.getPlayerHands().get(hand).isStand() && !player.getPlayerHands().get(hand).isBust()) {   // Edit later when dealing with splits
                    // Asking for input
                    displayHands(player, dealer, true, hand);
                    System.out.println("\nWould you like to (1)Hit or (2)Stand");
                    response = input.nextInt();

                    // They hit
                    if (response == 1) {
                        hit(player, shoe, hand);    // Returns false if busted, returns true if hand didn't bust
                    }

                    // Stand
                    else if (response == 2) {
                        player.stand(hand);
                    }
                }
            }
        } else {
            displayHands(player, dealer, true, hand);
        }
    }

    /* Basic Strategy */
    public static void startRoundBS(Player player, Dealer dealer, Shoe shoe, StatsTracker workbook) {
        // Separating turns by printing a line
        System.out.println("\nGame number: " + (workbook.getGameNumber()+1));
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        // Taking bet
        System.out.println("\nYou have $" + player.getBankroll() + ", how much would you like to bet?");
        int bet = 10;
        player.bet(bet);
        System.out.println("\nYou have $" + player.getBankroll());

        // Recording in Excel
        workbook.addGame(bet);

        // Dealing hands
        player.dealHand(shoe);
        dealer.dealHand(shoe);
    }

    public static boolean splitBS(Player player, Dealer dealer, Shoe shoe, int hand) {
        // If split, deal extra card until hand has a length of 2
        while (player.getPlayerHands().get(hand).getHand().size() < 2) {
            System.out.println("\nHAND NUMBER " + (hand+1) + "\n---------------------------");
            hit(player, shoe, hand);
        }

        Card firstCard = player.getPlayerHands().get(hand).getHand().get(0);
        Card secondCard = player.getPlayerHands().get(hand).getHand().get(1);
        int upCard = dealer.getDealerHand().getHand().get(1).getValue();

        // Player can't split a split hand
        if (player.getPlayerHands().size() < 2) {
            // Checking if two cards in hands are the same value or if they're both aces
            if ((firstCard.getValue() == secondCard.getValue())
                    || ((firstCard.getRank().equals("Ace")) && (secondCard.getRank().equals("Ace")))) {

                displayHands(player, dealer, true, hand);

                // Always splitting aces and eights
                if (firstCard.getRank().equals("Ace") || firstCard.getRank().equals("Eight")) {
                    player.split(hand);
                    return true;
                }

                // A pair of 9’s splits against dealer 2 through 9, except for 7
                else if (firstCard.getRank().equals("Nine") && (upCard != 7)
                        && ((upCard >= 2) && (upCard <= 9))) {
                    player.split(hand);
                    return true;
                }

                // A pair of 7’s splits against dealer 2 through 7
                else if (firstCard.getRank().equals("Seven") && ((upCard >= 2) && (upCard <= 7))) {
                    player.split(hand);
                    return true;
                }

                // A pair of 6’s splits against dealer 2 through 6
                else if (firstCard.getRank().equals("Six") && ((upCard >= 2) && (upCard <= 6))) {
                    player.split(hand);
                    return true;
                }

                // A pair of 4’s splits against dealer 5 and 6
                else if (firstCard.getRank().equals("Four") && ((upCard == 5) || (upCard == 6))) {
                    player.split(hand);
                    return true;
                }

                // A pair of 3’s splits against dealer 2 through 7
                else if (firstCard.getRank().equals("Three") && ((upCard >= 2) && (upCard <= 7))) {
                    player.split(hand);
                    return true;
                }

                // A pair of 2’s splits against dealer 2 through 7
                else if (firstCard.getRank().equals("Two") && ((upCard >= 2) && (upCard <= 7))) {
                    player.split(hand);
                    return true;
                }
            }
        }

        return false;
    }

    public static void playBS(Player player, Dealer dealer, Shoe shoe, int hand) {
        // If player insures and dealer has blackjack or if player has blackjack, skip code
        if (!(player.isInsurance() && dealer.getDealerHand().isBlackjack()) && !player.blackjack(hand)) {

            int upCard = dealer.getDealerHand().getHand().get(1).getValue();

            // Looping until player stands or busts
            while (!player.getPlayerHands().get(hand).isStand() && !player.getPlayerHands().get(hand).isBust()) {
                int handValue = player.getPlayerHands().get(hand).checkHandValue();
                displayHands(player, dealer, true, hand);

                // Only one card is drawn to split aces
                if ((player.getPlayerHands().size() == 2) && player.getPlayerHands().get(hand).getHand().get(0).getRank().equals("Ace")) {
                    player.stand(hand);
                }

                // Soft Totals
                else if (player.getPlayerHands().get(hand).checkSoftTotal()) {
                    // Soft 20 (A,9) always stands
                    if (handValue == 20)
                        player.stand(hand);

                        // Soft 19 (A,8) doubles against dealer 6, otherwise stand
                    else if (handValue == 19) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2) && (upCard == 6))
                            doubleDown(player, shoe, hand);
                        else
                            player.stand(hand);
                    }

                    // Soft 18 (A,7) doubles against dealer 2 through 6, and hits against 9 through Ace, otherwise stand
                    else if (handValue == 18) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2)  && ((upCard >= 2) && (upCard <= 6)))
                            doubleDown(player, shoe, hand);
                        else if ((upCard >= 9) && (upCard <= 11))
                            hit(player, shoe, hand);
                        else
                            player.stand(hand);
                    }

                    // Soft 17 (A,6) doubles against dealer 3 through 6, otherwise hit
                    else if (handValue == 17) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2)  && ((upCard >= 3) && (upCard <= 6)))
                            doubleDown(player, shoe, hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // Soft 16 (A,5) doubles against dealer 4 through 6, otherwise hit.
                    else if (handValue == 16) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2)  && ((upCard >= 4) && (upCard <= 6)))
                            doubleDown(player, shoe, hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // Soft 15 (A,4) doubles against dealer 4 through 6, otherwise hit.
                    else if (handValue == 15) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2)  && ((upCard >= 4) && (upCard <= 6)))
                            doubleDown(player, shoe, hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // Soft 14 (A,3) doubles against dealer 5 through 6, otherwise hit.
                    else if (handValue == 14) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2)  && ((upCard >= 5) && (upCard <= 6)))
                            doubleDown(player, shoe, hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // Soft 13 (A,2) doubles against dealer 5 through 6, otherwise hit.
                    else if (handValue == 13) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2)  && ((upCard >= 5) && (upCard <= 6)))
                            doubleDown(player, shoe, hand);
                        else
                            hit(player, shoe, hand);
                    }
                }

                // Hard Totals
                else {
                    // 17 and up always stands
                    if (handValue >= 17)
                        player.stand(hand);

                        // 16, 15, 14, 13 stands against dealer 2 through 6, otherwise hit
                    else if (handValue >= 13) {
                        if ((upCard >= 2) && (upCard <= 6))
                            player.stand(hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // 12 stands against dealer 4 through 6, otherwise hit.
                    else if (handValue == 12) {
                        if ((upCard >= 4) && (upCard <= 6))
                            player.stand(hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // 11 always doubles otherwise hit
                    else if (handValue == 11) {
                        if (player.getPlayerHands().get(hand).getHand().size() == 2)
                            doubleDown(player, shoe, hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // 10 doubles against dealer 2 through 9 otherwise hit.
                    else if (handValue == 10) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2)  && ((upCard >= 2) && (upCard <= 9)))
                            doubleDown(player, shoe, hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // 9 doubles against dealer 3 through 6 otherwise hit.
                    else if (handValue == 9) {
                        if ((player.getPlayerHands().get(hand).getHand().size() == 2)  && ((upCard >= 3) && (upCard <= 6)))
                            doubleDown(player, shoe, hand);
                        else
                            hit(player, shoe, hand);
                    }

                    // 8 and lower always hits.
                    else
                        hit(player, shoe, hand);
                }

            }
        } else {
            displayHands(player, dealer, true, hand);
        }
    }

    // Displaying hands
    public static void displayHands(Player player, Dealer dealer, boolean hideUpCard, int hand) {
        System.out.println("\nYour hand:");
        System.out.println(player.getPlayerHands().get(hand).toString());
        System.out.println("\n     Hand value: " + player.getPlayerHands().get(hand).checkHandValue());

        System.out.println("\nDealer's hand:");
        if (hideUpCard) {
            System.out.println(dealer.getDealerHand().toString(true));
            System.out.println("\n     Dealer's hand value: " + dealer.getDealerHand().getHand().get(1).getValue());
        } else {
            System.out.println(dealer.getDealerHand().toString());
            System.out.println("\n     Dealer's hand value: " + dealer.getDealerHand().checkHandValue());
        }
    }

    // Insurance
    public static void insurance(Player player, Dealer dealer) {
        // Checking if dealer upcard is an Ace
        if (dealer.getDealerHand().getHand().get(1).getRank().equals("Ace")) {
            displayHands(player, dealer, true, 0);

            // Scanner for user input
            Scanner input = new Scanner(System.in);

            // Asking for input
            System.out.println("\nInsurance? (1) Yes or (2) No");
            int response = input.nextInt();

            // If player wants insurance
            if (response == 1) {
                player.insurance();
                player.setInsurance(true);
                System.out.println("\nYou have $" + player.getBankroll());
            }
        }
    }

    // Hitting
    public static void hit(Player player, Shoe shoe, int hand) {
        Card cardDrawn = shoe.drawCard();
        player.hit(cardDrawn, hand);
        System.out.println("\n,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        System.out.println("You drew a: " + cardDrawn.toString());
        System.out.println("```````````````````````````");
        bust(player, hand); //Bust if they go over 21
        player.tenCardCharlie(hand);    // Auto-win if 10 card charlie
    }

    // Doubling down
    public static void doubleDown(Player player, Shoe shoe, int hand) {
        Card cardDrawn = shoe.drawCard();
        player.doubleDown(cardDrawn, hand);
        System.out.println("\n,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        System.out.println("You drew a: " + cardDrawn.toString());
        System.out.println("```````````````````````````");

        bust(player, hand); //Bust if they go over 21
    }

    // Checking if specific hand busted
    public static void bust(Player player, int hand) {
        if (player.bust(hand)) {
            System.out.println("Bust. Hand currently valued at: " + player.getPlayerHands().get(hand).checkHandValue());
        }
    }

    // Checking who won, how they won, and distributing winnings
    public static void checkWinnings(Player player, Dealer dealer, Shoe shoe, StatsTracker workbook) {
        // Reveal Dealer Cards
        System.out.println("\nDealer Cards:");
        System.out.println(dealer.getDealerHand().toString());
        System.out.println("\n     Dealer's hand value: " + dealer.getDealerHand().checkHandValue());

        boolean skipDealer = false; // boolean variable to control if dealer plays

        // Player wins insurance (skips dealer)
        if (player.isInsurance() && dealer.getDealerHand().isBlackjack()) {
            System.out.println("\nDealer has blackjack. You got $" + player.getInsuranceBet()*3);
            player.winInsurance();
            skipDealer = true;
            workbook.winInsurance();    // Recording in Excel

            if (player.getPlayerHands().get(0).checkHandValue() < 21)
                workbook.recordOutcome("Lose");  // Recording in Excel
        } else if (player.isInsurance()) {
            workbook.loseInsurance();    // Recording in Excel
        }

        // Check for 10 card Charlie (skips dealer)
        int charlieCounter = 0;
        for (playerHand i : player.getPlayerHands()) {
            if (i.isTenCardCharlie()) {
                charlieCounter++;
            }
        }
        if ((player.getPlayerHands().size() == 1) && player.getPlayerHands().get(0).isTenCardCharlie()) {
            player.winBet(2, 0);
            System.out.println("\n10 card Charlie! You win! You got $" + player.getPlayerHands().get(0).getBet()*2 + ".");
            skipDealer = true;
            workbook.recordOutcome("Win");  // Recording in Excel
        } else if (charlieCounter == player.getPlayerHands().size()) {
            skipDealer = true;
        }

        // Check for blackjack (only applies if player didn't split)
        if (!player.isSplit()) {
            // Both dealer and player has blackjack, push (skips dealer)
            if (player.blackjack(0) && dealer.getDealerHand().isBlackjack()) {
                System.out.println("\nPush.");
                player.push(0);
                skipDealer = true;
                workbook.recordOutcome("Push"); // Recording in Excel
            }

            // Player wins blackjack (skips dealer)
            else if ((!player.isSplit()) && player.blackjack(0)) {
                System.out.println("\nBlackjack. You win 1.5x your original bet! You got $" + player.getPlayerHands().get(0).getBet()*2.5 + ".");
                player.winBet(2.5, 0);
                skipDealer = true;
                workbook.recordOutcome("Blackjack");    // Recording in Excel
            }
        }

        // Checking if all hands busted
        int bustCounter = 0;
        for (playerHand i : player.getPlayerHands()) {
            if (i.isBust()) {
                bustCounter++;
            }
        }
        if (bustCounter == player.getPlayerHands().size()) {    // If all hands busted, record all losses
            for (playerHand i : player.getPlayerHands()) {
                workbook.checkDoubleDownLoss(i);    // Recording in Excel
            }
            skipDealer = true;
        }

        // Dealing dealer's cards
        if (!skipDealer) {
            dealer.dealDealerHand(shoe);    // Dealer hits at 16 stands at 17

            // Dealer busts
            if ((dealer.getDealerHand().checkHandValue() > 21)) {
                int counter = 0;
                // Loop through all hands, distribute bets for hands that didn't bust
                for (playerHand i : player.getPlayerHands()) {
                    if (i.isBust()) {
                        System.out.println("\nBust. Hand currently valued at: " + i.checkHandValue());
                        workbook.checkDoubleDownLoss(i);    // Recording in Excel
                    } else if (i.isTenCardCharlie()) {
                        player.winBet(2, counter);
                        System.out.println("\n10 card Charlie! You win! You got $" + i.getBet()*2 + ".");
                        workbook.recordOutcome("Win");  // Recording in Excel
                    } else {
                        player.winBet(2, counter);
                        System.out.println("\nDealer Busts with a hand value of " + dealer.getDealerHand().checkHandValue()
                                + ". You win! You got $" + i.getBet()*2 + ".");
                        workbook.checkDoubleDownWin(i);    // Recording in Excel
                    }

                    counter++;  // Counting the hands
                }
            }

            // Comparing hands
            else {
                int counter = 0;
                // Loop through all hands
                for (playerHand i : player.getPlayerHands()) {
                    // Checking if hand busted
                    if (i.isBust()) {
                        System.out.println("\nBust. Hand currently valued at: " + i.checkHandValue());
                        workbook.checkDoubleDownLoss(i);    // Recording in Excel
                    }

                    // Checking if player got 10 card Charlie
                    else if (i.isTenCardCharlie()) {
                        player.winBet(2, counter);
                        System.out.println("\n10 card Charlie! You win! You got $" + i.getBet()*2 + ".");
                        workbook.recordOutcome("Win");  // Recording in Excel
                    }

                    // Player has greater hand than dealer
                    else if (i.checkHandValue() > dealer.getDealerHand().checkHandValue()) {
                        System.out.println("\nYou win the hand " + i.checkHandValue()
                                + " to " + dealer.getDealerHand().checkHandValue()
                                + ". You got $" + i.getBet()*2 + ".");
                        player.winBet(2, counter);
                        workbook.checkDoubleDownWin(i);    // Recording in Excel
                    }

                    // Dealer has greater hand than dealer
                    else if (dealer.getDealerHand().checkHandValue() > i.checkHandValue()) {
                        System.out.println("\nDealer beats you " + dealer.getDealerHand().checkHandValue()
                                + " to " + i.checkHandValue());
                        workbook.checkDoubleDownLoss(i);    // Recording in Excel
                    }

                    // Player and dealer have hands equal to each other
                    else {
                        System.out.println("\nPush. You got $" + i.getBet() + " back.");
                        player.push(counter);
                        workbook.recordOutcome("Push"); // Recording in Excel
                    }

                    counter++;  // Counting the hands
                }
            }
        }
    }

    // Resetting the round
    public static void reset(Player player, Dealer dealer, Shoe shoe, StatsTracker workbook) {
        workbook.endRound(player);  // Recording in Excel

        player.reset();
        dealer.reset();

        // Reshuffling shoe if half of shoe is dealt
        if (shoe.getNumOfDecks() < ((float) shoe.getDecks()/2)) {
            System.out.println("\n*********************************************************");
            System.out.println("Reshuffling deck!");
            System.out.println("*********************************************************");
            shoe.shuffle();
        }
    }
}