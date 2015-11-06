package PhysicsExtraCredit;

/******************************************************************************
 *  Compilation:  javac SimpsonsRule.java
 *  Execution:    java SimpsonsRule a b
 *  
 *  Numerically integrate the function in the interval [a, b].
 *
 *  % java SimpsonsRule -3 3
 *  0.9972993166805203                // true answer = 0.9973002040...
 *
 *  Observation: this says that 99.7% of time a standard normal random
 *  variable is within 3 standard deviation of its mean.
 *
 *  % java SimpsonsRule 0 100000
 *  1.3299405953976486                 // true answer = 1/2
 *
 *  Caveat: this is not the best way to integrate the normal density
 *  function. See what happens if you make b very big.
 *
 ******************************************************************************/

public class SimpsonsRule 
{

  /**********************************************************************
   * Standard normal distribution density function.
   * Replace with any sufficiently smooth function.
   **********************************************************************/
   public static double f(double x) 
   {
	   //Math.exp(- x * x / 2) / Math.sqrt(2 * Math.PI);
      return  1/x;//Math.pow(x, 2); // Math.sqrt(2 * Math.PI);
   }

   
  /**********************************************************************
   * Integrate f from a to b using Simpson's rule.
   * Increase N for more precision.
   **********************************************************************/
   public static double integrate(double a, double b) 
   {
      int N = 10000;                    // precision parameter
      double h = ((b - a) / (N - 1));     // step size
 
      // 1/3 terms
      double sum =  (1.0 / 3.0 * (f(a) + f(b)));

      // 4/3 terms
      for (int i = 1; i < N - 1; i += 2) 
      {
    	  double x = a + h * i;
         sum += 4.0 / 3.0 * f(x);
      }

      // 2/3 terms
      for (int i = 2; i < N - 1; i += 2) 
      {
    	  double x = a + h * i;
         sum += 2.0 / 3.0 * f(x);
      }

      return sum * h;
   }

}
