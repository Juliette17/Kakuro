/**
 * 
 */
package KakuroImprovedPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * Model module of Kakuro program.
 * Holds data and logic of the game.
 * @author Julita O³tusek
 *
 */
public class KakuroModel {

	/**
	 * Number of columns and rows in board
	 */
	final static int BOARD_SIZE = 6;
	
	/**
	 * Board with correct and user solutions.
	 */
	private BoardModel boardModel;
	
	/**
	 * Size of the board above.
	 */
	private int numberOfSet;
	
	/**
	 * Model Constructor
	 * Set board in model and bumber of set used in this game
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public KakuroModel() throws ClassNotFoundException, IOException 
	{
		boardModel = new BoardModel();
		numberOfSet = boardModel.getnumberOfSet();
		//getBoardModel().outpstream();
	}
	
	/**
	 * @return board in Model, which is object of type BoardModel
	 */
	public BoardModel getBoardModel()
	{
		return boardModel;
	}
	
	/**
	 * @return number of set used in this game
	 */
	public int getNumberOfSet()
	{
		return numberOfSet;
	}

	/**
	 * Check if text entered by user to text field is valid in the game (integer between 1 and 10)
	 * @param number is string entered by user to text field
	 * @return true if string is integer between 1 and 9, false anything else
	 */
	public boolean isActionPermited(String number)
	{
		if(number.length() != 1)
			return false;
		for(int i = 0; i < number.length(); ++i)
	    {
			char c=number.charAt(i);
			if(!Character.isDigit(c))
				return false;   
	    }   	
		int x = Integer.parseInt(number);
		return (x > 0 && x < 10);
	}
	
	/**
	 * Update user solution in changed text field, converting field positions in the View to indexes in 2D array of fields in Model
	 * @param number is entered solution
	 * @param x is horizontal position of the field
	 * @param y is vertical position of field
	 */
	public void setUserSolution(String number, int x, int y)
	{
		int a, b, c;
		a = Integer.parseInt(number);
		/*
		 *conversion from x and y position of TextField to FieldBoard table indexes
		 */
		b = x/82;
		c = y/82;
		getBoardModel().setCurrentBoardState(a, c, b);
	}
	
	/** 
	 * @return weather solution entered by the User is correct or not
	 * @see BoardModel
	 */
	public boolean isSolutionCorrect()
	{
		return getBoardModel().isSolutionCorrect();
	}
}
/**
 * Class of board in Model, hold 2D array of fields and number of set used in game. Used also to generate board and write it to file.
 * @author Julita O³tusek
 *
 */
class BoardModel {
	
