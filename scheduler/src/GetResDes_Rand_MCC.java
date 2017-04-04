//import ilog.concert.IloNumVarType;
import java.lang.Math;
import java.util.Random;

public class GetResDes_Rand_MCC {
	static int [] rvInteger 			= null;
	static double[] normalDouble 		= null;
	static double[] rvDouble 			= null;
	static double[][] MDResDes 			= null;
	static double F 					= 4.0;
	static double [] array_t_lm 		= null ;
	static double [] array_t_rmc 		= null;
	static double [] array_k_im 		= null;
	static double [] array_k_om 		= null;
	//static double [] array_lambda_m 	= null;
	//static double [] array_beta_m 		= null;
	static double [] array_e_lm 		= null;
	static double [][] array_t_imc 		= null;
	static double [][] array_t_omc 		= null;
	static double [] array_b_c 			= null;
	
	static double [] e_lm 				= null ;
	static double [][] e_rmc 			= null;
	static double [][] d_mc 			= null;
	static double [] t_lm 				= null;
    static double [] lambda_m 			= null;
	//static double [] beta_m 			= null;
	static double [] b_c 				= null;
	
	static double [][] rvDoubleDouble 	= null ;
	static double [][] temp 			= null ;
	static double [] inputSizeArray 	= null;
	
	
	static double [] timeSend 			= null;
	static double [] timeReceive 		= null;
	static double [][] procTimeRemote 	= null;
	
	static double[][]MDResDes_Data 		= null;
		
	int nMD;
	int nCloud;
	
	public GetResDes_Rand_MCC(int nMD){
		
		this.nMD = nMD;
		int row = 7;
		MDResDes = new double[row][nMD];
		setMdData(nMD);
			
		for (int j = 0 ; j < nMD; j++){MDResDes[0][j] = array_t_lm[j];}
		for (int j = 0 ; j < nMD; j++){MDResDes[1][j] = array_t_rmc[j];}
		for (int j = 0 ; j < nMD; j++){MDResDes[2][j] = array_k_im[j];}
		for (int j = 0 ; j < nMD; j++){MDResDes[3][j] = array_k_om[j];}
		//for (int j = 0 ; j < nMD; j++){MDResDes[4][j] = array_lambda_m[j];}
		//for (int j = 0 ; j < nMD; j++){MDResDes[5][j] = array_beta_m[j];}
		for (int j = 0 ; j < nMD; j++){MDResDes[6][j] = array_e_lm[j];}
		
		System.out.println("array_e_lm   " + array_t_lm[0] );
		
		System.out.println("Md row  " + MDResDes.length);
		System.out.println("Md col  " +  MDResDes[1].length);
		System.out.println("====== MDResDes.length =========");
		for(int r = 0; r < MDResDes.length; r++) {
			for(int c = 0; c < MDResDes[r].length; c++){
				System.out.print(MDResDes[r][c] + " ");
				}
			System.out.println();
			}
		}
	
