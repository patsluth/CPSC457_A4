
/**
 * Represents the DSM layer. 
 * DSM executes in a separate thread.
 * @author patsluth
 *
 */
public class DSM 
{
	private LocalMemory localMemory;
	private BroadcastAgent broadcastAgent;
	
	public DSM() 
	{
	}

	/**
	 * Returns the value of x read from the local memory
	 * @param x
	 * @return value
	 */
	public Object load(Object x)
	{
		// TODO: implement
		return null;
	}
	
	/**
	 * Writes v into x in the local memory and broadcasts a message to all other
	 * @param x
	 * @param v
	 */
	public void store(Object x, Object v)
	{
		// TODO: implement
	}

}
