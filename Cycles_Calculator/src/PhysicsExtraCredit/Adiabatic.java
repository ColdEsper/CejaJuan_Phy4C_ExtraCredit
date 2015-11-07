package PhysicsExtraCredit;

public class Adiabatic 
{
	public static float adiabaticculationsWork(float volume2,float volume1)
	{
		final float gamma = (float)1.6651;//ratio of Cp/Cv different
	    final float K = (float)1.6651608;//adiabatic condition K=PV^gamma ; gamma = Cp/Cv
		float work;
		//calculate work which is w = K(Vf^(1-gamma)-Vi^(1-gamma))/(1-gamma)
	   	work = (float)(K*(Math.pow(volume2, (1-gamma))-Math.pow(volume1, (1-gamma))))/(1-gamma);
	   
	   return work;
	}
	
	public static float adiabaticCalculationsQ()
	{
		float Q;
		 Q= 0;
		 
		 return Q;
	}
	
	public static float adiabaticCalculationsS(float moles,float Cv, float tempFinal,float tempInitial)
	{
		float S;
		
		//calculate the entropy for Free expansion
		 S= (moles*Cv*(float)Math.log((tempFinal-tempInitial)));
		 
		 return S;
	}
	
	public static float adiabaticCalculationsU(float volume2,float volume1)
	{

		final float gamma = (float)1.6651;//ratio of Cp/Cv different
	    final float K = (float)1.6651608;//adiabatic condition K=PV^gamma ; gamma = Cp/Cv
		float U;
	
		//calculate U = -work 
	    U= -1*(float)(K*(Math.pow(volume2, (1-gamma))-Math.pow(volume1, (1-gamma))))/(1-gamma);
		 
		 return U;
	}
	

	public static float adiabaticCalculationsPressure1(float moles,float tempInKelvin,float volume1)
	{
		float Pinitial;
		
		  //calculate the initial pressure using P = nRT/V
		 Pinitial = (moles*Cycle.R*tempInKelvin)/volume1;
		 
		 return Pinitial;
	}
	
	public static float adiabaticCalculationsPressure2(float moles,float tempInKelvin,float volume2)
	{
		float Pfinal ;
		
		//calculate the entropy for Free expansion
		Pfinal = (moles*Cycle.R*tempInKelvin)/volume2;
		 
		 return Pfinal;
	}

}
