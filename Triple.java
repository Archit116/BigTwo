/**
 * Subclass of the Hand class, used to model a hand of Triple
 * 
 * @author archit
 *
 */

public class Triple extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**
	 * A constructor for  setting up Triple
	 * 
	 * @param player variable for specified player
	 * @param cards variable for specified list of cards
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * 
	 * @return string value
	 */
	public String getType() {
		return "Triple";
	}
	

	/**
	 * a method for checking if this is a valid hand
	 * 
	 * @return boolean value
	 */
	public boolean isValid() {
		if (this.size() == 3) {
			if (getCard(0).rank == getCard(1).rank && getCard(0).rank == getCard(2).rank) {
				return true;
			}
		}
		return false;
	}
}
