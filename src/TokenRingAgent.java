
/**
 * Each Processor has a TokenRingAgent object.
 * Each TokenRingAgent executes in a separate thread.
 * @author patsluth
 *
 */
public class TokenRingAgent 
{
	private Object uniqueId;	// a unique identifier for the token
	private boolean isActive;	// if false, then the token ring is non-existent; the value will be true if the token ring is active
	private Object logicalId;	// logical id for the processor on the ring
	private Object ringPredecessor;	// logical id of the predecessor on the ring
	private Object ringSuccessor;	// logical is of the successor
	
	public TokenRingAgent() 
	{
	}
	
	/**
	 * Returns the unique identifier for the token received from the predecessor
	 * @return
	 */
	public Object recieveToken()
	{
		// TODO: implement
		return null;
	}
	
	/**
	 * Sends the token to the successor
	 */
	public void sendToken(Token t)
	{
		// TODO: implement
	}
}
