public abstract class Player
{
    private String name;

    public abstract ReversiMove determineMove(int playerIndex, ReversiBoard board);
    
    public abstract boolean isHuman();
}
