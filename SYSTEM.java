import java.util.ArrayList;

public class SYSTEM{
     
// SYSTEM variables
    private static int CLOCK = 0;          				// virtual clock initialization
     protected static String systemStartTime = "start..";       // stores the CSX system time when SYSTEM initialized    
     private static int lastWriteToSysLog = 0;
     private static int freeMemory = 512;      					// Memory setup initial free space: 512 units
     public static int totalMemory = 512;        				// Memory setup initial used: 0 units
 
     private int jobsDelivered = 0;
     private boolean jobsIncoming = true;
     
// Object Variables
     Mem_manager mem_manager;
     Scheduler scheduler;
     Loader loader;
     CPU cpu;
     LogWriter logger;
         
// Constructor
    public SYSTEM(){
        initSysTime();
        
        mem_manager = new Mem_manager(this);
        scheduler = new Scheduler(this, mem_manager);
        loader = new Loader(this, mem_manager, scheduler);       
        cpu = new CPU(this, scheduler);
        logger = new LogWriter(this, mem_manager, scheduler, loader);
        this.simulate();  
        
    }
    
    public static void main(String args[]){
    	SYSTEM sys = new SYSTEM();
    }
       
// SYSTEM Object Methods
   
     
     public void simulate(){
    	loader.loadTasks();
    		
/**/			scheduler.getSubQ(1).printIDs();

System.out.println("total PCBs in system: " +scheduler.getTotalPCBs());
System.out.println("items in JobQ: "+ loader.getJobQSize());
    		if(scheduler.getRQSize() != 0) cpu.execute();

    	//	if(CLOCK  - lastWriteToSysLog >= 100){

    			lastWriteToSysLog = CLOCK;
    			logger.writeToSysLog();
    	//	}
    		
    	
/**/ 	System.out.println("break while loop");
     }
     
     public String getSystemStartTime(){
        return systemStartTime;
     }
     public void initSysTime(){
       /* DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        systemStartTime = df.format(date);     */
     }

  // Clock Methods
     public int getClk(){
         return CLOCK;
      }
      public void addToClk(int value){
         CLOCK += value;
      }
      public void setClk(int time){
     	CLOCK = time;
      }     
      public void incrementClk(){
         CLOCK++;
      }
    
// Memory Methods    
    public int getFreeMemory(){
        return freeMemory;
    }
    public void setFreeMemory(int value){
        freeMemory = value;
    }
    public void addFreeSpace(int value){
        freeMemory += value;
    }

// Jobs Methods 
    public void incrJobsDelivered(){
    	jobsDelivered++;
    }
    public int getJobsDelivered(){
    	return jobsDelivered;
    }
    
    public void jobTerminated(PCB finishedJob){
        logger.writeToJobLog(finishedJob);
        mem_manager.release(finishedJob.getJobSize());
        incrJobsDelivered();
    }
    public boolean hasJobsIncoming(){
    	return jobsIncoming;
    }
    public void setJobsIncoming(boolean value){
    	jobsIncoming = value;
    }
    	
    
}
