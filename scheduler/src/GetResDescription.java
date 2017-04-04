import java.util.Random;
import java.lang.Math;

public class GetResDescription {
	
	static double [] rvDouble = null ;
	static double [][] rvDoubleDouble = null ;
	static double [][] temp = null ;
	static double [][] appProfile = null; 
	static double [] inputSizeArray =null;// new double[2];
	
	static double [] latencyLocal = null;
	static double [][] latencyRemote = null;
	static double [] timeSend = null;
	static double [] timeReceive = null;
	static double [] energySend = null;
	static double [] energyReceive = null;
	static double [][] procTimeRemote = null;
	static double[][] energyRemote = null;
	static double[] energyLocal = null;
	static double[] procPower = null; 
	static double[] rhoN = null;
	static double[][] rhoMN = null;
	
	int nMD;
	int nSN;
	
	public GetResDescription(int nMD, int nSN){
		this.nMD = nMD;
		this.nSN = nSN;
		latencyLocal = new double[nMD];
		latencyRemote = new double[nSN][nMD];
		timeSend = new double[nMD];
		timeReceive = new double[nMD];
		procTimeRemote = new double[nSN][nMD];
		energyRemote = new double[nSN][nMD];
		energySend = new double[nMD];
		energyReceive = new double[nMD];
		energyLocal =  new double[nMD];
		procPower = new double[nMD];
		rhoN = new double[nSN];
		rhoMN = new double[nSN][nMD];
		
		try {	
			ApplProfile objApplProfile  = new ApplProfile();
			appProfile = objApplProfile.getValue();
		}catch (java.io.IOException ex) {
			   System.out.println("IO Error: " + ex);
			   }
		for(int r = 0; r < appProfile.length; r++) {
			  for(int c = 0; c < appProfile[r].length; c++){
				  System.out.print(appProfile[r][c] + " ");
				  }
			  System.out.println();
			  }
		
		inputSizeArray = new double[nMD];
		inputSizeArray = inputSize();
		System.out.println("================================");
		for (double lng : inputSizeArray) {System.out.println(lng);}
		System.out.println("================================");
		//objApplProfile = new ApplProfile();
		setLatencyLocal();
		setTimeSend();
		setTimeReceive();
		setProcTimeRemote();
		setLatencyRemote();
		setEnergySend();
		setEnergyReceive();
		setEnergyRemote();
		setProcPower();
	}
	
	
	public GetResDescription(){
		}
	
	 public double[] inputSize(){
			//double[] inputSizeArray = randDouble(nMD, 60.0 , 300.0);
			inputSizeArray = randDouble(nMD, 60.0 , 300.0);
			System.out.println("================================");
			for (double lng : inputSizeArray) {System.out.println(lng);}
			System.out.println("================================");
			return inputSizeArray;
		}
	
	//=========== Energy ============
	public double[] energyLocal(){
		//double[] energyLocal = {3, 7};
		double[] energyGain = randDouble(nMD, 1.0, 5.0);
		
		for (double lng : energyGain) {System.out.println(lng);}
		for (int eleIndex = 0; eleIndex < nMD; eleIndex++ ){
			energyLocal[eleIndex] = energyRemote[0][eleIndex] * energyGain[eleIndex];
		}
			
		return energyLocal;
		}
	// =========== Energy Remote =============
	public void setEnergySend(){
		
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		for (int eleIndex = 0; eleIndex< nMD; eleIndex++){
			for (int i = 0; i< 18; i++){
				//System.out.println("hello " + appProfile[i][0]);
				if (inputSizeArray[eleIndex] == appProfile[i][0]){
					energySend[eleIndex] = appProfile[i][0];
					}else{ if (inputSizeArray[eleIndex] > ApplProfile.appProfile[i][0] && inputSizeArray[eleIndex] < ApplProfile.appProfile[i+1][0] ){
						x1 = appProfile[i][0];
					    x2 = appProfile[i+1][0];
						y1 = appProfile[i][6];
						y2 = appProfile[i+1][6];
						double slope = (appProfile[i+1][6]- appProfile[i][6])/(appProfile[i+1][0]-appProfile[i][0]);
						System.out.println("slope  " + slope);
						energySend[eleIndex]  = appProfile[i][6] + (slope * (inputSizeArray[eleIndex]-appProfile[i][0]));
						System.out.println("energySend[eleIndex]  " + energySend[eleIndex]);
						}
					} // if
				} //for
			}// for 
		System.out.println("x1  " + x1 + "  x2  " + x2+ "  y1  " + y1+ "  y2  " + y2);
		
		// double[] latencyLocal = {10, 12};
		//double[] latencyLocal  = randDouble(nMD, 10.0 , 12.0);
		//return latencyLocal;
		}
	public void setEnergyReceive(){
		//System.out.println("hello ================================");
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		for (int eleIndex = 0; eleIndex< nMD; eleIndex++){
			for (int i = 0; i< 18; i++){
				//System.out.println("hello " + appProfile[i][0]);
				if (inputSizeArray[eleIndex] == appProfile[i][0]){
					energyReceive[eleIndex] = appProfile[i][0];
					}else{ if (inputSizeArray[eleIndex] > ApplProfile.appProfile[i][0] && inputSizeArray[eleIndex] < ApplProfile.appProfile[i+1][0] ){
						x1 = appProfile[i][0];
					    x2 = appProfile[i+1][0];
						y1 = appProfile[i][7];
						y2 = appProfile[i+1][7];
						double slope = (appProfile[i+1][7]- appProfile[i][7])/(appProfile[i+1][0]-appProfile[i][0]);
						System.out.println("slope  " + slope);
						energyReceive[eleIndex]  = appProfile[i][7] + (slope * (inputSizeArray[eleIndex] - appProfile[i][0]));
						System.out.println("energyReceive[eleIndex]  " + energyReceive[eleIndex]);
						}
					} // if
				} //for
			}// for 
		System.out.println("x1  " + x1 + "  x2  " + x2+ "  y1  " + y1 + "  y2  " + y2);
		
		// double[] latencyLocal = {10, 12};
		//double[] latencyLocal  = randDouble(nMD, 10.0 , 12.0);
		//return latencyLocal;
		}
	
