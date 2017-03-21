import java.util.ArrayList;

public class Queue extends ArrayList<PCB>{

// Class Variables
    public ArrayList<PCB> al_q;

// Constructor
    public Queue(){
        al_q = new ArrayList<PCB>();
    }
    public Queue(int capacity){
    	al_q = new ArrayList<PCB>(capacity);
    }

// Overridden Methods
    public boolean add(PCB job){
       return al_q.add(job);
    }
    public PCB pop(){
        return al_q.remove(0);
    }
    public PCB peek(){
        return al_q.get(0);
    }
    public PCB get(int index){
    	return al_q.get(index);
    }
    public PCB remove(int index){
    	return al_q.remove(index);
    }
    public void printIDs(){
    	
    	System.out.print("\nThe Job ID's are: ");
    	for(PCB j : al_q){
    		System.out.print("" +j.getJobID()+ " ");
    	}
    	System.out.println();
    }



}