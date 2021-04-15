/**
 * Subclass of the Hand class, used to model a hand of Single
 * 
 * @author archit
 *
 */

public class Single extends Hand {
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor for  setting up Single
	 * 
	 * @param player variable for specified player
	 * @param cards variable for specified list of cards 
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * 
	 * @return string value
	 */
	public String getType() {
		return "Single";
	}
	
	
	/**
	 * a method for checking if this is a valid hand
	 * 
	 * @return boolean value
	 */
	public boolean isValid() {
		if (this.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
