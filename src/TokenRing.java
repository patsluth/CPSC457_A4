
/**
 * This is the arrangement of the token ring. 
 * The token ring consists of individual TokenRingAgents. 
 * The TokenRing, if active, creates the necessary token and passes it to an initially designated TokenRingAgent. 
 * There can be more than one TokenRing instances, with different token messages.
 * @author patsluth
 *
 */
public class TokenRing 
{
	private Token token;
	private boolean _isActive = false;	
	
	private TokenRingAgent first = null;
	private TokenRingAgent last = null;
	
	public TokenRing(boolean isActive, Process[] processes) 
	{
		this._isActive = isActive;
		if (!this._isActive) {		//if inactive do nothing else
			return;
		}
		
		token = new Token(1);
		for (int i=0; i<processes.length; i++) {
			this.insert(processes[i].getTRA());			
		}
		last.sendToken(token);
		//TODO: Arrange all the agents into a ring
		//		pass the token to some agent
	}
	
	public void insert(TokenRingAgent someAgent) {
		if (first == null) {
			first = someAgent;
			last = someAgent;
		} else {
			last.setSuccessor(someAgent);
			someAgent.setPredecessor(last);
			last = someAgent;
			last.setSuccessor(first);
			first.setPredecessor(last);
		}
	}
	
	public boolean isActive()
	{
		return this._isActive;
	}
}
