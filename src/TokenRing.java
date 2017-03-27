
/**
 * This is the arrangement of the token ring. 
 * The token ring consists of individual TokenRingAgents. 
 * The TokenRing, if active, creates the necessary token and passes it to an initially designated TokenRingAgent. 
 * There can be more than one TokenRing instances, with different token messages.
 * @author patsluth
 * @author charlieroy
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
		}		
		token = new Token(tokenID);
		_TRAs[0].setToken(token);
	}

	
	public boolean isActive()
	{
		return this._isActive;
	}
	
	public TokenRingAgent getTRA(int index) {
		return _TRAs[index];
	}
}
