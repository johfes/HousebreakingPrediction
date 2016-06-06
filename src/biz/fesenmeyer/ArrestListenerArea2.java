package biz.fesenmeyer;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ArrestListenerArea2 implements UpdateListener {

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {
		System.out.println("Aufgrund einer Verhaftung findet vorerst kein weiterer Einbruch in area2 statt");
	    Integer entryToDelete = null;
	    boolean found = false;
	    for(Entry<Integer, ArrayList<String>> entry : Simulation.FEL.entrySet()) {
	    	  Integer key = entry.getKey();
	    	  if(key != Simulation.simulationTime){
		    	  ArrayList<String> list = entry.getValue();
		    	  for(String str : list){
		    		  if("area2".equalsIgnoreCase(str)){
		    			  entryToDelete = key;
		    			  found = true;
		    			  break;
		    		  }
		    	  }   
	    	  } 	 
	    }
	    
	    if(found){
			  	Simulation.removeFromFel(entryToDelete, "area2");
			  	System.out.println("Ein geplanter Einbruch in area2 am Tag "+entryToDelete+ " findet nun nicht statt.");			  	

				String location = "area1";	
				int eventTime = Simulation.simulationTime+EventGenerator.planEvent();
				Simulation.addToFel(eventTime, location);
	    }

	}

}
