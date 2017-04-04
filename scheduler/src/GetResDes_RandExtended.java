import ilog.concert.IloNumVarType;
import java.lang.Math;

public class GetResDes_RandExtended {
	static int [] rvInteger = null;
	static double [] rvDouble = null ;
	static double [][] rvDoubleDouble = null ;
	static double [][] temp = null ;
	static double [][] appProfile = null; 
	static double [] inputSizeArray =null;// new double[2];
	static int [] AppTypeArray = null;
	
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
	static double[] alphaN = null;
	static double[][] alphaMN = null;
	static int[] MDAppType = null;  
	static double[][] MDResDes = null;
	static double[][]MDResDes_Data = null;
	static double Ts;
	static double Tr;
	static double Es;
	static double Er;
	
	int nMD;
	int nSN;
	
	public GetResDes_RandExtended(int nMD){
		this.nMD = nMD;
		MDAppType = new int[nMD];
		AppTypeArray = new int[nMD];
		
		MDResDes = new double[9][nMD];
		setMDAppType();
		setData();
		//setRhoN();
		
	}
		
	public GetResDes_RandExtended(int nMD, int nSN, double[][]MDResDes){
		this.nSN = nSN;
		this.nMD = nMD;
		this.MDResDes_Data = MDResDes; 
		System.out.println(" ====  MDResDes_Data  ===== ");
		  for(int r = 0; r < MDResDes_Data.length; r++) {
			  for(int c = 0; c < MDResDes_Data[r].length; c++){
				  System.out.print(MDResDes_Data[r][c] + " ");
				  }
			  System.out.println();
			  }
		
		latencyLocal = new double[nMD];
		latencyRemote = new double[nSN][nMD];
		energyLocal =  new double[nMD];
		energyRemote = new double[nSN][nMD];
		rhoN = new double[nSN];
		rhoMN = new double[nSN][nMD];
		alphaN = new double[nSN];
		alphaMN = new double[nSN][nMD];
		
		setLatencyLocal(MDResDes_Data, nMD);
		setEnergyLocal(MDResDes_Data, nMD);
		setLatencyRemote(MDResDes_Data, nMD, nSN);	
		setEnergyRemote(MDResDes_Data, nMD, nSN);
		setRhoN(nSN);
		setRhoMN(MDResDes_Data);
		setAlphaN(nSN);
		setAlphaMN(MDResDes_Data);
		}
	void setLatencyLocal(double[][] array, int nMD){
		for (int i = 0; i< nMD; i++){
			latencyLocal[i] = array[2][i];
		}
	}
	
	void setEnergyLocal(double[][] array, int nMD){
		for (int i = 0; i< nMD; i++){
			energyLocal[i] = array[2][i] * 0.7;
		}
	}
	
	void setLatencyRemote(double[][] array, int nMD, int nSN){
		for(int i = 0; i < nMD; i++){
		for(int j = 0; j < nSN; j++ ){
			latencyRemote[j][i] = array[3][i] + array[4][i] + array[5][i];
			}
		}
	}
	
	void setEnergyRemote(double[][] array, int nMD, int nSN){
		for(int i = 0; i < nMD; i++){
		for(int j = 0; j < nSN; j++ ){
			energyRemote[j][i] = array[6][i] + array[7][i];
			}
		}
	}
	
	void setRhoN(int _nSN){
		for(int i = 0; i< nSN; i++){
			rhoN[i]	= 100.0;
		}
		
	}
	
	void setAlphaN(int _nSN){
		for(int i = 0; i< nSN; i++){
			alphaN[i]	= 100.0;
		}
		
	}
	
