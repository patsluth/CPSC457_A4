import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the implementation of the broadcasting mechanism between processors. 
 * One BroadcastSystem object is shared between all BroadcastAgents. 
 * The BroadcastSystem executes in a separate thread.
 * @author patsluth
 *
 */
public final class BroadcastSystem 
{
	// NOTES
	// You may implement this using sockets, but this is not required. 
	// It is sufficient to implement some delays to simulate sending and receiving messages. 
	
	private static BroadcastSystem _broadcastSystem = null;
	public synchronized static BroadcastSystem getSharedInstance()
	{
		if (BroadcastSystem._broadcastSystem == null) {
			BroadcastSystem._broadcastSystem = new BroadcastSystem();
		}
		
		return BroadcastSystem._broadcastSystem;
	}
	
	private ConcurrentLinkedQueue<BroadcastAgent> broadcastAgents = null;
	
	private BroadcastSystem() 
	{
		this.broadcastAgents = new ConcurrentLinkedQueue<>();
	}
	
	public void addBroadcastAgent(BroadcastAgent broadcastAgent)
	{
		if (broadcastAgent != null) {
			this.broadcastAgents.add(broadcastAgent);
		}
	}
	
	public void removeBroadcastAgent(BroadcastAgent broadcastAgent)
	{
		if (broadcastAgent != null && this.broadcastAgents.contains(broadcastAgent)) {
			this.broadcastAgents.remove(broadcastAgent);
		}
	}

	public void broadcast(String key, Object value, BroadcastAgent sourceBroadcastAgent)
	{
		if (sourceBroadcastAgent != null && this.broadcastAgents.contains(sourceBroadcastAgent)) {
			for (BroadcastAgent broadcastAgent : this.broadcastAgents) {
				// TODO: filter out source?
				if (broadcastAgent != sourceBroadcastAgent) {
					broadcastAgent.receive(key, value, sourceBroadcastAgent);
				}
			}
		}
	}
}
