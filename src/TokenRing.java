
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
	public boolean isActive()
	{
		return this._isActive;
	}
	
	public TokenRing(boolean isActive) 
	{
		this._isActive = isActive;	
	}

}
