package PhysicsExtraCredit;

import java.util.*;


public class Cycles_2nd_Law
{
	/*******************************
	 * Main Method                 *
	 * Execution begins here       * 
	 *******************************/
	public static void main(String[] args) 
	{ 
	  int userPick;
	  final int isoChor = 1;
	  final int isoBar = 2;
	  final int adiabatic = 3;
	  final int isoTherm = 4;
	  int runAgain;
		     
	//instantiate a scanner object
		@SuppressWarnings("resource")
		Scanner keyBoard = new Scanner(System.in);
				
		do
		{
	//ask the user to input the cycle he/she wants to focus on
		System.out.print("1. isochor\n"
				 +"2. isobar\n"
				 +"3. adiabatic\n"
				 +"4. isothermal\n");
			
		userPick =  keyBoard.nextInt();	
		
		//validate the input 
			while(userPick<1||userPick>4)
			{
				System.out.println("invalid input");
				System.out.print("1. isochor\n"
						 +"2. isobar\n"
						 +"3. adiabatic\n"
						 +"4. isothermal\n");
					
				userPick =  keyBoard.nextInt();	
				
			}
			
				//use a switch statement that allows the user to choice 
				//which cycle he wants to work with
				
				switch(userPick)
				{
					case isoChor:
						isoChor();
						break;
					case isoBar:
						isoBar();
						break;
					case adiabatic:
						adiabatic();
						break;
					case isoTherm:
						isoTherm();
						break;
				
				}
				
			//ask the user if he/she wants to run the program again
				System.out.println("Enter -14 to run the program again");
				runAgain = keyBoard.nextInt();
				
		}while(runAgain==-14);
		    
  }
	
