import java.util.*;
/**
 * Each Processor has a TokenRingAgent object.
 * Each TokenRingAgent executes in a separate thread.
 * @author patsluth
 * @author charlieroy
 *
 */
public class TokenRingAgent 
{
	private Token[] tokens;          		//the unique identifier for the token?
//	private Object uniqueId = null;
	private List<TokenRing> tokenRings = new ArrayList<TokenRing>();;
	
	private int processorID = -1;		
	private boolean _isActive = false;		

	private int ringPredecessor;
	private int ringSuccessor;
	private int index = 0;
	private int numProcessors;
	
	public TokenRingAgent(int processorID, boolean isActive, int numProcessors) 
	{
		this.processorID = processorID;
		this._isActive = isActive;
		tokens = new Token[numProcessors];
		for (int i=0; i<numProcessors; i += 1) {
			tokens[i] = null;
		}
		this.numProcessors = numProcessors;
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
	public synchronized Token recieveToken(int i)
	{
		return this.tokens[i];
	}
	
	/**
	 * Sends the token to the successor
	 */
	public synchronized void sendToken(Token token)
	{
		tokenRings.get(token.getID()).getTRA(ringSuccessor).setToken(token);
//		System.out.println("Token passed to process["+ringSuccessor+"].");
		this.tokens[token.getID()] = null;
	}
	
	/**
	 * Used by the token ring to send the token out initially
	 * @param token
	 */
	
	public void passAllTokens(){
		for (int i = 0; i < numProcessors; i += 1) {
			if (this.tokens[i] != null) {
				this.sendToken(this.tokens[i]);
			}
		}
	}
	
	public void setToken(Token token) {
		this.tokens[token.getID()] = token;
	}
	
	public void addRing(TokenRing tr) {
		tokenRings.add(index, tr);
		index++;
	}
}
