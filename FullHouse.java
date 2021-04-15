/**
 * Subclass of the Hand class, used to model a hand of FullHouse
 * 
 * @author archit
 *
 */

public class FullHouse extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**
	 * A constructor for setting up FullHouse
	 * 
	 * @param player variable for specified player
	 * @param cards variable for specified list of cards
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * 
	 * @return string value
	 */
	public String getType() {
		return "FullHouse";
	}
	
	
	/**
	 * a method for retrieving top card of the hand
	 * 
	 * @return The Card object of top-most card
	 */
	public Card getTopCard() {
		this.sort();
		return this.getCard(2); //Center card will always be a part of the triplet
	}
	
	
	/**
	 * a method for checking if this is a valid hand
	 * 
	 * @return boolean value
	 */
	public boolean isValid() {
		if (this.size() ==5) {
			this.sort();
			if ((this.getCard(0).getRank() == this.getCard(1).getRank()) && (this.getCard(0).getRank() == this.getCard(2).getRank()) && (this.getCard(3).getRank() == this.getCard(4).getRank())) {
				return true;				
			}
			else if ((this.getCard(0).getRank() == this.getCard(1).getRank()) && (this.getCard(2).getRank() == this.getCard(3).getRank()) && (this.getCard(3).getRank() == this.getCard(4).getRank())) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
}
