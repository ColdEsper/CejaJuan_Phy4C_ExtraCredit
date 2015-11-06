package PhysicsExtraCredit;

public class IsoBar 
{
	public static double isobarCalculationsU(double moles,double Cp,double tempFinal,double tempInitial)
	{
		double U;
		
		//calculate internal energy
		U= (moles*Cp*(tempFinal-tempInitial));
		 
		 return U;
	}
	

	public static double isobarCalculationsTempWork(double volume2,double volume1,double Pinitial)
	{
		double work;
		
		//note work = P(Vf-Vi) for isobaric process
	   	work = Pinitial*(volume2-volume1);
		 
		 return work;
	}
	
	public static double isobarCalculationsQ(double moles,double Cp,double tempFinal,double tempInitial)
	{
		double Q ;
		
		 //calculate Q= nCp(Tf-Ti)
	    Q= (moles*Cp*(tempFinal-tempInitial));
		 
		 return Q;
	}

}
