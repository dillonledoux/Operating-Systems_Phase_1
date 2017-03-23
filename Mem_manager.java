/*
 * The Mem_manager serves to manage the memory use of
 * the system by allocating memory to jobs if there is
 * enough available and by releasing memory of the jobs
 * who have terminated.
 */
public class Mem_manager{
	
//		---Class Variables---		
	SYSTEM system;
	
	// Defines the point at which the memory is considered full
	private static final int memCutoff = 5;	
	
//		---Constructor---		
	public Mem_manager(SYSTEM systemIn){
	    system = systemIn;
	}
	
//		---Memory Mutators---
 
	//allocates free space to jobs if available
	public boolean allocate(int size){
        if(system.getFreeMemory() >= size){
            system.setFreeMemory(system.getFreeMemory() - size);
            return true;
        }
		return false;
    } 
	//releases the memory occupied by tasks which have terminated
    public void release(int size){
        system.addFreeSpace(size);
    }
   
//		---Getter and Logging--- 
    public int getMemCutoff(){
    	return memCutoff;
    }
    //collects the memory statistics at the time invoked
    public String memStats(){
    	String toReturn = String.format("%-5d   |   %-3d  |  %-3d   |",
    			system.getClk(), system.getFreeMemory(), 
    			(SYSTEM.TOTAL_MEMORY-system.getFreeMemory()));
    	return toReturn;
    }
}