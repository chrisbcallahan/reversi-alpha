public interface Board
{
    public static int EMPTY_SPACE = 0;
    public boolean isSpaceEmpty(int x, int y);
    public boolean isBoardFull();
    public int getPieceAtSpace(int x, int y);
    public void placePieceAtSpace(Move move);
}