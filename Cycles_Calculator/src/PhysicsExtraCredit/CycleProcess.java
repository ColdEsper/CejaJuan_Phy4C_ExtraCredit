package PhysicsExtraCredit;

//Process is the edge of the graph
public class CycleProcess {
	public enum ProcessType {
		UNDEFINED,
		ABDIABATIC,
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
	CycleNode start;
	CycleNode end;
	float heatChange;
	float workChange;
	float energyChange;
	ProcessType type;
}
