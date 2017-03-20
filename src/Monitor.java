
public class Monitor
{
    public static void main(String args[]) 
    {
    	int n = 4;

    	Process[] processes = new Process[n];
    	
 	   	for (int i = 0; i < n; i += 1) {
 	   		processes[i] = new Process(i, n);
 	   	}

 	   	for (int i = 0; i < n; i += 1) {
 	   		processes[i].start();
 	   	}
    }   
}
