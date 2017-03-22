
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
		return false;
    }    
    public void release(int size){
        system.addFreeSpace(size);
    }
   
// Housekeeping and Logging 

    public int getMemCutoff(){
    	return memCutoff;
    }
    public String memStats(){

    	String toReturn = String.format("\n%-5d   |   %-3d  |  %-3d   |",
    			system.getClk(), system.getFreeMemory(), (SYSTEM.totalMemory-system.getFreeMemory()));
    	return toReturn;
    }

}