	/**
	 * Number of files with different boards (serialized objects of type FieldModel)
	 */
	final static int BOARDS = 2;
	final static int BOARD_SIZE = 6;
	/**
	 * 2D Table with fields containing correct and user solution
	 */
	private FieldModel[][] board;
	/**
	 * Size of the table above
	 */
	private int numberOfSet;
	/**
	 * Construct new Board Model. Read from file the board and create a 2D array of fields in Model, insert objects to it.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	BoardModel() throws ClassNotFoundException, IOException 
	{
		//ArrayList<FieldModel> theBoard = generateBoard();
		ArrayList<FieldModel> theBoard = readBoardFromFile(); 
		double howManyFields = (double) theBoard.size();
		int howManyRows = (int) Math.sqrt(howManyFields);
		board = new FieldModel[howManyRows][howManyRows];
		
		/*
		 * insert fields to the 2D array in model
		 * bardModel[i][j].correctSolution :
		 * 0 - empty field
		 * 1-9 - solution value
		 */
		for (int i = 0; i < howManyRows; ++i)
			for (int j = 0; j < howManyRows; ++j)
				board[i][j] = theBoard.get(howManyRows*i + j);
	}
	
	/**
	 * Check current state of entered user solutions
	 * @return 2D array which is board of fields
	 */
	public FieldModel[][] getCurrentBoardState()
	{
		return board;
	}
	
	/**
	 * Update user solutions in 2D array which is board in Model.
	 * @param a user solution
	 * @param b horizontal index of 2D array
	 * @param c vertical index of 2D array
	 */
	public void setCurrentBoardState(int a, int b, int c)
	{
		board[b][c].setUserSolution(a);
	}
	/**
	 * @return number of set of Kakuro puzzle, set with board
	 */
	public int getnumberOfSet()
	{
		return numberOfSet;
	}
	/**
	 * Check if user solutions are the same like correct solutions
	 * @return weather solution entered by the User is correct or not
	 */
	public boolean isSolutionCorrect()
	{
		for (int i = 0; i < BOARD_SIZE; ++i)
			for (int j = 0; j < BOARD_SIZE; ++j)
			{
				if (board[i][j].getUserSolution() != board[i][j].getCorrectSolution())
					return false;
			}
		return true;
	}
	
	/**
	 * Draw number of set with board
	 * and name of file containing serialized package of objects of type FieldModel
	 * @return name of the drawn file (random board)
	 */
	public String drawBoardFile()
	{
		Random generator = new Random();
		int randomNumber = generator.nextInt(BOARDS) + 1;
		String fileName = "KakuroBoard" + randomNumber + ".ser";
		
		numberOfSet = randomNumber;
		return fileName;
	}
	/**
	 * Read game board from drawn file.
	 * @return object of type ArrayList containing objects of type FieldModel got from the file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<FieldModel> readBoardFromFile() throws IOException, ClassNotFoundException 
	{
		String fileName = drawBoardFile();
		FileInputStream streamFile = new FileInputStream(fileName);
		ObjectInputStream b = new ObjectInputStream(streamFile);
		Object obj;
		FieldModel ne;
		ArrayList<FieldModel> mytab = new ArrayList<FieldModel>();
		
		for (int i = 0; i < BOARD_SIZE*BOARD_SIZE; ++i)
		{
			obj = b.readObject();
			ne = (FieldModel) obj;
			mytab.add(ne);
			//System.out.print(ne.getCorrectSolution());	
		}
		
		b.close();
		
		return mytab;
	}
	/**
	 * Generate board with correct solutions for Model
	 * @return ArrayList of objects of type FieldModel which represents generated board
	 */
	public ArrayList<FieldModel> generateBoard()
	{
		ArrayList<FieldModel> mytab = new ArrayList<FieldModel>();
		for (int i = 0; i < BOARD_SIZE*BOARD_SIZE; ++i)
		{
			FieldModel ne = new FieldModel();
			mytab.add(ne);
		}
		FieldModel ne1 = new FieldModel(4);
		mytab.set(8, ne1);
		FieldModel ne2 = new FieldModel(1);
		mytab.set(9, ne2);
		FieldModel ne3 = new FieldModel(9);
		mytab.set(14, ne3);
		FieldModel ne4 = new FieldModel(6);
		mytab.set(15, ne4);
		FieldModel ne5 = new FieldModel(8);
		mytab.set(16, ne5);
		FieldModel ne6 = new FieldModel(7);
		mytab.set(17, ne6);
		FieldModel ne7 = new FieldModel(1);
		mytab.set(19, ne7);
		FieldModel ne8 = new FieldModel(7);
		mytab.set(20, ne8);
		FieldModel ne9 = new FieldModel(9);
		mytab.set(22, ne9);
		FieldModel ne10 = new FieldModel(3);
		mytab.set(23, ne10);
		FieldModel ne11 = new FieldModel(5);
		mytab.set(25, ne11);
		FieldModel ne12 = new FieldModel(2);
		mytab.set(26, ne12);
		FieldModel ne13 = new FieldModel(1);
		mytab.set(27, ne13);
		FieldModel ne14 = new FieldModel(4);
		mytab.set(28, ne14);
		FieldModel ne15 = new FieldModel(3);
		mytab.set(33, ne15);
		FieldModel ne16 = new FieldModel(7);
		mytab.set(34, ne16);
		
		return mytab;
	}
	
	/**
	 * Save the board to file.
	 * @throws IOException 
	 */
	public void outpstream() throws IOException
	{
		FileOutputStream streamFile = new FileOutputStream("KakuroBoard2.ser");
		ObjectOutputStream a = new ObjectOutputStream(streamFile);
		for (int i = 0; i < BOARD_SIZE; ++i)
		{	
			for (int j = 0; j < BOARD_SIZE; ++j)
			{
				FieldModel first = board[i][j];
				a.writeObject(first);
			}
		}
		a.close();
	}
	
}


/**
 * Class of board field in Model.
 * @author Julita O³tusek
 *
 */
class FieldModel implements Serializable {
	
	/**
	 * Some field which has to be overrode in Serializable interface
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * correct solution for the Field constant in model
	 */
	private int correctSolution;
	/**
	 * solution entered by user in the View, updated with help of controller
	 */
	private int userSolution;
	
	/**
	 * Construct empty field
	 * correctSolution = 0 - empty field
	 */
	
	public FieldModel()
	{
		this.correctSolution = 0;
		this.userSolution = 0;		
	}
	
	/**
	 * construct field to fill
	 * correctSolution != 0 - field to fill
	 */
	
	public FieldModel(int cS)
	{
		this.correctSolution = cS;
		this.userSolution = 0;	
		
	}
	/**
	 * @return correct solution
	 */
	public int getCorrectSolution()
	{
		return correctSolution;
	}
	/**
	 * @return user solution
	 */
	public int getUserSolution()
	{
		return userSolution;
	}
	/**
	 * @param newSolution new solution to set
	 */
	public void setUserSolution(int newSolution)
	{
		userSolution = newSolution;
	}		
}