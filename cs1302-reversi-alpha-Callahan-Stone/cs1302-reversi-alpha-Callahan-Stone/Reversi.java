public class Reversi
{
    // Values passed to the 
    private static final int NORTH_WEST = 0;
    private static final int NORTH = 1;
    private static final int NORTH_EAST = 2;
    private static final int EAST = 3;
    private static final int SOUTH_EAST = 4;
    private static final int SOUTH = 5;
    private static final int SOUTH_WEST = 6;
    private static final int WEST = 7;

    private static ReversiBoard board;

    private static PlayerList players;
    
    private static void gameLoop()
    {
	System.out.println("Welcome to Reversi! Moves should be entered in \"[row] [column]\" format.");
	printBoard();
	while(!isGameOver())
	    {
		ReversiMove currentMove = players.getPlayer(players.getActivePlayerIndex()).determineMove(players.getActivePlayerIndex(), board);
		updateBoard(currentMove);
		printBoard();
		printScores(determineScores());
		players.advanceActivePlayerIndex();
	    }
	determineWinner();
    }

    private static boolean isGameOver()
    {
	return (!isAnyMovePossible() || board.isBoardFull());
    }

    private static boolean isAnyMovePossible()
    {
	boolean aMoveIsPossible = false;
	for(int row = 0; row < 8; row++)
	    {
		for(int col = 0; col < 8; col++)
		    {
			for(int color = ReversiBoard.BLACK_PIECE; color <= ReversiBoard.WHITE_PIECE; color++)
			    {
				aMoveIsPossible |= isMoveLegal(new ReversiMove(row, col, color), board);
			    } 
		    }
	    }
	return aMoveIsPossible;
    }

    private static void printScores(int[] scores)
    {
	System.out.println();
	System.out.println("X Player: " + scores[0]);
	System.out.println("O Player: " + scores[1]);
	System.out.println();
    }

    private static int[] determineScores()
    {
	int [] scores = new int[2];
	scores[0] = 0;
	scores[1] = 0;
	for(int row  = 0; row < 8; row++)
	    {
		for(int col = 0; col < 8; col++)
		    {
			if(board.getPieceAtSpace(row, col) == ReversiBoard.BLACK_PIECE)
			    {
				scores[0]++;
			    }
			else if(board.getPieceAtSpace(row, col) == ReversiBoard.WHITE_PIECE)
			    {
				scores[1]++;
			    }
		    }
	    }

	return scores;
    }
    private static void determineWinner()
    {
	int [] scores = determineScores();
	System.out.println();
	if(scores[0] > scores[1])
	    {
		System.out.println("Player X wins!");
	    }
	else if(scores[1] > scores[0])
	    {
		System.out.println("Player O wins!");
	    }
	else
	    {
		System.out.println("It's a tie...");
	    }
    }

    private static void updateBoard(ReversiMove currentMove)
    {
	ReversiMove[][] lines = new ReversiMove[8][0];
	
	board.placePieceAtSpace(currentMove);
	
	//Obtains each line starting one space away from the currentMove and continuing till the end of the board.
	for(int i = 0; i < lines.length; i++)
	    {
		lines[i] = obtainLine(currentMove.getRow(), currentMove.getCol(), i, board);
	    }
	for(int i = 0; i < lines.length; i++)
	    {
		int countPiecesInBetween = 0;
		for(int i2 = 0; i2 < lines[i].length; i2++)
		    {
			if(lines[i][i2].getGamePieceColor() == ReversiBoard.EMPTY_SPACE)
			    {
				break;
			    }
			else if(lines[i][i2].getGamePieceColor() == currentMove.getGamePieceColor())
			    {
				for(int i3 = 0; i3<countPiecesInBetween; i3++)
				    {
					ReversiMove changeColor = new ReversiMove(lines[i][i3].getRow(), lines[i][i3].getCol(),
										  currentMove.getGamePieceColor());
					board.placePieceAtSpace(changeColor);
				    }
				break;
			    }
			else
			    {
				countPiecesInBetween++;
			    }
		    }
	    }
	
    } 
    
    public static boolean isMoveLegal(ReversiMove move, ReversiBoard board)
    {
	if(board.getPieceAtSpace(move.getRow(), move.getCol()) == 1 || board.getPieceAtSpace(move.getRow(), move.getCol()) == 2)
	{
	    return false;
	}
	// boolean to be returned.
	boolean moveIsLegal = false;
	
	// Create an array to store lines for each of the eight directions.
	ReversiMove[][] lines = new ReversiMove[8][0];
	
	// Check lines in each direction to see if there is a piece with the color of the Move object passed to this method.
	
	int[] matchingMoveRow = {-1, -1, -1, -1, -1, -1, -1, -1}; // The row value of a piece of a matching color.
	int[] matchingMoveCol = {-1, -1, -1, -1, -1, -1, -1, -1}; // The col value of a piece of a matching color.

	for (int i = 0; i < 8; i++) { //For each direction...
	    ReversiMove[] newLine = obtainLine(move.getRow(), move.getCol(), i, board); /* Obtain a the line of Move objects from the space
									   * of
									   * the Move object passed to this method to the end
									   * of the board. Cycles through directions starting
									   * at North-West and ending at West.
								       */
	    for (int i2 = 0; i2 < newLine.length; i2++) { // For each space in that line.
		if (newLine[i2].getGamePieceColor() == move.getGamePieceColor()) { // then a matching color piece was found.
		    lines[i] = newLine; // Add that line to the list of potentially valid lines.
		    // Get the row and column value of the space of the matching color piece.
		    matchingMoveRow[i] = newLine[i2].getRow();
		    matchingMoveCol[i] = newLine[i2].getCol();
		    break; //Escape the loop that checks this line.
		}
	    }
	}

	/*
	 * For each case that there is a piece of the same color as the Move, check to see if an unbroken line of opposite
	 * color exists.
	 */
	for (int i = 0; i < 8; i++) { // For each direction...
	    if (lines[i].length  != 0) { // then the line in this direction had a matching color piece.
		boolean unbrokenLine = true;
		for (int i2 = 0; i2 < lines[i].length; i2++) { // For each piece in that line...
		    if((i2 == 0) && (lines[i][i2].getGamePieceColor() == move.getGamePieceColor())) /*Then the first game
												     * piece in the line
												     * matches the move
												     * being checked.
												     */
			{
			    unbrokenLine &= false;
			}
		    if ((lines[i][i2].getGamePieceColor() != move.getGamePieceColor()) && 
			(lines[i][i2].getGamePieceColor() != board.EMPTY_SPACE)) { /* then there is still an unbroken line of
										  * opposite game piece colors.
										  */
			unbrokenLine &= true;
		    }
		    else if ((lines[i][i2].getRow() == matchingMoveRow[i]) &&
			     (lines[i][i2].getCol() == matchingMoveCol[i])) { /* then we've reached the piece that matched
									     * colors with the move passed to this object.
									     */ 
			unbrokenLine &= true;
			break; // Stop checking this line.
		    }
		    else { // there is a break somewhere in the line.
			unbrokenLine &= false;
		    }
		}
		if (unbrokenLine) { // then one line provides a valid move.
		    moveIsLegal = true;
		    break; // Stop checking any lines.
		}

	    }

	}
	
	return moveIsLegal;
    }

    /**
     * Cycles through the game board and returns an array of the Move objects that are contained in the line indicated by
     * the direction and starting point passed to the method.
     */
    public static ReversiMove[] obtainLine(int row, int col, int direction, ReversiBoard board)
    {
	// Determine the number of spaces between the starting position and the end of the board in the given direction.
	int count = 0;
	int lineRow = row;
	int lineCol = col;
	switch (direction) {
	case NORTH_WEST:
	    while (lineRow != 0 && lineCol != 0) {
		count++;
		lineRow--;
		lineCol--;
	    }
	    break;
	case NORTH:
	    while (lineRow != 0) {
		count++;
		lineRow--;
	    }
	    break;
	case NORTH_EAST:
	    while (lineRow != 0 && lineCol != 7) {
		count++;
		lineRow--;
		lineCol++;
	    }
	    break;
	case EAST:
	    while (lineCol != 7) {
		count++;
		lineCol++;
	    }
	    break;
	case SOUTH_EAST:
	    while (lineRow != 7 && lineCol != 7) {
		count++;
		lineRow++;
		lineCol++;
	    }
	    break;
	case SOUTH:
	    while (lineRow != 7) {
		count++;
		lineRow++;
	    }
	    break;
	case SOUTH_WEST:
	    while (lineRow != 7 && lineCol != 0) {
		count++;
		lineRow++;
		lineCol--;
	    }
	    break;
	case WEST:
	    while (lineCol != 0) {
		count++;
		lineCol--;
	    }
	    break;
	default:
	    break;
	}

	/* 
	 * Create an array equal to the number of spaces determined above and cycle through the spaces again, creating Move
	 * objects for each space in the line.
	 */
	lineRow = row;
	lineCol = col;
	
	ReversiMove[] line = new ReversiMove[count]; 
	
	for (int i = 0; i < line.length; i++) {
	    
	    // Increment the correct direction.
	    switch (direction) {
	    case NORTH_WEST:
	        lineRow--;
	        lineCol--;
		break;
	    case NORTH:
		lineRow--;
		break;
	    case NORTH_EAST:
		lineRow--;
		lineCol++;
		break;
	    case EAST:
		lineCol++;
		break;
	    case SOUTH_EAST:
		lineRow++;
		lineCol++;
		break;
	    case SOUTH:
		lineRow++;
		break;
	    case SOUTH_WEST:
		lineRow++;
	        lineCol--;
		break;
	    case WEST:
		lineCol--;
		break;
	    default:
		break;
	    }
	    
	    // Create the Move object and add it to the array.
	    line[i] = new ReversiMove(lineRow, lineCol, board.getPieceAtSpace(lineRow, lineCol));
	}

	// Return the array.
	return line;
    }
    private static void printBoard()
    {
	System.out.println();
	System.out.println("  0 1 2 3 4 5 6 7");
	for(int row = 0; row < 8; row++)
	    {
		System.out.print(row + " ");
		for(int col = 0; col < 8; col++)
		    {
			if(isMoveLegal(new ReversiMove(row, col, players.getActivePlayerIndex() + 1), board))
			   {
			       System.out.print("_ ");
			   }
			else if(board.getPieceAtSpace(row, col) == ReversiBoard.EMPTY_SPACE)
			    {
				System.out.print(". ");
			    }
			else if(board.getPieceAtSpace(row, col) == ReversiBoard.BLACK_PIECE)
			    {
				System.out.print("X ");
			    }
			else if(board.getPieceAtSpace(row, col) == ReversiBoard.WHITE_PIECE)
			    {
				System.out.print("O ");
			    }
			
		    }
			System.out.println();
	     }
	System.out.println();
    }

	public static void main(String[] args)
	{
	    
	    board = new ReversiBoard();
	 
	    
	    if(args[0].equalsIgnoreCase("RandomComputer") && args[1].equalsIgnoreCase("RandomComputer"))
	       {
		   players = new PlayerList(2, new RandomComputerPlayer(), new RandomComputerPlayer());
	       }
	    else if(args[0].equalsIgnoreCase("RandomComputer") && args[1].equalsIgnoreCase("Human"))
		{
		    players = new PlayerList(2, new RandomComputerPlayer(), new HumanPlayer());
		}
	    else if(args[0].equalsIgnoreCase("Human") && args[1].equalsIgnoreCase("RandomComputer"))
		{
		    players = new PlayerList(2, new HumanPlayer(), new RandomComputerPlayer());
		}
	    else if(args[0].equalsIgnoreCase("Human") && args[1].equalsIgnoreCase("Human"))
		{
		    players = new PlayerList(2, new HumanPlayer(), new HumanPlayer());
		}
	    
	    gameLoop();
	}	
}
