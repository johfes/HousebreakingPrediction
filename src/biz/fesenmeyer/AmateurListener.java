package biz.fesenmeyer;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AmateurListener implements UpdateListener {

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {

		int eventTime = Simulation.simulationTime+EventGenerator.planEvent();
		Housebreaking housebreaking = (Housebreaking) arg0[0].getUnderlying();
		Simulation.addToLocationMap(housebreaking.getLocation());
		
		System.out.println("********************************************************************************************************************************");
		System.out.println("WARNUNG: Amateur-Einbruch in "+ housebreaking.getLocation() + 
				"\nHieraus können keine Voraussagen gemacht werden");

		Simulation.addToFel(eventTime, EventGenerator.generateLocation());
		EventGenerator.generateArrest(housebreaking.getLocation());
	}

}
