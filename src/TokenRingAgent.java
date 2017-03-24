import java.util.*;
/**
 * Each Processor has a TokenRingAgent object.
 * Each TokenRingAgent executes in a separate thread.
 * @author patsluth
 *
 */
public class TokenRingAgent 
{
	private Token token = null;          		//the unique identifier for the token?
//	private Object uniqueId = null;
	private List<TokenRing> tokenRings = new ArrayList<TokenRing>();;
	
	private int processorID = -1;		
	private boolean _isActive = false;		

	private int ringPredecessor;
	private int ringSuccessor;
	private int index = 0;		
	
	public TokenRingAgent(int processorID, boolean isActive, int numProcessors) 
	{
		this.processorID = processorID;
		this._isActive = isActive;
		if (processorID == 0) {
			this.ringPredecessor = numProcessors-1;
			this.ringSuccessor = this.processorID+1;
		} else if (processorID == numProcessors-1) {
			this.ringPredecessor = this.processorID-1;
			this.ringSuccessor = 0;
		} else {
			this.ringPredecessor = this.processorID-1;
			this.ringSuccessor = this.processorID+1;
		}
	}
	
	public boolean isActive()
	{
		return this._isActive;
	}	
	
	/**
	 * Returns the unique identifier for the token received from the predecessor
	 * @return
	 */
	public Token recieveToken()
	{
//		if (this.token != null) {
//			return this.token;
//		} else {
//			return new Token(-1);
//		}
		return this.token;
	}
	
	/**
	 * Sends the token to the successor
	 */
	public void sendToken(Token token)
	{
		tokenRings.get(token.getID()).getTRA(ringSuccessor).setToken(token);
		this.token = null;
//		if (this.ringSuccessor != null) {
//			this.ringSuccessor.token = token;
//		}
//		this.token = null;						//We no longer have the token either
	}
	
	/**
	 * Used by the token ring to send the token out initially
	 * @param token
	 */
	
	public void setToken(Token token) {
		this.token = token;
	}
	
	public void addRing(TokenRing tr) {
		tokenRings.add(index, tr);
		index++;
	}
}
