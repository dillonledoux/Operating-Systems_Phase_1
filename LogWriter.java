import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
	
	private static final String SYS_LOG_PATH = "/Users/dillonledoux/Desktop/SYS_LOG.txt";  //   C:\\Users\\Dillon LeDoux\\Desktop\\SYS_LOG.txt    /Users/dillonledoux/Desktop/SYS_LOG.txt
	private static final String JOB_LOG_PATH = "/Users/dillonledoux/Desktop/JOB_LOG.txt";	// C:\\Users\\Dillon LeDoux\\Desktop\\JOB_LOG.txt
	
	SYSTEM sys;
	Mem_manager mem;
	Scheduler sch;
	Loader ld;

	
	public LogWriter(SYSTEM sysIn, Mem_manager memIn, Scheduler schIn, Loader ldIn){
		sys = sysIn;
		mem = memIn;
		sch = schIn;
		ld = ldIn;
		
		setupSysLog();
		setupJobLog();
	}
	
    public void writeToSysLog(){
        String toWriteMem = mem.memStats();
        String toWriteJobs = "\nNumber of jobs in Job_Q:\t" +ld.getJobQ().size()+ 
                             "\nNumber of jobs in Blocked_Q:\t" +sch.getBlockedQ().size()+
                             "\nNumber of jobs in Ready_Q:\t" +sch.getRQSize()+
                             "\nTotal jobs delivered:\t\t" +sys.getJobsDelivered()+ "\n";
        
        File file = new File(SYS_LOG_PATH);
    	try{
    		FileWriter append = new FileWriter(file, true);
        	append.write(toWriteMem);
        	append.write(toWriteJobs);
        	append.close();
    	}
    	catch(IOException e){
    		System.out.println("Error while writing to SYS_LOG");
    		e.printStackTrace();
    		System.exit(1);
    	}
                                 
     } 
    
    public static void setupSysLog(){
    	String header = "\nSYS_LOG file starting at CSX time of "+SYSTEM.systemStartTime;        
		File file = new File(SYS_LOG_PATH);
		try{
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(header);
			writer.close();
		}
		catch(IOException e){
			System.out.println("Cannot Create SYS_LOG.txt");
		}
		
    }
    public static void setupJobLog(){
        String header = "\nJOB_LOG file starting at CSX time of "+SYSTEM.systemStartTime+ " \n\nJob ID\tEntry Time\tTerm. Time\tEx. Time\tCPU Shots\n";        
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
    
    public void writeToJobLog(PCB job){
        String toWrite = job.jobStats();
        File file = new File(JOB_LOG_PATH);
    	try{
    		FileWriter append = new FileWriter(file, true);
        	append.write(toWrite);
        	append.close();
    	}
    	catch(IOException e){
    		System.out.println("Error writing to JOB_LOG");
    	}
        
    }
    
    
}
