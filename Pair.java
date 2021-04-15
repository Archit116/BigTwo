/**
 * Subclass of the Hand class, used to model a hand of Pair
 *  
 * @author archit
 *
 */

public class Pair extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**
	 * A constructor for setting up Pair
	 * 
	 * @param player variable for specified player
	 * @param cards variable for specified list of cards
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * 
	 * @return string value
	 */
	public String getType() {
		return "Pair";
	}
	
	
	/**
	 * a method for checking if this is a valid hand
	 * 
	 * @return boolean value
	 */
	public boolean isValid() {
		if (this.size() == 2) {
			if (this.getCard(0).getRank() == this.getCard(1).getRank()) {
				return true;
			}	
		}
		return false;
	}
	
}
