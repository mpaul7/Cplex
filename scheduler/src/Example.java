
import ilog.concert.*;
import ilog.cplex.*;  

public class Example {
	
	
public static void main(String[] args) {
	double [] p_in_c = {2.0, 3.0, 5.0};
	double [] p_out_c = {1.0, 5.0, 6.0};
	double [] p_res_i_c = {2.0, 15.0, 10.0};
	int [][] temp = {{1,1}, {1,1}, {1,1}};
	
	try	{
	
		IloCplex cplex = new IloCplex();
		IloNumVar[][] x = new IloNumVar[2][];
		
		for(int i = 0; i < 2; i++) x[i] = cplex.boolVarArray(3);
		
		for(int m = 0; m < 2; m ++){
			for(int k = 0; k < 3; k++){
				System.out.println(x[m][k] + " ");
				}
			}
	  	// Objective 		  
		IloLinearNumExpr P = cplex.linearNumExpr();
		for(int m = 0; m < 2; m ++){
			for(int k = 0; k < 3; k++){
				P.addTerm(p_res_i_c[k], x[m][k]);
				}
			}
		
		IloObjective obj = cplex.maximize(P);
		cplex.add(obj);
		
		//Constriants
		for(int m = 0; m < 2; m++){
			   IloLinearNumExpr  v = cplex.linearNumExpr();
			   for(int n = 0; n < 3; n ++){
				   v.addTerm(temp[n][m], x[m][n]);
				   }
			   cplex.addLe(v, 1);
			   }
		
		
				
		 cplex.exportModel("example.lp");
		
		if(cplex.solve()){
			System.out.println("Solution status = " + cplex.getStatus());
			System.out.println();
			System.out.println(" cost = " + cplex.getObjValue());
			
			for(int n = 0; n < 3; n++){
				for(int m = 0; m < 2; m++){
					System.out.println(" X[" + m +  "]" + "[" + n + "]  = " + cplex.getValue(x[m][n]));
					}
				} 
		//	double[] val	 =	 cplex.getObjValue(x);
			System.out.println(" ncols  = " + cplex.getNcols());
			//System.out.println(" x.lenght  = " + x.length());
			int	 ncols	 =	 cplex.getNcols();
			/*for (int j = 0; j < ncols; ++j)
				System.out.println("Column:	 "	 + j + " Value  = " + x[j]);
			*/}
		
		cplex.end();
		
	}catch(IloException	 e)	 {
			System.err.println("Concert exception '" + e + "' caught");
			}
	}
}