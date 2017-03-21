
import java.util.ArrayList;

public class PCB {
	
// Global variables
	private int clk;
	
	private int jobID;
	private int jobSize;		// memory required for job
	
	private int numberPredBursts; // bursts predicted at the start of the job
	private ArrayList<Integer> totalPredBursts;		// the burst length for each predicted job
	private int curBurst;		// current burst length
	
	private int timeArrival;	// time job entered the system
	private int timeDelivered;       // time job terminated
	private int timeUsed;		// cumulative CPU time used by the job
	private int timeFinishIO;
	private int IoReq;          // number of I/O requests
	private int cpuShots;       // number of shots the job gets at the cpu
	private boolean isFinished = false;
	
	private int subQ = 0;       //current subqueue
	private int subQTurns = 0;  //turns spent in given subqueue;
	
// Constructor
	public PCB(int id, int size, ArrayList<Integer> bursts, int clkIn){
		jobID = id;
		jobSize = size;	
		totalPredBursts = bursts;
		clk = clkIn;
	}

//Functions
    public String jobStats(){
        String stats;
        // format:
        // job ID   EntryTime   TerminationTime    ExecTime    CpuShots
        stats = "\n" +jobID+ "   " +timeArrival+ "   " +timeDelivered+ 
                "   " +((IoReq*10)+timeUsed)+ "   " +cpuShots+ "";
        return stats;
    }
    	
//Setters
	public void setTimeFinishIO(int clk){
		timeFinishIO = clk+10;
	}
    
    public void setIsFinished(boolean bool){
    	isFinished = bool;
    }
    
    public void setTotalPredBursts(int[] numbers){
		ArrayList<Integer> intList = new ArrayList<Integer>();
		for (int i = 0; i < numbers.length; i++)
		{
		    intList.add(numbers[i]);
		}
		totalPredBursts = intList;		
	}

	public void setCurBurst(int number){
		curBurst = number;
	}
	public void setArrivalTime(){
		timeArrival = clk;
	}
	public void setTimeDelivered(){
		timeDelivered = clk;
	}
	public void setTimeUsed(int time){
		timeUsed = time;
	}


	public void setSubQ(int number){
	    subQ = number;
	}
    public void assignTurns(int n){
        switch (n){
            case 1: subQTurns = 3;
                    break;
            case 2: subQTurns = 5;
                    break;
            case 3: subQTurns = 6;
                    break;
            case 4: subQTurns = 2147483647;
                    break;
            default: subQTurns = -1;
                    break;
        }
    }
	public void incrIoRequests(){
	    IoReq++;
	}

		
// Getters
	public boolean hasMoreBursts(){
		if(totalPredBursts.isEmpty()){
			return false;
		}
		return true;
	}
	
	public boolean isFinished(){
		return isFinished;
	}
	
	public int getTimeFinishIO(){
		return timeFinishIO;
	}
	
	public ArrayList<Integer> getTotalPredBursts(){
		return totalPredBursts;
	}
	
	public int getJobID(){
		return jobID;
	}
	public int getJobSize(){
		return jobSize;
	}
	public int getNextBurst(){
		return totalPredBursts.remove(0);	    		
	}
	public int getCurBurst(){
		return totalPredBursts.get(0);
	}
	public int getTimeArrival(){
		return timeArrival;
	}
	public int getTimeUsed(){
		return timeUsed;
	}
	public int getQuantum(){
	    if(subQ==1){
	        return 20;
	    }
	    else if(subQ==2){
	        return 30;
	    }
	    else if(subQ==3){
	        return 50;
	    }
	    else{
	        return 80;
	    }
	}
	public int getTurns(){
	    return subQTurns;
	}
    public int getSubQNumber(){
        return subQ;
    }

// Mutators 
    public void updateCurBurst(){
    	if(getTotalPredBursts().isEmpty()){
    		setCurBurst(-1);
    		setIsFinished(true);   		
    	}
    	else{
    		setCurBurst(getNextBurst());
    	}
 
    }
    
    public void incrementTurns(){
	    subQTurns++;
	}
	public void decrementTurns(){
	    subQTurns--;
	}
	public void incrTimeUsed(){
	    timeUsed++;
	}
	public void incrTimeUsed(int value){
	    timeUsed += value;
	}
	public void incrCpuShots(){
	    cpuShots++;
	}
	public void incrSubQ(){
	    if(subQ<=3){
	        subQ++;
	    }
	}
	public void incrIOReq(){
	    IoReq++;
	}
	public void resetTurns(){
	    switch (subQ){
            case 1: subQTurns = 3;
                    break;
            case 2: subQTurns = 5;
                    break;
            case 3: subQTurns = 6;
                    break;
            case 4: subQTurns = 2147483647;
                    break;
            default: subQTurns = -1;
                    break;
        }
	}
	public void curBurstFinished(){
	    totalPredBursts.remove(0);
	    numberPredBursts--;
	}



}

