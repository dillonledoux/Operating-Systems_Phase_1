/*
 * ---Description---
 * The LogWriter serves to construct and write the log files
 * of the system in a neatly formatted manner. 
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
	
//		---Class Variables---	
	
	private static final String SYS_LOG_PATH = "SYS_LOG";  
	private static final String JOB_LOG_PATH = "JOB_LOG";
	
	SYSTEM sys;
	Mem_manager mem;
	Scheduler sch;
	Loader ld;

//		---Constructor---	
	
	public LogWriter(SYSTEM sysIn, Mem_manager memIn, Scheduler schIn, Loader ldIn){
		sys = sysIn;
		mem = memIn;
		sch = schIn;
		ld = ldIn;
		
		setupSysLog();
		setupJobLog();
	}
	
//		---SYS_LOG Setup and Write---	
	/**
	 * Creates a new file titles SYS_LOG if its not already created
	 * and writes the header of the file containing some organization
	 * information.
	 */
	public static void setupSysLog(){
    	String header1 = "SYS_LOG file starting at CSX time of "+SYSTEM.systemStartTime;        
		String header2 = "\n\nVirtual |  Free  |  Used  |  Job  | Blocked | Ready |   Jobs    |"
						+  "\n Clock  | Memory | Memory | Queue |  Queue  | Queue | Delivered |"
						+  "\n*****************************************************************\n";						
    	File file = new File(SYS_LOG_PATH);	    	
		try{
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(header1);
			writer.write(header2);
			writer.close();
		}
		catch(IOException e){
			System.out.println("Cannot Create SYS_LOG.txt");
		}		
    }
    /**
     * This method writes the system's current statistics when invoked
     */
	public void writeToSysLog(){
        String toWriteMem = mem.memStats();
        String toWriteJobs = String.format("  %-2d   |    %-2d   |  %-2d   |    %-3d    |", 
        						ld.getJobQ().size(),sch.getBlockedQ().size(),sch.getRQSize(),
        						sys.getJobsDelivered());
        String dottedLine = "\n-----------------------------------------------------------------\n";		     		
        File file = new File(SYS_LOG_PATH);
    	try{
    		FileWriter append = new FileWriter(file, true);
        	append.write(toWriteMem);
        	append.write(toWriteJobs);
        	append.write(dottedLine);
        	append.close();
    	}
    	catch(IOException e){
    		System.out.println("Error while writing to SYS_LOG");
    		e.printStackTrace();
    	}                                 
     }
        
//		---JOB_LOG Setup and Write---  	
	/**
	 * Creates a new file titles JOB_LOG if its not already created
	 * and writes the header of the file containing some organization
	 * information.
	 */
    public static void setupJobLog(){
        String header = "JOB_LOG file starting at CSX time of "+SYSTEM.systemStartTime+ 
        			 " \n\nJob ID | Time of |   Time of   | Execution |  CPU  |"
        			 +  "\nNumber | Arrival | Termination |    Time   | Shots |"
        			 +  "\n****************************************************\n";
        File file = new File(JOB_LOG_PATH);
        try{    
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(header);
			writer.close();		    
		}
		catch (IOException e){
		    System.out.println("Error while initializing JOB_LOG");    	
		}
    }
    /**
     * Given a job, this method writes the job's statistics when invoked
     */
    public void writeToJobLog(PCB job){
        String toWrite = job.jobStats();
        String dottedLine ="\n----------------------------------------------------\n";
        File file = new File(JOB_LOG_PATH);
    	try{
    		FileWriter append = new FileWriter(file, true);
    		append.write(toWrite);
    		append.write(dottedLine);
        	append.close();
    	}
    	catch(IOException e){
    		System.out.println("Error writing to JOB_LOG");
    	}        
    }             
}