			/*******************************
			 * isoChor Method              *
			 * this method calculates the  *
			 * work,Q,and U of an isochoric* 
			 * process                     * 
			 *******************************/
		  public static void isoChor()
		   {
			   final float R = (float) 8.314; //this is the ideal gas law units J/(mol*k)
			   float Cv;//Specific molar heat capacity at constant volume Cv= Cp-R
			   float moles;//this is the number of moles
			   float volume1;
			   float work;//work
			   float Q;//heat flow
			   float U;//internal energy
			   float S;//entropy
			   float tempInitial;//find using PV=nRT
			   float tempFinal;//find using PV=nRT
			   float Pinitial;//initial pressure calculated from ideal gas law PV=nRT
			   float Pfinal;//final pressure calculated from the ideal gas law PV=nRT
			   
			   
		//instantiate a scanner object
			   @SuppressWarnings("resource")
			Scanner keyBoard = new Scanner(System.in);
			   
			 //prompt the user to enter the moles 
			   System.out.println("Enter the number of moles(n) the \n"
					   +"it must be greater than zero");
			   moles = keyBoard.nextFloat();
			   
			   
			   //validate the input to make sure its greater then zero
			   while(moles<=0)
			   {
				 //prompt the user to enter the moles
				   System.out.println("Enter the number of moles(n) the \n"
						   +"it must be greater than zero");
				   moles = keyBoard.nextFloat();
				   
			   }
			   
			 //prompt the user to enter the Cv
			   System.out.println("Enter the value of Cv \n");
			   Cv = keyBoard.nextFloat();
			   
			   //prompt the user to enter the initial volume V1
			   System.out.println("Enter the volume of the \n"
					   +"isochor in meter^3");
			   volume1 = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(volume1<=0)
			   {
				   //prompt the user to enter the initial volume
				   System.out.println("Enter the volume of the \n"
						   +"isochor remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume1 = keyBoard.nextFloat();
			   }
			   	
			 //prompt the user to enter the initial pressure P1
			   System.out.println("Enter the initial pressure(P1)of the \n"
					   +"isochor in pascals");
			   Pinitial = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(Pinitial<=0)
			   {
				   //prompt the user to enter the initial pressure
				   System.out.println("Enter the inital pressure(P1) of the \n"
						   +"isochor remember you cannot have negative values \n"
						   +"or a value of zero");
				   Pinitial = keyBoard.nextFloat();
			   }
			   	
			   	
			   
			   //prompt the user to enter the final pressure P2
			   System.out.println("Enter the final pressure(P2)of the \n"
					   +"isochor in pascals");
			   Pfinal = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(Pfinal<0)
			   {
				   //prompt the user to enter the final pressure
				   System.out.println("Enter the final pressure(P2) of the \n"
						   +"isochor remember you cannot have negative values \n");
				   Pfinal = keyBoard.nextFloat();
			   }
			   	
			   
			   //note work = 0 for isochoric process
			   	work = 0;
			   
			    
			   //calculate Q = (Cv/R)*V(Pf-Pi)
			    Q= (Cv/R)*(volume1 *(Pfinal - Pinitial));
			    
			    //calculate U = Q for an isochoric process
			    U= Q;
			    
			   //display the total work which is w = K(Vf^(1-gamma)-Vi^(1-gamma))/(1-gamma) 
			   System.out.print("Total work(joules)= ");
			   System.out.format(" %-10.2f%n",work);
			   
			   //display the amount of heat Q = 0
			   System.out.print("\nNote:\nQ= (Cv/R)*V(Pf-Pi) for and isochoric process\n"
			   		+ "so Q= ");
			   System.out.format(" %-10.4f%n",Q);
			   
			 //display the amount of heat u = -Work
			   System.out.print("\nNote:\nU= Q for and isochoric process\n"
			   		+ "so U= ");
			   System.out.format(" %-10.4f%n",U);
			   
			  //calculate T1 with T1=(P1V1/nR)
			   tempInitial = ((Pinitial* volume1)/(moles*R));
			   
			 //calculate T2 with T2=(P2V1/nR)
			   tempFinal = ((Pfinal*volume1)/(moles*R));
			   
			   
			 //calculate the entropy S = nCvln(T2-T1)
			   S= (moles*Cv*(float)Math.log((tempFinal-tempInitial)));
			   System.out.print("\nEntropy(S) = ");
			   System.out.format("%-10.4f%n",S);  
			    
			   
		   }
		    /*******************************
			 * isoBar Method               *
			 * this method calculates the  *
			 * work,Q,and U of an isoBar   * 
			 * process                     * 
			 *******************************/
		   
		   public static void isoBar()
		   {
			   final float R = (float) 8.314; //this is the ideal gas law units J/(mol*k)
			   float Cp;//Specific molar heat capacity at constant pressure Cp= Cv+R
			   float moles;//this is the number of moles
			   float volume1;
			   float volume2;
			   float tempInitial;
			   float tempFinal;
			   float work;//work
			   float Q;//heat flow
			   float U;//internal energy
			   float S;//entropy
			   float Pinitial;//initial pressure calculated from ideal gas law PV=nRT
			  
			   
			   
		//instantiate a scanner object
			   @SuppressWarnings("resource")
			Scanner keyBoard = new Scanner(System.in);
			   
			 //prompt the user to enter the moles 
			   System.out.println("Enter the number of moles(n) the \n"
					   +"it must be greater than zero");
			   moles = keyBoard.nextFloat();
			   
			   
			   //validate the input to make sure its greater then zero
			   while(moles<=0)
			   {
				 //prompt the user to enter the moles
				   System.out.println("Enter the number of moles(n) the \n"
						   +"it must be greater than zero");
				   moles = keyBoard.nextFloat();
				   
			   }
			   
			 //prompt the user to enter the Cp
			   System.out.println("Enter the value of Cp \n");
			   Cp = keyBoard.nextFloat();
			   
			 //prompt the user to enter the initial pressure P1
			   System.out.println("Enter the initial pressure(P1)of the \n"
					   +"isochor in pascals");
			   Pinitial = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(Pinitial<=0)
			   {
				   //prompt the user to enter the initial pressure
				   System.out.println("Enter the inital pressure(P1) of the \n"
						   +"isochor remember you cannot have negative values \n"
						   +"or a value of zero");
				   Pinitial = keyBoard.nextFloat();
			   }
			 //prompt the user to enter the initial volume V1
			   System.out.println("Enter the inital volume(V1) of the \n"
					   +"isoterm in meter^3");
			   volume1 = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(volume1<=0)
			   {
				   //prompt the user to enter the initial volume
				   System.out.println("Enter the inital volume(V1) of the \n"
						   +"isoterm remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume1 = keyBoard.nextFloat();
			   }
			     	
			 //prompt the user to enter the final volume V2
			   System.out.println("Enter the final volume(V2) of the \n"
					   +"isoterm in meter^3");
			   volume2 = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(volume2<=0)
			   {
				   //prompt the user to enter the final volume
				   System.out.println("Enter the final volume(V2) of the \n"
						   +"isoterm remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume2 = keyBoard.nextFloat();
			   }
			   	
			   //prompt the user to enter the initial temperature
			   System.out.println("Enter the initial temperature of the \n"
			   				+"isobaric process in kelvin");
			   tempInitial = keyBoard.nextFloat();
			   
			   //validate the input
			   while(tempInitial<0)
			   {
				   //prompt the user to enter the initial temperature
				   System.out.println("Enter the initial temperature of the \n"
				   				+"isobaric process in kelvin");
				   tempInitial = keyBoard.nextFloat();
			   }
			   
			   //prompt the user to enter the final temperature
			   System.out.println("Enter the initial temperature of the \n"
			   				+"isobaric process in kelvin");
			   tempFinal = keyBoard.nextFloat();
			   
			   //validate the input
			   while(tempFinal<0)
			   {
				   //prompt the user to enter the initial temperature
				   System.out.println("Enter the Final temperature of the \n"
				   				+"isobaric process in kelvin");
				   tempFinal = keyBoard.nextFloat();
			   }
			   
			   //note work = P(Vf-Vi) for isobaric process
			   	work = Pinitial*(volume2-volume1);
			   
			    
			   //calculate Q= nCp(Tf-Ti)
			    Q= (moles*Cp*(tempFinal-tempInitial));
			    
			    //calculate U = Q for an isobaric process
			    U= Q;
			    
			   //display the total work 
			   System.out.print("Total work(joules)= ");
			   System.out.format(" %-10.2f%n",work);
			   
			   //display the amount of heat Q 
			   System.out.print("\nNote:\nQ= nCp(Tf-Ti) for and isobaric process\n"
			   		+ "so Q= ");
			   System.out.format(" %-10.2f%n",Q);
			   
			 //display the amount of heat u 
			   System.out.print("\nNote:\nU= Q for and isobaric process\n"
			   		+ "so U= ");
			   System.out.format(" %-10.2f%n",U);
			    
			  
		   }
		   
		    /*******************************
			 * adiabatic Method            *
			 * this method calculates the  *
			 * work,Q,and U of an adiabatic* 
			 * process                     * 
			 *******************************/
		   
		   public static void adiabatic()
		   {
			   
			   final float Cp = (float) 20.81451;//specfic molar heat capacity at constant pressure Cp=Cv+R
			   final float Cv = (float) 12.5;//Specific molar heat capacity at constant volume Cv= Cp-R
			   final float R = (float) 8.314; //this is the ideal gas law units J/(mol*k)
			   final float gamma = (float)1.6651;//ratio of Cp/Cv different
			   float moles;//this is the number of moles
			   float tempInKelvin;//temperature in kelvin
			   float volume1;
			   float volume2;
			   float work;//work
			   float Q;//heat flow
			   float U;//internal energy
			   float S;//entropy
			   float K;//adiabatic condition K=PV^gamma ; gamma = Cp/Cv
			   float Pinitial;//initial pressure calculated from ideal gas law PV=nRT
			   float Pfinal;//final pressure calculated from the ideal gas law PV=nRT
			   
			   
		//instantiate a scanner object
			   @SuppressWarnings("resource")
			Scanner keyBoard = new Scanner(System.in);
			   
			   //prompt the user to enter the temperature in kelvin
			   System.out.println("Enter The temperature of the \n"
					   +"isoterm in kelvin");
			   tempInKelvin = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(tempInKelvin<=0)
			   {
				   //prompt the user to enter the temperature in kelvin
				   System.out.println("Enter The temperature of the \n"
						   +"isoterm in kelvin remember you cannot have negative values in the \n"
						   +"Kelvin scale or a value of zero");
				   tempInKelvin = keyBoard.nextFloat();
			   }
			   
			 //prompt the user to enter the moles
			   System.out.println("Enter the number of moles(n) the \n"
					   +"it must be greater than zero");
			   moles = keyBoard.nextFloat();
			   
			   
			   //validate the input to make sure its greater then zero
			   while(moles<=0)
			   {
				 //prompt the user to enter the moles
				   System.out.println("Enter the number of moles(n) the \n"
						   +"it must be greater than zero");
				   moles = keyBoard.nextFloat();
				   
			   }
			   
			 //prompt the user to enter the initial volume V1
			   System.out.println("Enter the inital volume(V1) of the \n"
					   +"isoterm in meter^3");
			   volume1 = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(volume1<=0)
			   {
				   //prompt the user to enter the initial volume
				   System.out.println("Enter the inital volume(V1) of the \n"
						   +"isoterm remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume1 = keyBoard.nextFloat();
			   }
			   
			   //calculate the initial pressure using P = nRT/V
			   	Pinitial = (moles*R*tempInKelvin)/volume1;
			   	
			   	
			   	//calculate the adiabatic condition K = PV^gamma
			   	 K = (float) (Pinitial * Math.pow(volume1, gamma));
			   
			 //prompt the user to enter the final volume V2
			   System.out.println("Enter the final volume(V2) of the \n"
					   +"isoterm in meter^3");
			   volume2 = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(volume2<=0)
			   {
				   //prompt the user to enter the final volume
				   System.out.println("Enter the final volume(V2) of the \n"
						   +"isoterm remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume2 = keyBoard.nextFloat();
			   }
			   
			   //calculate work which is w = K(Vf^(1-gamma)-Vi^(1-gamma))/(1-gamma)
			   	work = (float)(K*(Math.pow(volume2, (1-gamma))-Math.pow(volume1, (1-gamma))))/(1-gamma);
			   
			    
			   //calculate Q = 0 for adiabatic processes
			    Q= 0;
			    
			    //calculate U = -work 
			    U= -1*work;
			    
			   //display the total work which is w = K(Vf^(1-gamma)-Vi^(1-gamma))/(1-gamma) 
			   System.out.print("Total work(joules)= ");
			   System.out.format(" %-10.4f%n",work);
			   
			   //display the amount of heat Q = 0
			   System.out.print("\nNote:\nQ=0 for and adiabatic process\n"
			   		+ "so Q= ");
			   System.out.format(" %-10.2f%n",Q);
			   
			 //display the amount of heat u = -Work
			   System.out.print("\nNote:\nU= -work for and adiabatic process\n"
			   		+ "so U= ");
			   System.out.format(" %-10.2f%n",U);
			    
		   }
		   
		    /********************************
			 * Isotherm Method              *
			 * this function calculates the *
			 *  work and Q of an isothermal *
			 *  process for a cycle         * 
			 *******************************/ 
		public static void isoTherm()
		   {
			   final float R = (float) 8.314; //this is the ideal gas law units J/(mol*k) 
			   float moles;//this is the number of moles
			   float tempInKelvin;//temperature in kelvin
			   float volume1;
			   float volume2;
			   float work;//work
			   float Q;//heat flow
			   float U;//internal energy
			   float S;//entropy
			   float userChoice;
			   
			   
		//instantiate a scanner object
			   @SuppressWarnings("resource")
			Scanner keyBoard = new Scanner(System.in);
			   
			  
			   
			   
			   //prompt the user to enter the temperature in kelvin
			   System.out.println("Enter The temperature of the \n"
					   +"isoterm in kelvin");
			   tempInKelvin = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(tempInKelvin<=0)
			   {
				   //prompt the user to enter the temperature in kelvin
				   System.out.println("Enter The temperature of the \n"
						   +"isoterm in kelvin remember you cannot have negative values in the \n"
						   +"Kelvin scale or a value of zero");
				   tempInKelvin = keyBoard.nextFloat();
			   }
			   
			 //prompt the user to enter the moles
			   System.out.println("Enter the number of moles(n) the \n"
					   +"it must be greater than zero");
			   moles = keyBoard.nextFloat();
			   
			   
			   //validate the input to make sure its greater then zero
			   while(moles<=0)
			   {
				 //prompt the user to enter the moles
				   System.out.println("Enter the number of moles(n) the \n"
						   +"it must be greater than zero");
				   moles = keyBoard.nextFloat();
				   
			   }
			   
			 //prompt the user to enter the inital volume V1
			   System.out.println("Enter the inital volume(V1) of the \n"
					   +"isoterm in meter^3");
			   volume1 = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(tempInKelvin<=0)
			   {
				   //prompt the user to enter the temperature in kelvin
				   System.out.println("Enter the inital volume(V1) of the \n"
						   +"isoterm remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume1 = keyBoard.nextFloat();
			   }
			   
			   
			 //prompt the user to enter the final volume V2
			   System.out.println("Enter the final volume(V2) of the \n"
					   +"isoterm in meter^3");
			   volume2 = keyBoard.nextFloat();
			   
			   //validate the input to make sure its greater then zero
			   while(volume2<=0)
			   {
				   //prompt the user to enter the final volume
				   System.out.println("Enter the final volume(V2) of the \n"
						   +"isoterm remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume2 = keyBoard.nextFloat();
			   }
			   
			   //calculate the work which is nRT=ln(V2/V1)
			    work =  (float) (R*moles*tempInKelvin*Math.log(volume2/volume1));
			    
			   //calculate Q = work
			    Q= work;
			    
			    //calculate U = 0 cause delta Q = constant
			    U=0;
			    
			   //display the total work
			   System.out.print("Total work(joules)= ");
			   System.out.format(" %-10.4f%n",work);
			   
			   //display the amount of heat Q 
			   System.out.print("\nNote:\nQ=work for and isothermal process\n"
			   		+ "so Q= ");
			   System.out.format(" %-10.4f%n",Q);
			   
			 //display the amount of internal energy U 
			   System.out.print("\nNote:\nU= 0 for and isothermal process\n"
			   		+ "so U= ");
			   System.out.format(" %-10.2f%n",U);
			   
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
			   
			//ask the user if he/she wants the entropy for Free Expansion or 
			//for the reversible isothermal process
			   System.out.println("\nWhich entropy calculation would you like to run\n"
					   +"1.Free Expansion\n"
					   +"2.Reversible isothermal process\n"
					   +"Press the number that goes with your choice\n");
			   userChoice = keyBoard.nextFloat();
			   
			   //validate the input
			   while(userChoice<1||userChoice>2)
			   {
				   System.out.println("\nWhich entropy calculation would you like to run\n"
						   +"1.Free Expansion\n"
						   +"2.Reversible isothermal process\n"
						   +"Press the number that goes with your choice\n");
				   userChoice = keyBoard.nextFloat();   
			   }
			   //free expansion calculation
			   if(userChoice== 1)
			   {
				   //calculate the entropy for Free expansion
				   S= (moles*R*tempInKelvin*(float)Math.log(volume2/volume1));
				   System.out.print("Free Expansion Entropy = ");
				   System.out.format("%-10.4f%n",S);   
			   }
			   else if (userChoice==2)
			   {
				   //calculate the entropy for Reversible isothermal process
				   S=0;
				   System.out.println("Reversible process Entropy(joules/K) = " + S);
				    
			   }
			  
		   }
}
