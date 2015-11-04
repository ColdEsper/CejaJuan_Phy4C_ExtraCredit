import java.util.Hashtable;
import java.util.ArrayList;

public class Cycle {
	public enum ProcessType {
		ABDIABATIC,
		ISOTHERMAL,
		ISOBARIC,
		ISOCHORIC,
	}
	//Process is the edge of the graph
	public class Process {
		Node start;
		Node end;
		double heatChange;
		double workChange;
		double energyChange;
		ProcessType type;
	}
	public class Node {
		double pressure;
		double temperature;
		double volume;
		String name;
	}
	Hashtable<String, Cycle.Node> nodes;
	ArrayList<Cycle.Process> processes;
	//nodes that have initial values, which will be used for starting 
	//points of calculating the other values
	ArrayList<Cycle.Node> startNodes;
	public double moles;
	public static double GAS_CONSTANT = 8.31;
	public Cycle (String fileName) {
		//TODO load file data into nodes and processes
		nodes = new Hashtable<String,Cycle.Node>();
		startNodes = new ArrayList<Cycle.Node>();
		for (int i=0;i<nodes.size();++i) {
			Cycle.Node node = nodes.get(i);
			if (node.pressure != Float.NaN || node.temperature != Float.NaN || node.volume != Float.NaN) {
				startNodes.add(node);
			}
		}
		//TODO calculations to find as much data as possible about the other points
	}
}
