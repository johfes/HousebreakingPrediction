package biz.fesenmeyer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Map.Entry;

import com.espertech.esper.client.EPRuntime;

public class EventGenerator {
	
	public static int planEvent(){
			return generateRandomInt(1, 7);		
	}

	public static void generateEvent(EPRuntime cepRT, String location){
		Housebreaking housebreaking = 
	    new Housebreaking(generateDoorKickedIn(), 
	    		generateMoneyJewelryStolen(), 
	    		generateMoneyJewelryStolen(), 
	    		generateMobileStolen(), 
	    		generateOtherThingsStolen(), location);
		System.out.println("Tag "+Simulation.getSimulationTime() + ": "
	    					+housebreaking);
		cepRT.sendEvent(housebreaking);
	}
	
	public static boolean generateDoorKickedIn(){
		if(generateRandomInt(1, 10) > 7){
			return true;
		}else{
			return false;
		}
	}

	public static boolean generateMoneyJewelryStolen(){
		if(generateRandomInt(1, 10) > 9){
			return false;
		}else{
			return true;
		}
	}

	public static boolean generateMobileStolen(){
		if(generateRandomInt(1, 10) > 7){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean generateOtherThingsStolen(){
		if(generateRandomInt(1, 10) > 7){
			return true;
		}else{
			return false;
		}
	}
	
	public static void generateArrest(String location){
		if(generateRandomInt(1, 10) > 6){
			Arrest arrest = new Arrest(location);
			System.out.println("Verhaftung in "+ location);
			Simulation.getCepRT().sendEvent(arrest);
		}
	}
	
	public static String generateLocation(){
			return "area"+generateRandomInt(1, 2);
	}
	
	public static int generateRandomInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

}

