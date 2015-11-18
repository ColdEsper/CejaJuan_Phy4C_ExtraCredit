package PhysicsExtraCredit;

//Process is the edge of the graph
public class CycleProcess {
	public enum ProcessType {
		UNDEFINED,
		ADIABATIC,
		ISOTHERMAL,
		ISOBARIC,
		ISOCHORIC,
	}
	CycleProcess () {
		heatChange = Float.NaN;
		workChange = Float.NaN;
		energyChange = Float.NaN;
		type = ProcessType.UNDEFINED;
		start = null;
		end = null;
	}
	public void display () {
		System.out.println("Start:");
		start.display();
		System.out.println();
		System.out.println("End:");
		end.display();
		System.out.println();
		System.out.println("Heat: "+heatChange);
		System.out.println("Work: "+workChange);
		System.out.println("Energy: "+energyChange);
		System.out.println("Type: "+type);
	}
	CycleNode start;
	CycleNode end;
	float heatChange;
	float workChange;
	float energyChange;
	ProcessType type;
}
