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
		final float R = (float) 8.314; //this is the ideal gas law units J/(mol*k)
		float Q;
		 Q= (Cv/R)*(volume1 *(Pfinal - Pinitial));
		 
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
		final float R = (float) 8.314; //this is the ideal gas law units J/(mol*k)
		float U;
		
		//calculate the entropy for Free expansion
		U= (Cv/R)*(volume1 *(Pfinal - Pinitial));
		 
		 return U;
	}
	

	public static float isochorCalculationsTemp1(float moles,float volume1,float Pinitial)
	{
		final float R = (float) 8.314; //this is the ideal gas law units J/(mol*k)
		float temp1;
		
		//calculate the entropy for Free expansion
		 temp1= ((Pinitial* volume1)/(moles*R));
		 
		 return temp1;
	}
	
	public static float isochorCalculationsTemp2(float moles,float volume1,float Pfinal)
	{
		final float R = (float) 8.314; //this is the ideal gas law units J/(mol*k)
		float temp2 ;
		
		//calculate the entropy for Free expansion
		temp2= ((Pfinal*volume1)/(moles*R));
		 
		 return temp2;
	}

}