	public void setMdData(int nMD){
		array_t_lm 		= new double[nMD];
		array_t_rmc 	= new double[nMD];
		array_k_im 		= new double[nMD];
		array_k_om 		= new double[nMD];
		//array_lambda_m 	= new double[nMD];
		//array_beta_m 	= new double[nMD];
		array_e_lm 		= new double[nMD];
		//array_b_c 		= new double[nCloud];
		
		//===================================
		array_t_lm = randDouble(nMD, 0, 100);
		for (int i = 0; i < nMD; i++){array_t_rmc[i] = array_t_lm[i]/F;}
		array_k_im = randDouble(nMD, 0, 30000);
		array_k_om = randDouble(nMD, 0, 30000);
		//array_lambda_m = randDouble(nMD, 1/F, 2.0);
		
		/*for(int i = 0; i< nMD; i++){
			array_beta_m[i] = (array_k_im[i] + array_k_om[i])/
					((array_lambda_m[i] * array_t_lm[i]) - array_t_rmc[i]);
			}*/
		
		for(int i = 0; i< nMD; i++){
			array_e_lm[i] = array_t_lm[i] * 0.7;
			}
			
		print_MD_array();
	}
	   void print_MD_array(){
		   System.out.println("========== array_t_lm ===============");
		for (double eL : array_t_lm)
			System.out.println(eL + " " );
		System.out.println();
		System.out.println("========== array_t_rmc ===============");
		for (double eL : array_t_rmc)
			System.out.println(eL + " " );
		System.out.println();
		System.out.println("========= array_k_im ================");
		for (double eL : array_k_im)
			System.out.println(eL + " " );
		System.out.println();
		
		System.out.println("========= array_k_om ================");
		for (double eL : array_k_om)
			System.out.println(eL + " " );
		System.out.println();
		
		/*System.out.println("========= array_lambda_m ================");
		for (double eL : array_lambda_m)
			System.out.println(eL + " " );
		System.out.println();*/
		
		/*System.out.println("========= array_beta_m ================");
		for (double eL : array_beta_m)
			System.out.println(eL + " " );
		System.out.println();*/
		
		System.out.println("========= array_e_lm ================");
		for (double eL : array_e_lm)
			System.out.println(eL + " " );
		System.out.println();
		
			
		}
	
//===============================================================================================
	public GetResDes_Rand_MCC(int nMD, int nCloud, double[][] MDResDes, double[] b_c, double[] lambda_m){
		
		this.nCloud = nCloud;
		this.nMD = nMD;
		this.MDResDes_Data = MDResDes; 
		GetResDes_Rand_MCC.b_c = b_c;
		GetResDes_Rand_MCC.lambda_m = lambda_m;
		
		System.out.println("========= lambda_m  from GetResDes_Rand_MCC================");
		   for (double eL : lambda_m){System.out.println(eL + " " ); System.out.println();}
		System.out.println("========= b_c from GetResDes_Rand_MCC================");
		   for (double eL : b_c) {System.out.println(eL + " " ); System.out.println();}
		System.out.println(" ====  MDResDes_Data from GetResDes_Rand_MCC ===== ");
		  for(int r = 0; r < MDResDes_Data.length; r++) {
			  for(int c = 0; c < MDResDes_Data[r].length; c++){
				  System.out.print(MDResDes_Data[r][c] + " ");
				  }
			  System.out.println();
			  }
				  		
		e_lm		= new double[nMD];
		e_rmc 		= new double[nCloud][nMD];
		d_mc 		= new double[nCloud][nMD];
	//	lambda_m 	= new double[nMD];
		t_lm 		= new double[nMD];
	//	b_c 		= new double[nCloud];
		set_e_lm(MDResDes_Data, nMD);
		set_e_rmc(MDResDes_Data, nMD, nCloud);
		set_d_mc(MDResDes_Data, nMD, nCloud);
		set_t_lm(MDResDes_Data, nMD);
		}
	
	void set_e_lm(double[][] array, int nMD){
		for (int i = 0; i< nMD; i++){
			e_lm[i] = array[6][i]; 
		}
	}
	
	void set_e_rmc(double[][] array, int nMD, int nCloud){
		for(int c = 0; c < nCloud; c++ ){
			for(int m = 0; m < nMD; m++){
				e_rmc[c][m] = 0.323 * (array[2][m] / b_c[c]) + 0.20064 *(array[3][m] / b_c[c]);
				}
			}
		}
	
	void set_d_mc(double[][] array, int nMD, int nCloud){
		for(int m = 0; m < nMD; m++){
			for(int c = 0; c < nCloud; c++ ){
				d_mc[c][m] = array[2][m] / b_c[c] + array[3][m] / b_c[c] + array[1][m];
				}
			}
		}
	
	void set_t_lm(double[][] array, int nMD){
		for (int m = 0; m< nMD; m++){
			t_lm[m] = array[0][m]; 
		}
	}
	
		
	// ========= Get Values ================
	 
	public double [][] getData(){
		return MDResDes;
	}
	
	public double [] getB_c(){
		return array_b_c;
	}
	
	public double[] getE_lm(){
		return e_lm;
		}
	
	public double[][] getE_rmc(){
		return e_rmc;
		}
	
	public double[][] getD_mc(){
		return d_mc;
		}
	
	public double[] getLambda_m(){
		return lambda_m;
		}
	
	public double[] getT_lm(){
		return t_lm;
		}
	
	/*public double[] GetBeta_m(){
		return beta_m;
		}*/
	
	
  //=============================================
  public double [][] temp1 (int nMD, int nCloud){
	  temp = new double[nCloud][nMD];
	 	  double constant = 1.0;
	  //double [][] temp = {{1, 1}, {1, 1}, {1, 1}};
	  for (int i = 0; i < nCloud ; i++) {
		  for (int j = 0; j < nMD ; j++){ 
			  temp[i][j] = constant;
			  }
		  }
	  return temp;
	  }
 
  //==============    Random Number Generation  =====================
  