	public void setEnergyRemote(){
		//System.out.println("hello ================================");
		
		for (int c = 0; c< nMD; c++){
			for (int r = 0; r< nSN; r++){
				energyRemote[r][c] = energySend[c] +energyReceive[c];
				} //for
			}// for 
		for(int r = 0; r < energyRemote.length; r++) {
			  for(int c = 0; c < energyRemote[r].length; c++){
				  System.out.print(energyRemote[r][c] + " ");
				  }
			  System.out.println();
			  }
		}
	
	public double[][] energyRemote(){
		//double[][] energyRemote = {{9, 4}, {2, 9}, {8, 8}};
		//double[][] energyRemote = randDoubleDouble(nSN, nMD, 2, 9);
		//for (double lng : energyRemote) {System.out.println(lng);}
		return energyRemote;
		}
	//=====================================================
	
	public void setProcPower(){
		
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		for (int eleIndex = 0; eleIndex< nMD; eleIndex++){
			for (int i = 0; i< 18; i++){
				//System.out.println("hello " + appProfile[i][0]);
				if (inputSizeArray[eleIndex] == appProfile[i][0]){
					procPower[eleIndex] = appProfile[i][0];
					}else{ if (inputSizeArray[eleIndex] > ApplProfile.appProfile[i][0] && inputSizeArray[eleIndex] < ApplProfile.appProfile[i+1][0] ){
						x1 = appProfile[i][0];
					    x2 = appProfile[i+1][0];
						y1 = appProfile[i][8];
						y2 = appProfile[i+1][8];
						double slope = (appProfile[i+1][8]- appProfile[i][8])/(appProfile[i+1][0]-appProfile[i][0]);
						System.out.println("slope  " + slope);
						procPower[eleIndex]  = appProfile[i][8] + (slope * (inputSizeArray[eleIndex]-appProfile[i][0]));
						System.out.println("procPower[eleIndex] " + procPower[eleIndex]);
						}
					} // if
				} //for
			}// for 
		System.out.println("x1  " + x1 + "  x2  " + x2+ "  y1  " + y1+ "  y2  " + y2);
		
		// double[] latencyLocal = {10, 12};
		//double[] latencyLocal  = randDouble(nMD, 10.0 , 12.0);
		//return latencyLocal;
		}
	public double[] rhoN(){
		//double[] rhoN = {50, 70, 60};
		for (int eleIndex = 0; eleIndex< nSN; eleIndex++){
			rhoN [eleIndex] = 600.0;
		}
		//double[] rhoN  = randDouble(nSN, 50.0 , 60.0);
		//for (double lng : energyLocal) {System.out.println(lng);}
		return rhoN;
		}
	
