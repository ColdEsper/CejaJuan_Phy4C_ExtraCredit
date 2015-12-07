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
		return processUpdated;
	}
}