	void setRhoMN(double[][] array){
		double _rhoMN = 0;
		for (int i = 0; i < nMD; i++){
			double AppCode = array[8][i];
			if(AppCode == 1 || AppCode == 4 || AppCode == 7 || AppCode ==10) // L!
				_rhoMN = 10.0; //_alphaMN = 10.0;
			if(AppCode == 2 || AppCode == 5 || AppCode == 8 || AppCode ==11) // L2
				_rhoMN = 10.0; //_alphaMN = 10.0;
			if(AppCode == 3 || AppCode == 6 || AppCode == 9 || AppCode ==12) // L3
				_rhoMN = 10.0; //_alphaMN = 10.0;
				
			if(AppCode == 13 || AppCode == 16 || AppCode == 19 || AppCode ==22) // M1
				_rhoMN = 10.0; //_alphaMN = 30.0;
			if(AppCode == 14 || AppCode == 17 || AppCode == 20 || AppCode ==23) // M2
				_rhoMN = 20.0; //_alphaMN = 10.0; 
			if(AppCode == 15 || AppCode == 18 || AppCode == 21 || AppCode ==24) //M3 
				_rhoMN = 20.0; //_alphaMN = 30.0;
				
			if(AppCode == 25 || AppCode == 28 || AppCode == 31 || AppCode ==34) //H1
				_rhoMN = 10.0; //_alphaMN = 50.0;
			if(AppCode == 26|| AppCode == 29 || AppCode == 32 || AppCode ==35) // H2
				_rhoMN = 40.0; //_alphaMN = 10.0;
			if(AppCode == 27 || AppCode == 30 || AppCode == 33 || AppCode ==36) // H3
				_rhoMN = 40.0; //_alphaMN = 50.0;	
				for (int j = 0; j < nSN; j++){
					rhoMN[j][i] = _rhoMN;
			}
		}
		/*if(AppCode == 1 || AppCode == 4 || AppCode == 7 || AppCode ==10)
			_rhoMN = 10.0;
		if(AppCode == 2 || AppCode == 5 || AppCode == 8 || AppCode ==11)
			_rhoMN = 30.0;
		if(AppCode == 3 || AppCode == 6 || AppCode == 9 || AppCode ==12)
			_rhoMN = 50.0;
		for(int i = 0; i < nSN; i++){ 
			rhoMN[i][mthMD] = _rhoMN;
			}*/
	}
	
	void setAlphaMN(double[][] array){
		double _alphaMN = 0;
		for (int i = 0; i < nMD; i++){
			double AppCode = array[8][i];
			
			if(AppCode == 1 || AppCode == 4 || AppCode == 7 || AppCode ==10) // L!
				_alphaMN = 10.0;
			if(AppCode == 2 || AppCode == 5 || AppCode == 8 || AppCode ==11) // L2
				_alphaMN = 10.0;
			if(AppCode == 3 || AppCode == 6 || AppCode == 9 || AppCode ==12) // L3
				_alphaMN = 10.0;
				
			if(AppCode == 13 || AppCode == 16 || AppCode == 19 || AppCode ==22) // M1
				_alphaMN = 20.0;
			if(AppCode == 14 || AppCode == 17 || AppCode == 20 || AppCode ==23) // M2
				_alphaMN = 10.0; 
			if(AppCode == 15 || AppCode == 18 || AppCode == 21 || AppCode ==24) //M3 
				_alphaMN = 20.0;
				
			if(AppCode == 25 || AppCode == 28 || AppCode == 31 || AppCode ==34) //H1
				_alphaMN = 40.0;
			if(AppCode == 26|| AppCode == 29 || AppCode == 32 || AppCode ==35) // H2
				_alphaMN = 10.0;
			if(AppCode == 27 || AppCode == 30 || AppCode == 33 || AppCode ==36) // H3
				_alphaMN = 40.0;	
				for (int j = 0; j < nSN; j++){
					alphaMN[j][i] = _alphaMN;
			}
		}
		/*if(AppCode == 1 || AppCode == 4 || AppCode == 7 || AppCode ==10)
			_rhoMN = 10.0;
		if(AppCode == 2 || AppCode == 5 || AppCode == 8 || AppCode ==11)
			_rhoMN = 30.0;
		if(AppCode == 3 || AppCode == 6 || AppCode == 9 || AppCode ==12)
			_rhoMN = 50.0;
		for(int i = 0; i < nSN; i++){ 
			rhoMN[i][mthMD] = _rhoMN;
			}*/
	}
	
	
	//====================================
	public void setMDAppType(){
		AppTypeArray = randInteger(nMD, 1, 36); 
		for (int i = 0; i< nMD; i++){
			MDAppType[i] = AppTypeArray[i];
			}
		for (int lng : MDAppType) {System.out.print(lng + "  ");}
		System.out.println(" ");
		}
	
	/*public void setRhoN(){
		for(int i = 0; i < nSN; i++)
			rhoN[i] = 100.0;
		}*/
	
	// =========  Set Data =================
	
