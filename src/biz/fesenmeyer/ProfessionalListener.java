package biz.fesenmeyer;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ProfessionalListener implements UpdateListener {

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {

		int eventTime = Simulation.getSimulationTime()+EventGenerator.planEvent();
		Housebreaking housebreaking = (Housebreaking) arg0[0].getUnderlying();
		Simulation.addToLocationMap(housebreaking.getLocation());

		System.out.println("********************************************************************************************************************************");
		System.out.println("WARNUNG: Professionaler Einbruch in " +housebreaking.getLocation()+
				"\nInnerhalb der nächsten 7 Tage wird ein Einbruch in der gleichen Gegend erwartet.");

		Simulation.addToFel(eventTime, housebreaking.getLocation());
		EventGenerator.generateArrest(housebreaking.getLocation());
	}

}
