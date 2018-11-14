public class ReversiMove extends Move
{
    private int gamePieceColor;
    private int row  = -1;
    private int column = -1;

    public ReversiMove(int row, int column, int gamePieceColor)
    {
	if(gamePieceColor == ReversiBoard.BLACK_PIECE || gamePieceColor == ReversiBoard.WHITE_PIECE || gamePieceColor == 0)
	    {
		this.gamePieceColor = gamePieceColor;
	    }
	else
	    {
		
	    }
	
	if(row < 0 || row > 7)
	    {
		
	    }
	else
	    {
		this.row = row;
	    }
	
	if(column < 0 || column > 7)
	    {
		
	    }
	else
	    {
		this.column = column;
	    }
    }
    
    public int getGamePieceColor()
    {
	return this.gamePieceColor;
    }

    public int getRow()
    {
	return this.row;
    }

    public int getCol()
    {
	return this.column;
    }
}