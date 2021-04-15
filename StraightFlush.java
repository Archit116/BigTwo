/**
 * Subclass of the Hand class, used to model a hand of StraightFlush
 * 
 * @author archit
 *
 */

public class StraightFlush extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**
	 * A constructor for  setting up StraighFlush
	 * 
	 * @param player variable for specified player 
	 * @param cards variable for specified list of cards
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * 
	 * @return string value
	 */
	public String getType() {
		return "StraightFlush";
	}	
	
	/**
	 * a method for checking if this is a valid hand
	 * 
	 * @return boolean value
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			if ((this.getCard(0).getSuit() == this.getCard(1).getSuit()) && (this.getCard(0).getSuit() == this.getCard(2).getSuit()) && (this.getCard(0).getSuit() == this.getCard(3).getSuit()) && (this.getCard(0).getSuit() == this.getCard(4).getSuit())) {
				if ((this.getCard(0).getRank() == this.getCard(1).getRank()) && (this.getCard(0).getRank() == this.getCard(2).getRank()) && (this.getCard(0).getRank() == this.getCard(3).getRank()) && (this.getCard(0).getRank() == this.getCard(4).getRank())) {
					return true;
				}
				else {
					return false;
				}
			} 
			else {
				return false;
			}
		}		
		return false;
	}
}

