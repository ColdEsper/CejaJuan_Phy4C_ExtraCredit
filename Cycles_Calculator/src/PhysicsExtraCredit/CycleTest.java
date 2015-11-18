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
		if (!CycleTest.testProcess(data.processData.get(0),"A","B",Float.NaN,Float.NaN,Float.NaN,
					CycleProcess.ProcessType.ISOTHERMAL)) {
			System.out.println("Process 0 loaded values are");
			data.processData.get(0).display();
			return;
		}
	}
	private static boolean testProcess (CycleProcess proc, String startNodeName, String endNodeName,
			float heatChange, float workChange, float energyChange, CycleProcess.ProcessType procType) {
		if (!proc.start.name.equals("A")) {
			System.out.println("Process loaded incorrectly for start node");
			return false;
		}
		if (!proc.end.name.equals("B")) {
			System.out.println("Process loaded incorrectly for end node");
			return false;
		}
		if (proc.heatChange != Float.NaN) {
			System.out.println("Value initialization incorrect for heatChange of process");
			return false;
		}
		if (proc.workChange != Float.NaN) {
			System.out.println("Value initialization incorrect for workChange of process");
			return false;
		}
		if (proc.energyChange != Float.NaN) {
			System.out.println("Value initialization incorrect for energyChange of process");
			return false;
		}
		if (proc.type != CycleProcess.ProcessType.ISOTHERMAL) {
			System.out.println("Process type loaded incorrectly for process");
			return false;
		}
		return true;
	}
}
