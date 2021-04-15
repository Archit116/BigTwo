/**
 * subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game.
 * 
 * @author archit
 *
 */

public class BigTwoDeck extends Deck {
	private static final long serialVersionUID = 1L;
	
	/**
	 * a method for initializing a deck of Big Two cards
	 */
	public void initialise() {
		removeAllCards();
		for (int s=0; s<4; s++) {
			for (int r=0; r<13; r++) {
				BigTwoCard card = new BigTwoCard(s,r);
				addCard(card);
			}
		}
	}
}