	public void setData(){
		System.out.println(" hello from setData");
		//System.out.println(nMD);
		int appType;
		for (int i = 0; i < nMD; i++) {
			appType = MDAppType[i];
			switch (appType) {
				case 1:
					case1(i, appType);
					break;
				case 2:
					case1(i, appType);
					break;
				case 3:
				    case1(i, appType);
					break;
				case 4:
					case2(i, appType);
					break;
				case 5:
					case2(i, appType);
					break;
				case 6:
					case2(i, appType);
					break;
				case 7:
					case3(i, appType);
					break;
				case 8:
					case3(i, appType);
					break;
				case 9:
					case3(i, appType);
					break;
				case 10:
					case4(i, appType);
					break;
				case 11:
					case4(i, appType);
					break;
				case 12:
					case4(i, appType);
					break;
				case 13:
					case5(i, appType);
					break;
				case 14:
					case5(i, appType);
					break;
				case 15:
				    case5(i, appType);
					break;
				case 16:
					case6(i, appType);
					break;
				case 17:
					case6(i, appType);
					break;
				case 18:
					case6(i, appType);
					break;
				case 19:
					case7(i, appType);
					break;
				case 20:
					case7(i, appType);
					break;
				case 21:
					case7(i, appType);
					break;
				case 22:
					case8(i, appType);
					break;
				case 23:
					case8(i, appType);
					break;
				case 24:
					case8(i, appType);
					break;
				case 25:
					case9(i, appType);
					break;
				case 26:
					case9(i, appType);
					break;
				case 27:
				    case9(i, appType);
					break;
				case 28:
					case10(i, appType);
					break;
				case 29:
					case10(i, appType);
					break;
				case 30:
					case10(i, appType);
					break;
				case 31:
					case11(i, appType);
					break;
				case 32:
					case11(i, appType);
					break;
				case 33:
					case11(i, appType);
					break;
				case 34:
					case12(i, appType);
					break;
				case 35:
					case12(i, appType);
					break;
				case 36:
					case12(i, appType);
					break;
					} // end of Switch
			} //  end of if
		} // end of for    
	
