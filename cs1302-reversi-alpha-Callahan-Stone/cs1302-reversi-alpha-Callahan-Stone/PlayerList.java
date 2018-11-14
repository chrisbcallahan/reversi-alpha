public class PlayerList
{

    Player [] players;
    int activePlayerIndex;
    
    public PlayerList(int numOfPlayers, Player player1, Player player2)
    {
	this.activePlayerIndex = 0;
	players = new Player[numOfPlayers];
	
	players[0] = player1;
	players[1] = player2;
    }
    
    public void advanceActivePlayerIndex()
    {
	activePlayerIndex++;
	if(activePlayerIndex >= players.length)
	    {
		activePlayerIndex = 0;
	    }
    }
    public int getActivePlayerIndex()
    {
	return activePlayerIndex;
    }
    public Player getPlayer(int index)
    {
	return players[index];
    }


}