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

	public static float gammaWork (float volumeOne, float pressureOne,
			float volumeTwo, float pressureTwo, float heatCapacityRatio) {
		return (1/(heatCapacityRatio-1))*(pressureOne*volumeOne-pressureTwo*volumeTwo);
	}

	public static float cvWork (float volumeOne, float pressureOne,
			float volumeTwo, float pressureTwo, float heatCapacityV) {
		return (heatCapacityV/Cycle.R)*(pressureOne*volumeOne-pressureTwo*volumeTwo);
	}

	public static float temperatureWork (float temperatureOne, float temperatureTwo, float heatCapacityV, float moles) {
		return moles*heatCapacityV*(temperatureOne-temperatureTwo);
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
	//For things like pv^gamma=constant and  Tv^(gamma-1)=constant
	public static float newtonCalculation (double constant, double other, double power) {
		double guess = constant/(power*other);
		double prev = -1.0;
		double prevTwo = -1.0;
		while (guess != prev && guess != prevTwo) {
			prevTwo=prev;
			prev=guess;
			guess=guess-((other*Math.pow(guess,power)-constant)/(other*power*Math.pow(guess,power-1)));
		}
		return (float)guess;
	}
	//pv^gamma)=constant
	public static boolean pvCalculation (CycleNode nodeOne, CycleNode nodeTwo, Cycle cycle) throws PhysicsException {
		boolean processUpdated = false;
		//calculate volume
		if (!Float.isNaN(nodeOne.pressure) && !Float.isNaN(nodeOne.volume) && !Float.isNaN(cycle.heatCapacityRatio)) {
			if (!Float.isNaN(nodeTwo.pressure)) {
				if (Float.isNaN(nodeTwo.volume)) {
					nodeTwo.volume = Adiabatic.newtonCalculation(
						nodeOne.pressure*Math.pow(nodeOne.volume,cycle.heatCapacityRatio),
						nodeTwo.pressure,cycle.heatCapacityRatio);
					processUpdated = true;
				} else {
					if (!Cycle.apprxEq(nodeTwo.volume,Adiabatic.newtonCalculation(
							nodeOne.pressure*Math.pow(nodeOne.volume,cycle.heatCapacityRatio),
							nodeTwo.pressure,cycle.heatCapacityRatio))) {
						throw new PhysicsException("Adiabatic pv^(gamma-1) failed to be a constant!");
					}
				}
			} else if (!Float.isNaN(nodeTwo.volume)) {
				nodeTwo.pressure = nodeOne.pressure*(float)Math.pow(
						nodeOne.volume/nodeTwo.volume,
						cycle.heatCapacityRatio-1);
				processUpdated = true;
			}
		}
		return processUpdated;
	}
	//Tv^(gamma-1)=constant
	public static boolean tvCalculation (CycleNode nodeOne, CycleNode nodeTwo, Cycle cycle) throws PhysicsException {
		boolean processUpdated = false;
		//calculate volume
		if (!Float.isNaN(nodeOne.temperature) && !Float.isNaN(nodeOne.volume) && !Float.isNaN(cycle.heatCapacityRatio)) {
			if (!Float.isNaN(nodeTwo.temperature)) {
				if (Float.isNaN(nodeTwo.volume)) {
					nodeTwo.volume = Adiabatic.newtonCalculation(
						nodeOne.temperature*Math.pow(nodeOne.volume,cycle.heatCapacityRatio),
						nodeTwo.temperature,cycle.heatCapacityRatio);
					processUpdated = true;
				} else {
					if (!Cycle.apprxEq(nodeTwo.volume,Adiabatic.newtonCalculation(
								nodeOne.temperature*Math.pow(nodeOne.volume,cycle.heatCapacityRatio-1),
								nodeTwo.temperature,cycle.heatCapacityRatio-1))) {
						System.out.println("Current");
						System.out.println(nodeTwo.volume);
						System.out.println("Calc");
						System.out.println(Adiabatic.newtonCalculation( nodeOne.temperature*Math.pow(nodeOne.volume,cycle.heatCapacityRatio-1), nodeTwo.temperature,cycle.heatCapacityRatio-1));
						throw new PhysicsException("Adiabatic tv^(gamma-1) failed to be a constant!");
					}
				}
			} else if (!Float.isNaN(nodeTwo.volume)) {
				nodeTwo.temperature = nodeOne.temperature*(float)Math.pow(
						nodeOne.volume/nodeTwo.volume,
						cycle.heatCapacityRatio-1);
				processUpdated = true;
			}
		}
		return processUpdated;
	}
	public static boolean update (CycleProcess process, Cycle cycle) throws PhysicsException {
		boolean processUpdated = false;
		//heat calculations
		if (Float.isNaN(process.heatChange)) {
			process.heatChange = 0;
			processUpdated = true;
		} else if (process.heatChange != 0.0f) {
			throw new PhysicsException ("Adiaabatic process had heat added!");
		}
		//Work calculations
		if (!Float.isNaN(process.start.pressure) && !Float.isNaN(process.end.pressure)
		&& !Float.isNaN(process.start.volume) && !Float.isNaN(process.end.volume)) {
			if (Float.isNaN(process.workChange)) {
				if (!Float.isNaN(cycle.heatCapacityRatio)) {
					process.workChange = gammaWork(process.start.volume,
						process.start.pressure, process.end.volume,
						process.end.pressure, cycle.heatCapacityRatio);
					processUpdated = true;
					if (!Float.isNaN(cycle.heatCapacityV)) {
						float work = cvWork(process.start.volume,
							process.start.pressure, process.end.volume,
							process.end.pressure, cycle.heatCapacityV);
						if (work != process.workChange) {
							throw new PhysicsException("Adiabatic work didn't match by Cv calculation!");
						}
					}
				} else if (!Float.isNaN(cycle.heatCapacityV)) {
					process.workChange = cvWork(process.start.volume,
						process.start.pressure, process.end.volume,
						process.end.pressure, cycle.heatCapacityV);
					processUpdated = true;
				}
			} else {
				if (!Float.isNaN(cycle.heatCapacityRatio)) {
					float work = gammaWork(process.start.volume,
							process.start.pressure, process.end.volume,
							process.end.pressure, cycle.heatCapacityRatio);
					if (process.workChange != work) {
						throw new PhysicsException("Adiabatic work didn't match by gamma calulation!");
					}
				}
			}
		} else if (!Float.isNaN(process.start.temperature) && !Float.isNaN(process.end.temperature) 
		&& !Float.isNaN(cycle.moles) && !Float.isNaN(cycle.heatCapacityV)) {
			if (Float.isNaN(process.workChange)) {
				process.workChange = temperatureWork(process.start.temperature,
						process.end.temperature, cycle.heatCapacityV, cycle.moles);
				processUpdated = true;
			} else if (process.workChange != temperatureWork(process.start.temperature, 
			process.end.temperature, cycle.heatCapacityV, cycle.moles)) {
				throw new PhysicsException("Adiabatic work didn't match by temperature calculation!");
			}
		}
		//pV^gamma calculations
		if (pvCalculation(process.start,process.end,cycle)) {
			processUpdated=true;
		}
		if (pvCalculation(process.end,process.start,cycle)) {
			processUpdated=true;
		}
		//TV^(gamma-1) calculations
		if (tvCalculation(process.start,process.end,cycle)) {
			processUpdated=true;
		}
		if (tvCalculation(process.end,process.start,cycle)) {
			processUpdated=true;
		}
		return processUpdated;
	}
}
