import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;


/**
 * The BigTwoTable class implements the CardGameTable interface. It is used to build a GUI for the Big Two card game and handle all user actions
 * 
 * @author archit
 *
 */
public class BigTwoTable implements CardGameTable {
	
	/**
	 * To load the images used 
	 */
	private void imageLoader() {
		avatars = new Image[4];
		avatars[0] = new ImageIcon ("src/avatars/flash_128.png").getImage();
		avatars[1] = new ImageIcon ("src/avatars/superman_128.png").getImage();
		avatars[2] = new ImageIcon ("src/avatars/green_lantern_128.png").getImage();
		avatars[3] = new ImageIcon ("src/avatars/batman_128.png").getImage();
		cardBackImage = new ImageIcon("src/cards/b.gif").getImage();
		
		char[] suit = {'d','c','h','s'};
		char[] rank = {'a','2','3','4','5','6','7','8','9','t','j','q','k'};
		cardImages = new Image[4][13];
		
		for (int r=0; r<13; r++) {
			for (int s=0; s<4; s++) {
				String loc = new String();
				loc = "src/cards/" + rank[r] + suit[s] + ".gif";
				cardImages[s][r] = new ImageIcon(loc).getImage();
			}
		}
		
	}
	
	/**
	 * The GUI of the BIG Two Game
	 */
	private void go() {
		
		//FRame part
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setTitle("Big Two");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		//Menu
		JMenu menu = new JMenu("Game");
		menuBar.add(menu);
		//Message part of menu
		JMenu menu2 = new JMenu("Message");
		menuBar.add(menu2);
		
		
		//Menu items for Game
		JMenuItem connect= new JMenuItem("Connect");
		connect.addActionListener(new ConnectMenuItemListener());
		menu.add(connect);
		//JMenuItem restart = new JMenuItem("Restart");
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener (new QuitMenuItemListener());
		//menu.add(restart);
		menu.add(quit);		
		//item actions
		//restart.addActionListener (new RestartMenuItemListener());
		
		
		//Menu items for message
		JMenuItem infoBoard =new JMenuItem("Clear Information Board");
		infoBoard.addActionListener(new ClearInfoBoardListener());
		JMenuItem chat = new JMenuItem("Clear Chat Screen");
		chat.addActionListener(new ClearChatListener());
		menu2.add(infoBoard);
		menu2.add(chat);
		
		
		
		
		
		//Chat panel
		JPanel msg = new JPanel();
		msg.setLayout(new BoxLayout(msg, BoxLayout.PAGE_AXIS));
		
		
		//Messages area
		msgArea = new JTextArea(35,40);
		msgArea.setEnabled(false);
		DefaultCaret caret = (DefaultCaret) msgArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scroller = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		//Chatbox area
		chatArea = new JTextArea(40,40);
	    chatArea.setEnabled(false);
	    DefaultCaret caret2 = (DefaultCaret) chatArea.getCaret();
		caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scroller2 = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		
		JPanel chatBox = new JPanel();
		chatBox.setLayout(new FlowLayout());
		chatBox.add(new JLabel("Message: "));
		
		//TExtField
		Area = new FieldText(30);
		Area.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		Area.setPreferredSize(new Dimension(200, 40));
		chatBox.add(Area);
		
		
		msg.add(scroller);
	    msg.add(scroller2);
	    msg.add(chatBox);
		//Buttons panel
		JPanel buttonPanel = new JPanel();
		playButton = new JButton("Play");
		passButton = new JButton("Pass");
		playButton.addActionListener (new PlayButtonListener());
		passButton.addActionListener (new PassButtonListener());
		//Adding buttons
		//buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(playButton);
		//buttonPanel.add(Box.createHorizontalStrut(20));
		buttonPanel.add(passButton);
		
		if (game.getCurrentIdx() != activePlayer) {
			passButton.setEnabled(false);
			playButton.setEnabled(false);
			buttonPanel.setEnabled(false);
			
		}
		else {
			passButton.setEnabled(true);
			playButton.setEnabled(true);
			buttonPanel.setEnabled(true);
		}
		
		
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setPreferredSize(new Dimension(800,800));
		
//		msgArea = new JTextArea();
//		msgArea.setEditable(false);
//		Font fonts = new Font ("Verdana",Font.BOLD, 20);
//		msgArea.setFont(fonts);
		
		
		//JScrollPane scroller = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//scroller.setPreferredSize(new Dimension(525,0));
		//DefaultCaret caret = (DefaultCaret) msgArea.getCaret();
		//caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		/*frame.getContentPane().add(BorderLayout.EAST, scroller);
		frame.add(BorderLayout.CENTER, bigTwoPanel);
		frame.add(BorderLayout.SOUTH, buttonPanel);
		frame.setSize(1450, 1000);
		frame.setVisible(true);		*/
		frame.add(msg, BorderLayout.EAST);
	    frame.add(bigTwoPanel,BorderLayout.WEST);
	    frame.add(buttonPanel, BorderLayout.SOUTH);
	    
		frame.setSize(1300,900);
	    frame.setVisible(true);
		
	}
	
