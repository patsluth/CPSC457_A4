
/**
 * Each Processor has a TokenRingAgent object.
 * Each TokenRingAgent executes in a separate thread.
 * @author patsluth
 *
 */
public class TokenRingAgent 
{
	private Token token = null;
	private Object uniqueId = null;		
	
	
	
	
	
	private int processorID = -1;		
	private boolean _isActive = false;	
	public boolean isActive()
	{
		return this._isActive;
	}
	
//	private Object logicalId = null;			
	
	private TokenRingAgent ringPredecessor = null;	
	private TokenRingAgent ringSuccessor = null;		
	
	public TokenRingAgent(int processorID, boolean isActive) 
	{
		this.processorID = processorID;
		this._isActive = isActive;
	}
	
	/**
	 * Returns the unique identifier for the token received from the predecessor
	 * @return
	 */
	public Token recieveToken()
	{
		return this.token;
	}
	
	/**
	 * Sends the token to the successor
	 */
	public void sendToken(Token token)
	{
		if (this.ringSuccessor != null) {
			this.ringSuccessor.token = token;
		}
	}
}
