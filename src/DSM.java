import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.swing.internal.plaf.synth.resources.synth;

/**
 * DSM ~ Distributed Shared Memory
 * Represents the DSM layer. 
 * DSM executes in a separate thread.
 * @author patsluth
 * @author charlieroy
 *
 */
public class DSM extends Thread
{
	private LocalMemory localMemory = null;
	private BroadcastAgent broadcastAgent = null;
	private Process process = null;
	
	private List<Runnable> pendingRunnables = new ArrayList<>();
	
	public DSM(Process process) 
	{
		this.localMemory = new LocalMemory();
		this.broadcastAgent = new BroadcastAgent(this.localMemory);
		this.process = process;	
		
		this.start();
	}
	
	@Override public void run()
	{
		while (true) {
			Runnable runnable = null;
			
			synchronized (this.pendingRunnables) {
				if (this.pendingRunnables.size() > 0) {
					runnable = this.pendingRunnables.remove(0);
				}
			}
			
			if (runnable != null) {
				synchronized (this) {
					runnable.run();
				}
			}
		}
	}

	/**
	 * Returns the value of key read from the local memory
	 * @param key
	 * @return value
	 */
	public Object load(String key, Object defaultValue)
	{
		if (this.localMemory != null) {
			return this.localMemory.load(key, defaultValue);
		}

		return null;
	}
	
	/**
	 * Writes value for key in the local memory and broadcasts a message to all other
	 * @param key
	 * @param value
	 */
	public void store(final String key, final Object value, final boolean checkForToken, final boolean manyTokens)
	{
//		Thread thread = new Thread(new Runnable() 
//		{
//			@Override public void run() 
//			{
//				synchronized (DSM.this) {
//					// while (not have the token) { wait for the token }
//					int tokenValue = 0;
//					while (checkForToken) {
//						if (manyTokens) {
//							tokenValue = (Integer)value;
//						}
//						if (process.hasToken(tokenValue)) {
//							break;
//						}
//					} 
//					if (localMemory != null && broadcastAgent != null) {
//						localMemory.store(key, value);
//						broadcastAgent.broadcast(key, value);
//					}
//
//					// Send the token onwards after writing
//					// Helpful Breakpoint here for testing
//					if (checkForToken) {
//						process.getTRA().sendToken(process.getTRA().recieveToken(tokenValue)); //sends the token onwards, q3
//					}
//				}
//			}
//		});
//		thread.start();
		
		
		
		
//		Runnable runnable = new Runnable() 
//		{
//			@Override public void run() 
//			{
				// while (not have the token) { wait for the token }
				int tokenValue = 0;
				while (checkForToken) {
					if (manyTokens) {
						tokenValue = (Integer)value;
					}
					if (process.hasToken(tokenValue)) {
						break;
					}
				} 
				if (localMemory != null && broadcastAgent != null) {
					localMemory.store(key, value);
					broadcastAgent.broadcast(key, value);
				}

				// Send the token onwards after writing
				// Helpful Breakpoint here for testing
				if (checkForToken) {
					synchronized (process) {
						TokenRingAgent tokenRingAgent = process.getTRA();
						if (tokenRingAgent != null) {
							tokenRingAgent.sendToken(tokenRingAgent.recieveToken(tokenValue)); //sends the token onwards, q3
						}
					}
				}
//			}
//		};
		
//		synchronized (this.pendingRunnables) {
//			this.pendingRunnables.add(runnable);
//		}
	}
	
	public void logData()
	{
		if (this.localMemory != null) {
			this.localMemory.logData();
		}
	}

}
