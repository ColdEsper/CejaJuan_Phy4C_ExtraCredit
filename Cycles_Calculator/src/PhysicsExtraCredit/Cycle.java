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
	public final static float R =  8.3144598f; //this is the ideal gas law units J/(mol*k)
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
					CycleProcess loadProcess = new CycleProcess();
					for (int i=0;i<procs.length;++i) {
						String procProp = procs[i].toLowerCase().trim();
						if (procProp.startsWith("start=")) {
							String name = procProp.toUpperCase().substring(6);
							loadProcess.start= new CycleNode();
							loadProcess.start.name = name;
						} else if (procProp.startsWith("end=")) {
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
							loadNode.volume=Float.valueOf(doub);
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
		//calculate pressure
		if (!Float.isNaN(moles) && !Float.isNaN(node.volume) && !Float.isNaN(node.temperature)) {
			if(!Float.isNaN(node.pressure)) {
				if (node.pressure != (moles*R*node.temperature)/node.volume) {
					throw new PhysicsException("Ideal gas law violated!");
				}
			} else {
				node.pressure = (moles*R*node.temperature)/node.volume;
				for (String nodeName: nodes.keySet()) {
					if (node!=nodes.get(nodeName)) {
						updateNode(nodes.get(nodeName),nextProcesses);
					}
				}
			}
		//calculate volume
		} else if (!Float.isNaN(node.pressure) && !Float.isNaN(moles) && !Float.isNaN(node.temperature)) {
			if(!Float.isNaN(node.volume)) {
				if (node.volume!= (moles*R*node.temperature)/node.pressure) {
					throw new PhysicsException("Ideal gas law violated!");
				}
			} else {
				node.volume = (moles*R*node.temperature)/node.pressure;
				for (String nodeName: nodes.keySet()) {
					if (node!=nodes.get(nodeName)) {
						updateNode(nodes.get(nodeName),nextProcesses);
					}
				}
			}
		//calculate moles
		} else if (!Float.isNaN(node.pressure) && !Float.isNaN(node.volume) && !Float.isNaN(node.temperature)) {
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
		//calculate temperature
		} else if (!Float.isNaN(node.pressure) && !Float.isNaN(node.volume) && !Float.isNaN(moles)) {
			if(!Float.isNaN(node.temperature)) {
				if (node.temperature != (node.pressure*node.volume)/(moles*R)) {
					throw new PhysicsException("Ideal gas law violated!");
				}
			} else {
				node.temperature = (node.pressure*node.volume)/(moles*R);
				for (String nodeName: nodes.keySet()) {
					if (node!=nodes.get(nodeName)) {
						updateNode(nodes.get(nodeName),nextProcesses);
					}
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
		//Calculate all heat capacities from any single one of them
		if (!Float.isNaN(heatCapacityV)) {
			if (Float.isNaN(heatCapacityP)) {
				heatCapacityP = heatCapacityV + R;
			}
			if (Float.isNaN(heatCapacityRatio)) {
				heatCapacityRatio = heatCapacityP/heatCapacityV;
			} else if (heatCapacityRatio != heatCapacityP/heatCapacityV) {
				throw new PhysicsException("gamma didn't match Cp/Cv");
			}
		} else if (!Float.isNaN(heatCapacityP)) {
			heatCapacityV = heatCapacityP - R;
			if (Float.isNaN(heatCapacityRatio)) {
				heatCapacityRatio = heatCapacityP/heatCapacityV;
			} else if (heatCapacityRatio != heatCapacityP/heatCapacityV) {
				throw new PhysicsException("gamma didn't match Cp/Cv");
			}
		} else if (!Float.isNaN(heatCapacityRatio)) {
			heatCapacityV = R/(heatCapacityRatio-1);
			heatCapacityP = heatCapacityV + R;
		}
		//calculate values from initial values for nodes first
		for (String nodeName: nodes.keySet()) {
			updateNode(nodes.get(nodeName),nextProcesses);
		}
		while (!nextProcesses.isEmpty()) {
			int nextProcIndex = nextProcesses.get(0);
			CycleProcess proc = processes.get(nextProcIndex);
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
			//internal energy related calculations
			//Energy is not calculated using n*Cv*dT due to differences in values between it
			//and calculating energy from work and heat
			if (Float.isNaN(moles)) {
				if (!Float.isNaN(proc.start.temperature) && !Float.isNaN(proc.end.temperature) 
				&& !Float.isNaN(heatCapacityV) && !Float.isNaN(proc.energyChange)) {
					moles = proc.energyChange/(heatCapacityV*(proc.end.temperature-proc.start.temperature));
					processUpdate=true;
				}
			} else if (Float.isNaN(heatCapacityV)) {
				if (!Float.isNaN(proc.start.temperature) && !Float.isNaN(proc.end.temperature)
				&& !Float.isNaN(proc.energyChange) 
				&& moles*(proc.end.temperature-proc.start.temperature) != 0.0f) {
					heatCapacityV= proc.energyChange/(moles*(proc.end.temperature-proc.start.temperature));
					processUpdate=true;
					System.out.println("here");
				}
			} else if (Float.isNaN(proc.start.temperature)) {
				if (!Float.isNaN(proc.end.temperature) && !Float.isNaN(proc.energyChange)) {
					proc.start.temperature = proc.end.temperature-(proc.energyChange)/(moles*heatCapacityV);
					processUpdate=true;
				}
			} else if (Float.isNaN(proc.end.temperature)) {
				if (!Float.isNaN(proc.energyChange) && moles*heatCapacityV!=0) {
					proc.end.temperature = proc.start.temperature+(proc.energyChange)/(moles*heatCapacityV);
					processUpdate=true;
				}
			} /*else if (proc.energyChange != moles*heatCapacityV*(proc.start.temperature-proc.end.temperature)) {
				throw new PhysicsException("Internal energy doesn't match n*Cv*dT");
			}*/
			switch (proc.type) {
				case ADIABATIC:
					if (Adiabatic.update(proc,this)) {
						processUpdate = true;
					}
					break;
				case ISOTHERMAL:
					if (IsoThermal.update(proc,this)) {
						processUpdate = true;
					}
					break;
				case ISOBARIC:
					if (IsoBar.update(proc,this)) {
						processUpdate = true;
					}
					break;
				case ISOCHORIC:
					if (IsoChor.update(proc,this)) {
						processUpdate = true;
					}
					break;
			}
			if (processUpdate) {
				updateNode(proc.start,nextProcesses); 
				updateNode(proc.end,nextProcesses); 
				nextProcesses.add(nextProcIndex);
				for (int i=0;i<processes.size();++i) {
					boolean alreadyNext = false;
					for (int j=0;j<nextProcesses.size();++j) {
						if (nextProcesses.get(j)==i) {
							alreadyNext=true;
							break;
						}
					}
					if (!alreadyNext) {
						nextProcesses.add(i);
					}
				}
			}
		}
	}
	public Cycle (CycleData data) throws PhysicsException {
		nodes = new Hashtable<String,CycleNode>();
		nodeConnections = new Hashtable<String,ArrayList<Integer>>();
		processes = new ArrayList<CycleProcess>();
		if (!Float.isNaN(data.moles)) {
			moles = data.moles;
		} else {
			moles = Float.NaN;
		}
		if (!Float.isNaN(data.heatCapacityRatio)) {
			heatCapacityRatio = data.heatCapacityRatio;
		} else {
			heatCapacityRatio = Float.NaN;
		}
		if (!Float.isNaN(data.heatCapacityV)) {
			heatCapacityV = data.heatCapacityV;
		} else {
			heatCapacityV = Float.NaN;
		}
		if (!Float.isNaN(data.heatCapacityP)) {
			heatCapacityP = data.heatCapacityP;
		} else {
			heatCapacityP = Float.NaN;
		}
		/* deep copy */
		for (int i=0;i<data.processData.size();++i) {
			CycleProcess original = data.processData.get(i);
			CycleProcess processCopy = new CycleProcess ();
			processCopy.heatChange = original.heatChange;
			processCopy.workChange = original.workChange;
			processCopy.energyChange = original.energyChange;
			if (!nodes.containsKey(original.start.name)) {
				CycleNode newNode = new CycleNode();
				newNode.name = original.start.name;
				processCopy.start = newNode;
				nodes.put(original.start.name,newNode);
				nodeConnections.put(original.start.name,new ArrayList<Integer>());
			} else {
				processCopy.start = nodes.get(original.start.name);
			}
			nodeConnections.get(original.start.name).add(i);
			if (!nodes.containsKey(original.end.name)) {
				CycleNode newNode = new CycleNode();
				newNode.name = original.end.name;
				processCopy.end = newNode;
				nodes.put(original.end.name,newNode);
				nodeConnections.put(original.end.name,new ArrayList<Integer>());
			} else {
				processCopy.end = nodes.get(original.end.name);
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
		calculateCycle();
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
	public static boolean apprxEq (float valOne, float valTwo) {
		final float RANGE=0.0005f;
		if (valOne >= valTwo) {
			if (valOne-valTwo <= RANGE) {
				return true;
			} else {
				return false;
			}
		} else {
			if (valTwo-valOne <= RANGE) {
				return true;
			} else {
				return false;
			}
		}
	}
}
