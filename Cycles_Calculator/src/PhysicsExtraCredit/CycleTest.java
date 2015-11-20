package PhysicsExtraCredit;

import java.io.IOException;

class CycleTest {
	public static void main(String args[]) throws IOException {
		CycleData data = Cycle.loadFile("../data/test.cycle");
		if (data.moles != 3.2190908f) {
			System.out.println("Moles loaded states "+data.moles);
			System.out.println("Moles should've been 3.2190908");
			return;
		}
		//test processes
		if (!CycleTest.testProcess(data.processData.get(0),"A","B",Float.NaN,32.992f,Float.NaN,
					CycleProcess.ProcessType.ISOTHERMAL)) {
			System.out.println("Process 0 loaded values are");
			data.processData.get(0).display();
			return;
		}
		if (!CycleTest.testProcess(data.processData.get(1),"B","C",Float.NaN,Float.NaN,Float.NaN,
					CycleProcess.ProcessType.ADIABATIC)) {
			System.out.println("Process 1 loaded values are");
			data.processData.get(1).display();
			return;
		}
		if (!CycleTest.testProcess(data.processData.get(2),"C","D",80.23f,Float.NaN,999.98f,
					CycleProcess.ProcessType.ISOTHERMAL)) {
			System.out.println("Process 2 loaded values are");
			data.processData.get(2).display();
			return;
		}
		if (!CycleTest.testProcess(data.processData.get(3),"D","A",88.88f,39.7f,Float.NaN,
					CycleProcess.ProcessType.ADIABATIC)) {
			System.out.println("Process 3 loaded values are");
			data.processData.get(3).display();
			return;
		}
		//test nodes
		if (!CycleTest.testNode(data.nodeData.get(0),"A",35.029f,22.03f,Float.NaN)) {
			System.out.println("Node 0 loaded values are");
			data.nodeData.get(0).display();
			return;
		}
		if (!CycleTest.testNode(data.nodeData.get(1),"C",Float.NaN,Float.NaN,200.03f)) {
			System.out.println("Node 1 loaded values are");
			data.nodeData.get(1).display();
			return;
		}
		//Test object creation from data
		Cycle cyc = new Cycle(data);
		if (!CycleTest.testProcess(cyc.processes.get(0),"A","B",Float.NaN,32.992f,Float.NaN,
					CycleProcess.ProcessType.ISOTHERMAL)) {
			System.out.println("Created Cycle process 0 initialized incorrectly");
			cyc.processes.get(0).display();
			return;
		}
		if (!CycleTest.testProcess(cyc.processes.get(1),"B","C",Float.NaN,Float.NaN,Float.NaN,
					CycleProcess.ProcessType.ADIABATIC)) {
			System.out.println("Created Cycle process 1 initialized incorrectly");
			cyc.processes.get(1).display();
			return;
		}
		if (!CycleTest.testProcess(cyc.processes.get(2),"C","D",80.23f,Float.NaN,999.98f,
					CycleProcess.ProcessType.ISOTHERMAL)) {
			System.out.println("Created Cycle process 2 initialized incorrectly");
			cyc.processes.get(2).display();
			return;
		}
		if (!CycleTest.testProcess(cyc.processes.get(3),"D","A",88.88f,39.7f,Float.NaN,
					CycleProcess.ProcessType.ADIABATIC)) {
			System.out.println("Created Cycle process 3 initialized incorrectly");
			cyc.processes.get(3).display();
			return;
		}
		if (!CycleTest.testNode(cyc.nodes.get("A"),"A",35.029f,22.03f,Float.NaN)) {
			System.out.println("Created Cycle node A initialized incorrectly");
			cyc.nodes.get("A").display();
			return;
		}
		if (!CycleTest.testNode(cyc.nodes.get("C"),"C",Float.NaN,Float.NaN,200.03f)) {
			System.out.println("Created Cycle node C initialized incorrectly");
			cyc.nodes.get("C").display();
			return;
		}
		System.out.println("Load test was Successful!!!");
	}
	private static boolean testProcess (CycleProcess proc, String startNodeName, String endNodeName,
			float heatChange, float workChange, float energyChange, CycleProcess.ProcessType procType) {
		if (proc.start == null) {
			System.out.println("Process start node null!!!");
			return false;
		}
		if (proc.end == null) {
			System.out.println("Process end node null!!!");
			return false;
		}
		if (!proc.start.name.equals(startNodeName)) {
			System.out.println("Process loaded incorrectly for start node");
			return false;
		}
		if (!proc.end.name.equals(endNodeName)) {
			System.out.println("Process loaded incorrectly for end node");
			return false;
		}
		if(!testFloatVal(proc.heatChange, Float.NaN,"Value initialization incorrect for heatChange of process")) {
			return false;
		}
		if(!testFloatVal(proc.workChange, Float.NaN,"Value initialization incorrect for workChange of process")) {
			return false;
		}
		if (!testFloatVal(proc.energyChange, Float.NaN,"Value initialization incorrect for energyChange of process")) {
			return false;
		}
		if (proc.type != procType) {
			System.out.println("Process type loaded incorrectly for process");
			return false;
		}
		return true;
	}
	private static boolean testNode (CycleNode node, String nodeName, float pressure, float volume, float temperature) {
		if (!node.name.equals(nodeName)) {
			return false;
		}
		if (!testFloatVal(node.pressure,pressure,"Pressure loaded incorrectly for node")) {
			return false;
		}
		if (!testFloatVal(node.temperature,temperature,"temperature loaded incorrectly for node")) {
			return false;
		}
		if (!testFloatVal(node.temperature,volume,"volume loaded incorrectly for node")) {
			return false;
		}
		return true;
	}
	private static boolean testFloatVal (float actual, float correct, String errMsg) {
		boolean error = false;
		if (Float.isNaN(correct)) {
			if (!Float.isNaN(actual)) {
				error = false;
			}
		} else {
			if (Float.isNaN(actual) || actual != correct) {
				error = false;
			}
		}
		if (error) {
			System.out.println(errMsg);
			return false;
		} else {
			return true;
		}
	}
}
