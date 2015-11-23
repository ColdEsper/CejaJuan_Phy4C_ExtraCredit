package PhysicsExtraCredit;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class Cycle {
	Hashtable<String, CycleNode> nodes;
	ArrayList<CycleProcess> processes;
	public float moles;
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
				}
				else if (data.toLowerCase().trim().startsWith("process:")) {
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
			CycleNode newNode = new CycleNode();
			newNode.name = original.start.name;
			processCopy.start = newNode;
			if (!nodes.containsKey(original.start.name)) {
				nodes.put(original.start.name,newNode);
			}
			newNode = new CycleNode();
			newNode.name = original.end.name;
			processCopy.end = newNode;
			if (!nodes.containsKey(original.end.name)) {
				nodes.put(original.end.name,newNode);
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
		}
		//TODO calculations to find as much data as possible about the other points
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
}
