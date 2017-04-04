import java.util.Random;
import java.lang.Math;

public class invokeRM{
	static double [] RV_array = null ;
	static double [] CDF_RV_array = null;
	static int [] int_CDF_RV_array = null;
	static int RV_index = 0;
	static int k;
			
	public static void main (String args[]){  
		 
		//=============================
		double Time = 15;
		//=============================
		int MD_num = 10;
		//=============================
		//int MD_num = MD_num_temp / 2;
		double theta = Time/(double)MD_num;
		int array_size = MD_num + 1;
		RV_array = new double[array_size];
		CDF_RV_array = new double[array_size];
		int_CDF_RV_array = new int [array_size];
		Random rng = new Random();
		
		for (int i = 1; i < MD_num+1; i++) {
		    double randNum = rng.nextDouble();
		   //double ERV = Math.log(1.0 - randNum)/-lambda*1000;
		    double ERV = Math.log(1.0 - randNum)*(-theta);
		   //double ERV = Math.log(1.0 - randNum)*(-lambda);
		   //  System.out.println("RV" + i + " : "  + randNum + "     " + ERV);
		    System.out.println(ERV); // exponential random variable 
		    RV_array[i] = ERV; // ERVs stored in an array 
		    CDF_RV_array[i] = CDF_RV_array[i-1] + RV_array[i]; // CDF of the inter-arrival times
		    int_CDF_RV_array[i] = (int)(CDF_RV_array[i] * 1000); 
		    
		    }// for 
	    System.out.println("==========================================");
		for (double lng : RV_array) { 
			System.out.println(lng);
		} // for 
			
		for (double lng1 : CDF_RV_array) { 
			System.out.println(lng1);
		} // for
		
		for (int lng2 : int_CDF_RV_array) { 
			System.out.println(lng2);
		} // for
		 System.out.println(theta);
		 System.out.println(MD_num);
		//==============================
		/*for (int i = 1; i <= MD_num; i++){
			new Thread(new rmService(int_CDF_RV_array[RV_index++], i)).start();
			} // for 
*/			
		} // main ()
	} //  main class
