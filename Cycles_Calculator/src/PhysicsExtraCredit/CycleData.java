package PhysicsExtraCredit;

import java.util.ArrayList;

public class CycleData {
	public CycleData () {
		processData = new ArrayList<CycleProcess>();
		nodeData = new ArrayList<CycleNode>();
	}
	public boolean verify () {
		//TODO check that loaded data is sensible.
		//For example, check the values don't violate any laws of physics
		return true;
	}
	ArrayList<CycleProcess> processData;
	ArrayList<CycleNode> nodeData;
	float moles;
}
