


public class CPU{
	SYSTEM system;
	Scheduler scheduler;

    
	public CPU(SYSTEM systemIn, Scheduler schedulerIn){
		system = systemIn;
		scheduler = schedulerIn;
	}
	
	public void execute(){
		
		System.out.print("yay");
		
		try{
			PCB task = scheduler.getNextTask();
			
			int exTime = scheduler.update(task);

			task.incrCpuShots();
			task.incrTimeUsed(exTime);
			system.addToClk(exTime);
			if(task.isFinished()){
				task.setTimeDelivered();
				system.jobTerminated(task);			
			}
		}
		catch(Exception e){
			noJobWait();
		}
	}
	
	public void noJobWait(){
		if(scheduler.getRQSize()==0 && scheduler.getBlockedQ().size()>0){
			int newclk = scheduler.getBlockedQ().peek().getTimeFinishIO();
			system.setClk(newclk);
		}
	}
	
	
}