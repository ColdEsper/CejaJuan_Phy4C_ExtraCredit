package PhysicsExtraCredit;

public class IsoThermal 
{ 
		   
		   /************************************************************************************
		    * **********************************************************************************
		    * Note: calculate the entropy (S)
		    *for a state change from initial value and temperature 
		    *V1 and T1 to to final volume and the same temperature
		    *V2 and T1 
		    * Entropy for this case is equal to 
		    * 	integrals of du/T + integral of Pdv/T
		    * 	which simplifies to delta S = NRTln(V2/V1) notice du=0 for isothermal process
		    * 
		    * FREE EXPANSION:
		    * 	S(system)= NRTln(V2/V1) and S(surroundings)=0
		    * since:
		    * 	S(total)= S(system)+ S(surroundings)
		    * 	So S(total)=NRTln(V2/V1)+0
		    * 
		    * FOR THE REVERSIBLE ISOTHERMAL PROCESS:
		    * 	S(system)= NRTln(V2/V1) and S(surroundings)= -NRTln(V2/V1)
		    * So:
		    * 	S(total)=0
		    ************************************************************************************
		    ************************************************************************************/
	
	//create a method that calculates work,U,Q,and S 
	//it must accept moles, temp, volume1, volume 2
	public static float isothermCalculationsWork(float moles,float volume1, float volume2,float tempInKelvin)
	{
		float work;
	   //calculate the work which is nRT=ln(V2/V1)
	    work =  (float) (Cycle.R*moles*tempInKelvin*Math.log(volume2/volume1));
	    
	   return work;
	}
	
	public static float isothermalCalculationsQ(float moles,float volume1, float volume2,float tempInKelvin)
	{
		float Q;
		 Q =  (float) (Cycle.R*moles*tempInKelvin*Math.log(volume2/volume1));
		 
		 return Q;
	}
	
	public static float isothermalCalculationsS(float moles,float volume1, float volume2,float tempInKelvin)
	{
		float S;
		
		//calculate the entropy for Free expansion
		S= (moles*Cycle.R*tempInKelvin*(float)Math.log(volume2/volume1));
		 
		 return S;
	}
	
	public static float isothermalCalculationsU()
	{
		float U;
		
		//calculate the entropy for Free expansion
		U= 0;
		 
		 return U;
	}
    
	public static boolean update (CycleProcess process, Cycle cycle) throws PhysicsException {
		boolean processUpdated = false;
		return processUpdated;
	}

    }
