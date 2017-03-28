
/**
 * Represents a token message.
 * @author patsluth
 * @author charlieroy
 *
 */
public class Token 
{
	private Integer uniqueID = null;
	
	public Token(Integer id) 
	{
		this.uniqueID = id;
	}

	public synchronized int getID()
	{
		return uniqueID;
	}
}
