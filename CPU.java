


public class CPU{
	SYSTEM system;
	Scheduler scheduler;

    
	public CPU(SYSTEM systemIn, Scheduler schedulerIn){
		system = systemIn;
		scheduler = schedulerIn;
	}
	
	public void execute(){
		
		int executeTime = scheduler.getNextTask();
		system.incrSysClock(executeTime);
		if(executeTime == 0){
			noJobWait();			
		}	
	}
	
	public boolean noJobWait(){
		if(scheduler.getRQSize()==0 && scheduler.getBlockedQ().size()>0){
			int newclk = scheduler.getBlockedQ().peek().getTimeFinishIO()-system.getClk();
			system.incrSysClock(newclk);
			System.out.println("BLALALLALAHAJLA");
			return true;
		}
		return false;
	}
	
	
}