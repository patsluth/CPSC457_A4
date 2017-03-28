/**
 * Represents a process. 
 * Each Process is executed in a separate thread.
 * @author patsluth
 * @author charlieroy
 *
 */
public class Process
{	
	private Integer id;
	public boolean finished = false;
	
	private DSM distributedMemorySystem = null;
	private TokenRingAgent tokenRingAgent = null;
	
	private Thread thread = null;
	
	private boolean checkFlags = false;		//decides whether we have to check for a token before writing flags
	private boolean checkKeys = false;		//decides whether we have to check for a token before writing keys
	private boolean tokenRingActive = false;//denotes whether there is an active token ring in the system
	private boolean manyTokens = false;
	
	public Process(final int i, final int n, int questionNumber) 
	{
		if (questionNumber == 1) {
			//All values are already correct
		} else if (questionNumber == 3) {
			checkFlags = true;
			checkKeys = true;
			tokenRingActive = true;
		} else if (questionNumber == 4) {
			checkKeys = true;
			tokenRingActive = true;
			manyTokens = true;
		}
		
		this.id = i;
		this.distributedMemorySystem = new DSM(this);
		this.tokenRingAgent = new TokenRingAgent(this.id, tokenRingActive, n); 	//QUESTION 3
		
		this.thread = new Thread(new Runnable() 
		{
			@Override public void run() 
			{
				synchronized (Process.this) {
					for (Integer j = 0; j <= n - 2; j += 1) {	// Loop is repeated N-1 times

						distributedMemorySystem.store(LocalMemory.getFlagKey(i), j, checkFlags, manyTokens);	// flag[i] = j;
						distributedMemorySystem.store(LocalMemory.getTurnKey(j), i, checkKeys, manyTokens);	// turn[j] = i;
						
						//System.out.printf("flag[%d] = %d\n", i, j);
						//System.out.printf("turn[%d] = %d\n", j, i);
						
						// WHILE there exists a k such that flag[k] >= j and turn[i] == j
						while (true) {

							boolean flag_k_GTE_j = false;
							for (Integer k = 0; k < n; k += 1) {
								if (((Integer)distributedMemorySystem.load(LocalMemory.getFlagKey(k), -1) >= j) && (k != i)){
									flag_k_GTE_j = true;
									break;
								}
							}
							Integer turn_j = (Integer)distributedMemorySystem.load(LocalMemory.getTurnKey(j), -1);
							
							if (!(flag_k_GTE_j && turn_j == i)) {
								break;
							}
//							Token temp = tokenRingAgent.recieveToken();
//							if (temp != null) { //you are stuck in a loop and don't need to write
//								tokenRingAgent.sendToken(tokenRingAgent.recieveToken());
//							}
							if (tokenRingActive) {
								tokenRingAgent.passAllTokens();
							}
						}
						System.out.println("process["+i+"] has succeeded on level "+j+".");
											
					}
					System.out.printf("process[%d] has entered the critical section\n", i);

					try {
						Thread.sleep(100); 				//wait 100ms; simulates working on something in the critical section
					} catch (InterruptedException e) {
						System.out.println("Process["+id+"] interrupted:" + e);
					}
					
					System.out.printf("process[%d] has exited the critical section\n", i);
					distributedMemorySystem.store(LocalMemory.getFlagKey(i), -1, checkFlags, manyTokens);
					
//					System.out.printf("flag[%d] = %d\n", i, -1);
//					_process.distributedMemorySystem.logData();
					finished = true; //signal that process is done
					while (tokenRingActive) {
//						if (_process.getTRA().recieveToken() != null) { //if you get the token
//							_process.getTRA().sendToken(_process.getTRA().recieveToken());	//pass it onwards
//						}
						tokenRingAgent.passAllTokens();
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
	
	public void kill(){
		this.tokenRingActive = false;
	}
	
	public TokenRingAgent getTRA() {
		return this.tokenRingAgent;
	}
	
	/**
	 * Checks if this process' tokenRingAgent has a token with id i
	 * @param tokenID
	 */
	public synchronized boolean hasToken(int tokenID) {
//		if (this.tokenRingAgent.recieveToken(tokenID) != null) {
//			return (this.tokenRingAgent.recieveToken().getID() == tokenID);
//		} else {
//			return false;
//		}
		return (this.tokenRingAgent.recieveToken(tokenID) != null);
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
