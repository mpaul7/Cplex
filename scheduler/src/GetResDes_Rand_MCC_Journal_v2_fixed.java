//import ilog.concert.IloNumVarType;
import java.lang.Math;
import java.util.Random;

public class GetResDes_Rand_MCC_Journal_v2_fixed {
	static int [] rvInteger 			= null;
	static double[] normalDouble 		= null;
	static double[] rvDouble 			= null;
	static double[][] MDResDes 			= null;
	static double [] array_t_lm 		= {16.0, 32.0};
	static double [][] array_t_rmc 		= null;
	static double [][] array_s_k          = null;
	static double [] array_k_im 		= {200, 4000};
	static double [] array_k_om 		= {800, 2000};
	static double [] array_e_lm 		= null;
	static double [][] array_t_imc 		= null;
	static double [][] array_t_omc 		= null;
	static double [] array_b_c 			= null;
	
	static double [] e_lm 				= null;
	static double [][] e_rmc 			= null;
	static double [][] d_mc 			= null;
	static double [] t_lm 				= null;
	static double [][] s_k              = null;
    static double [] lambda_m 			= null;
    static double [] b_c 				= null;
	static double [] k_im               = null;
	static double [] k_om               = null;
	static double [][] rvDoubleDouble 	= null;
	static double [][] temp 			= null;
	static double [] inputSizeArray 	= null;
	
	static double [] timeSend 			= null;
	static double [] timeReceive 		= null;
	static double [][] procTimeRemote 	= null;
	
	static double[][]MDResDes_Data 		= null;
	static int [] F = {4, 6, 8};
		
	int nMD;
	int nCloud;
	int nK;
	int nI;
	int nVM;
	int f;
	
	public GetResDes_Rand_MCC_Journal_v2_fixed(int nMD, int nK, int nCloud, int nI, int nVM){
		
		this.nMD = nMD;
		this.nCloud = nCloud;
		this.nK = nK;
		this.nI = nI;
		this.nVM = nVM;
		int row = 10;
		
		MDResDes = new double[row][nK*nMD];
		setMdData(nMD, nK, nI);
		print_MD_array();
		}
	
	public void setMdData(int nMD, int nK, int nI){
		//array_t_lm 		= new double[nMD*nK];
		array_t_rmc 	= new double[nI][nMD*nK];
		//array_k_im 		= new double[nMD*nK];
		//array_k_om 		= new double[nMD*nK];
		array_e_lm 		= new double[nMD*nK];
		array_s_k       = new double[nI][nMD*nK];
		
		//===================================
		//array_t_lm = randDouble(nMD*nK, 0, 100);
		for (int i = 0; i< nI; i++){
			for (int m = 0; m < nMD; m++){
				for (int k = 0; k < nK; k++){
					if (i == 0)f = 4;
					else if (i == 1)f = 6;
					else f = 8;
					array_t_rmc[i][(m * nK) + k] = array_t_lm[(m * nK) + k]/f;
					array_s_k[i][(m * nK) + k] = Math.ceil(array_t_rmc[i][(m * nK) + k]/10.0);
					}
				}
			}
		/*for (int i = 0; i< nI; i++){
			for (int m = 0; m < nMD; m++){
				for (int k = 0; k < nK; k++){
					if (i == 0)f = 2;
					else if (i == 1)f = 4;
					else f = 8;
					array_t_rmc[i][(m * nK) + k] = array_t_lm[(m * nK) + k]/f;
					}
				}
			}*/
		
		//array_k_im = randDouble(nMD*nK, 0, 30000);
		//array_k_om = randDouble(nMD*nK, 0, 30000);
		//double [] array_k_im = {10, 20};
		//double [] array_k_om = {4, 10};
		
		for(int mk = 0; mk< nMD*nK; mk++){
			array_e_lm[mk] = array_t_lm[mk] * 0.5;
			}
		
		
		for (int mk = 0 ; mk < nMD*nK; mk++){
			MDResDes[0][mk] = array_t_lm[mk];
			MDResDes[1][mk] = array_t_rmc[0][mk];
			MDResDes[2][mk] = array_t_rmc[1][mk];
			MDResDes[3][mk] = array_t_rmc[2][mk];
			MDResDes[4][mk] = array_k_im[mk];
			MDResDes[5][mk] = array_k_om[mk];
			MDResDes[6][mk] = array_e_lm[mk];
			MDResDes[7][mk] = array_s_k[0][mk];
			MDResDes[8][mk] = array_s_k[1][mk];
			MDResDes[9][mk] = array_s_k[2][mk];}
	}
	
