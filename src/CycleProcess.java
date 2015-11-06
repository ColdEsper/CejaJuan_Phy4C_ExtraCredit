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
		heatChange = Double.NaN;
		workChange = Double.NaN;
		energyChange = Double.NaN;
		type = ProcessType.UNDEFINED;
		start = null;
		end = null;
	}
	CycleNode start;
	CycleNode end;
	double heatChange;
	double workChange;
	double energyChange;
	ProcessType type;
}
