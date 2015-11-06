package PhysicsExtraCredit;

public class Adiabatic 
{
	public static double adiabaticculationsWork(double volume2,double volume1)
	{
		final double gamma = (double)1.6651;//ratio of Cp/Cv different
	    final double K = (double)1.6651608;//adiabatic condition K=PV^gamma ; gamma = Cp/Cv
		double work;
		//calculate work which is w = K(Vf^(1-gamma)-Vi^(1-gamma))/(1-gamma)
	   	work = (double)(K*(Math.pow(volume2, (1-gamma))-Math.pow(volume1, (1-gamma))))/(1-gamma);
	   
	   return work;
	}
	
	public static double adiabaticCalculationsQ()
	{
		double Q;
		 Q= 0;
		 
		 return Q;
	}
	
	public static double adiabaticCalculationsS(double moles,double Cv, double tempFinal,double tempInitial)
	{
		double S;
		
		//calculate the entropy for Free expansion
		 S= (moles*Cv*(double)Math.log((tempFinal-tempInitial)));
		 
		 return S;
	}
	
	public static double adiabaticCalculationsU(double volume2,double volume1)
	{

		final double gamma = (double)1.6651;//ratio of Cp/Cv different
	    final double K = (double)1.6651608;//adiabatic condition K=PV^gamma ; gamma = Cp/Cv
		double U;
	
		//calculate U = -work 
	    U= -1*(double)(K*(Math.pow(volume2, (1-gamma))-Math.pow(volume1, (1-gamma))))/(1-gamma);
		 
		 return U;
	}
	

	public static double adiabaticCalculationsPressure1(double moles,double tempInKelvin,double volume1)
	{
		double Pinitial;
		
		  //calculate the initial pressure using P = nRT/V
		 Pinitial = (moles*Cycle.R*tempInKelvin)/volume1;
		 
		 return Pinitial;
	}
	
	public static double adiabaticCalculationsPressure2(double moles,double tempInKelvin,double volume2)
	{
		double Pfinal ;
		
		//calculate the entropy for Free expansion
		Pfinal = (moles*Cycle.R*tempInKelvin)/volume2;
		 
		 return Pfinal;
	}

}