	// =======  Type -I ========== 
	public void case1(int mthMD, int appType){
		int input  = randInt(5, 10);
		int output  = randInt(5, 10);
		int latencyLocal = randInt(7, 10);
		double Tp = latencyLocal/4;
		setLatencyParameters(input, output); // set Ts and Tr
		setEnergyParameters(Ts, Tr); // set Es and Er
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setLatencyRemote(mthMD, input, output, latencyLocal);
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 1);
		}
	
	public void case2(int mthMD, int appType){
		int input = randInt(5, 10);
		int output = randInt(5, 10);
		int latencyLocal = randInt(11, 20);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setEnergyLoal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 2);
		}
	
	public void case3(int mthMD, int appType){
		int input = randInt(5, 10);
		int output = randInt(5, 10);
		int latencyLocal = randInt(21, 30);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 3);
		}
	
	// =======  Type - II ========== 
	public void case4(int mthMD, int appType){
		int input = randInt(5, 10);
		int output = randInt(10000, 500000);
		int latencyLocal = randInt(7, 10);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		
		//setLatencyRemote(mthMD, input, output, latencyLocal);
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 4);
		}
	
	public void case5(int mthMD, int appType){
		int input = randInt(5, 10);
		int output = randInt(10000, 500000);
		int latencyLocal = randInt(11, 20);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setLatencyRemote(mthMD, input, output, latencyLocal);
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 5);
		}
	
	public void case6(int mthMD, int appType){
		int input = randInt(5, 10);
		int output = randInt(10000, 500000);
		int latencyLocal = randInt(21, 30);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
	//	System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;	
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 6);
		}
	
	// =======  Type -III ========== 
	public void case7(int mthMD, int appType){
		int input = randInt(10000, 500000);
		int output = randInt(5, 10);
		int latencyLocal = randInt(7, 10);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;	
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setEnergyLo6cal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 7);
		}
	
	public void case8(int mthMD, int appType){
		int input = randInt(10000, 500000);
		int output = randInt(5, 10);
		int latencyLocal = randInt(11, 20);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 8);
		}
	
	public void case9(int mthMD, int appType){
		
		int input = randInt(10000, 500000);
		int output = randInt(5, 10);
		int latencyLocal = randInt(21, 30);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 9);
		}
	
	// =======  Type -IV ========== 
	public void case10(int mthMD, int appType){
		int input = randInt(10000, 500000);
		int output = randInt(10000, 500000);
		int latencyLocal = randInt(7, 10);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//setRhoMN(mthMD, 10);
		}
	
	public void case11(int mthMD, int appType){
		int input = randInt(10000, 500000);
		int output = randInt(10000, 500000);
		int latencyLocal = randInt(11, 20);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//etRhoMN(mthMD, 11);
		}
	
	public void case12(int mthMD, int appType){
		
		int input = randInt(10000, 500000);
		int output = randInt(10000, 500000);
		int latencyLocal = randInt(21, 30);
		double Tp = (double)latencyLocal/4;
		setLatencyParameters(input, output);
		setEnergyParameters(Ts, Tr);
		//System.out.println("Hello from setLatencyRemote  " + Ts + " " + Tr );
		MDResDes[0][mthMD] = input;
		MDResDes[1][mthMD] = output ;
		MDResDes[2][mthMD] = latencyLocal;
		MDResDes[3][mthMD] = Tp;
		MDResDes[4][mthMD] = Ts;
		MDResDes[5][mthMD] = Tr;
		MDResDes[6][mthMD] = Es;
		MDResDes[7][mthMD] = Er;
		MDResDes[8][mthMD] = appType;
		
		//setEnergyLocal(mthMD, latencyLocal);
		//setEnergyRemote(mthMD, input, output);
		//	setRhoMN(mthMD, 12);
		}
	
	//================   Set Values ================
	public void setLatencyParameters(int input, int output){
		if (input < 10000){Ts = (double)input/500000;}
		if (input >= 10000 && input <= 50000 ){Ts = (double)input/700000;}
		if (input > 50000){Ts = (double)input/900000;}
		
		if(output < 10000){Tr = (double)output/500000;}
		if (output >= 10000 && output <= 50000 ){Tr = (double)output/700000;}
		if (output > 50000){Tr = (double)output/900000;}
		
		/*for(int i = 0; i < nSN; i++ ){
			latencyRemote[i][mthMD] = (Ts + Tp + Tr);
			}*/
		}
	
	public void setEnergyLocal(int mthMD, double[] latencyLocal){
		energyLocal[mthMD] = latencyLocal[mthMD] * 0.7; 
		}
	
	public void setEnergyParameters(double temp_Ts, double temp_Tr){
		//double Ts = 0;
		//double Tr = 0;
		//double Tp = 0;
		
		/*if(input < 10000){Ts = (double)input/500000;}
		if (input >= 10000 && input <= 50000 ){Ts = (double)input/700000;}
		if (input > 50000){Ts = (double)input/900000;}
		
		if(output < 10000){Tr = (double)output/500000;}
		if (output >= 10000 && input <= 50000 ){Tr = (double)output/700000;}
		if (output > 50000){Tr = (double)output/900000;}*/
		Es = temp_Ts * 0.325;
		Er = temp_Tr * 0.20064;
		
	}
	
	
	
	// ========= Get Values ================
	 
	public double [][] getData(){
		return MDResDes;
	}
	
	public double[] energyLocal(){
		return energyLocal;
		}
	
	public double[][] energyRemote(){
		return energyRemote;
		}
	
	public double[] latencyLocal(){
		return latencyLocal;
		}
	
	public double[][] latencyRemote(){
		return latencyRemote;
		}
	
	public double[] rhoN(){
		return rhoN;
		}
	
	public double[][] rhoMN(){
		return rhoMN;
		}
	
	public double[] alphaN(){
		return alphaN;
		}
	
	public double[][] alphaMN(){
		return alphaMN;
		}
	
	/* public double[] inputSize(){
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
  */ //=============================================
  
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
 
  //==============    Random Number Generation  =================================
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
	  double roundedRandNum = (double) Math.round(randNum * 100) / 100;
	  return roundedRandNum;
	  }
  //===============================================================================
  
  public static void main(String args[]){
	  GetResDes_RandExtended obj = new GetResDes_RandExtended(10);
	  System.out.println(" ====  MDResDes  ===== ");
	  for(int r = 0; r < MDResDes.length; r++) {
		  for(int c = 0; c < MDResDes[r].length; c++){
			  System.out.print(MDResDes[r][c] + " ");
			  }
		  System.out.println();
		  }
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



