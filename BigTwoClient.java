import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * The BigTwo class is used to model a Big Two card game 
 * 
 * @author archit
 *
 */
public class BigTwoClient implements CardGame, NetworkGame {
	
	/**
	 * a constructor for creating a Big Two card game
	 */
	public BigTwoClient() {
		playerList = new ArrayList<CardGamePlayer> ();
		for (int i=0; i<4; i++) {
			CardGamePlayer player = new CardGamePlayer();
			playerList.add(player);
		}
		
		
		currentIdx=-1;
		numOfPlayers = playerList.size();
		handsOnTable = new ArrayList<Hand>();
		table =  new BigTwoTable(this);
		
		String name = (String) JOptionPane.showInputDialog("Enter yor Name: ");
		if (name == "" || name == null ) {
			setPlayerName("Default");
		}
		else {
			setPlayerName(name);
		}
		setServerIP ("127.0.0.1");
		setServerPort (2396);
		makeConnection();
		table.disable();
		table.repaint();
		
	}
	
	/**
	 * a Big Two table which builds the GUI for the game and handles all user actions
	 */
	private BigTwoTable table;	
	
	/**
	 * a deck of cards
	 */
	private Deck deck;
	
	/**
	 * a list of players.
	 */
	private ArrayList<CardGamePlayer> playerList;
	
	/**
	 * a list of hands played on the table
	 */
	private ArrayList<Hand> handsOnTable;
	
	/**
	 * an integer specifying the index of the current player
	 */
	private int currentIdx;
	
	/**
	 * an integer value specifying number of players
	 */
	private int numOfPlayers;
	
	/**
	 * an integer specifying the playerID (i.e., index) of the local player
	 */
	private int playerID;
	
	/**
	 * a string specifying the name of the local player
	 */
	private String playerName;
	
	/**
	 * a string specifying the IP address of the game server
	 */
	private String serverIP;
	
	/**
	 * an integer specifying the TCP port of the game server
	 */
	private int serverPort;
	
	/**
	 * a socket connection to the game server.
	 */
	private Socket sock;
	
	/**
	 * an ObjectOutputStream for sending messages to the server
	 */
	private ObjectOutputStream oos;
	
	/**
	 * an objectinputstream object
	 */
	private ObjectInputStream ois;
	
	
	
	

	/**
	 * a method for retrieving the deck of cards being used
	 * 
	 * @return deck of cards being used
	 */
	public Deck getDeck() {
		return deck;
	}
	
	
	/**
	 * a method for retrieving the list of players
	 * 
	 * @return a list of players
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		return playerList;
	}
	
	
	/**
	 * a method for retrieving the list of hands played on the table.
	 * 
	 * @return list of hands played on the table
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return handsOnTable;
	}
	
	/**
	 * a method for retrieving the index of the current player
	 * 
	 * @return index of current player
	 */
	public int getCurrentIdx() {
		return currentIdx;
	}
	
	/**
	 * a method for getting the number of players 
	 * 
	 * @return the total number of players
	 */
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	/**
	 * – a method for getting the playerID (i.e., index) of the local player.
	 * 
	 * @return playerID
	 */
	public int getPlayerID() {
		return playerID;
	}
	

	/**
	 * a method for getting the name of the local player
	 * 
	 * @return name of the player
	 */
	public String getPlayerName() {
		return playerName;
	}
	

	/**
	 * a method for getting the TCP port of the game server.
	 * 
	 * @return serverport
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * a method for getting the IP address of the game server
	 * 
	 * @return IP ADDRESS of game server 
	 */
	public String getServerIP() {
		return serverIP;
	}

	
	/**
	 * a method for starting the game with a (shuffled) deck of cards supplied as the argument. It implements the Big Two game logics
	 * 
	 * @param Deck shuffled deck of cards
	 */
	public void start(Deck Deck)  {
		
		//Removing from table
		handsOnTable.clear();
		
		//Removing from hands
		for (int i=0; i < 4; i++) {
			playerList.get(i).getCardsInHand().removeAllCards();
		}
		
		int player = 0;
		
		
		
		
		//Giving cards to the players
		for (int i=0; i<52; i++ ) {
			playerList.get(player % 4).addCard(Deck.getCard(i));
			player++;
		}
		
		// Sorting Players hands
		for (int i=0; i<4; i++) {
			playerList.get(i).getCardsInHand().sort();
		}

		
		//1st player 3 of diamonds
		Card firstCard = new Card(0,2); //3 of diamonds
		for (int i=0; i<4; i++) {
			if (playerList.get(i).getCardsInHand().contains(firstCard)) {
				currentIdx = i;
				table.setActivePlayer(currentIdx);
				break;
			}
		}
		
		table.setActivePlayer(getPlayerID());
		table.printMsg("All players ready!! BEGIN");
		
		table.printMsg(getPlayerList().get(currentIdx).getName() + "'s turn");
		table.repaint();
	}
		
