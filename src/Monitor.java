
public class Monitor
{
    public static void main(String args[]) 
    {
    	int n = 10; //number of processes
    	/*
    	 * int questionNumber
    	 * Valid values 1, 3, 4 relating to their questions
    	 * 1 = no token ring; failure expected		//working
    	 * 3 = one token ring; success expected		//working
    	 * 4 = n token rings; success expected		//working
    	 */
    	int questionNumber = 3;
    	
    	if ((questionNumber != 1) && ((questionNumber != 3) && (questionNumber != 4))) {
    		System.out.println("Please change the question number in the source code to 1, 3, or 4 depending on what you want to run.");
    		System.exit(0);
    	}
    	
    	Process[] processes = new Process[n];
    	
 	   	for (int i = 0; i < n; i += 1) {
 	   		processes[i] = new Process(i, n, questionNumber);
 	   	}

 	   	for (int i = 0; i < n; i += 1) {
 	   		processes[i].start();
 	   	}
 	   	
 	   	if (questionNumber == 1) {
 	   		//No token ring
 	   	} else if (questionNumber == 3) {//1 token ring
 	   		new TokenRing(true, processes, 0);
 	   	} else if (questionNumber == 4) {//n token rings
 	   		for (int i = 0; i < n; i += 1) {
 	   			new TokenRing(true, processes, i);
 	   		}
 	   	}
 	   	
 	   	//Closes the processes at the end
 	   	if (questionNumber != 1) {
	 	   	boolean allFinished = true;
	 	   	while (true) {
	 	   		allFinished = true;
		 	   	for (int i = 0; i < n; i += 1) {
		 	   		if (!processes[i].finished) {
		 	   			allFinished = false;
		 	   			break;
		 	   		}
		 	   	}
		 	   	if (allFinished) {
		 	   		for (int i = 0; i < n; i += 1) {
		 	   			processes[i].kill();
		 	   		}
		 	   		System.out.println("All processes have been killed");
		 	   		System.exit(0);
		 	   	}
	 	   	}
    	}
    }   
}
