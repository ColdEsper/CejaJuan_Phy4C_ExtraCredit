package PhysicsExtraCredit;

public class IsoChor 
{
	
	//create a method that calculates work,U,Q,and S 
	//it must accept moles, temp, volume1, volume 2 as arguments
	public static double isochorculationsWork()
	{
		double work;
	   //calculate the work which is nRT=ln(V2/V1)
	    work = 0;
	    
	   return work;
	}
	
	public static double isochorCalculationsQ(double Cv,double volume1,double Pfinal,double Pinitial)
	{
		double Q;
		 Q= (Cv/Cycle.R)*(volume1 *(Pfinal - Pinitial));
		 
		 return Q;
	}
	
	public static double isochorCalculationsS(double moles,double Cv, double tempFinal,double tempInitial)
	{
		double S;
		
		//calculate the entropy for Free expansion
		 S= (moles*Cv*(double)Math.log((tempFinal-tempInitial)));
		 
		 return S;
	}
	
	public static double isochorCalculationsU(double Cv,double volume1,double Pfinal,double Pinitial)
	{
		double U;
		
		//calculate the entropy for Free expansion
		U= (Cv/Cycle.R)*(volume1 *(Pfinal - Pinitial));
		 
		 return U;
	}
	

	public static double isochorCalculationsTemp1(double moles,double volume1,double Pinitial)
	{
		double temp1;
		
		//calculate the entropy for Free expansion
		 temp1= ((Pinitial* volume1)/(moles*Cycle.R));
		 
		 return temp1;
	}
	
	public static double isochorCalculationsTemp2(double moles,double volume1,double Pfinal)
	{
		double temp2 ;
		
		//calculate the entropy for Free expansion
		temp2= ((Pfinal*volume1)/(moles*Cycle.R));
		 
		 return temp2;
	}

}
