import java.util.concurrent.locks.Lock;

/**
 * Represents a process. 
 * Each Process is executed in a separate thread.
 * @author patsluth
 *
 */
public class Process
{	
	private Integer id;
	
	private DSM distributedMemorySystem = null;
	private TokenRingAgent tokenRingAgent = null;
	
	private Thread thread = null;

	public Process(final int i, final int n) 
	{
		final Process _process = this;
		
		this.id = i;
		this.distributedMemorySystem = new DSM(this);
		this.tokenRingAgent = new TokenRingAgent(this.id, false, n);	// QUESTION 1
//		this.tokenRingAgent = new TokenRingAgent(this.id, true); 	//QUESTION 3
		
		this.thread = new Thread(new Runnable() 
		{
			@Override public void run() 
			{
				for (Integer j = 0; j <= n - 2; j += 1) {	// Loop is repeated N-1 times

					_process.distributedMemorySystem.store(LocalMemory.getFlagKey(i), j);	// flag[i] = j;
					_process.distributedMemorySystem.store(LocalMemory.getTurnKey(j), i);	// turn[j] = i;
					
					//System.out.printf("flag[%d] = %d\n", i, j);
					//System.out.printf("turn[%d] = %d\n", j, i);
					
					// WHILE there exists a k such that flag[k] >= j and turn[i] == j
					while (true) {

						boolean flag_k_GTE_j = false;
						for (Integer k = 0; k < n; k += 1) {
//							int temp = (Integer)_process.distributedMemorySystem.load(LocalMemory.getFlagKey(k), -1);
							if (((Integer)_process.distributedMemorySystem.load(LocalMemory.getFlagKey(k), -1) >= j) && (k != i)){
								flag_k_GTE_j = true;
								break;
							}
						}
//						Integer turn_i = (Integer)_process.distributedMemorySystem.load(LocalMemory.getTurnKey(i), -1);
//						
//						if (!(flag_k_GTE_j && turn_i == j)) {
//							break;
//						}
						Integer turn_j = (Integer)_process.distributedMemorySystem.load(LocalMemory.getTurnKey(j), -1);
						
						if (!(flag_k_GTE_j && turn_j == i)) {
							break;
						} else if (tokenRingAgent.recieveToken() != null) { //you are stuck in a loop and don't need to write
							tokenRingAgent.sendToken(tokenRingAgent.recieveToken());
						}
					}
					System.out.println("process["+i+"] has succeeded on level "+j+".");
										
				}
				System.out.printf("process[%d] has entered the critical section\n", i);

				try {
					Thread.sleep(1000); 				//wait 1s; simulates working on something in the critical section
				} catch (InterruptedException e) {
					System.out.println("Process["+id+"] interrupted:" + e);
				}
				
				System.out.printf("process[%d] has exited the critical section\n", i);
				_process.distributedMemorySystem.store(LocalMemory.getFlagKey(i), -1);
				
//				System.out.printf("flag[%d] = %d\n", i, -1);
//				_process.distributedMemorySystem.logData();
				while (true) {
					if (tokenRingAgent.recieveToken() != null) { //if you get the token
						tokenRingAgent.sendToken(tokenRingAgent.recieveToken());	//pass it onwards
					}
				}
			}
		});
	}
	
	public void start()
	{
		if (!this.thread.isAlive()) {
			this.thread.start();
		}
	}
	
	public TokenRingAgent getTRA() {
		return this.tokenRingAgent;
	}
	
	/**
	 * Checks if this process' tokenRingAgent has a token with id i
	 * @param tokenID
	 */
	public boolean hasToken(int tokenID) {
		if (this.tokenRingAgent.recieveToken() != null) {
			return (this.tokenRingAgent.recieveToken().getID() == tokenID);
		} else {
			return false;
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