		/*
		
		CardList cardlist = new CardList();
		//Flag to check if game is over or not
		boolean EndOfGame = false;
		//Flag to check if valid or not
		boolean Validity = true;
		
		//While game is not over
		while (!EndOfGame) { 
			if (Validity) {
				bigTwoConsole.repaint();
			}
			
			int[] selected = bigTwoConsole.getSelected();
			if (selected != null) {
				cardlist = playerList.get(x).play(selected);
				Hand hand = composeHand(playerList.get(x), cardlist);
				
				if (handsOnTable.isEmpty()) {

					if (hand.contains(firstCard) && hand.isEmpty() == false && hand.isValid() == true )   {
						Validity = true;
					}
					else {
						Validity = false;
					}
				}
				else {
					
					if (prevPlayer != x) {
						Validity = hand.beats(handsOnTable.get(handsOnTable.size()-1));
					}
					else {
						Validity = true;
					}
				}
				
				if (Validity && hand.isValid()) {
					prevPlayer = x;
					
					for (int i=0; i<cardlist.size(); i++) {
						playerList.get(x).getCardsInHand().removeCard(cardlist.getCard(i));
						
						if(playerList.get(x).getCardsInHand().isEmpty() == true) {
							EndOfGame = true;
						}
					}
					//Output part
					System.out.println("{" +hand.getType() + "} " + hand);
					handsOnTable.add(hand);
					x = (x+1) % 4;
					bigTwoConsole.setActivePlayer(x);
					System.out.println("");
					
				}
				else {
					//Output part
					System.out.println("Not a legal move!!!");
				}
				
			}
			else {
				if (handsOnTable.isEmpty() == false && prevPlayer != x) {
					//Output part
					x = (x+1) % 4;
					bigTwoConsole.setActivePlayer(x);
					System.out.println("{Pass}");
					System.out.println("");
					Validity = true;
				}
				else {
					//Output part
					System.out.println("Not a legal move!!!");
				}
			}
		}
		
		//End Game
		if (EndOfGame) {
			//Output part
			bigTwoConsole.setActivePlayer(-1);
			bigTwoConsole.repaint();
			System.out.println("");
			System.out.println("Game ends");
			
			for (int i=0; i<playerList.size(); i++) {
				
				if (playerList.get(i).getNumOfCards() == 0) {
					System.out.println("Player " + i + " wins the game");
				}
				else {
					System.out.println("Player " + i + " has " + playerList.get(i).getNumOfCards() + " cards in the hand");
				}
			}
		}
		
		*/

