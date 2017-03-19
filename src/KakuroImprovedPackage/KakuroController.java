/**
 * 
 */
package KakuroImprovedPackage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Controller module for Kakuro program.
 * Connects View and Model, checks correction os users actions.
 * @author Julita O³tusek
 *
 */
public class KakuroController {

	/**
	 * Pointer for the View.
	 */
	private KakuroView view;
	/**
	 * Pointer for the Model.
	 */
	private KakuroModel model;
	
	/**
	 * Controller Constructor.
	 * Set controller in the view field.
	 * Run the right interface with board.
	 */
	public KakuroController(KakuroView theView, KakuroModel theModel) 
	{
		view = theView;
		model = theModel;
		view.setController(this);
		view.makePanels(theModel.getBoardModel().getCurrentBoardState(), theModel.getNumberOfSet());
	}
	
	/**
	 * Manage action in the text field. 
	 * If permitted, set it in Model and View, if not, delete text from the field. 
	 * @param board1 text field where action was performed
	 */
	public void actionInTextField(JTextField board1)
	{
		int x, y;
		String number = board1.getText();
		
		if (model.isActionPermited(number))
		{	
			board1.setHorizontalAlignment(JTextField.CENTER);
			x = board1.getBounds().x;
			y = board1.getBounds().y;
			model.setUserSolution(number, x, y);
		}
		else
		{	
			board1.setHorizontalAlignment(JTextField.LEFT);
			board1.setText("");
		}
		
	}
	
	/**
	 * Manage action in Check button.
	 * Ask Model if solution is correct. If yes, show in the View image for winners, if not, continue game.
	 */
	public void checkButtonPressed()
	{		
		if (model.isSolutionCorrect())
			view.win();
		else 
			view.continueGame();
	}
}
;