	public double[][] rhoMN(){
		//double[][] rhoMN = {{50, 60}, {60, 40}, {90, 30}};
		//double[][] rhoMN = randDoubleDouble(nSN, nMD, 30, 90);
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
		  }
		return rhoMN;
		}
	
	// ====================================================
	public void setLatencyLocal(){
		//System.out.println("hello ================================");
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		for (int eleIndex = 0; eleIndex< nMD; eleIndex++){
			for (int i = 0; i< 18; i++){
				//System.out.println("hello " + appProfile[i][0]);
				if (inputSizeArray[eleIndex] == appProfile[i][0]){
					latencyLocal[eleIndex] = appProfile[i][0];
					}else{ if (inputSizeArray[eleIndex] > ApplProfile.appProfile[i][0] && inputSizeArray[eleIndex] < ApplProfile.appProfile[i+1][0] ){
						x1 = appProfile[i][0];
					    x2 = appProfile[i+1][0];
						y1 = appProfile[i][2];
						y2 = appProfile[i+1][2];
						double slope = (appProfile[i+1][2]- appProfile[i][2])/(appProfile[i+1][0]-appProfile[i][0]);
						System.out.println("slope  " + slope);
						latencyLocal[eleIndex]  = appProfile[i][2] + (slope * (inputSizeArray[eleIndex]-appProfile[i][0]));
						System.out.println("localLatency[eleIndex]  " + latencyLocal[eleIndex]);
						}
					} // if
				} //for
			}// for 
		System.out.println("x1  " + x1 + "  x2  " + x2+ "  y1  " + y1+ "  y2  " + y2);
		
		// double[] latencyLocal = {10, 12};
		//double[] latencyLocal  = randDouble(nMD, 10.0 , 12.0);
		//return latencyLocal;
		}
	
	public double[] latencyLocal(){
		//double[] latencyLocal = {10, 12};
		//double[] latencyLocal  = randDouble(nMD, 10.0 , 12.0);
		return latencyLocal;
		}
   
	// ==================   Remote Latency =======
	public void setTimeSend(){
	
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		for (int eleIndex = 0; eleIndex< nMD; eleIndex++){
			for (int i = 0; i< 18; i++){
				//System.out.println("hello " + appProfile[i][0]);
				if (inputSizeArray[eleIndex] == appProfile[i][0]){
					timeSend[eleIndex] = appProfile[i][0];
					}else{ if (inputSizeArray[eleIndex] > ApplProfile.appProfile[i][0] && inputSizeArray[eleIndex] < ApplProfile.appProfile[i+1][0] ){
						x1 = appProfile[i][0];
					    x2 = appProfile[i+1][0];
						y1 = appProfile[i][4];
						y2 = appProfile[i+1][4];
						double slope = (appProfile[i+1][4]- appProfile[i][4])/(appProfile[i+1][0]-appProfile[i][0]);
						System.out.println("slope  " + slope);
						timeSend[eleIndex]  = appProfile[i][4] + (slope * (inputSizeArray[eleIndex]-appProfile[i][0]));
						System.out.println("timeSend[eleIndex]  " + timeSend[eleIndex]);
						}
					} // if
				} //for
			}// for 
		System.out.println("x1  " + x1 + "  x2  " + x2+ "  y1  " + y1+ "  y2  " + y2);
		
		// double[] latencyLocal = {10, 12};
		//double[] latencyLocal  = randDouble(nMD, 10.0 , 12.0);
		//return latencyLocal;
		}
	
	public void setTimeReceive(){
		//System.out.println("hello ================================");
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		for (int eleIndex = 0; eleIndex< nMD; eleIndex++){
			for (int i = 0; i< 18; i++){
				//System.out.println("hello " + appProfile[i][0]);
				if (inputSizeArray[eleIndex] == appProfile[i][0]){
					timeReceive[eleIndex] = appProfile[i][0];
					}else{ if (inputSizeArray[eleIndex] > ApplProfile.appProfile[i][0] && inputSizeArray[eleIndex] < ApplProfile.appProfile[i+1][0] ){
						x1 = appProfile[i][0];
					    x2 = appProfile[i+1][0];
						y1 = appProfile[i][5];
						y2 = appProfile[i+1][5];
						double slope = (appProfile[i+1][5]- appProfile[i][5])/(appProfile[i+1][0]-appProfile[i][0]);
						System.out.println("slope  " + slope);
						timeReceive[eleIndex]  = appProfile[i][5] + (slope * (inputSizeArray[eleIndex] - appProfile[i][0]));
						System.out.println("timeReceive[eleIndex]  " + timeReceive[eleIndex]);
						}
					} // if
				} //for
			}// for 
		System.out.println("x1  " + x1 + "  x2  " + x2+ "  y1  " + y1 + "  y2  " + y2);
		
		// double[] latencyLocal = {10, 12};
		//double[] latencyLocal  = randDouble(nMD, 10.0 , 12.0);
		//return latencyLocal;
		}
	
	public void setProcTimeRemote(){
		for (int c = 0; c< nMD; c++){
			for (int r = 0; r< nSN; r++){
				procTimeRemote [r][c]= appProfile[r][3];
			   }
			}
		 System.out.println("procTimeRemote.length");
		for(int r = 0; r < procTimeRemote.length; r++) {
		  for(int c = 0; c < procTimeRemote[r].length; c++){
			  System.out.print(procTimeRemote[r][c] + " ");
			  }
		  System.out.println();
		  }
	}
	
    public void setLatencyRemote(){
		//System.out.println("hello ================================");
		
		for (int c = 0; c< nMD; c++){
			for (int r = 0; r< nSN; r++){
				latencyRemote[r][c] = procTimeRemote[r][c] + timeSend[c] +timeReceive[c];
				} //for
			}// for 
		for(int r = 0; r < latencyRemote.length; r++) {
			  for(int c = 0; c < latencyRemote[r].length; c++){
				  System.out.print(latencyRemote[r][c] + " ");
				  }
			  System.out.println();
			  }
		}
	
  public double [][] latencyRemote(){
	 // double[][] latencyRemote = {{5, 4}, {6, 8}, {10, 12}};
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