  public static double[] randNormalDouble(int nCloud, int mean, int sd){
	  normalDouble = new double[nCloud];
	  Random fRandom = new Random();
	  for (int i = 0; i < nCloud ; ++i){
		  normalDouble[i]  = mean + fRandom.nextGaussian() * sd;
		 // System.out.println(String.valueOf(String.valueOf(normalDouble[i])));
		  }
	  return normalDouble;
	  }
  
  //=================================================================
  public static double[] randDouble(int col, double min, double max) {
	  rvDouble = new double[col];
	  for (int i = 0; i < col ; i++) {
		  //double randNum = min + (Math.random() * ((max - min) + 1));
		  double randNum = min + (Math.random() * (max - min) + min);
		  //  double roundedRandNum = (double) Math.round(randNum * 100) / 100;
		  rvDouble[i] = randNum ;
		  }
	  //for (double lng : RV_array) {System.out.println(lng);} // for
	  return rvDouble;
	  }
  
  public static double[][] randDoubleDouble(int row, int col, double min, double max) {
	  rvDoubleDouble = new double[row][col];
	  for (int i = 0; i < row ; i++) {
		  for (int j = 0; j < col ; j++){ 
		  double randNum = min + (Math.random() * ((max - min) + 1));
		  double roundedRandNum = (double) Math.round(randNum * 100) / 100;
		  rvDoubleDouble[i][j] = roundedRandNum ;
		  }
	  }
	  //for (double lng : RV_array) {System.out.println(lng);} // for
	  return rvDoubleDouble;
	  }
  
  public static int[] randInteger(int col, int min, int max) {
	  rvInteger = new int[col];
	  for (int i = 0; i < col ; i++) {
		  int  randNum = (int)(min + (Math.random() * ((max - min) + 1)));
		  //Min + (int)(Math.random() * ((Max - Min) + 1))
		  //double roundedRandNum = (double) Math.round(randNum * 100) / 100;
		  rvInteger[i] = randNum;//oundedRandNum ;
		  }
	  //for (double lng : RV_array) {System.out.println(lng);} // for
	  return rvInteger;
	  }
  
  public static int randInt(int min, int max) {
	  int  randNum = (int)(min + (Math.random() * ((max - min) + 1)));
	  return randNum;
	  }
  
  public static double randDoub(int min, int max) {
	  double randNum = min + (Math.random() * ((max - min) + 1));
	 // double roundedRandNum = (double) Math.round(randNum * 100) / 100;
	 // return roundedRandNum;
	  return randNum;
	  }
  //===============================================================================
  
  public static void main(String args[]){
	  GetResDes_Rand_MCC obj = new GetResDes_Rand_MCC(2);
	  System.out.println(" ====  MDResDes  ===== ");
	 /* for(int r = 0; r < MDResDes.length; r++) {
		  for(int c = 0; c < MDResDes[r].length; c++){
			  System.out.print(MDResDes[r][c] + " ");
			  }
		  System.out.println();
		  }*/
	  /*System.out.println(" ");
	  System.out.println(" ====  latencyLocal ===== ");
	  for (double lng : latencyLocal) {System.out.print(lng + " " );}
	  System.out.println(" ");
	  System.out.println(" ====  energyLocal ===== ");
	  for (double lng : energyLocal) {System.out.print(lng + " " );}
	  System.out.println(" ");
	  System.out.println(" ====  rhoN ===== ");
	  //for (double lng : rhoN) {System.out.print(lng + " " );}
	  System.out.println(" ");*/
	 
	//  System.out.println(" ====  energyRemote  ===== ");
	 /* for(int r = 0; r < energyRemote.length; r++) {
		  for(int c = 0; c < energyRemote[r].length; c++){
			  System.out.print(energyRemote[r][c] + " ");
			  }
		  System.out.println();
		  }*/
	 
	 /* System.out.println(" ");
	  System.out.println(" ====  latencyRemote ===== ");
	  for(int r = 0; r < latencyRemote.length; r++) {
		  for(int c = 0; c < latencyRemote[r].length; c++){
			  System.out.print(latencyRemote[r][c] + " ");
			  }
		  System.out.println();
		  }
	  System.out.println(" ");
	  System.out.println(" ====  rhoMN ===== ");
	  for(int r = 0; r < rhoMN.length; r++) {
		  for(int c = 0; c < rhoMN[r].length; c++){
			  System.out.print(rhoMN[r][c] + " ");
			  }
		  System.out.println();
		  }*/
	  }
  }