	void print_MD_array(){
		   System.out.println("========== array_t_lm =============");
		for (double eL : array_t_lm)
			System.out.print(eL + "  " );
		
		System.out.println();
		
		System.out.println("========== array_t_rmc ===============");
		for(int r = 0; r < array_t_rmc.length; r++) {
			for(int c = 0; c < array_t_rmc[r].length; c++){
				System.out.print(array_t_rmc[r][c] + "  ");
				}
			System.out.println();
			}
		
		System.out.println("========= array_k_im ================");
		for (double eL : array_k_im)
			System.out.print(eL + "  " );
		System.out.println();
		
		System.out.println("========= array_k_om ================");
		for (double eL : array_k_om)
			System.out.print(eL + "  " );
		System.out.println();
		
		System.out.println("========= array_e_lm ================");
		for (double eL : array_e_lm)
			System.out.print(eL + "  " );
		System.out.println();
		
		System.out.println("====== MDResDes.length =========");
		for(int r = 0; r < MDResDes.length; r++) {
			for(int c = 0; c < MDResDes[r].length; c++){
				System.out.print(MDResDes[r][c] + " ");
				}
			System.out.println();
			}
		}
	
//===============================================================================================
	public GetResDes_Rand_MCC_Journal_v2_fixed(int nMD, int nK, int nCloud, int nI, int nVM, double[][] MDResDes, double[] b_c, double[] lambda_m){
		this.nMD = nMD;
		this.nK = nK;
		this.nCloud = nCloud;
		this.nI = nI;
		this.nVM = nVM;		
		this.MDResDes_Data = MDResDes; 
		GetResDes_Rand_MCC_Journal_v2_fixed.b_c = b_c;
		GetResDes_Rand_MCC_Journal_v2_fixed.lambda_m = lambda_m;
		
		//print_migrated_info();
				  		
		e_lm		= new double[nMD*nK];
		e_rmc 		= new double[nCloud*nI*nVM][nMD*nK];
		d_mc 		= new double[nCloud*nI*nVM][nMD*nK];
		t_lm 		= new double[nMD*nK];
		k_im 	    = new double[nMD*nK];
		k_om        = new double[nMD*nK];
		s_k         = new double[nCloud*nI*nVM][nMD*nK];
		
		set_e_lm(MDResDes_Data, nMD, nK);
		set_e_rmc(MDResDes_Data, nMD, nK, nCloud, nI, nVM);
		set_d_mc(MDResDes_Data, nMD, nK, nCloud, nI, nVM);
		set_t_lm(MDResDes_Data, nMD, nK);	
		set_k_im(MDResDes_Data, nMD, nK);
		set_k_om(MDResDes_Data, nMD, nK);	
		set_s_k(MDResDes_Data, nMD, nK, nI);
		}
	
	void print_migrated_info(){

		System.out.println("========= lambda_m  from GetResDes_Rand_MCC================");
		   for (double eL : lambda_m){System.out.println(eL + " " ); System.out.println();}
		System.out.println("========= b_c from GetResDes_Rand_MCC================");
		   for (double eL : b_c) {System.out.println(eL + " " ); System.out.println();}
		System.out.println(" ====  MDResDes_Data from GetRe	static double [] s_k                = null;sDes_Rand_MCC ===== ");
		  for(int r = 0; r < MDResDes_Data.length; r++) {
			  for(int c = 0; c < MDResDes_Data[r].length; c++){
				  System.out.print(MDResDes_Data[r][c] + " ");
				  }
			  System.out.println();
			  }
	}
	
	void set_e_lm(double[][] array, int nMD, int nK){
		for (int m = 0; m < nMD; m++){
			for(int k = 0; k < nK; k++){
				e_lm[(m*nK)+k] = array[6][(m*nK)+k];
				}
			}
		}
	
