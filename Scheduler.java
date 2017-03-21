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
    
    private ArrayList<PCB> al1 = new ArrayList<>();
    private ArrayList<PCB> al2 = new ArrayList<>();


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
	
    public int getSize(){
    	if(al1 == null || al1.isEmpty()){
    		return 0;
    	}
    	return al1.size();
    }
    
// Core Functions	
    public int getTotalArListElements(){
    	return al1.size() + al2.size();
    }

    
    public PCB getNextTask(){
    	if(!sbq1.isEmpty()){
    		return sbq2.peek();
    	}
    	else if(!sbq2.isEmpty()){
    		return getSubQ(2).peek();
    	}
    	else if(!sbq3.isEmpty()){
    		return sbq3.peek();
    	}
    	else{
    		return sbq4.peek();
    	}
    }
    
    public boolean pollBlockedQ(){
    	if(blockedQ.peek().getTimeFinishIO()<=system.getClk()){
    		moveFromBtoR(blockedQ.peek());
    		return true;
    	}
    	return false;   	
    }
     
    
	public void setup(ArrayList<Integer> list){    
	    PCB job = createPCB(list);
		job.setArrivalTime();
		addToReadyQ(job);
	}	

	public int update(PCB job){
		int curBurst = job.getCurBurst();
		System.out.println("here");
		if(curBurst>job.getQuantum()){
			job.setCurBurst(curBurst-job.getQuantum());
			updateQandT(job);
			endQuantumResch(job);
			return job.getQuantum();
		}
		else if(curBurst<job.getQuantum()){
			if(job.hasMoreBursts()){
				moveFromRtoB(job);
				job.resetTurns();
				job.updateCurBurst();
				return curBurst;
			}
			job.setIsFinished(true);
//			job.removeFromReadyQ();
			return curBurst;
			
		}
		else{
			if(job.hasMoreBursts()){
				moveFromRtoB(job);
				return job.getQuantum();
			}
			job.setIsFinished(true);
			return curBurst;
		}
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
    	System.out.println("Added to Ready Q");

    	job.assignTurns(1);
        sbq1.add(job);
 /**/       //System.out.println(getSubQ(1).peek().getJobID());
        
        
    }



    public void moveFromRtoB(PCB job){
        switch (job.getSubQNumber()){
            case 1: sbq1.pop();
                    break;
            case 2: sbq2.pop();
                    break;
            case 3: sbq3.pop();
                    break;
            default:sbq4.pop();
             		break;
        }
        blockedQ.add(job);
        job.incrIOReq();
        job.setTimeFinishIO(system.getClk());

    }
    public void moveFromBtoR(PCB job){
        getBlockedQ().remove(0);
        switch (job.getSubQNumber()){
            case 1: sbq1.add(job);
                    break;
            case 2: sbq2.add(job);
                    break;
            case 3: sbq3.add(job);
                    break;
            default:sbq4.add(job);
            		break;
        }
    }
    public void demote(PCB job){
        if(job.getSubQNumber()!=4){job.setSubQ(job.getSubQNumber()+1);}
        switch (job.getSubQNumber()){
            case 1: sbq1.remove(job);
            		sbq2.add(job);
                    break;
            case 2: sbq2.remove(job);
            		sbq3.add(job);
                    break;
            case 3: sbq3.remove(job);
            		sbq4.add(job);
                    break;
            default: break;                 
        }
    }
    public void promote(PCB job){
        if(job.getSubQNumber()!=1){job.setSubQ(job.getSubQNumber()-1);}
        switch (job.getSubQNumber()){
            case 4:sbq4.remove(job);
            		sbq3.add(job);
                    break;
            case 3: sbq3.remove(job);
            		sbq2.add(job);
                    break;
            case 2: sbq2.remove(job);
            		sbq1.add(job);
                    break;
            default: break;                  
        }    
    }
    
// Situational Functions
    public PCB createPCB(ArrayList<Integer> list){
        ArrayList<Integer> info = list;
	    int jID = info.remove(0);
	    int jSize = info.remove(0);
        PCB pcb = new PCB(jID, jSize, info, system.getClk()); // adding job id and size
	    return pcb;
    }
     


    public void endQuantumResch(PCB job){
        int q = job.getSubQNumber();
        switch (q) {
            case 1: getSubQ(1).remove(0);
            		getSubQ(1).add(job);
                    break;
            case 2: getSubQ(2).remove(0);
            		getSubQ(2).add(job);
                    break;
            case 3: getSubQ(3).remove(0);
            		getSubQ(3).add(job);
                    break;
            case 4: getSubQ(4).remove(0);
            		getSubQ(4).add(job);
                    break;
            default: break;
        }
    }
      
	public void removeFromReadyQ(PCB job){
		if(job.getSubQNumber()==1){
			getSubQ(1).remove(0);
	    }
	    else if(job.getSubQNumber()==2){
	    	getSubQ(2).remove(0);
	    }
	    else if(job.getSubQNumber()==3){
	    	getSubQ(3).remove(0);
	    }
	    else{
	    	getSubQ(4).remove(0);
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
        
    public int getSbqSize(int number){
    	if(number == 1){
  //  		if(sbq1 == null || sbq1.isEmpty()){
    			return 0;
  //  		}
  //  		return sbq1.size();   		
    	}
    	else if(number == 2){
    		if(sbq2 == null || sbq2.isEmpty()){
    			return 0;
    		}
    		return sbq2.size();  
    	}
    	else if(number == 3){
    		if(sbq3 == null || sbq3.isEmpty()){
    			return 0;
    		}
    		return sbq3.size();  
    	}
    	else{
    		if(sbq4 == null || sbq4.isEmpty()){
    			return 0;
    		}
    		return sbq4.size();  
    	}
    }
    
    public int getTotalPCBs(){
    	return (blockedQ.size() + sbq1.size() + sbq2.size()
    			+ sbq3.size() + sbq4.size());
    }
    public int getRQSize(){
        int sizeRQ = sbq1.size() + sbq2.size() + sbq3.size() + sbq4.size();
        return sizeRQ;
    }    
    public Queue getSubQ(int number){
    	switch (number){
    		case 1: return sbq1;
    		case 2: return sbq2;
    		case 3: return sbq3;
    		default: return sbq4;
    	}
    }
    public Queue getBlockedQ(){
    	return blockedQ;
    }
}