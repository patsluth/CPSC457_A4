
/**
 * Provides the implementation of the broadcast mechanism needed by DSM. 
 * Each BroadcastAgent executes in a separate thread.
 * @author patsluth
 *
 */
public class BroadcastAgent 
{
	private LocalMemory localMemory = null;
	
	public BroadcastAgent(LocalMemory localMemory) 
	{
		this.localMemory = localMemory;
		
		BroadcastSystem.getSharedInstance().addBroadcastAgent(this);
	}

	/**
	 * Send a store
	 * @param message
	 */
	public void broadcast(String key, Object value)
	{
		BroadcastSystem.getSharedInstance().broadcast(key, value, this);
	}
	
	/**
	 * Receive a store
	 */
	public void receive(String key, Object value, BroadcastAgent sourceBroadcastAgent)
	{
		try {
			Thread.sleep( (int) (Math.random() * 100)); 				//wait 0-100ms
		} catch (InterruptedException e) {
			System.out.println("Broadcast Agent Interrupted: " + e);
		}
		if (this.localMemory != null) {
			this.localMemory.store(key, value);
		}
	}
}
