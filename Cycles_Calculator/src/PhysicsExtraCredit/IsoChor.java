package PhysicsExtraCredit;

public class IsoChor 
{
	
	//create a method that calculates work,U,Q,and S 
	//it must accept moles, temp, volume1, volume 2 as arguments
	public static float isochorculationsWork()
	{
		float work;
	   //calculate the work which is nRT=ln(V2/V1)
	    work = 0;
	    
	   return work;
	}
	
	public static float isochorCalculationsQ(float Cv,float volume1,float Pfinal,float Pinitial)
	{
		float Q;
		 Q= (Cv/Cycle.R)*(volume1 *(Pfinal - Pinitial));
		 
		 return Q;
	}
	
	public static float isochorCalculationsS(float moles,float Cv, float tempFinal,float tempInitial)
	{
		float S;
		
		//calculate the entropy for Free expansion
		 S= (moles*Cv*(float)Math.log((tempFinal-tempInitial)));
		 
		 return S;
	}
	
	public static float isochorCalculationsU(float Cv,float volume1,float Pfinal,float Pinitial)
	{
		float U;
		
		//calculate the entropy for Free expansion
		U= (Cv/Cycle.R)*(volume1 *(Pfinal - Pinitial));
		 
		 return U;
	}
	

	public static float isochorCalculationsTemp1(float moles,float volume1,float Pinitial)
	{
		float temp1;
		
		//calculate the entropy for Free expansion
		 temp1= ((Pinitial* volume1)/(moles*Cycle.R));
		 
		 return temp1;
	}
	
	public static float isochorCalculationsTemp2(float moles,float volume1,float Pfinal)
	{
		float temp2 ;
		
		//calculate the entropy for Free expansion
		temp2= ((Pfinal*volume1)/(moles*Cycle.R));
		 
		 return temp2;
	}

	public static boolean update (CycleProcess process, Cycle cycle) throws PhysicsException {
		boolean processUpdated = false;
		if (Float.isNaN(process.workChange)) {
			process.workChange =0.0f;
			processUpdated=true;
		} else if (process.workChange != 0.0f) {
			throw new PhysicsException("Isochoric process did work");
		}
		return processUpdated;
	}
}