	/**
	 * a method for making a move by a player with the specified playerID using the cards specified by the list of indices
	 * 
	 * @param playerID integer value of ID of the player
	 * @param cardIdx integer array of Indices of the cards selected
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		CardGameMessage m = new CardGameMessage(CardGameMessage.MOVE, playerID, cardIdx);
		sendMessage(m);
		//checkMove(playerID, cardIdx);
	}
	/**
	 * custom variable counter for pass limit
	 */
	private int x =0;
	/**
	 * custom variable boolean for pass limit problem
	 */
	private boolean y = true;
	
	
	/**
	 * a method for checking a move made by a player
	 * 
	 * @param playerID integer value of ID of the player
	 * @param cardIdx integer array of Indices of the cards selected
	 */
	public void checkMove(int playerID, int[] cardIdx) {
		
		CardList cardlist = new CardList(); 
		
		boolean Validity = true;
		
		Card firstCard = new Card(0,2); //Diamond 3
		
		if(cardIdx != null) {
			x=0;
			cardlist = playerList.get(playerID).play(cardIdx);
			Hand hand = composeHand(playerList.get(playerID),cardlist);
			
			if(handsOnTable.isEmpty()) {
				//Checker for checking that the first move has the 3 of diamonds or not
				if(hand.contains(firstCard) && hand.isEmpty() == false && hand.isValid() == true) {
					Validity = true; 
				}
					
				else {
					Validity = false;
				}
					
			}
			
			else {
				
				if(handsOnTable.get(handsOnTable.size()-1).getPlayer() != playerList.get(playerID) && y == true) {
					
					if(!hand.isEmpty()) {
						Validity = hand.beats(handsOnTable.get(handsOnTable.size()-1));
					}
					else {
						Validity=false;
					}
					
				}
				
				else {
					
					if(!hand.isEmpty()) {
						Validity = true;
					}
					else {
						Validity= false;
					}
					
				}
			}
			
			if(Validity && hand.isValid()) {
				for(int i=0; i < cardlist.size(); i++) {
					playerList.get(playerID).getCardsInHand().removeCard(cardlist.getCard(i)); 
				}
				
				table.printMsg("{" + hand.getType() + "} " +hand);
				handsOnTable.add(hand);
				currentIdx = (currentIdx + 1)%4;
				table.setActivePlayer(currentIdx);
				table.printMsg("Player " + (currentIdx) + "'s turn");
			}
			else {
				table.printMsg(cardlist + " <= Not a legal move!!!");
			}
		}
		
		else {
			x++;
			if(!handsOnTable.isEmpty() && handsOnTable.get(handsOnTable.size()-1).getPlayer() != playerList.get(playerID) && x < 4 ) {
				currentIdx=(currentIdx+1)%4;
				table.setActivePlayer(currentIdx);
				table.printMsg("{Pass}");
				table.printMsg("Player " + currentIdx + "'s turn");
				Validity = true;
			}
			else {
				table.printMsg("Pass limit exceeded");
				y = false;
				Validity = false;
				
			}
		}
		if (Validity)
			y = true;
		table.repaint();
		
		
		//checking for end of game
		if(endOfGame()) {
			
			table.setActivePlayer(-1);
			table.repaint();
			//bigTwoTable.printMsg("Game end");
			String msg = "Game End!\n";
			
			for(int i=0; i < playerList.size(); i++) {
				if(playerList.get(i).getCardsInHand().size()==0) {
					//bigTwoTable.printMsg("Player " + i +" wins the game");
					msg += "Player " + i + " wins the game!!\n";
				}
				else{
					//bigTwoTable.printMsg("Player " + i + " has " + playerList.get(i).getCardsInHand().size() +" cards in hand"); // list the number of cards left in the other players' hand
					msg += "Player " + i + " has " + playerList.get(i).getCardsInHand().size() + " cards in hand.\n";
				}
			}
			
			table.disable();
			for (int i=0; i<4; i++) {
				getPlayerList().get(i).removeAllCards();
			}
			JOptionPane.showMessageDialog(null, msg);
			CardGameMessage c = new CardGameMessage(CardGameMessage.READY, -1, null);
			sendMessage(c);
		}
	}
	
	/**
	 * a method for checking if the game ends
	 * 
	 * @return boolean value representing end of game or not
	 */
	public boolean endOfGame() {
		for (int i=0; i < playerList.size(); i++) {
			if (playerList.get(i).getNumOfCards()==0) {
				return true;
				
			}	
		}
		
		return false;
	}
	
	

	
	
	
	
