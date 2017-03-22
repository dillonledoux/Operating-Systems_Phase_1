import java.util.ArrayList;

public class Scheduler{
    
// Class Variables
    SYSTEM system;
    Mem_manager mem_manager;
    
    private Queue sbq1;  //subqueue 1
    private Queue sbq2;  //subqueue 2
    private Queue sbq3;  //subqueue 3
    private Queue sbq4;  //subqueue 4
    
    private Queue blockedQ;

// Constructor    
    public Scheduler(SYSTEM systemIn, Mem_manager mem_managerIn){
        system = systemIn;
        mem_manager = mem_managerIn;

    
        sbq1 = new Queue(15);
        sbq2 = new Queue(15);
        sbq3 = new Queue(15);
        sbq4 = new Queue(15);
        
        blockedQ = new Queue(15);  
    }
	
    
// Core Functions	
   
    
    public int getHighestNonEmptySbqNumber(){
    	if(!sbq1.isEmpty()){
    		return 1;
    	}
    	else if(!sbq2.isEmpty()){
    		return 2;
    	}
    	else if(!sbq3.isEmpty()){
    		return 3;
    	}
    	else if(!sbq4.isEmpty()){
    		return 4;
    	}
    	else{
    		return 0;
    	}
    }
    
    public Queue getSubQ(int number){
    	switch (number){
    		case 1: return sbq1;
    		case 2: return sbq2;
    		case 3: return sbq3;
    		default: return sbq4;
    	}
    }
    
    
    public PCB getNextPCB(){
    	PCB job = getSubQ(getHighestNonEmptySbqNumber()).remove(0);
    	return job;
    }
    
    
    
	public void setup(ArrayList<Integer> list){    
	    PCB job = createPCB(list);
		job.setArrivalTime(system.getClk());
		addToReadyQ(job);
	}	

	public int getNextTask(){
		if(getRQSize() != 0){

			PCB job = getNextPCB();
			int curBurst = job.getCurBurst();
			int quantum = job.getQuantum();
			job.incrCpuShots();
			if(curBurst>quantum){
				System.out.println("aqui1");
				job.setCurBurst(curBurst-quantum);
				job.incrTimeUsed(quantum);
				updateQandT(job);
				endQuantumResch(job);
				return quantum;
				
			}
			else if(curBurst<quantum){
				job.incrTimeUsed(curBurst);
				System.out.println("aqui2");
				if(job.hasMoreBursts()){
					
					moveFromRtoB(job);
					job.resetTurns();
					job.advanceCurBurst();
					return curBurst;
				}
				system.jobTerminated(job);
				return curBurst;
			}
			else{
				job.incrTimeUsed(quantum);
				System.out.println("aqui3");
				if(job.hasMoreBursts()){
					System.out.print("HELP");
					job.advanceCurBurst();
					updateQandT(job);
					moveFromRtoB(job);
					return quantum;
				}
				
				system.jobTerminated(job);
				return curBurst;
			}
		}
		System.out.println("aqui4");
		return 0;							// return of zero indicates no jobs in RQ
	}
	
	
	public void updateQandT(PCB job){
		job.decrementTurns();
        if(job.getTurns()<=1){
            demote(job);
            job.resetTurns();  
        }
        else{
        	job.decrementTurns();
        }
	}

// Queue Mutators
    public void addToReadyQ(PCB job){
    	job.assignTurns(1);
        sbq1.add(job);        
    }

    public void moveFromRtoB(PCB job){
        blockedQ.add(job);
        if(job.getSubQNumber()==4){
        	job.setSubQ(1);
        	job.resetTurns();
        }
        job.incrIOReq();
        job.setTimeFinishIO(system.getClk());

    }
    public void checkBlockedQ(){
    	
		System.out.println("here - 2");
		boolean enoughTime = true;
    	while(enoughTime && !blockedQ.isEmpty()){
    		System.out.println("here -3");
    		PCB job = blockedQ.peek();
    		if(system.getClk() < job.getTimeFinishIO()){
    			enoughTime = false;
    		}
    		else{
    			job = blockedQ.pop();
    			addToSubQ(job.getSubQNumber(), job);
    		}
    	}
    		
    }
    public void demote(PCB job){
        if(job.getSubQNumber()<4){
	        job.setSubQ(job.getSubQNumber()+1);
        }    
    }
  
// Situational Functions
    public PCB createPCB(ArrayList<Integer> list){
        ArrayList<Integer> info = list;
	    int jID = info.remove(0);
	    int jSize = info.remove(0);
	    int cBurst = info.remove(0);
        PCB pcb = new PCB(jID, jSize, cBurst, info, system.getClk()); // adding job id and size
	    return pcb;
    }
     
    public void endQuantumResch(PCB job){
        int qNumber = job.getSubQNumber();
        switch (qNumber) {
            case 1: sbq1.add(job);
                    break;
            case 2: sbq2.add(job);
                    break;
            case 3: sbq3.add(job);
                    break;
            default:sbq4.add(job);
        }
    }
      

	// Q Methods
    public void addToSubQ(int number, PCB job ){
    	if(number == 1){
    		sbq1.add(job);
    	}
    	else if(number == 2){
    		sbq2.add(job);
    	}
    	else if(number == 3){
    		sbq3.add(job);
    	}
    	else{
    		sbq4.add(job);
    	}
    }
        
    public int getTotalPCBs(){
    	int toReturn = (blockedQ.size() + sbq1.size() + sbq2.size()
		+ sbq3.size() + sbq4.size());
    	return toReturn;
    }
    public int getRQSize(){
        int sizeRQ = sbq1.size() + sbq2.size() + sbq3.size() + sbq4.size();
        return sizeRQ;
    }    

    public Queue getBlockedQ(){
    	return blockedQ;
    } 
}