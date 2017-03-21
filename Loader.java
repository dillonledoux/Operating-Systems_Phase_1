
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Loader{
	
// Class Variables
	
	private String jobFilePointerCSX = "/home/opsys/OS-I/SP17/17s-ph1-data";
	private String jobFilePointerLocalWin = "C:\\Users\\Dillon LeDoux\\Desktop\\17s-ph1-data.txt";
	private String jobFilePointerLocalMac = "/Users/dillonledoux/Desktop/testData.txt";
	
	private final File jobFile = new File(jobFilePointerLocalMac);
	private Scanner scanner;
	
	private boolean moreJobsInFile = true;
//	private int counter = 0;
	
	protected ArrayList<ArrayList<Integer>> jobQ = new ArrayList<>(); 
		
	SYSTEM system;
	Mem_manager mem_manager;
	Scheduler scheduler;
	
	
		
	
// Constructor
	public Loader(SYSTEM systemIn, Mem_manager mem_managerIn, Scheduler schedulerIn){
	    system = systemIn;
	    mem_manager = mem_managerIn;
	    scheduler = schedulerIn;
	    
	    try{
	    	scanner = new Scanner(jobFile);
	    }
	    catch(IOException e){
	    	System.out.println("Could not load form Job File");
	    	System.exit(1);
	    }
	}
	
// Function Methods     

    public boolean hasMoreJobsInFile(){
      return moreJobsInFile;
    }
   
    public void loadTasks(){
    	int index = 0;
    	boolean canAllocate;
/**/    	System.out.println(scheduler.getTotalPCBs());  
    	while(system.getFreeMemory()>=mem_manager.getMemCutoff()  
    			&& scheduler.getSubQ(1).size()<15 && index<jobQ.size()){
    		
    			canAllocate = mem_manager.allocate(jobQ.get(index).get(1));
    			
    			if(canAllocate){
    				scheduler.setup(jobQ.remove(index));
    			}
    			index++;
    	}
    	ArrayList<Integer> newJob = getNextJob();
    	System.out.println("here");
    	
    	while(system.getFreeMemory()>=mem_manager.getMemCutoff() && scheduler.getTotalPCBs()<15
    			&& moreJobsInFile && newJob.get(0) != 0){
    		canAllocate = mem_manager.allocate(newJob.get(1));
    			if(canAllocate){
    				scheduler.setup(newJob);
    			}
    			else{
    				jobQ.add(newJob);
    			}
    	}
    }
    
// Initialization Methods
    
    public ArrayList<Integer> getNextJob(){
		System.out.println("here");
    	String line = scanner.nextLine();
    	if(scanner.hasNextLine() == false){
    		moreJobsInFile = false;
    	}
    	
        ArrayList<String> stringList = new ArrayList<>(Arrays.asList(line.split("\\W+")));
        stringList.remove(0);
        ArrayList<Integer> jobInfo = new ArrayList<>();
        for(String s: stringList){
        	jobInfo.add(Integer.valueOf(s));
        }
        ;
        return jobInfo;          
     }

     public ArrayList<ArrayList<Integer>> getJobQ(){
    	return jobQ;
     }
     public int getJobQSize(){
    	 return jobQ.size();
     }
     public void addToJobQ(ArrayList<Integer> job){
     	jobQ.add(job);
     }
    
}