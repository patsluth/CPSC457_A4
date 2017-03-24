
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
	
	private TokenRingAgent[] _TRAs;
	
	public TokenRing(boolean isActive, Process[] processes, int tokenID) 
	{
		this._isActive = isActive;
		if (!this._isActive) {		//if inactive do nothing else
			return;
		}
		_TRAs = new TokenRingAgent[processes.length];
		

		
		for (int i = 0; i < processes.length; i++) {
			_TRAs[i] = processes[i].getTRA();
			_TRAs[i].addRing(this);
//			_TRAs[i].setToken(token);
		}
		
		token = new Token(tokenID);
		_TRAs[0].setToken(token);
		
		
//		token = new Token(1);
//		for (int i=0; i<processes.length; i++) {
//			this.insert(processes[i].getTRA());			
//		}
//		last.sendToken(token);
		//TODO: Arrange all the agents into a ring
		//		pass the token to some agent
	}
	
//	public void insert(TokenRingAgent someAgent) {
//		if (first == null) {
//			first = someAgent;
//			last = someAgent;
//		} else {
//			last.setSuccessor(someAgent);
//			someAgent.setPredecessor(last);
//			last = someAgent;
//			last.setSuccessor(first);
//			first.setPredecessor(last);
//		}
//	}
	
	public boolean isActive()
	{
		return this._isActive;
	}
	
	public TokenRingAgent getTRA(int index) {
		return _TRAs[index];
	}
}
