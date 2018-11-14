import java.util.Random;

public class RandomComputerPlayer extends ComputerPlayer
{
    @Override
	public ReversiMove determineMove(int playerIndex, ReversiBoard board)
	{
	    System.out.println();
	    System.out.println("Waiting...");
	    System.out.println();
	    // Try-catch block from http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-seconds
	    try{
		Thread.sleep(5000);
	    }
	    catch(InterruptedException ex){
		    Thread.currentThread().interrupt();
		}

	    int [][] possibleMoves = new int[64][2];
	    int count = 0;
	    for(int row = 0; row < 8; row++)
	    {
		for(int col = 0; col < 8; col++)
		{
		    ReversiMove attemptedMove = new ReversiMove(row, col, playerIndex + 1);
		    if(Reversi.isMoveLegal(attemptedMove, board))
		    {
			possibleMoves[count][0] = row;
			possibleMoves[count][1] = col;
			count++;
		    }
		}
	    }

	    Random randomIndex = new Random();
	    int randomInt = randomIndex.nextInt(count);

	    ReversiMove randomMove = new ReversiMove(possibleMoves[randomInt][0], possibleMoves[randomInt][1], playerIndex + 1);
	    return randomMove;
	}
}