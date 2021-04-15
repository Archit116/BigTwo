/**
 * Subclass of the Hand class, used to model a hand of Flush
 * 
 * @author archit
 *
 */

public class Flush extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**
	 * A constructor for Flush
	 * 
	 * @param player variable for specified player
	 * @param cards variable for specified list of cards
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);	
	}
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * 
	 * @return string value
	 */
	public String getType() {
		return "Flush";
	}
	
	/**
	 * a method for checking if this is a valid hand
	 * 
	 * @return boolean value
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			if ((getCard(0).getSuit() == getCard(1).getSuit()) && (getCard(0).getSuit() == getCard(2).getSuit()) && (getCard(0).getSuit() == getCard(3).getSuit()) && (getCard(0).getSuit() == getCard(4).getSuit())) {
				return true;
			}			
		}
		return false;
	}
	
}