	/**
	 * a method for parsing the messages received from the game server
	 * 
	 * @param object of class GameMessage
	 */
	public void parseMessage (GameMessage msg) {
		
		//When it is FULL
		if (msg.getType() == CardGameMessage.FULL) {
			table.printMsg("The srever is full...  Whoops.. :)");
			try {
				sock.close();
			}
			catch (IOException exc) {
				exc.printStackTrace();
			}
		}
		
		//When sending a message
		else if(msg.getType() == CardGameMessage.MSG) {
			table.printChatMsg((String) msg.getData());
		}
		
		//When playing a move
		else if ( msg.getType() ==CardGameMessage.MOVE) {
			checkMove( msg.getPlayerID(), (int[]) msg.getData() );
			table.repaint();
		}
		
		
		//When joining a new player
		else if (msg.getType() == CardGameMessage.JOIN) {
			getPlayerList().get(msg.getPlayerID()).setName((String) msg.getData());
			table.repaint();
			table.printMsg("Player " + playerList.get(msg.getPlayerID()).getName() + " joined the game");
		}
		
		
		//When quitting a player
		else if (msg.getType() == CardGameMessage.QUIT) {
			table.printMsg("Player "+ msg.getPlayerID()+" "+playerList.get(msg.getPlayerID()).getName() + "  has left the game");
			getPlayerList().get(msg.getPlayerID()).setName("");
			if (endOfGame() == false) {
				table.disable();
				CardGameMessage msg2 = new CardGameMessage (CardGameMessage.READY, -1, null);
				sendMessage(msg2);
				for (int i=0; i<4; i++) {
					getPlayerList().get(i).removeAllCards();
				}
				table.repaint();
			}
			table.repaint();
		}
		
		//When ready
		else if (msg.getType()== CardGameMessage.READY) {
			table.printMsg("Player "+ msg.getPlayerID() + " is ready to play");
		}
		
		//When list of players
		else if (msg.getType() == CardGameMessage.PLAYER_LIST) {
			setPlayerID(msg.getPlayerID());
			table.setActivePlayer(msg.getPlayerID());
			
			for (int i=0; i < getNumOfPlayers(); i++) {
				if( ( (String[]) msg.getData() )[i] != null ) {
					getPlayerList().get(i).setName( ( (String[]) msg.getData() )[i] );
				}
			}
		}
		
		//When starting
		else if (msg.getType() == CardGameMessage.START) {
			deck = (BigTwoDeck) msg.getData();
			start(deck);
			table.enable();
			table.repaint();
		}
		
		
	}
	
	
	/**
	 * – a method for sending the specified message to the game server
	 * 
	 * @param object of class GameMessage
	 */
	public void sendMessage(GameMessage msg) {
		try {
			oos.writeObject(msg);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	
	/**
	 * a method for starting a Big Two card game. It should create a Big Two card game, create and shuffle a deck of cards, and start the game with the deck of cards
	 * 
	 * @param args a string arr for main 
	 */
	public static void main(String[] args) {
		
		BigTwoClient game = new BigTwoClient();
	}
	
	/**
	 * a method for returning a valid hand from the specified list of cards of the player. Returns null is no valid hand can be composed from the specified list of cards.
	 *  
	 * @param player variable for specified player
	 * @param cards variable for specified list of cards
	 * @return a valid hand
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		
		Single single = new Single(player, cards);
		Pair pair = new Pair(player, cards);
		Triple triple = new Triple(player, cards);
		Straight straight = new Straight(player, cards);
		Flush flush = new Flush(player, cards);
		Quad quad = new Quad(player, cards);
		FullHouse fullhouse = new FullHouse(player,cards);
		StraightFlush straightflush =new StraightFlush(player, cards);
		
		if (straightflush.isValid()) {
			return straightflush;
		}
		else if (quad.isValid()) {
			return quad;
		}
		else if(fullhouse.isValid()) {
			return fullhouse;
		}
		else if(flush.isValid()) {
			return flush;
		}
		else if (straight.isValid()) {
			return straight;
		}
		else if (triple.isValid()) {
			return triple;
		}
		else if(pair.isValid()) {
			return pair;
		}
		else if (single.isValid()) {
			return single;
		}
		else {
			return null;
		}
	}

	
	
	/**
	 * to check if connected or not
	 * 
	 * @return boolean value
	 */
	public boolean isConnected() {
		if (sock.isClosed()) {
			return false;
		}
		return true;
	}
	
	/**
	 * a method for making a socket connection with the game server
	 */
	public void makeConnection() {
		try {
			String IP = getServerIP();
			int port = getServerPort();
			
			sock = new Socket(IP, port);
			oos = new ObjectOutputStream(sock.getOutputStream());
			
			Runnable job= new ServerHandler();
			
			Thread thread =new Thread(job);
			thread.start();
			
			CardGameMessage join = new CardGameMessage(CardGameMessage.JOIN, -1, this.getPlayerName());
			sendMessage(join);
			
			CardGameMessage ready = new CardGameMessage(CardGameMessage.READY, -1, null);
			sendMessage(ready);
			
			table.repaint();
			
			
			
			
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		
	}
		

		/**
		 * an inner class that implements the Runnable interface
		 * 
		 * @author archit
		 *
		 */
		class ServerHandler implements Runnable {
			/**
			 * method run 
			 */
			public void run() {
				CardGameMessage msg = null;
				
				try {
					ois = new ObjectInputStream(sock.getInputStream());
					while (sock.isClosed() == false) {
						if ((msg = (CardGameMessage) ois.readObject()) != null) {
							parseMessage(msg);
						}
					}
					ois.close();
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
				table.repaint();
			}
		}
		
		/**
		 * a method for setting the playerID (i.e., index) of the local player.
		 */
		@Override
		public void setPlayerID(int playerID) {
			// TODO Auto-generated method stub
			this.playerID = playerID;
		}

		/**
		 * a method for setting the name of the local player
		 */
		@Override
		public void setPlayerName(String playerName) {
			// TODO Auto-generated method stub
			this.playerName = playerName;
		}

		/**
		 * a method for setting the IP address of the game server.
		 */
		@Override
		public void setServerIP(String serverIP) {
			// TODO Auto-generated method stub
			this.serverIP = serverIP;
		}

		/**
		 * a method for setting the TCP port of the game server
		 */
		@Override
		public void setServerPort(int serverPort) {
			// TODO Auto-generated method stub
			this.serverPort = serverPort;
		}

}
