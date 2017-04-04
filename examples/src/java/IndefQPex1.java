/* --------------------------------------------------------------------------
 * File: IndefQPex1.java
 * Version 12.5
 * --------------------------------------------------------------------------
 * Licensed Materials - Property of IBM
 * 5725-A06 5725-A29 5724-Y48 5724-Y49 5724-Y54 5724-Y55 5655-Y21
 * Copyright IBM Corporation 2001, 2013. All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with
 * IBM Corp.
 * --------------------------------------------------------------------------
 *
 * IndefQPex1.java - Entering and optimizing an indefinite QP problem
 */

import ilog.concert.*;
import ilog.cplex.*;


public class IndefQPex1 {
   public static void main(String[] args) {
      try {
         IloCplex cplex = new IloCplex();
         IloLPMatrix lp = populateByRow(cplex);

         int[]    ind = {0};
         double[] val = {1.0};

         // When a non-convex objective function is present, CPLEX
         // will raise an exception unless the parameter
         // IloCplex.IntParam.SolutionTarget is set to accept
         // first-order optimal solutions
         cplex.setParam(IloCplex.IntParam.SolutionTarget,
                        IloCplex.SolutionTarget.FirstOrder);

         // CPLEX may converge to either local optimum 
         solveAndDisplay(cplex, lp);

         // Add a constraint that cuts off the solution at (-1, 1)
         lp.addRow(0.0, Double.MAX_VALUE, ind, val);
         solveAndDisplay(cplex, lp);

         // Remove the newly added constraint and add a new constraint
         // with the opposite sense to cut off the solution at (1, 1)
         lp.removeRow(lp.getNrows() - 1);
         lp.addRow(-Double.MAX_VALUE, 0.0, ind, val);
         solveAndDisplay(cplex, lp);

         cplex.exportModel("indefqpex1.lp");
         cplex.end();
      }
      catch (IloException e) {
         System.err.println("Concert exception '" + e + "' caught");
      }
   }

   static IloLPMatrix populateByRow(IloMPModeler model) throws IloException {
      IloLPMatrix lp = model.addLPMatrix();

      double[]    lb = {-1.0, 0.0};
      double[]    ub = { 1.0, 1.0};
      IloNumVar[] x  = model.numVarArray(model.columnArray(lp, 2), lb, ub);

      // - x0 +   x1 + x2 <= 20
      //   x0 - 3*x1 + x2 <= 30
      double[]   lhs = {0.0, 0.0};
      double[]   rhs = {Double.MAX_VALUE, Double.MAX_VALUE};
      double[][] val = {{-1.0, 1.0},
                        { 1.0, 1.0}};
      int[][]    ind = {{0, 1},
                        {0, 1}};
      lp.addRows(lhs, rhs, ind, val);

      // Q = 0.5 ( 33*x0*x0 + 22*x1*x1 + 11*x2*x2 - 12*x0*x1 - 23*x1*x2 )
      IloNumExpr x00 = model.prod(-3.0, x[0], x[0]);
      IloNumExpr x11 = model.prod(-3.0, x[1], x[1]);
      IloNumExpr x01 = model.prod(-1.0, x[0], x[1]);
      IloNumExpr Q   = model.prod(0.5, model.sum(x00, x11, x01));

      // minimize Q
      model.add(model.minimize(Q));

      return (lp);
   }

   static void solveAndDisplay(IloCplex cplex, IloLPMatrix lp) throws IloException {
      
      if ( cplex.solve() ) {
         double[] x     = cplex.getValues(lp);
         double[] dj    = cplex.getReducedCosts(lp);
         double[] pi    = cplex.getDuals(lp);
         double[] slack = cplex.getSlacks(lp);

         System.out.println("Solution status = " + cplex.getStatus());
         System.out.println("Solution value  = " + cplex.getObjValue());

         int nvars = x.length;
         for (int j = 0; j < nvars; ++j) {
            System.out.println("Variable " + j +
                               ": Value = " + x[j] +
                               " Reduced cost = " + dj[j]);
         }

         int ncons = slack.length;
         for (int i = 0; i < ncons; ++i) {
            System.out.println("Constraint " + i +
                               ": Slack = " + slack[i] +
                               " Pi = " + pi[i]);
         }

      }
   }
}
