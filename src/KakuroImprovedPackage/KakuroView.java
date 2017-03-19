/**
 * 
 */
package KakuroImprovedPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * View module of Kakuro program.
 * Holds GUI of the Game.
 * @author Julita O�tusek
 *
 */
public class KakuroView {

	/**
	 * Number of columns and rows in board.
	 */
	final static int BOARD_SIZE = 6;
	/**
	 * Points to controller module.
	 */
	private KakuroController controller;
	/**
	 * Main frame of GUI.
	 */
	private JFrame frame;
	/**
	 * Main panel of frame.
	 */
	private JPanel mainPanel;
	
	/**
	 * Constructs a new KakuroView.
	 * Controller not set yet, set frame, set its size and make it visible.
	 * @param windowName is name of a window
	 */
	public KakuroView(String windowName) {
		
		controller = null;
		setFrame(new JFrame(windowName));
		getFrame().setSize(BOARD_SIZE*100, BOARD_SIZE*100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	getFrame().setVisible(true);
	}
	
	/**
	 * Set pointer to Controller in the View.
	 * @param controller the controller to set
	 */
	public void setController(KakuroController controller)
	{
		this.controller = controller;
	}
	
	/**
	 * Make all panels in the game and add them to the frame.
	 * @param board is 2D array from model with fields of board
	 * @param numberOfSet is number of set used in this game
	 */
	public void makePanels(FieldModel[][] board, int numberOfSet)
	{
		JPanel boardPanel = makeBoardPanel(board, numberOfSet);
		JPanel buttonPanel = makeButtonPanel();
		JPanel panel3 = new JPanel();
		panel3.add(boardPanel, BorderLayout.NORTH);
		panel3.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel = panel3;
		frame.add(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(JFrame frame) 
	{
		this.frame = frame;
	}
	
	/**
	 * Make panel for check button.
	 * @return button panel which was created
	 */
	private JPanel makeButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		JButton check = new JButton("CHECK");
		buttonPanel.add(check);
		check.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
		      {
		    	  controller.checkButtonPressed();
		      }
		});
		
		return buttonPanel;
	}	
	
	/**
	 * Make panel for board with text fields and labels with graphics loaded from right folder
	 * @param board is 2D array of fields from the Model
	 * @param numberOfSet is number of set used in this game
	 * @return panel with board which was made
	 */
	private JPanel makeBoardPanel(FieldModel[][] board, int numberOfSet)
	{
		ImageIcon icon;
		JLabel label;
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
			
		for (int i = 0; i < BOARD_SIZE; ++i)
			for (int j = 0; j < BOARD_SIZE; ++j)
			{	
				if (board[i][j].getCorrectSolution() == 0)
				{	
					String name = "board"+numberOfSet+"/Field"+i+j+".jpg";
					icon = new ImageIcon(name);
					label = new JLabel(icon);
					boardPanel.add(label);
				}
				
				else
				{
					JTextField board1 = new JTextField(2);
					board1.setPreferredSize(new Dimension(70, 70));
					Font font1 = new Font("SansSerif", Font.BOLD, 40);
					board1.setFont(font1);
					board1.addActionListener(new ActionListener() 
					{
					      public void actionPerformed(ActionEvent e) 
					      {
					    	  controller.actionInTextField(board1);
					      }
					});
					boardPanel.add(board1);
				}
			}
		
		return boardPanel;
	}
	
	/**
	 * Controller confirmed victory, show image for the winners and exit button.
	 */
	public void win()
	{
		JPanel winPanel = new JPanel();
		JLabel winLabel;
		JButton exitButton = new JButton("EXIT");
		ImageIcon icon = new ImageIcon("win.jpg");
		winLabel = new JLabel(icon);
		winPanel.add(winLabel);
		winPanel.add(exitButton);
		exitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
		      {
		    	 System.exit(0);
		      }
		});
		frame.getContentPane().removeAll();
		frame.add(winPanel);
		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * Entered solution is not correct, show label and go back to game.
	 */
	public void continueGame()
	{
		JLabel notWin = new JLabel("Solution is not correct! Try to fix something.");
		JButton ok = new JButton("OK");
		mainPanel.remove(mainPanel.getComponent(1));
		mainPanel.add(notWin);
		mainPanel.add(ok);
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
		      {
		    	  mainPanel.remove(mainPanel.getComponent(1));
		    	  mainPanel.remove(mainPanel.getComponent(1));
		    	  JPanel buttonPanel = makeButtonPanel();
		    	  mainPanel.add(buttonPanel);  
		      }
		});
		frame.revalidate();
		frame.repaint();
	}	
}