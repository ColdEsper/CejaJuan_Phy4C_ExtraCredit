package PhysicsExtraCredit;

import java.util.ArrayList;

public class CycleNode {
	CycleNode () {
		pressure = Float.NaN;
		temperature = Float.NaN;
		volume = Float.NaN;
		name = "";
	}
	public void display () {
		System.out.println("Node Name: "+name);
		System.out.println("Pressure: "+pressure);
		System.out.println("Temperature: "+temperature);
		System.out.println("Volume: "+volume);
	}
	float pressure;
	float temperature;
	float volume;
	String name;
}