	/**
	 * a constructor for creating a BigTwoTable
	 * 
	 * @param game is a reference to a card game associates with this table
	 */
	BigTwoTable(BigTwoClient game) {
		this.game = game;
		this.selected = new boolean[13];
		setActivePlayer(game.getPlayerID());
		imageLoader();
		go();
	}
	
	/**
	 * a card game associates with this table
	 */
	private BigTwoClient game;
	
	/**
	 * a boolean array indicating which cards are being selected
	 */
	private boolean[] selected;
	
	/**
	 * an integer specifying the index of the active player
	 */
	private int activePlayer;
	
	/**
	 * the main window of the application
	 */
	private JFrame frame;
	
	/**
	 * a panel for showing the cards of each player and the cards played on the table
	 */
	private JPanel bigTwoPanel;
	
	/**
	 * a “Play” button for the active player to play the selected cards
	 */
	private JButton playButton;
	
	/**
	 * a “Pass” button for the active player to pass his/her turn to the next player
	 */
	private JButton passButton;
	
	/**
	 * a text area for showing the current game status as well as end of game message
	 */
	private JTextArea msgArea;
	
	/**
	 * text area for chat messages
	 */
	private JTextArea chatArea;
	
	/**
	 * text area
	 */
	private JTextField Area;
	
	/**
	 * a 2D array storing the images for the faces of the cards
	 */
	private Image[][] cardImages;
	
	/**
	 * an image for the backs of the cards
	 */
	private Image cardBackImage;
	
	/**
	 * an array storing the images for the avatar's
	 */
	private Image[] avatars;
	
	//CardGameTable interface methods-------------
	
	
	/**
	 * a method for setting the index of the active player (i.e., the current player)
	 * 
	 * @param activePlayer represents the current player
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	/**
	 * a method for getting an array of indices of the cards selected
	 * 
	 * @return an integer array of the indices of cards selected
	 */
	public int[] getSelected() {
		
		int ctr = 0;
		for (int i=0; i < selected.length; i++) {
			if (selected[i] == true) {ctr++;}
		}
		
		int[] temp;
		if (ctr == 0) {return null;}
		temp = new int[ctr];
		
		int ctr2=0;
		for (int i=0; i < selected.length; i++) {
			if (selected[i] == true) {
				temp[ctr2] = i;
				ctr2++;
			}
		}
		return temp;
	}
	
	/**
	 * a method for resetting the list of selected cards
	 */
	public void resetSelected() {
		for (int i=0; i < selected.length; i++) {
			selected[i] = false;
		}
	}
	
	/**
	 * a method for repainting the GUI
	 */
	public void repaint() {
		resetSelected();
		frame.repaint();
	}
	
	
	/**
	 * a method for printing the specified string to the message area of the GUI
	 * 
	 * @param msg The string to be printed in message area
	 */
	public void printMsg( String msg) {
		msgArea.append(msg + '\n');
	}
	
	/**
	 * a method for clearing the message area of the GUI
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	}
	
	/**
	 * a method for resetting the GUI
	 */
	public void reset() {
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
	}
	
	/**
	 * a method for enabling user interactions with the GUI
	 */
	public void enable() {
		bigTwoPanel.setEnabled(true);
		playButton.setEnabled(true);
		passButton.setEnabled(true);	
	}
	
	/**
	 * a method for disabling user interactions with the GUI
	 */
	public void disable() {
		bigTwoPanel.setEnabled(false);
		playButton.setEnabled(false);
		passButton.setEnabled(false);
	}
	
	/**
	 * method for printing chat message
	 * 
	 * @param msg a string value thats appended
	 */
	public void printChatMsg(String msg) {
		chatArea.append(msg+"\n");
	}
	
	/**
	 * method to cleear the chat message box
	 */
	public void clearChatMsgArea() {
		chatArea.setText("");
	}
	
	
	/**
	 * an inner class that extends the JPanel class and implements the MouseListener interface 
	 * 
	 * @author archit
	 * 
	 */
	class BigTwoPanel extends JPanel implements MouseListener {
		
		//Private instance variables for setting up the placement
		private static final long serialVersionUID = 1L;
		private int NameX = 40;
		private int NameY = 20;
		private int AvX = 5;
		private int AvY = 30;
		private int lineX = 160;
		private int lineY = 1600;
		private int cardX = 155;
		private  int widthCard = 40;
		private int c = 160;
		private  int DownC = 43;
		private int UpC = 23;
		
