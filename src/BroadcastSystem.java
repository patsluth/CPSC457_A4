
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
	
	private BroadcastSystem() 
	{
	}

}
