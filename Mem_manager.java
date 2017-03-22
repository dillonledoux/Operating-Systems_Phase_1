
public class Mem_manager{
	
	SYSTEM system;
	private static final int memCutoff = 5;
	
	public Mem_manager(SYSTEM systemIn){
	    system = systemIn;
	}
	
// Memory Mutator Functions
    public boolean allocate(int size){
        if(system.getFreeMemory() >= size){
            system.setFreeMemory(system.getFreeMemory() - size);
            return true;
        }
		System.out.println("Memory full, not able to allocate.");
		return false;
    }    
    public void release(int size){
        system.addFreeSpace(size);
        System.out.println("" +size+ " memory units released");
        System.out.println("Free space is now: " +system.getFreeMemory()+ " units");
    }
   
// Housekeeping and Logging 

    public int getMemCutoff(){
    	return memCutoff;
    }
    public String memStats(){
            //  @ X time units          Free Space                 Used Space
        return "\n@ " +system.getClk()+ " Time Units:\nFree Space = " +system.getFreeMemory()+ "  |  Used Space = " 
                +(SYSTEM.totalMemory-system.getFreeMemory())+"";
    }

}