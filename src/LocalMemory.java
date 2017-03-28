import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents the local replica
 * @author patsluth
 *
 */
public class LocalMemory 
{
	private ConcurrentMap<String, Object> data = null;
	
	public LocalMemory() 
	{
		this.data = new ConcurrentHashMap<>();
	}
	
	/**
	 * Returns the value for key
	 * @param key
	 * @return value
	 */
	public synchronized Object load(String key, Object defaultValue)
	{
		return this.data.getOrDefault(key, defaultValue);
	}
	
	/**
	 * Stores the value for key to memory
	 * @param key
	 * @param value
	 */
	public synchronized void store(String key, Object value)
	{
		this.data.put(key, value);
	}

	
	
	public synchronized static String getFlagKey(int processID)
	{
		return String.format("flag %d", processID);
	}
	
	public synchronized static String getTurnKey(int processID)
	{
		return String.format("turn %d", processID);
	}

	
	public void logData()
	{
		for (String key : this.data.keySet()) {
			System.out.printf("\tkey[%s] = %s\n", key, this.data.get(key));
		}
	}
}
