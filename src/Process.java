import jdk.nashorn.internal.ir.Flags;

/**
 * Represents a process. 
 * Each Process is executed in a separate thread.
 * @author patsluth
 *
 */
public class Process
{	
	private Integer id;
	private int n = 0;
	
	private DSM distributedMemorySystem = null;
	private TokenRingAgent tokenRingAgent = null;
	
	private Thread thread = null;
	
	
	public static String getFlagKey(int processID)
	{
		return String.format("flag %d", processID);
	}
	
	public static String getTurnKey(int processID)
	{
		return String.format("turn %d", processID);
	}
	
	
	
	public Process(int id, int n) 
	{
		final Process _process = this;
		
		this.id = id;
		this.n = n;
		this.distributedMemorySystem = new DSM();
		this.tokenRingAgent = new TokenRingAgent(this.id, false);	// QUESTION 1
		
		this.thread = new Thread(new Runnable() 
		{
			@Override public void run() 
			{
				// TEMP to compile
				int i = _process.id;
				
//				lock() {
				for (Integer j = 0; j < _process.n - 2; j += 1) {	// Loop is repeated N-1 times
					
					// QUESTION 1
					if (!_process.tokenRingAgent.isActive()) {	
						
						_process.distributedMemorySystem.store(getFlagKey(i), j);
						_process.distributedMemorySystem.store(getTurnKey(j), i);
						
						System.out.printf("flag[%d] = %d\n", i, j);
						System.out.printf("turn[%d] = %d\n", j, i);
						
						boolean flag_k_GTE_j = false;
						for (Integer k = 0; k < _process.n; k += 1) {
							if ((Integer)_process.distributedMemorySystem.load(getFlagKey(k), -1) >= j) {
								flag_k_GTE_j = true;
								break;
							}
						}
						Integer turn_i = (Integer)_process.distributedMemorySystem.load(getTurnKey(i), -1);
						
						// WHILE there exists a k such that flag[k] >= j and turn[i] == j
						while (flag_k_GTE_j && turn_i == j) {
							// NOP
						}
						
						System.out.printf("process[%d] has entered the critical section\n", i);
						
					}
					
				}
//				} endLock
				
//				unlock() { // The player writes -1 to his personal board.
				
				_process.distributedMemorySystem.store(getFlagKey(i), -1);
				
				System.out.printf("process[%d] has exited the critical section\n", i);
//				System.out.printf("flag[%d] = %d\n", i, -1);
//				_process.distributedMemorySystem.logData();
			}
		});
	}
	
	public void start()
	{
		if (!this.thread.isAlive()) {
			this.thread.start();
		}
	}
	
	
	
	
//	 public Process(int id, CriticalSection cs) {
//			this.id = id;
//			this.cs = cs;
//		    }	
//		  
//		    public void run() {
//			while(true) {
//			    cs.critical(this.id);
//			    try {
//				Thread.sleep( (int) (Math.random() * 3000));
//			    }
//			    catch (InterruptedException e) {}
//			}
//		    }
//		 
//		private int id;
//		private CriticalSection cs;

}
