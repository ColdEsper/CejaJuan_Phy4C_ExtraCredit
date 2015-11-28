package PhysicsExtraCredit;

import java.util.ArrayList;

public class CycleData {
	public CycleData () {
		processData = new ArrayList<CycleProcess>();
		nodeData = new ArrayList<CycleNode>();
		moles = Float.NaN;
		heatCapacityP = Float.NaN;
		heatCapacityV = Float.NaN;
		heatCapacityRatio = Float.NaN;
	}
	ArrayList<CycleProcess> processData;
	ArrayList<CycleNode> nodeData;
	float moles;
	float heatCapacityRatio;
	float heatCapacityP;
	float heatCapacityV;
}
