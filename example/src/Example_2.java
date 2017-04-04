import ilog.concert.*;
import ilog.cplex.*;

public class Example_2 { 
	public static void main(String[] args) {
		try {
			IloCplex cplex = new IloCplex();
			double[] lb = {0.0, 0.0};
			double[] ub = {Double.MAX_VALUE, Double.MAX_VALUE};
			IloNumVar[] x = cplex.numVarArray(2, lb, ub);
			//IloNumVar[] x = cplex.numVarArray(3, lb, ub);
			//double[] objvals = {1.0};
			IloLinearNumExpr expr = cplex.linearNumExpr(); 
			expr.addTerm(0.0, x[0]);
			expr.addTerm(1.0, x[1]);
			IloObjective obj = cplex.maximize(expr);
			cplex.add(obj);

			//cplex.addMaximize(cplex.scalProd(x[1], objvals));
			cplex.addLe(cplex.sum(cplex.prod(-1.0, x[0]),cplex.prod(1.0, x[1])), 1.0);
			cplex.addLe(cplex.sum(cplex.prod( 3.0, x[0]),cplex.prod(2.0, x[1])), 2.0);
			cplex.addLe(cplex.sum(cplex.prod( 2.0, x[0]),cplex.prod(3.0, x[1])), 12.0);
			cplex.exportModel("Example_2.lp");
			
			if ( cplex.solve() ) {
				cplex.output().println("Solution status = " + cplex.getStatus());
				cplex.output().println("Solution value = " + cplex.getObjValue());
				double[] val = cplex.getValues(x);
				int ncols = cplex.getNcols();
				
				for (int j = 0; j < ncols; ++j){
					cplex.output().println("Column: " + j + " Value = " + val[j]);
					} // end of for 
				} // end of if 
			cplex.end(); 
			}catch (IloException e) {
				System.err.println("Concert exception '" + e + "' caught");
				} // end of try-catch 
		} // end of main
	}// end of class
