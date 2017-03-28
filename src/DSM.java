/**
 * DSM ~ Distributed Shared Memory
 * Represents the DSM layer. 
 * DSM executes in a separate thread.
 * @author patsluth
 * @author charlieroy
 *
 */
public class DSM
{
	private LocalMemory localMemory = null;
	private BroadcastAgent broadcastAgent = null;
	private Process process = null;
	
	public DSM(Process process) 
	{
		this.localMemory = new LocalMemory();
		this.broadcastAgent = new BroadcastAgent(this.localMemory);
		this.process = process;	
	}

	/**
	 * Returns the value of key read from the local memory
	 * @param key
	 * @return value
	 */
	public synchronized Object load(String key, Object defaultValue)
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
	public synchronized void store(final String key, final Object value, final boolean checkForToken, final boolean manyTokens)
	{
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
	}
	
	public void logData()
	{
		if (this.localMemory != null) {
			this.localMemory.logData();
		}
	}

}
