package PhysicsExtraCredit;

public class IsoBar 
{
	public static float isobarCalculationsU(float moles,float Cp,float tempFinal,float tempInitial)
	{
		float U;
		
		//calculate internal energy
		U= (moles*Cp*(tempFinal-tempInitial));
		 
		 return U;
	}
	

	public static float isobarCalculationsTempWork(float volume2,float volume1,float Pinitial)
	{
		float work;
		
		//note work = P(Vf-Vi) for isobaric process
	   	work = Pinitial*(volume2-volume1);
		 
		 return work;
	}
	
	public static float isobarCalculationsQ(float moles,float Cp,float tempFinal,float tempInitial)
	{
		float Q ;
		
		 //calculate Q= nCp(Tf-Ti)
	    Q= (moles*Cp*(tempFinal-tempInitial));
		 
		 return Q;
	}

	public static boolean update (CycleProcess process, Cycle cycle) throws PhysicsException {
		boolean processUpdated = false;
		if (!Float.isNaN(process.start.pressure)) {
			if (!Float.isNaN(process.end.pressure)) {
				if (process.end.pressure!=process.start.pressure) {
					throw new PhysicsException("Isobaric pressure was not constant");
				}
			} else {
				process.end.pressure = process.start.pressure;
				processUpdated=true;
			}
		} else if (!Float.isNaN(process.end.pressure)) {
			process.start.pressure = process.end.pressure;
			processUpdated=true;
		}
		if (!Float.isNaN(process.start.volume) && !Float.isNaN(process.end.volume)) {
			if (Float.isNaN(process.workChange)) {
				process.workChange = process.start.pressure*(process.end.volume-process.start.volume);
				processUpdated=true;
			} else if (Float.isNaN(process.start.pressure)) {
				process.start.pressure=process.workChange/(process.end.volume-process.start.volume);
				process.end.pressure = process.start.pressure;
				processUpdated=true;
			} else if (process.workChange!=process.start.pressure*(process.end.volume-process.start.volume)) {
				throw new PhysicsException("Isobar work doesn't match volume and pressure calculation");
			}
		} else if (!Float.isNaN(process.workChange)) {
			if (!Float.isNaN(process.start.volume) && Float.isNaN(process.end.volume)) {
				process.end.volume = process.workChange/process.start.pressure+process.start.volume;
				processUpdated=true;
			} else if (Float.isNaN(process.start.volume) && !Float.isNaN(process.end.volume)) {
				process.start.volume = process.workChange/process.start.pressure+process.end.volume;
				processUpdated=true;
			}
		}
		return processUpdated;
	}
}
