/**
 * 
 */
package KakuroImprovedPackage;

import java.io.IOException;

/**
 * This is a graphic program: Kakuro, a simple board game. 
 * Implemented as MVC architectural pattern.
 * @author Julita O³tusek
 *
 */
public class KakuroMVC {

	/**
	 * Create GUI of Kakuro game, the Model and the Controller, all the modules of MVC.
	 * @param args
	 * @throws IOException for file stream management.
	 * @throws ClassNotFoundException for reading object from file.
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException 
	{		
		KakuroView theView = new KakuroView("Kakuro");
		KakuroModel theModel = new KakuroModel();
		new KakuroController(theView, theModel);
	}

}
