import java.util.ArrayList;

import com.sun.swing.internal.plaf.synth.resources.synth;

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
	
	private ArrayList<TokenRingAgent> _TRAs;
	
	public TokenRing(boolean isActive, Process[] processes, int tokenID) 
	{
		this._isActive = isActive;
		if (!this._isActive) {		//if inactive do nothing else
			return;
		}
		this._TRAs = new ArrayList<>();
		for (int i = 0; i < processes.length; i++) {
			TokenRingAgent tokenRingAgent = processes[i].getTRA();
			this._TRAs.add(tokenRingAgent);
			tokenRingAgent.addRing(this);
		}		
		token = new Token(tokenID);
		this._TRAs.get(0).setToken(token);
	}

	public synchronized boolean isActive()
	{
		return this._isActive;
	}
	
	public synchronized TokenRingAgent getTRA(int index) 
	{
		synchronized (this._TRAs) {
			return this._TRAs.get(index);
		}
	}
}
