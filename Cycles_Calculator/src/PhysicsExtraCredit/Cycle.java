package PhysicsExtraCredit;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Cycle {
	Hashtable<String, CycleNode> nodes;
	ArrayList<CycleProcess> processes;
	//nodes that have initial values, which will be used for starting 
	//points of calculating the other values
	public double moles;
	public static double GAS_CONSTANT = 8.31;
	public static CycleData loadFile (String fileName) throws IOException {
		CycleData cycData = new CycleData ();
		Scanner readFile = new Scanner(fileName);
		try {
			String data = readFile.nextLine();
			if (data.toLowerCase().trim().startsWith("moles:")) {
				String mol = data.toLowerCase().trim().substring(6);
				cycData.moles = Double.valueOf(mol);
			}
			else if (data.toLowerCase().trim().startsWith("process:")) {
				String procData = data.toLowerCase().substring(8);
				String[] procs = procData.split(",");
				assert(procs.length>=3);
				CycleProcess loadProcess = new CycleProcess();
				for (int i=0;i<procs.length;++i) {
					if (procs[i].toLowerCase().trim().startsWith("start=")) {
						String name = procs[i].toLowerCase().substring(6);
						loadProcess.start= new CycleNode();
						loadProcess.start.name = name;
					} else if (procs[i].toLowerCase().trim().startsWith("end=")) {
						String name = procs[i].toLowerCase().substring(6);
						loadProcess.start= new CycleNode();
						loadProcess.end= new CycleNode();
						loadProcess.end.name = name;
					}
					else if (procs[i].toLowerCase().trim().startsWith("type=")) {
						String type = procs[i].trim().toLowerCase().substring(5);
						if (type.equals("abdiabatic")) {
							loadProcess.type = CycleProcess.ProcessType.ABDIABATIC;
						} else if (type.equals("isothermal")) {
							loadProcess.type = CycleProcess.ProcessType.ISOTHERMAL;
						} else if (type.equals("isobaric")) {
							loadProcess.type = CycleProcess.ProcessType.ISOBARIC;
						} else if (type.equals("isochoric")) {
							loadProcess.type = CycleProcess.ProcessType.ISOCHORIC;
						}
					} else if (procs[i].toLowerCase().startsWith("heat=")) {
						String doub = procs[i].toLowerCase().substring(5);
						loadProcess.heatChange=Double.valueOf(doub);
					} else if (procs[i].toLowerCase().startsWith("work=")) {
						String doub = procs[i].toLowerCase().substring(5);
						loadProcess.workChange=Double.valueOf(doub);
					} else if (procs[i].toLowerCase().startsWith("energy=")) {
						String doub = procs[i].toLowerCase().substring(7);
						loadProcess.energyChange=Double.valueOf(doub);
					} else {
						throw new IOException ("Malformed process data in file "+fileName);
					}
				}
				cycData.processData.add(loadProcess);
			} else if (data.toLowerCase().trim().startsWith("node:")) {
				String nodeData = data.trim().toLowerCase().substring(5);
				String[] procs = nodeData.split(",");
				CycleNode loadNode = new CycleNode();
				for (int i=0;i<procs.length;++i) {
					if (procs[i].toLowerCase().trim().startsWith("name=")) {
						loadNode.name = procs[i].toLowerCase().substring(6);
					} else if (procs[i].toLowerCase().trim().startsWith("pressure=")) {
						String doub = procs[i].toLowerCase().substring(9);
						loadNode.pressure=Double.valueOf(doub);
					} else if (procs[i].toLowerCase().trim().startsWith("temperature=")) {
						String doub = procs[i].toLowerCase().substring(12);
						loadNode.temperature=Double.valueOf(doub);
					} else if (procs[i].toLowerCase().trim().startsWith("volume=")) {
						String doub = procs[i].toLowerCase().substring(7);
						loadNode.temperature=Double.valueOf(doub);
					} else {
						throw new IOException ("Malformed node data in file "+fileName);
					}
					cycData.nodeData.add(loadNode);
					cycData.nodeData.add(loadNode);
				}
			} else {
				throw new IOException ("Malformed line in file "+fileName);
			}
		} finally {
			readFile.close();
		}
		return cycData;
	}
	public Cycle (CycleData data) {
		nodes = new Hashtable<String,CycleNode>();
		processes = new ArrayList<CycleProcess>();
		/* deep copy */
		for (int i=0;i<data.processData.size();++i) {
			CycleProcess original = data.processData.get(i);
			CycleProcess processCopy = new CycleProcess ();
			processCopy.heatChange = original.heatChange;
			processCopy.workChange = original.workChange;
			processCopy.energyChange = original.energyChange;
			if (!nodes.containsKey(original.start.name)) {
				nodes.put(original.start.name,new CycleNode());
			}
			if (!nodes.containsKey(original.end.name)) {
				nodes.put(original.end.name,new CycleNode());
			}
			processCopy.type = data.processData.get(i).type;
			processes.add(processCopy);
		}
		/* deep copy */
		for (int i=0;i<data.nodeData.size();++i) {
			//assumes every node belongs to a process, so node Hashtable should contain every
			//node uninitialized at this point
			assert(nodes.containsKey(data.nodeData.get(i).name));
			CycleNode originalNode = data.nodeData.get(i);
			nodes.get(originalNode.name).pressure = originalNode.pressure;
			nodes.get(originalNode.name).temperature= originalNode.temperature;
			nodes.get(originalNode.name).volume= originalNode.volume;
			nodes.get(originalNode.name).name= originalNode.name;
		}
		//TODO calculations to find as much data as possible about the other points
	}
	public static double energy (double deltaWork,double deltaHeat) {
		return (deltaHeat-deltaWork);
	}
	public static double energy (double moles, double voulmeMolarCapacity, double deltaTemperature) {
		return (moles*voulmeMolarCapacity*deltaTemperature);
	}
	public static double work (double deltaEnergy,double deltaHeat) {
		return (deltaHeat-deltaEnergy);
	}
	public static double heat (double deltaEnergy,double deltaWork) {
		return (deltaEnergy+deltaWork);
	}
	public static double isobaricWork (double pressure,double startVolume, double endVolume) {
		return (pressure*(endVolume-startVolume));
	}
	public static final double ISOCHORIC_WORK = 0.0;
}
