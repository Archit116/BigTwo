/**
 * Subclass of the Hand class, used to model a hand of Straight
 * 
 * @author archit
 *
 */

public class Straight extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**
	 * A constructor for  setting up Straight
	 * 
	 * @param player variable for specified player 
	 * @param cards variable for specified list of cards
	 */
	public Straight (CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	
	/**
	 * a method for returning a string specifying the type of this hand
	 * 
	 * @return string value
	 */
	public String getType() {
		return "Straight";
	}
	
	
	/**
	 * a method for checking if this is a valid hand
	 * 
	 * @return boolean value
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			if ((getCard(0).rank +1 == getCard(1).rank) && (getCard(0).rank +2 == getCard(2).rank) && (getCard(0).rank +3 == getCard(3).rank) && (getCard(0).rank +4 == getCard(4).rank)) {
				return true;
			}
		}
		return false;
	}
	
}
