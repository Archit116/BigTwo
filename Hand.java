
/**
 * The Hand class is a subclass of the CardList class, and is used to model a hand of cards. 
 * 
 * @author archit
 *
 */

abstract class Hand extends CardList {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor for building the hand
	 * 
	 * @param player variable for specified player
	 * @param cards variable for specified list of cards
	 */
	public Hand(CardGamePlayer player, CardList cards) {
			this.player = player;
			for (int i=0; i<cards.size(); i++) {
				this.addCard(cards.getCard(i));
			}
	}
	
	/**
	 * The player who plays the hand
	 */
	private CardGamePlayer player;
	
	/**
	 * a method for retrieving the player of this hand
	 * 
	 * @return the player
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 * 
	 * @return the top card obj
	 */
	public Card getTopCard() {			
		int idx =-1;
		int max=0;
		int maxS=0;
		for (int i=0; i< size(); i++) {
			Card A = this.getCard(i);
			
			int num = A.rank;
			//A becomes 13 and 2 becomes 14 
			 if (num == 0 || num == 1) {
				 num += 13;
			 }
			 
			if (num > max) {
				max = num;
				idx = i;	
				maxS = A.suit;
			}
			else if (num == max) {				
				if (A.suit >= maxS) {
					maxS = A.suit;
					idx = i;
				}
			}
		}
		return this.getCard(idx);
	}
	
	/**
	 * a method for checking if this hand beats a specified hand
	 * 
	 * @param hand obj of class Hand
	 * @return boolean value depending on if this hand beats the specified hand
	 */
	public boolean beats(Hand hand) { 
		Card currentTop =  this.getTopCard();
		Card otherTop =  hand.getTopCard();
		int curr = currentTop.getRank();
		int other = otherTop.getRank();
		
		String[] Types = {"Straight", "Flush","FullHouse","Quad", "StraightFlush"};
		
		//If hand sizes are equal
		if (hand.size() == this.size()) {
			//if there are less than 5 cards i.e. - 1,2,3
			if (hand.size() < 5) {
				if ( curr == 0 || curr == 1) {
					curr += 13;
				}
				
				if (other == 0 || other == 1) {
					other += 13;
				}
				
				if (curr == other) {
					if (currentTop.getSuit() - otherTop.getSuit() > 0) {return true;}
					else {return false;}
				}
				
				else if (curr > other) {
					return true;
				}
				
				return false;
			}
			
			
			// if there are 5 cards, Types string[]
			else if (hand.size() == 5) {
				//if they same type
				if (this.getType() == hand.getType()) {
					if ( curr == 0 || curr == 1) {
						curr += 13;
					}
					
					if (other == 0 || other == 1) {
						other += 13;
					}
					
					if (curr == other) {
						if (currentTop.getSuit() - otherTop.getSuit() > 0) {return true;}
						else {return false;}
					}
					
					else if (curr > other) {
						return true;
					}
					
					return false;
				}
				//if they not same type
				else {
					// if it is equal to StraightFlush then true
					if (this.getType() == Types[4]) { 
						return true;
					}
					// if it is equal to Quad, then other hand can't be StraightFlush
					else if (this.getType() == Types[3] && hand.getType() != Types[4]) { 
						return true;
					}
					 // if it is equal to FullHouse then other hand cant be StraightFlush or Quad
					else if (this.getType() == Types[2] && (hand.getType() != Types[4] && hand.getType() != Types[3])) {
						return true;
					}
					// if it is equal to Flush then other hand cant be anything other than straight
					else if (this.getType() == Types[1] && (hand.getType() != Types[4] && hand.getType() != Types[3] && hand.getType() != Types[2])) { 
						return true;
					}
					//if it is straight then false
					else if (this.getType() == Types[0]) { 
						return false;
					}
					else {
						return false;
					}
				}
			} 
		}
		return false;
	}
		
	
	/**
	 * a method for checking if this is a valid hand
	 * 
	 * @return a false value
	 */
	abstract public boolean isValid();
	
	/**
	 *  a method for returning a string specifying the type of this hand
	 *  
	 * @return empty string
	 */
	abstract public String getType();

}
