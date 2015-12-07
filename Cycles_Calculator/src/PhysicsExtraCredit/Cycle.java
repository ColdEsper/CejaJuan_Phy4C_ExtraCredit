package PhysicsExtraCredit;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class Cycle {
	Hashtable<String, CycleNode> nodes;
	Hashtable<String, ArrayList<Integer> > nodeConnections;
	ArrayList<CycleProcess> processes;
	public float moles;
	public float heatCapacityRatio;
	public float heatCapacityV;
	public float heatCapacityP;
	public final static float R =  8.314f; //this is the ideal gas law units J/(mol*k)
	public static CycleData loadFile (String fileName) throws IOException {
		CycleData cycData = new CycleData ();
		File dataFile = new File(fileName);
		if (!dataFile.isFile()) {
			throw new IOException ("File "+fileName+" non-existant!");
		}
		Scanner readFile = new Scanner(dataFile);
		try {
			while (readFile.hasNextLine()) {
				String data = readFile.nextLine();
				if (data.toLowerCase().trim().startsWith("moles:")) {
					String mol = data.toLowerCase().trim().substring(6);
					cycData.moles = Float.valueOf(mol);
				} else if (data.toLowerCase().trim().startsWith("gamma:")) {
					String gam = data.toLowerCase().trim().substring(6);
					cycData.heatCapacityRatio= Float.valueOf(gam);
				} else if (data.toLowerCase().trim().startsWith("cp:")) {
					String heatP = data.toLowerCase().trim().substring(3);
					cycData.heatCapacityP= Float.valueOf(heatP);
				} else if (data.toLowerCase().trim().startsWith("cv:")) {
					String heatV = data.toLowerCase().trim().substring(3);
					cycData.heatCapacityV= Float.valueOf(heatV);
				} else if (data.toLowerCase().trim().startsWith("process:")) {
					String procData = data.toLowerCase().substring(8);
					String[] procs = procData.split(",");
					assert(procs.length>=3);
					CycleProcess loadProcess = new CycleProcess();
					for (int i=0;i<procs.length;++i) {
						String procProp = procs[i].toLowerCase().trim();
						if (procProp.startsWith("start=")) {
							String name = procProp.toUpperCase().substring(6);
							loadProcess.start= new CycleNode();
							loadProcess.start.name = name;
						} else if (procs[i].toLowerCase().trim().startsWith("end=")) {
							String name = procProp.toUpperCase().substring(4);
							loadProcess.end= new CycleNode();
							loadProcess.end.name = name;
						}
						else if (procProp.startsWith("type=")) {
							String type = procProp.substring(5);
							if (type.equals("adiabatic")) {
								loadProcess.type = CycleProcess.ProcessType.ADIABATIC;
							} else if (type.equals("isothermal")) {
								loadProcess.type = CycleProcess.ProcessType.ISOTHERMAL;
							} else if (type.equals("isobaric")) {
								loadProcess.type = CycleProcess.ProcessType.ISOBARIC;
							} else if (type.equals("isochoric")) {
								loadProcess.type = CycleProcess.ProcessType.ISOCHORIC;
							}
						} else if (procProp.startsWith("heat=")) {
							String doub = procProp.substring(5);
							loadProcess.heatChange=Float.valueOf(doub);
						} else if (procProp.startsWith("work=")) {
							String doub = procProp.substring(5);
							loadProcess.workChange=Float.valueOf(doub);
						} else if (procProp.startsWith("energy=")) {
							String doub = procProp.substring(7);
							loadProcess.energyChange=Float.valueOf(doub);
						} else {
							throw new IOException ("Malformed process data in file "+fileName);
						}
					}
					cycData.processData.add(loadProcess);
				} else if (data.toLowerCase().trim().startsWith("node:")) {
					String nodeData = data.trim().toLowerCase().substring(5);
					String[] props = nodeData.split(",");
					CycleNode loadNode = new CycleNode();
					for (int i=0;i<props.length;++i) {
						String nodeProperty = props[i].toLowerCase().trim();
						if (nodeProperty.startsWith("name=")) {
							loadNode.name = nodeProperty.toUpperCase().substring(5);
						} else if (nodeProperty.startsWith("pressure=")) {
							String doub = nodeProperty.substring(9);
							loadNode.pressure=Float.valueOf(doub);
						} else if (nodeProperty.startsWith("temperature=")) {
							String doub = nodeProperty.substring(12);
							loadNode.temperature=Float.valueOf(doub);
						} else if (nodeProperty.startsWith("volume=")) {
							String doub = nodeProperty.substring(7);
							loadNode.temperature=Float.valueOf(doub);
						} else {
							throw new IOException ("Malformed node data in file "+fileName);
						}
					}
					cycData.nodeData.add(loadNode);
				} else {
					throw new IOException ("Malformed line\n"+data+"\nin file "+fileName);
				}
			}
		} finally {
			readFile.close();
		}
		return cycData;
	}
	public void updateNode (CycleNode node,ArrayList<Integer> nextProcesses) throws PhysicsException {
		boolean updated = false;
		if (!Float.isNaN(node.pressure)) {
			if (!Float.isNaN(node.volume)) {
				if (!Float.isNaN(node.temperature)) {
					if(!Float.isNaN(moles)) {
						if (moles != calcMoles(node.pressure,node.volume,node.temperature)) {
							throw new PhysicsException("Ideal gas law violated!");
						}
					} else {
						moles = calcMoles(node.pressure,node.volume,node.temperature);
						for (String nodeName: nodes.keySet()) {
							if (node!=nodes.get(nodeName)) {
								updateNode(nodes.get(nodeName),nextProcesses);
							}
						}
					}
				}
			}
		}
		if (updated) {
			for (int i=0;i<nodeConnections.get(node.name).size();++i) {
				int nextProc = nodeConnections.get(node.name).get(i);
				boolean alreadyNext = false;
				for (int j=0;j<nextProcesses.size();++j) {
					if (nextProcesses.get(i)==nextProc) {
						alreadyNext=true;
						break;
					}
				}
				if (!alreadyNext) {
					nextProcesses.add(nextProc);
				}
			}
		}
	}
	//throws an error if inputted values generate conflicting values
	//through the usage of thermodynamics equations
	public void calculateCycle () throws PhysicsException {
		ArrayList<Integer> nextProcesses= new ArrayList<Integer>();
		//initialize nextProcess list with all edges so all edges get verified
		for (int i=0;i<processes.size();++i) {
			nextProcesses.add(i);
		}
		//calculate values from initial values for nodes first
		for (String nodeName: nodes.keySet()) {
			updateNode(nodes.get(nodeName),nextProcesses);
		}
		while (!nextProcesses.isEmpty()) {
			CycleProcess proc = processes.get(nextProcesses.get(0));
			nextProcesses.remove(0);
			boolean processUpdate = false;
			//calculate remaining part of first law of thermodynamics if there's two knowns
			if (!Float.isNaN(proc.heatChange)) {
				if (!Float.isNaN(proc.workChange)) {
					if (!Float.isNaN(proc.energyChange)) {
						if (heat(proc.energyChange, proc.workChange) != proc.heatChange 
						|| work(proc.energyChange,proc.heatChange) != proc.workChange 
						|| energy(proc.workChange,proc.heatChange) != proc.energyChange) {
							throw new PhysicsException("First law of thermodynamics violated! in process "
									+ "connecting "+proc.start.name+" to "+proc.end.name);
						}
					} else {
						proc.energyChange = energy(proc.workChange,proc.heatChange);
						processUpdate = true;
					}
				} else if (!Float.isNaN(proc.energyChange)) {
					proc.workChange = work(proc.energyChange,proc.heatChange);
					processUpdate= true;
				}
			} else if (!Float.isNaN(proc.workChange)) {
				if (!Float.isNaN(proc.energyChange)) {
					proc.heatChange = heat(proc.energyChange,proc.workChange);
					processUpdate= true;
				}
			}
			switch (proc.type) {
				case ADIABATIC:
					if (Adiabatic.update(proc,this)) {
						processUpdate = true;
					}
					break;
				case ISOTHERMAL:
					break;
				case ISOBARIC:
					break;
				case ISOCHORIC:
					break;
			}
			if (processUpdate) {
				//update nodes of process,
				updateNode(nodes.get(proc.start),nextProcesses); 
				updateNode(nodes.get(proc.end),nextProcesses); 
			}
		}
	}
	public Cycle (CycleData data) throws PhysicsException {
		nodes = new Hashtable<String,CycleNode>();
		nodeConnections = new Hashtable<String,ArrayList<Integer>>();
		processes = new ArrayList<CycleProcess>();
		if (!Float.isNaN(data.moles)) {
			moles = data.moles;
		}
		if (!Float.isNaN(data.heatCapacityRatio)) {
			heatCapacityRatio = data.heatCapacityRatio;
		}
		if (!Float.isNaN(data.heatCapacityV)) {
			heatCapacityV = data.heatCapacityV;
		}
		if (!Float.isNaN(data.heatCapacityP)) {
			heatCapacityP = data.heatCapacityP;
		}
		/* deep copy */
		for (int i=0;i<data.processData.size();++i) {
			CycleProcess original = data.processData.get(i);
			CycleProcess processCopy = new CycleProcess ();
			processCopy.heatChange = original.heatChange;
			processCopy.workChange = original.workChange;
			processCopy.energyChange = original.energyChange;
			CycleNode newNode = new CycleNode();
			newNode.name = original.start.name;
			processCopy.start = newNode;
			if (!nodes.containsKey(original.start.name)) {
				nodes.put(original.start.name,newNode);
				nodeConnections.put(original.start.name,new ArrayList<Integer>());
			}
			nodeConnections.get(original.start.name).add(i);
			newNode = new CycleNode();
			newNode.name = original.end.name;
			processCopy.end = newNode;
			if (!nodes.containsKey(original.end.name)) {
				nodes.put(original.end.name,newNode);
				nodeConnections.put(original.end.name,new ArrayList<Integer>());
			}
			nodeConnections.get(original.end.name).add(i);
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
		}
		//TODO uncomment when calculateCycle is complete
		//calculateCycle();
	}
	public static float energy (float deltaWork,float deltaHeat) {
		return (deltaHeat-deltaWork);
	}
	public static float energy (float moles, float voulmeMolarCapacity, float deltaTemperature) {
		return (moles*voulmeMolarCapacity*deltaTemperature);
	}
	public static float work (float deltaEnergy,float deltaHeat) {
		return (deltaHeat-deltaEnergy);
	}
	public static float heat (float deltaEnergy,float deltaWork) {
		return (deltaEnergy+deltaWork);
	}
	public static float isobaricWork (float pressure,float startVolume, float endVolume) {
		return (pressure*(endVolume-startVolume));
	}
	public static final float ISOCHORIC_WORK = 0.0f;
	public static float calcMoles (float pressure, float volume, float temperature) {
		return (pressure*volume)/(temperature*R);
	}
}