		/**
		 * Constructor for adding mouseListener
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}	
		
		/**
		 * inherited from the JPanel class to draw the card game table
		 *
		 * @param g object for graphics
		 *
		 */
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			
			this.setBackground(Color.GRAY.darker());
			g.setColor(Color.WHITE);
			
			int pctr = 0;
			
			//putting the avatar's of the players and their cards in a standard setting
			while(pctr < game.getNumOfPlayers()) {
				
				
				if (game.getPlayerID() == pctr) {
					if (game.getCurrentIdx() == pctr) {
						g.setColor(Color.BLUE);
					}
					else {
						g.setColor(Color.PINK);
					}
					
					g.drawString( game.getPlayerList().get(pctr).getName() + " (THIS IS YOU)", NameX, NameY+ 160*pctr);
					g.setColor(Color.WHITE);
				}
				
				else if(game.getCurrentIdx() == pctr) {
					g.setColor(Color.BLUE);
					g.drawString(game.getPlayerList().get(pctr).getName(), NameX, NameY+ 160*pctr);
					g.setColor(Color.WHITE);
				}
				
				else {
					g.drawString(game.getPlayerList().get(pctr).getName(), NameX, NameY+ 160*pctr); 
				}
				
				g.setColor(Color.WHITE);
												
				g.drawImage(avatars[pctr], AvX, AvY + 160 * pctr, this);
				g2d.drawLine( 0 , lineX * (pctr+1) , lineY , lineX * (pctr+1));
				
				//active player so showing the cards and not the back
				if (game.getPlayerID() == pctr ) {
					for (int i=0; i < game.getPlayerList().get(pctr).getNumOfCards(); i++) {
						int suit = game.getPlayerList().get(pctr).getCardsInHand().getCard(i).getSuit();
						int rank = game.getPlayerList().get(pctr).getCardsInHand().getCard(i).getRank();
						
						if (!selected[i]) {
							g.drawImage(cardImages[suit][rank], cardX + (widthCard*i), DownC + (c*pctr), this);
						}
						else {
							g.drawImage(cardImages[suit][rank], cardX + (widthCard*i), UpC + (c*pctr), this);							
						}
					}
				}
				
				//Back of the cards if not active
				else {
					for (int i=0; i< game.getPlayerList().get(pctr).getCardsInHand().size(); i++) {
						g.drawImage(cardBackImage, cardX + (widthCard*i), DownC + (c*pctr), this);
					}
				}
				pctr++;
				
			}
			
			
			
			g.drawString("Last Hand on Table", 10, 660);
			
			
			if (!game.getHandsOnTable().isEmpty()) {
				int handSize = game.getHandsOnTable().size();
				Hand tableHand = game.getHandsOnTable().get(handSize-1);
				//Changes maybe----------------------------
				g.drawString("Hand Type\n" + tableHand.getType(), 10, 700);
				
				for (int i=0; i < tableHand.size(); i++) {
					int suit = tableHand.getCard(i).getSuit();
					int rank = tableHand.getCard(i).getRank();
					
					g.drawImage(cardImages[suit][rank], 160+(40*i), 690, this);
				}
				g.drawString("Played by " + tableHand.getPlayer().getName(), 10, 725);
				
			}
			
			repaint();
			
		}
		
		/**
		 * method for mouse click methods
		 * 
		 * @param event object of mouse event
		 */
		public void mouseClicked(MouseEvent event) {
			
			boolean flag = false;
			int numOfCards = game.getPlayerList().get(activePlayer).getNumOfCards();
			int cardCheck = numOfCards - 1;
			
			
			if (event.getX() >= (cardX+(cardCheck*40)) && 
				event.getX() <= (cardX+(cardCheck*40) + 73)) {
				
				if (!selected[cardCheck] && 
					event.getY() >= (DownC + (c*activePlayer)) && 
					event.getY() <= (DownC + (c*activePlayer) + 97) ) {
					
					selected[cardCheck] = true;
					flag = true;
				}
				
				else if (selected[cardCheck] && 
						event.getY() >= (UpC + (c*activePlayer)) && 
						event.getY() <= (UpC + (c*activePlayer)) + 97 ) {
					
					selected[cardCheck] = false;
					flag = true;
				}
			}
			
			
			
			for (cardCheck = numOfCards - 2; cardCheck >= 0 && !flag; cardCheck--) {
				
				if (event.getX() >= (cardX + (cardCheck*widthCard)) && 
						event.getX() <= cardX+(cardCheck+1)*widthCard) {
					
					if (!selected[cardCheck] && 
						event.getY() >= DownC+ (c*activePlayer) &&
						event.getY() <= DownC+ (c*activePlayer) + 97) {
						
						selected[cardCheck] = true;
						flag = true;
					}
					else if (event.getY() >= UpC+(c*activePlayer) && 
							event.getY() <= UpC + (c*activePlayer) + 97 &&
							selected[cardCheck]) {
						
						selected[cardCheck] = false;
						flag = true;
					}
				}
				else if (event.getX() >= cardX+ (cardCheck+1)*widthCard && 
						 event.getX() <= cardX+ (cardCheck*widthCard)+73 &&
						 event.getY() >= DownC + (c*activePlayer) &&
						 event.getY() <= DownC + (c*activePlayer) + 97)  {
					
					if (selected[cardCheck + 1] && !selected[cardCheck]) {
						selected[cardCheck] = true;
						flag = true;						
					}
					
				}
				
				else if (event.getX() >= cardX + (cardCheck+1)*widthCard && 
						event.getX() <= cardX + (cardCheck*widthCard)+73 &&
						event.getY() >= UpC + (c*activePlayer) &&
						event.getY() <= UpC + (c*activePlayer) + 97) {
					
					if (!selected[cardCheck+1] && selected[cardCheck]) {
						
						selected[cardCheck]= false;
						flag= true;
					}
				}
			}
			this.repaint();
		}
		
