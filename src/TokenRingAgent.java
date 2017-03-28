import java.util.*;

import com.sun.swing.internal.plaf.synth.resources.synth;
/**
 * Each Processor has a TokenRingAgent object.
 * Each TokenRingAgent executes in a separate thread.
 * @author patsluth
 * @author charlieroy
 *
 */
public class TokenRingAgent extends Thread
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
	
	private List<Runnable> pendingRunnables = new ArrayList<>();
	
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
		
		this.start();
	}
	
	@Override public void run()
	{
		while (true) {
			synchronized (TokenRingAgent.this) {
				
				Runnable runnable = null;
				
				synchronized (this.pendingRunnables) {
					if (this.pendingRunnables.size() > 0) {
						runnable = this.pendingRunnables.remove(0);
					}
				}
				
				if (runnable != null) {
					runnable.run();
				}
			}
		}
	}
	
	public synchronized boolean isActive()
	{
		return this._isActive;
	}	
	
	/**
	 * Returns the unique identifier for the token received from the predecessor
	 * @return
	 */
	public synchronized Token recieveToken(int i)
	{
		synchronized (this.tokens) {
			return this.tokens[i];
		}
	}
	
	/**
	 * Sends the token to the successor
	 */
	public void sendToken(final Token token)
	{
		Runnable runnable = new Runnable() 
		{
			@Override public void run() 
			{
				synchronized (tokenRings) {
					if (tokenRings != null && token != null) {
						
						TokenRing tokenRing = tokenRings.get(token.getID());
						
						synchronized (tokenRing) {
							if (tokenRing != null) {
								
								TokenRingAgent tokenRingAgent = tokenRing.getTRA(ringSuccessor);
								
								if (tokenRingAgent != null) {
									tokenRingAgent.setToken(token);
									//System.out.println("Token passed to process["+ringSuccessor+"].");
									synchronized (tokens) {
										tokens[token.getID()] = null;
									}
								}
							}
						}
					}
				}
			}
		};
		
		synchronized (this.pendingRunnables) {
			this.pendingRunnables.add(runnable);
		}
	}
	
	/**
	 * Used by the token ring to send the token out initially
	 * @param token
	 */
	public synchronized void passAllTokens()
	{
//		Thread thread = new Thread(new Runnable() 
//		{
//			@Override public void run() 
//			{
//				synchronized (TokenRingAgent.this) {
//					for (int i = 0; i < numProcessors; i += 1) {
//						if (tokens[i] != null) {
//							sendToken(tokens[i]);
//						}
//					}
//				}
//			}
//		});
//		thread.start();
		
		synchronized (this.pendingRunnables) {
			this.pendingRunnables.clear();
			
			for (int i = 0; i < this.numProcessors; i += 1) {
				if (this.tokens[i] != null) {
					this.sendToken(this.tokens[i]);
				}
			}
		}
		
		
		
		
		
		
//		Runnable runnable = new Runnable() 
//		{
//			@Override public void run() 
//			{
//				synchronized (TokenRingAgent.this) {
//					for (int i = 0; i < numProcessors; i += 1) {
//						if (tokens[i] != null) {
//							sendToken(tokens[i]);
//						}
//					}
//				}
//			}
//		};
//		
//		synchronized (pendingRunnables) {
//			pendingRunnables.add(runnable);
//		}
	}
	
	public void setToken(final Token token)
	{
		synchronized (this.tokens) {
			this.tokens[token.getID()] = token;
		}
	}
	
	public void addRing(final TokenRing tr) 
	{
		synchronized (this.tokenRings) {
			this.tokenRings.add(index, tr);
		}
		this.index++;
	}
}
