/**
 * The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a Big Two card game.
 * 
 * @author archit
 *
 */

public class BigTwoCard extends Card {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * a constructor for building a card with the specified suit and rank
	 * 
	 * @param suit is an integer between 0 and 3 which shows the suit
	 * @param rank is an integer between 0 and 12 which shows the number

	 */
	public BigTwoCard(int suit,int rank) {
		super(suit,rank);
	}
	
	/**
	 * a method for comparing the order of this card with the specified card
	 * 
	 * @param card object of class Card, the card which has to be compared
	 * @return  Returns a negative integer, zero, or a positive integer as this card is less than, equal to, or greater than the specified card.
	 */
	public int compareTo(Card card) {
		int currentCardSuit = this.suit;
		int currentCardRank = this.rank;
		int otherCardRank = card.getRank();
		int otherCardSuit = card.getSuit();
		
		
		//Set 2s and A as the highest and second highest cards
		if (currentCardRank == 0 || currentCardRank == 1) {
			currentCardRank += 13;
		}
		
		if (otherCardRank == 0 || otherCardRank == 1) {
			otherCardRank += 13;
		}
		
		//Comparison
		if (currentCardRank == otherCardRank) {
			if (currentCardSuit - otherCardSuit > 0) {return 1;}
			else {return -1;}
		}
		else {
			if (currentCardRank - otherCardRank > 0) {return 1;}
			else {return -1;}
		}
	}

}