		@Override
		public void mousePressed(MouseEvent event) {
		}

		@Override
		public void mouseReleased(MouseEvent event) {
		}

		@Override
		public void mouseEntered(MouseEvent event) {
		}

		@Override
		public void mouseExited(MouseEvent event) {
		}
	}
	
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Play” button
	 * 
	 * @author archit
	 *
	 */
	class PlayButtonListener implements ActionListener {
		
		/**
		 *  Plays a turn
		 *  
		 * @param event is an ActionEvent Object
		 */
		public void actionPerformed(ActionEvent event) {
			if (getSelected()==null) {
				printMsg("Select card to play\n");
			} else {
				game.makeMove(activePlayer, getSelected());
			}
			
			repaint();
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Pass” button
	 * 
	 * @author archit
	 *
	 */
	class PassButtonListener implements ActionListener {
		/**
		 * Passes the turn
		 * 
		 * @param event is an ActionEvent Object
		 */
		public void actionPerformed (ActionEvent event) {
			game.makeMove(activePlayer, null);
			repaint();
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Restart” menu item
	 * 
	 * @author archit
	 *
	 */
//	class RestartMenuItemListener implements ActionListener {
//		/**
//		 * Restarts the Application
//		 * 
//		 * @param event is an ActionEvent object
//		 */
//		public void actionPerformed(ActionEvent event) {
//			BigTwoDeck deck = new BigTwoDeck();
//			deck.shuffle();
//			game.start(deck);
//			reset();
//		}
//	}
//	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item
	 * 
	 * @author archit
	 *
	 */
	class QuitMenuItemListener implements ActionListener {
		/**
		 * Exits the Application
		 * 
		 * @param event is an ActionEvent object
		 */
		public void actionPerformed (ActionEvent event) {
			System.exit(0);
		}
	}
	
	/**
	 * Inner class that implements ActionListener
	 * @author archit
	 *
	 */
	class ConnectMenuItemListener implements ActionListener {
		/**
		 * makes connection
		 * 
		 * @param event object for ACtionEvent
		 */
		public void actionPerformed (ActionEvent event) {
			if (!game.isConnected() ) {
				reset();
				game.makeConnection();
			}
		}
	}
	
	/**
	 * Inner Class that implements actionListener
	 * @author archit
	 *
	 */
	class ClearInfoBoardListener implements ActionListener {
		
		/**
		 * clears message area
		 * 
		 * @param event object for ACtionEvent
		 */
		public void actionPerformed (ActionEvent event) {
			clearMsgArea();
		}
	}
	
	/**
	 * inner class that implements ActionListener
	 * 
	 * @author archit
	 *
	 */
	class ClearChatListener implements ActionListener {
		/**
		 * Clears chat message area
		 * 
		 * @param event object for ACtionEvent
		 */
		public void actionPerformed(ActionEvent event) {
			clearChatMsgArea();
		}
	}
	
	
	/**
	 * Inner class that implements the action listener interfaace
	 * @author archit
	 *
	 */
	class FieldText extends JTextField implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Adds actionlisteber
		 * 
		 * @param a int value
		 */
		public FieldText (int a) {
			super(a);
			addActionListener(this);
		}
		
		/**
		 * sets text
		 * 
		 * @param event object for ACtionEvent
		 */
		public void actionPerformed(ActionEvent event) {
			String m = getText();
			CardGameMessage msg = new CardGameMessage (CardGameMessage.MSG, -1, m );
			game.sendMessage(msg);
			this.setText("");
		}
	}

}
