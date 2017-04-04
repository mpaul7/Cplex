
import ilog.concert.*;
import ilog.cplex.*;
import java.io.*;

public class Facility {
   static double[]   _capacity;
   static double[]   _fixedCost;
   static double[][] _cost;

   static int _nbLocations;
   static int _nbClients;

   static void readData(String fileName)
                         throws IloException, IOException,
                                InputDataReader.InputDataReaderException {
      System.out.println("Reading data from " + fileName);
      InputDataReader reader = new InputDataReader(fileName);
    
      _capacity  = reader.readDoubleArray();
      _fixedCost = reader.readDoubleArray();
      _cost      = reader.readDoubleArrayArray();
    
      _nbLocations = _capacity.length;
      _nbClients   = _cost.length;
      
      System.out.println("_nbLocations " + _nbLocations);
      System.out.println("_nbClients  " + _nbClients);
      System.out.println("_nbCost  " + _cost.length);
      for(int i = 0; i < _nbClients; i++)
         if ( _cost[i].length != _nbLocations )
           throw new IloException("inconsistent data in file " + fileName);
   }

   public static void main( String[] args ) {
      try {
         String filename  = "/home/mpaul/Dropbox/cplex/examples/data/facility.dat";
         if (args.length > 0)
            filename = args[0];
         readData(filename);
       
         IloCplex cplex = new IloCplex();
         IloNumVar[] open = cplex.boolVarArray(_nbLocations);
       
         IloNumVar[][] supply = new IloNumVar[_nbClients][];
         for(int i = 0; i < _nbClients; i++)
            supply[i] = cplex.boolVarArray(_nbLocations);
        /* for(int i = 0; i < (nEnergyLocal); i++){
  		   X[i] = cplex.numVarArray(nRhoN, 0, 1);
  		   }*/
       
         for(int i = 0; i < _nbClients; i++)
            cplex.addEq(cplex.sum(supply[i]), 1);
       
         for(int j = 0; j < _nbLocations; j++) {
            IloLinearNumExpr v = cplex.linearNumExpr();
            for(int i = 0; i < _nbClients; i++)
              v.addTerm(1., supply[i][j]);
            cplex.addLe(v, cplex.prod(_capacity[j], open[j]));
         }
       
         IloLinearNumExpr obj = cplex.scalProd(_fixedCost, open);
         for(int i = 0; i < _nbClients; i++)
            obj.add(cplex.scalProd(_cost[i], supply[i]));
       
         cplex.addMinimize(obj);
         cplex.exportModel("Facility.lp");
       
         if (cplex.solve()) {
            System.out.println("Solution status: " + cplex.getStatus());
            double tolerance = cplex.getParam(IloCplex.DoubleParam.EpInt);
            System.out.println("Optimal value: " + cplex.getObjValue());
            for(int j = 0; j < _nbLocations; j++) {
               if (cplex.getValue(open[j]) >= 1 - tolerance) {
                  System.out.print("Facility " + j +
                                   " is open, it serves clients ");
                  for(int i = 0; i < _nbClients; i++)
                     if (cplex.getValue(supply[i][j]) >= 1 - tolerance)
                        System.out.print(" " + i);
                  System.out.println(); 
               }
            }
         }
         cplex.end();
      }
      catch(IloException exc) {
         System.err.println("Concert exception '" + exc + "' caught");
      }
      catch (IOException exc) {
         System.err.println("Error reading file " + args[0] + ": " + exc);
      }
      catch (InputDataReader.InputDataReaderException exc) {
         System.err.println(exc);
      }
   }
}

/*
Optimal value: 1383
Facility 0 is open, it serves clients 2 5 7
Facility 1 is open, it serves clients 3
Facility 3 is open, it serves clients 0 1 4 6
*/
