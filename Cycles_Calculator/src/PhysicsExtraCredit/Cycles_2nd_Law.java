package PhysicsExtraCredit;

import java.util.*;


public class Cycles_2nd_Law
{
	/*******************************
	 * Command Line Main Method    *
	 * Execution begins here       * 
	 *******************************/
	public static void commandLineMain(String[] args) 
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
			do
			{
				//ask the user to input the cycle he/she wants to focus on
				System.out.print("1. isochor\n"
				 +"2. isobar\n"
				 +"3. adiabatic\n"
				 +"4. isothermal\n");
			
				userPick =  keyBoard.nextInt();
			}while(userPick<1||userPick>4);
			
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
			  do
			  { 
				  System.out.println("Enter the number of moles(n) the \n"
					   +"it must be greater than zero");
				  moles = keyBoard.nextFloat();
				   
			  }while(moles<=0);
			   
		//prompt the user to enter the Cv
			  System.out.println("Enter the value of Cv \n");
			  Cv = keyBoard.nextFloat();
			   
		//prompt the user to enter the initial volume
			 do
			 {
				  //validate the input to make sure its greater then zero
				  System.out.println("Enter the volume(meters^3) of the \n"
					   +"isochor remember you cannot have negative values \n"
					   +"or a value of zero");
			   volume1 = keyBoard.nextFloat();
			 } while(volume1<=0);
			   	
		//prompt the user to enter the initial pressure P1
			 do
			 {
			   //validate the input to make sure its greater then zero
			   //prompt the user to enter the initial pressure
				  System.out.println("Enter the inital pressure(pascals) of the \n"
						 +"isochor remember you cannot have negative values\n "
						 +"or a value of zero");
				  Pinitial = keyBoard.nextFloat();
			   
			 }while(Pinitial<=0);
			   	
			   	
			   
		//prompt the user to enter the final pressure P2
			 do
			 { 
			   //validate the input to make sure its greater then zero
			   //prompt the user to enter the final pressure
				   System.out.println("Enter the final pressure(pascals) of the \n"
						   +"isochor remember you cannot have negative values \n");
				   Pfinal = keyBoard.nextFloat();
			   
			 }while(Pfinal<0);
			   	
			   
			   //note work = 0 for isochoric process
			   	work = IsoChor.isochorculationsWork();
			   
			    
			   //calculate Q = (Cv/R)*V(Pf-Pi)
			    Q= IsoChor.isochorCalculationsQ(Cv, volume1, Pfinal, Pinitial);
			    
			    //calculate U = Q for an isochoric process
			    U=IsoChor.isochorCalculationsU(Cv, volume1, Pfinal, Pinitial);
			    
			   //display the total work 
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
			   tempInitial = IsoChor.isochorCalculationsTemp1(moles, volume1, Pinitial);
			   
			 //calculate T2 with T2=(P2V1/nR)
			   tempFinal = IsoChor.isochorCalculationsTemp2(moles, volume1, Pfinal);
			   
			   
			 //calculate the entropy S = nCvln(T2-T1)
			   S= IsoChor.isochorCalculationsS(moles, Cv, tempFinal, tempInitial);
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
				  do
				  { 
					  System.out.println("Enter the number of moles(n) the \n"
						   +"it must be greater than zero");
					  moles = keyBoard.nextFloat();
					   
				  }while(moles<=0);
			   
			 //prompt the user to enter the Cp
			   System.out.println("Enter the value of Cp \n");
			   Cp = keyBoard.nextFloat();
			   
			 //prompt the user to enter the initial pressure P1
				 do
				 {
				   //validate the input to make sure its greater then zero
				   //prompt the user to enter the initial pressure
					  System.out.println("Enter the inital pressure(pascals) of the \n"
							 +"isobar remember you cannot have negative values\n "
							 +"or a value of zero");
					  Pinitial = keyBoard.nextFloat();
				   
				 }while(Pinitial<=0);
				   	
