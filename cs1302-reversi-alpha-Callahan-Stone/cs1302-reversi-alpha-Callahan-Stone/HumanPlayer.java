import java.util.Scanner;

public class HumanPlayer extends Player
{
    @Override
    public ReversiMove determineMove(int playerIndex, ReversiBoard board)
    {
	Scanner input = new Scanner(System.in);

	int x = -1;
	int y = -1;
	
	ReversiMove move;
	
	boolean inputIsInBounds = false;
	
	do
	{
	    //Display prompt.
	    System.out.print("Enter your move, ");
	    if(playerIndex + 1 == ReversiBoard.BLACK_PIECE)
		{
		    System.out.print("X Player: ");
		}
	    else if(playerIndex + 1 == ReversiBoard.WHITE_PIECE)
		{
		    System.out.print("O Player: ");
		}

	    //Get input.
	    String playerInput = input.nextLine();
	
	    // Determine input is correct.
	    Scanner playerInputScanner = new Scanner(playerInput);
	
	    //Check to see if first value is an integer.
	    if(playerInputScanner.hasNextInt())
		{
		    x = playerInputScanner.nextInt();
		}
	
	    //Check to see if the second value is an integer.
	    if(playerInputScanner.hasNextInt())
		{
		    y = playerInputScanner.nextInt();
		}

	    //Checks to see that x and y are in bounds. If all inequalities below are false, input is in bounds.
	    if(!(x < 0 || x > 7 || y < 0 || y > 7))
		{
		    inputIsInBounds = true;
		}

	    //Attempts to construct a Move object with the given x and y.
	    move = new ReversiMove(x, y, playerIndex + 1);
	    
	    //If the input is incomplete (less than 2 integers), informs the user.
	    if(x == -1 || y == -1)
		{
		    System.out.println();
		    System.out.println("The input is not syntactically correct.");
		    System.out.println();
		    continue;
		}
	    
	    //Informs user if move is invalid.
	    if(!Reversi.isMoveLegal(move, board))
		{
		    System.out.println();
		    System.out.println("The move at " + x + ", " + y + " is not legal.");
		    System.out.println();
		}

	}while(!inputIsInBounds || !Reversi.isMoveLegal(move, board));
	
	//Return the move object.
	return move;
	
    }
    
    @Override
    public boolean isHuman()
    {
	return true;
    }
}