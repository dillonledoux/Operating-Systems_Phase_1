


public class CPU{
	SYSTEM system;
	Scheduler scheduler;

    
	public CPU(SYSTEM systemIn, Scheduler schedulerIn){
		system = systemIn;
		scheduler = schedulerIn;
	}
	
	public void execute(){
		
		System.out.print("yay");
	

		
		int executeTime = scheduler.getNextTask();
		if(executeTime == 0){
			noJobWait();
		}

		
	
	
	}
	
	public boolean noJobWait(){
		if(scheduler.getRQSize()==0 && scheduler.getBlockedQ().size()>0){
			int newclk = scheduler.getBlockedQ().peek().getTimeFinishIO();
			system.setClk(newclk);
			return true;
		}
		return false;
	}
	
	
}