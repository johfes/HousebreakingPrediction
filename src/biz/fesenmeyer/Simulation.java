package biz.fesenmeyer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class Simulation {
	/**
	 * Future Event List.
	 */
	public static final SortedMap<Integer, ArrayList<String>> FEL = new TreeMap<>();
	public static final HashMap<String, Integer> LOCATION_MAP = new HashMap<>();
	public static int simulationTime = 0;
	public static int simulationDurance;
	public static EPRuntime cepRT;
	
	public static void main(String[] args) {
		initCEP();

	    Scanner s = new Scanner(System.in);
	    System.out.print("Wieviele Tage sollen Einbrüche beobachtet werden?");
	    simulationDurance = s.nextInt();
	    addToFel(1, EventGenerator.generateLocation());
	    System.out.println("Simulationsbeginn");
	    
		while(true){
			System.out.println("********************************************************************************************************************************");
			
		    for(Entry<Integer, ArrayList<String>> entry : FEL.entrySet()) {
		    	  final Integer key = entry.getKey();
		    	  final Object value = entry.getValue();

		    	  System.out.println(key + " => " + value);
		    }
		    
	    	  simulationTime = FEL.firstKey();
	    	  if(simulationTime > simulationDurance){
	    		  break;
	    	  }
			System.out.println("********************************************************************************************************************************");
		    
		    ArrayList<String> areas = FEL.get(FEL.firstKey());
		    for (String area: areas){
		    	EventGenerator.generateEvent(cepRT, area);
		    }
		    FEL.remove(simulationTime);
		    
		}

		System.out.println("********************************************************************************************************************************");
		System.out.println("Simulationsende");
		System.out.println("********************************************************************************************************************************");		
		System.out.println("Statistik");
		System.out.println("Anzahl Einbrüche");
	    for(Entry<String, Integer> entry : LOCATION_MAP.entrySet()) {
	    	  final String key = entry.getKey();
	    	  final Integer value = entry.getValue();

	    	  System.out.println(key + " => " + value);
	    }
		System.out.println("********************************************************************************************************************************");
		
	}
	
	public static void initCEP(){
	    Configuration cepConfig = new Configuration();
	    // register objects the engine will have to handle
	    cepConfig.addEventType("Housebreaking",
	    		Housebreaking.class.getName());
	    cepConfig.addEventType("Arrest",
	    		Arrest.class.getName());
		// setup the engine
		EPServiceProvider cep = EPServiceProviderManager.
									getProvider("CEPEngine",cepConfig);
		cepRT = cep.getEPRuntime();
		//  register EPL statements
		EPAdministrator cepAdm = cep.getEPAdministrator();

		EPStatement cepStatement1 = cepAdm.createEPL("select * from " +
		                                "Housebreaking.win:length(1) " +
		                                "where NOT doorKickedIn=true "+
		                                "and NOT otherThingsStolen=true");

		EPStatement cepStatement2 = cepAdm.createEPL("select * from " +
		                                "Housebreaking.win:length(1) " +
		                                "where doorKickedIn=true "+
		                                "or otherThingsStolen=true");
		
		EPStatement cepStatement3 = cepAdm.createEPL("select * from " +
        "pattern [every(Housebreaking(location='area1') -> Arrest(location='area1'))]");
		EPStatement cepStatement4 = cepAdm.createEPL("select * from " +
        "pattern [every(Housebreaking(location='area2') -> Arrest(location='area2'))]");

		cepStatement1.addListener(new ProfessionalListener());
		cepStatement2.addListener(new AmateurListener());		
		cepStatement3.addListener(new ArrestListenerArea1());
		cepStatement4.addListener(new ArrestListenerArea2());
	}

	public static void addToFel(Integer key, String area){
		if(FEL.containsKey(key)){
			ArrayList<String> areaList= FEL.get(key);
			areaList.add(area);
		} else {
			ArrayList<String> areaList = new ArrayList<String>();
			areaList.add(area);
			FEL.put(key,areaList);
		}
	}
	
	public static void addToLocationMap(String area){
		if(LOCATION_MAP.containsKey(area)){
			int number= LOCATION_MAP.get(area);
			LOCATION_MAP.put(area, number+1);
		} else {
			LOCATION_MAP.put(area, 1);
		}
	}

	public static void removeFromFel(Integer key, String type){
  	  ArrayList<String> list = (ArrayList<String>) FEL.get(key);
  	  if(list.size() > 1){
  		list.remove(type);
  		FEL.replace(key, list);
  	  } else{
		  FEL.remove(key);
  	  }
	}
	
}


