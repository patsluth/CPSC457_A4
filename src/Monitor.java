
public class Monitor
{
    public static void main(String args[]) 
    {
    	int n = 10;
    	/*
    	 * int questionNumber
    	 * Valid values 1, 3, 4 relating to their questions
    	 * 1 = no token ring; failure expected		//not working in this version yet
    	 * 3 = one token ring; success expected		//not working in this version yet
    	 * 4 = n token rings; success expected
    	 */
    	int questionNumber = 4;
    	
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
    }   
}
