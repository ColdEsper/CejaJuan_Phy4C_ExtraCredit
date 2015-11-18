package PhysicsExtraCredit;

import java.io.IOException;

class CycleTest {
	public static void main(String args[]) throws IOException {
		CycleData data = Cycle.loadFile("test.cycle");
		if (data.moles != 3.2190908f) {
			System.out.println("Moles loaded states "+data.moles);
			System.out.println("Moles should've been 3.2190908");
			return;
		}
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
		System.out.println("Load test was Successful!!!");
	}
	private static boolean testProcess (CycleProcess proc, String startNodeName, String endNodeName,
			float heatChange, float workChange, float energyChange, CycleProcess.ProcessType procType) {
		if (!proc.start.name.equals(startNodeName)) {
			System.out.println("Process loaded incorrectly for start node");
			return false;
		}
		if (!proc.end.name.equals(endNodeName)) {
			System.out.println("Process loaded incorrectly for end node");
			return false;
		}
		testChangeVal(proc.heatChange, Float.NaN,"Value initialization incorrect for heatChange of process");
		testChangeVal(proc.workChange, Float.NaN,"Value initialization incorrect for workChange of process");
		testChangeVal(proc.energyChange, Float.NaN,"Value initialization incorrect for energyChange of process");
		if (proc.type != procType) {
			System.out.println("Process type loaded incorrectly for process");
			return false;
		}
		return true;
	}
	private static boolean testChangeVal (float actual, float correct, String errMsg) {
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
