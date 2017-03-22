
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SYSTEM{
     
// SYSTEM variables
    private static int CLOCK = 0;          				// virtual clock initialization
     public static String systemStartTime;       // stores the CSX system time when SYSTEM initialized    
     private static int freeMemory = 512;      					// Memory setup initial free space: 512 units
     public static int totalMemory = 512;        				// Memory setup initial used: 0 units
 
     private static final int WRITE_EVERY = 200;
     
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
        System.out.println(systemStartTime);
        mem_manager = new Mem_manager(this);
        scheduler = new Scheduler(this, mem_manager);
        loader = new Loader(this, mem_manager, scheduler);       
        logger = new LogWriter(this, mem_manager, scheduler, loader);
        cpu = new CPU(this, scheduler);
        
        this.simulate();  
        
    }
    
    public static void main(String args[]){
    	SYSTEM sys = new SYSTEM();
    }
       
// SYSTEM Object Methods
   
     
     public void simulate(){
    	int count = 0;
    	 while(loader.hasMoreJobsInFile() || scheduler.getTotalPCBs() != 0){
    		
    		loader.loadTasks();
/*    		System.out.print("sbq1");
    		scheduler.getSubQ(1).printIDs();
    		System.out.print("sbq4");
    		scheduler.getSubQ(4).printIDs();
 */   		
    		//if(scheduler.getRQSize() != 0){ 
    			cpu.execute();
    			System.out.println("Clock value:"+CLOCK);
    			System.out.println("RQ size: "+scheduler.getRQSize());
    			if(scheduler.getBlockedQ().size()>0){System.out.println("BQ size: "+scheduler.getBlockedQ().peek().getJobID());}
    			System.out.println("JQ size: "+loader.getJobQSize());
    		//}
    		scheduler.checkBlockedQ();

    	}	
    	
/**/ 	System.out.println(getJobsDelivered());
     }
     
     public String getSystemStartTime(){
        return systemStartTime;
     }
     public void initSysTime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        systemStartTime = df.format(cal.getTime());     
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
        finishedJob.setIsFinished(true);
    	finishedJob.setTimeDelivered(CLOCK);
    	logger.writeToJobLog(finishedJob);
        mem_manager.release(finishedJob.getJobSize());
        incrJobsDelivered();
        
    }
    public boolean hasJobsIncoming(){
    	return jobsIncoming;
    }

    public void incrSysClock(int value){
    	for(int i = 0; i<value; i++){
    		incrementClk();
    		if(CLOCK % WRITE_EVERY == 0){
    			logger.writeToSysLog();
    		}
    	}
    }
    	
    
}
