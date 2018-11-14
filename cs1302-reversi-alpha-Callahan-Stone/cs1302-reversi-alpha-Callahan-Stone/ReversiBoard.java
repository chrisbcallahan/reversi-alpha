public class ReversiBoard implements Board
{
    public static final int BLACK_PIECE = 1;
    public static final int WHITE_PIECE = 2;
    private int[][] gameBoard = new int[8][8];
    
    public ReversiBoard()
    {
	for(int i = 0; i<gameBoard.length; i++)
	{
	    for(int z = 0; z<gameBoard[i].length; z++)
	    {
		gameBoard[i][z] = EMPTY_SPACE;
	    }
	} 
	gameBoard[3][3] = WHITE_PIECE; 
	gameBoard[3][4] = BLACK_PIECE;
	gameBoard[4][3] = BLACK_PIECE;
	gameBoard[4][4] = WHITE_PIECE;
    }

    public boolean isSpaceEmpty(int row, int col)
    {
	if(gameBoard[row][col] != 0) return false;
	else return true;
    }
    public boolean isBoardFull()
    {
	for(int i = 0; i<gameBoard.length; i++)
	{
	    for(int z = 0; z < gameBoard[i].length; z++)
	    {
		if(gameBoard[i][z] == 0)return false;
	    }
	}
	return true;
    }
    public int getPieceAtSpace(int row, int col)
    {
	return gameBoard[row][col];
    }
    public void placePieceAtSpace(Move move)
    {
	if(move instanceof ReversiMove)
	{
	    ReversiMove rMove =(ReversiMove)move;
		int row = rMove.getRow();
		int col = rMove.getCol();
		int gamePieceColor = rMove.getGamePieceColor();

		gameBoard[row][col] = gamePieceColor;
	}
    }


}