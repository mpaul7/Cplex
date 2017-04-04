import java.util.Random;
import java.lang.Math;

public class GetResDescription_nr {
	
	static double [] rvDouble = null ;
	static double [][] rvDoubleDouble = null ;
	static double [][] temp = null ;
	static double [][] appProfile = null; 
	public static double [] inputSizeArray =null;// new double[2];
	//ApplProfile objApplProfile;
	int nMD;
	int nSN;
	
	public GetResDescription_nr(int nMD, int nSN){
		this.nMD = nMD;
		this.nSN = nSN;
		}
		
	public GetResDescription_nr(){
		}
	
	
	//=========== Energy ============
	public double[] energyLocal(){
		double[] energyLocal = {3, 7};
		/*double[] energyGain = randDouble(nMD, 1.0, 5.0);
		
		for (double lng : energyGain) {System.out.println(lng);}
		for (int eleIndex = 0; eleIndex < nMD; eleIndex++ ){
			energyLocal[eleIndex] = energyRemote[0][eleIndex] * energyGain[eleIndex];
		}*/
			
		return energyLocal;
		}
	// =========== Energy Remote =============
	
	public double[][] energyRemote(){
		double[][] energyRemote = {{6, 2}, {6, 2}, {6, 2}};
		//double[][] energyRemote = randDoubleDouble(nSN, nMD, 2, 9);
		//for (double lng : energyRemote) {System.out.println(lng);}
		return energyRemote;
		}
	//=====================================================
		
	public double[] rhoN(){
		double[] rhoN = {50, 70, 60};
		/*for (int eleIndex = 0; eleIndex< nSN; eleIndex++){
			rhoN [eleIndex] = 600.0;
		}*/
		//double[] rhoN  = randDouble(nSN, 50.0 , 60.0);
		//for (double lng : energyLocal) {System.out.println(lng);}
		return rhoN;
		}
	
	public double[][] rhoMN(){
		double[][] rhoMN = {{50, 60}, {60, 40}, {90, 30}};
		/*//double[][] rhoMN = randDoubleDouble(nSN, nMD, 30, 90);
		for (int c = 0; c< nMD; c++){
			for (int r = 0; r< nSN; r++){
				rhoMN [r][c]= procPower[c];
			   }
			}
		for(int r = 0; r < procTimeRemote.length; r++) {
		  for(int c = 0; c < procTimeRemote[r].length; c++){
			  System.out.print(procTimeRemote[r][c] + " ");
			  }
		  System.out.println();
		  }*/
		return rhoMN;
		}
	//=====================================================
	
	
		public double[] alphaN(){
			double[] alphaN = {31, 41, 51};
			/*for (int eleIndex = 0; eleIndex< nSN; eleIndex++){
				rhoN [eleIndex] = 600.0;
			}*/
			//double[] rhoN  = randDouble(nSN, 50.0 , 60.0);
			//for (double lng : energyLocal) {System.out.println(lng);}
			return alphaN;
			}
		
		public double[][] alphaMN(){
			double[][] alphaMN = {{40, 50}, {55, 45}, {77, 56}};
			/*//double[][] rhoMN = randDoubleDouble(nSN, nMD, 30, 90);
			for (int c = 0; c< nMD; c++){
				for (int r = 0; r< nSN; r++){
					rhoMN [r][c]= procPower[c];
				   }
				}
			for(int r = 0; r < procTimeRemote.length; r++) {
			  for(int c = 0; c < procTimeRemote[r].length; c++){
				  System.out.print(procTimeRemote[r][c] + " ");
				  }
			  System.out.println();
			  }*/
			return alphaMN;
			}
		
	// ====================================================
	
	public double[] latencyLocal(){
		double[] latencyLocal = {10, 12};
		//double[] latencyLocal  = randDouble(nMD, 10.0 , 12.0);
		return latencyLocal;
		}
   
	// ==================   Remote Latency =======
	
	
	
	
	
	
    	
  public double [][] latencyRemote(){
	  double[][] latencyRemote = {{5, 4}, {6, 8}, {10, 12}};
	  //double[][] latencyRemote = randDoubleDouble(nSN, nMD, 4, 12);
	  return latencyRemote;
	  }
   //=============================================
  public double [][] temp1 (int nMD, int nSN){
	  temp = new double[nSN][nMD];
	  double constant = 1.0;
	  //double [][] temp = {{1, 1}, {1, 1}, {1, 1}};
	  for (int i = 0; i < nSN ; i++) {
		  for (int j = 0; j < nMD ; j++){ 
			  temp[i][j] = constant;
			  }
		  }
	  return temp;
	  }
  
  //==================================================================
  public static double[] randDouble(int col, double min, double max) {
	  rvDouble = new double[col];
	  for (int i = 0; i < col ; i++) {
		  double randNum = min + (Math.random() * ((max - min) + 1));
		  double roundedRandNum = (double) Math.round(randNum * 100) / 100;
		  rvDouble[i] = roundedRandNum ;
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
  //===============================================================================
  
  public static void main(String args[]){
	  GetResDescription obj = new GetResDescription();
	 double[] array= obj.inputSize();
	 for (double lng : array) {System.out.println(lng);}
	  /*for(int r = 0; r < array.length; r++) {
		  for(int c = 0; c < array[r].length; c++){
			  System.out.print(array[r][c] + " ");
			  }
		  System.out.println();
		  }*/
	  
  }
  
  
  }



