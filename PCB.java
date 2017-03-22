
import java.util.ArrayList;

public class PCB {
	
// Global variables	
	private int jobID;
	private int jobSize;		// memory required for job
	
	private ArrayList<Integer> totalPredBursts;		// the burst length for each predicted job
	private int curBurst;		// current burst length
	
	private int timeArrival;	// time job entered the system
	private int timeDelivered;       // time job terminated
	private int timeUsed;		// cumulative CPU time used by the job
	private int timeFinishIO;
	private int IoReq;          // number of I/O requests
	private int cpuShots;       // number of shots the job gets at the cpu
	//private boolean isFinished = false;
	
	private int subQ = 0;       //current subqueue
	private int subQTurns = 0;  //turns spent in given subqueue;
	
// Constructor
	public PCB(int id, int size, int cBurst, ArrayList<Integer> bursts){
		jobID = id;
		jobSize = size;	
		totalPredBursts = bursts;

		curBurst = cBurst;
	}

//Functions
    public String jobStats(){
        String stats;
        stats = String.format("%-3d    |  %-5d  |    %-5d    |    %-4d   |  %-2d   |", 
        		jobID, timeArrival, timeDelivered, ((IoReq*10)+timeUsed), cpuShots);

        
        return stats;
    }
    	
//Setters
	public void setTimeFinishIO(int clkIn){
		timeFinishIO = clkIn+10;
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
	public void setArrivalTime(int time){
		timeArrival = time;
	}
	public void setTimeDelivered(int time){
		timeDelivered = time;
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
            default: subQTurns = 2147483647;
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

	public int getCurBurst(){
		return curBurst;
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
    public void advanceCurBurst(){
    	curBurst = totalPredBursts.remove(0);
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
            default: subQTurns = 2147483647;
                    break;
        }
	}

	public int getCPUShots(){
		return cpuShots;
	}
	


}