				//prompt the user to enter the initial volume
				 do
				 {
					  //validate the input to make sure its greater then zero
					  System.out.println("Enter the volume(meters^3) of the \n"
						   +"isobar remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume1 = keyBoard.nextFloat();
				 } while(volume1<=0);
			     	
			 //prompt the user to enter the final volume V2
				do
				{
					//prompt the user to enter the final volume
				   System.out.println("Enter the final volume(m^3) of the \n"
						   +"isobar remember you cannot have negative values \n"
						   +"or a value of zero");
				   volume2 = keyBoard.nextFloat();
			   
				}while(volume2<=0);
			   	
			   //prompt the user to enter the initial temperature
				do
				{
				   //prompt the user to enter the initial temperature
				   System.out.println("Enter the initial temperature of the \n"
				   				+"isobaric process in kelvin imput must\n"
				   				+"be values > 0");
				   tempInitial = keyBoard.nextFloat();
			   
				}while(tempInitial<0);
				
			   //prompt the user to enter the final temperature
				do
				{
				   //prompt the user to enter the initial temperature
				   System.out.println("Enter the Final temperature of the \n"
				   				+"isobaric process in kelvin input must\n"
				   				+"be values > 0");
				   tempFinal = keyBoard.nextFloat();
			   
				} while(tempFinal<0);
			   
			   //note work = P(Vf-Vi) for isobaric process
			   	work = IsoBar.isobarCalculationsTempWork(volume2, volume1, Pinitial);
			   
			    
			   //calculate Q= nCp(Tf-Ti)
			    Q= IsoBar.isobarCalculationsQ(moles, Cp, tempFinal, tempInitial);
			    
			    //calculate U = Q for an isobaric process
			    U= IsoBar.isobarCalculationsU(moles, Cp, tempFinal, tempInitial);
			    
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
			   
			   //final float Cp = (float) 20.81451;//specfic molar heat capacity at constant pressure Cp=Cv+R
			   //final float Cv = (float) 12.5;//Specific molar heat capacity at constant volume Cv= Cp-R
			
			   float moles;//this is the number of moles
			   float tempInKelvin;//temperature in kelvin
			   float volume1;
			   float volume2;
			   float work;//work
			   float Q;//heat flow
			   float U;//internal energy
			   float S;//entropy
			   float Pinitial;//initial pressure calculated from ideal gas law PV=nRT
			   float Pfinal;//final pressure calculated from the ideal gas law PV=nRT
			   
			   
		//instantiate a scanner object
			   @SuppressWarnings("resource")
			Scanner keyBoard = new Scanner(System.in);
			   
			   //prompt the user to enter the temperature in kelvin
			   do
			   {
				   //prompt the user to enter the temperature in kelvin
				   System.out.println("Enter The temperature of the \n"
						   +"adiabat in kelvin remember you cannot have negative values in the \n"
						   +"Kelvin scale or a value of zero");
				   tempInKelvin = keyBoard.nextFloat();
			   
			   }while(tempInKelvin<=0);
			   
			 //prompt the user to enter the moles
				  do
				  { 
					  System.out.println("Enter the number of moles(n) the \n"
						   +"it must be greater than zero");
					  moles = keyBoard.nextFloat();
					   
				  }while(moles<=0);
			   
				//prompt the user to enter the initial volume
					 do
					 {
						  //validate the input to make sure its greater then zero
						  System.out.println("Enter the volume(meters^3) of the \n"
							   +"adiabat remember you cannot have negative values \n"
							   +"or a value of zero");
					   volume1 = keyBoard.nextFloat();
					 } while(volume1<=0);
					 
			  //prompt the user to enter the final volume V2
					 do
					 {
						//prompt the user to enter the final volume
						   System.out.println("Enter the final volume(m^3) of the \n"
								  +"isobar remember you cannot have negative values \n"
								  +"or a value of zero");
						   volume2 = keyBoard.nextFloat();
					  }while(volume2<=0);
			   
			   //calculate the initial pressure using P = nRT/V
					 Pinitial = Adiabatic.adiabaticCalculationsPressure1(moles, tempInKelvin, volume1);
					 
			   //calculate the final pressure using P=nRT/V
					 Pfinal = Adiabatic.adiabaticCalculationsPressure2(moles, tempInKelvin, volume2);
			   
			   //prompt the user to enter the final volume V2
					do
					{
						//prompt the user to enter the final volume
					   System.out.println("Enter the final volume(m^3) of the \n"
							   +"adiabat remember you cannot have negative values \n"
							   +"or a value of zero");
					   volume2 = keyBoard.nextFloat();
				   
					}while(volume2<=0);
					
			   //calculate work which is w = K(Vf^(1-gamma)-Vi^(1-gamma))/(1-gamma)
			   	work = Adiabatic.adiabaticculationsWork(volume2, volume1);
			   
			    
			   //calculate Q = 0 for adiabatic processes
			    Q= Adiabatic.adiabaticCalculationsQ();
			    
			    //calculate U = -work 
			    U= Adiabatic.adiabaticCalculationsU(volume2, volume1);
			    
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
			   do
			   {
				   //prompt the user to enter the temperature in kelvin
				   System.out.println("Enter The temperature of the \n"
						   +"isotherm in kelvin remember you cannot have negative values in the \n"
						   +"Kelvin scale or a value of zero");
				   tempInKelvin = keyBoard.nextFloat();
			   
			   }while(tempInKelvin<=0);
			   
			 //prompt the user to enter the moles
				  do
				  { 
					  System.out.println("Enter the number of moles(n) the \n"
						   +"it must be greater than zero");
					  moles = keyBoard.nextFloat();
					   
				  }while(moles<=0);
			   
				//prompt the user to enter the initial volume
					 do
					 {
						  //validate the input to make sure its greater then zero
						  System.out.println("Enter the volume(meters^3) of the \n"
							   +"adiabat remember you cannot have negative values \n"
							   +"or a value of zero");
					   volume1 = keyBoard.nextFloat();
					 } while(volume1<=0);
			   
			   //prompt the user to enter the final volume V2
					do
					{
						//prompt the user to enter the final volume
					   System.out.println("Enter the final volume(m^3) of the \n"
							   +"adiabat remember you cannot have negative values \n"
							   +"or a value of zero");
					   volume2 = keyBoard.nextFloat();
				   
					}while(volume2<=0);
			   
			   //calculate the work which is nRT=ln(V2/V1)
			   //call the work method in the isoThermal class
				work=IsoThermal.isothermCalculationsWork(moles, volume1, volume2, tempInKelvin);
			    
			   //calculate Q = work
			   //call the calculate Q method in the Isothermal class
			    Q= IsoThermal.isothermalCalculationsQ(moles, volume1, volume2, tempInKelvin);
			    
			    //calculate U = 0 cause delta Q = constant
			    U=IsoThermal.isothermalCalculationsU();
			    
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
			   
			   
			//ask the user if he/she wants the entropy for Free Expansion or 
			//for the reversible isothermal process
			   do
			   {
				   System.out.println("\nWhich entropy calculation would you like to run\n"
						   +"1.Free Expansion\n"
						   +"2.Reversible isothermal process\n"
						   +"Press the number that goes with your choice\n");
				   userChoice = keyBoard.nextFloat();   
			   
			   } while(userChoice<1||userChoice>2);
			   
			   //free expansion calculation
			   if(userChoice== 1)
			   {
				   //calculate the entropy for Free expansion
				   //call the calculate S method in the isoThermal method
				   S= IsoThermal.isothermalCalculationsS(moles, volume1, volume2, tempInKelvin);
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