	void set_e_rmc(double[][] array, int nMD, int nK, int nCloud, int nI, int nVM){
		for(int c = 0; c < nCloud; c++){
			   for(int i = 0; i < nI; i++){
				   for (int vm = 0; vm <nVM; vm++){
					   for(int m = 0; m < nMD; m ++){
						   for(int k = 0; k < nK; k++){
							   e_rmc[(c*nI*nVM)+(i*nVM)+vm][(m*nK)+k] = Math.round(0.323 * (array[4][(m*nK)+k ] /
									   b_c[(c*nI*nVM)+(i*nVM)+vm]) + 0.20064 *(array[5][(m*nK)+k ] / b_c[(c*nI*nVM)+(i*nVM)+vm]));
							   }
						   }
					   }
				   }
			   }
		   }
				
	void set_d_mc(double[][] array, int nMD, int nK, int nCloud, int nI, int nVM){
		for(int c = 0; c < nCloud; c++){
			for(int i = 0; i < nI; i++){
				for (int vm = 0; vm <nVM; vm++){
					for(int m = 0; m < nMD; m ++){
						for(int k = 0; k < nK; k++){
							d_mc[(c*nI*nVM)+(i*nVM)+vm][(m*nK)+k] = Math.round(array[4][(m*nK)+k] /									
									   b_c[(c*nI*nVM)+(i*nVM)+vm] + array[5][(m*nK)+k] / b_c[(c*nI*nVM)+(i*nVM)+vm] + array[i+1][(m*nK)+k]);
							}
						}
					}
				}
			}
		}
		
	void set_t_lm(double[][] array, int nMD, int nK){
		for (int m = 0; m < nMD; m++){
			for(int k = 0; k < nK; k++){
				t_lm[(m*nK)+k] = array[0][(m*nK)+k];
				}
			}
		}
		
	void set_k_im(double[][] array, int nMD, int nk){
		for (int m = 0; m < nMD; m++){
			for(int k = 0; k < nK; k++){
				k_im[(m*nK)+k] = array[4][(m*nK)+k];
				}
			}
		}
	
	void set_k_om(double[][] array, int nMD, int nk){
		for (int m = 0; m < nMD; m++){
			for(int k = 0; k < nK; k++){
				k_om[(m*nK)+k] = array[5][(m*nK)+k];
				}
			}
		}
	
	void set_s_k(double [][] array, int nMD, int nK, int nI){
		for(int i = 0; i < nI; i++){
			for(int m = 0; m < nMD; m ++){
				for(int k = 0; k < nK; k++){
					s_k [i][(m*nK)+k]= array[7+i][(m*nK)+k];
					}
				}
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
	
	public double[] getK_im(){
		return k_im;
		}
	
	public double[] getK_om(){
		return k_om;
		}
	
	public double[][] getS_k(){
		return s_k;
		}
			
  //=============================================
  public double [][] temp1 (int m, int c){
	  temp = new double[c][m];
	  double constant = 1.0;
	  for (int i = 0; i < c ; i++) {
		  for (int j = 0; j < m ; j++){ 
			  temp[i][j] = constant;
			  }
		  }
		//System.out.println("========= temp================");
	 // for(int r = 0; r < temp.length; r++) {
		 // for(int cc = 0; cc < temp[r].length; c++){
		//	  System.out.print(temp[r][cc] + " ");
		//	  }
		//  System.out.println();
		//  }
	  return temp;
	  }
 
  //==============    Random Number Generation  =====================
  
  public static double[] randNormalDouble(int nCloud, int mean, int sd){
	  normalDouble = new double[nCloud];
	  Random fRandom = new Random();
	  for (int i = 0; i < nCloud ; ++i){
		  normalDouble[i]  = Math.round(mean + fRandom.nextGaussian() * sd);
		 // System.out.println(String.valueOf(String.valueOf(normalDouble[i])));
		  }
	  return normalDouble;
	  }
  
  //=================================================================
  public static double[] randDouble(int col, double min, double max) {
	  rvDouble = new double[col];
	  for (int i = 0; i < col ; i++) {
		  //double randNum = min + (Math.random() * ((max - min) + 1));
		  double randNum = Math.round(min + (Math.random() * (max - min) + min));
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
		  rvDoubleDouble[i][j] = Math.round(roundedRandNum) ;
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
	  GetResDes_Rand_MCC_Journal_v2_fixed obj = new GetResDes_Rand_MCC_Journal_v2_fixed(1, 2, 1, 3, 1);
	  }
  }



