
/**
 * DSM ~ Distributed Shared Memory
 * Represents the DSM layer. 
 * DSM executes in a separate thread.
 * @author patsluth
 *
 */
public class DSM 
{
	private LocalMemory localMemory = null;
	private BroadcastAgent broadcastAgent = null;
	
	public DSM() 
	{
		this.localMemory = new LocalMemory();
		this.broadcastAgent = new BroadcastAgent(this.localMemory);
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
	public void store(String key, Object value)
	{
		if (this.localMemory != null && this.broadcastAgent != null) {
			this.localMemory.store(key, value);
			this.broadcastAgent.broadcast(key, value);
		}
	}
	
	public void logData()
	{
		if (this.localMemory != null) {
			this.localMemory.logData();
		}
	}

}
