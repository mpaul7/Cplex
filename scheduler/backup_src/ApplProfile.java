//@SuppressWarnings("null");
import java.io.*;
import java.util.*;
public class ApplProfile
{
    //public DescriptiveStats(){}
 public static double[][] appProfile = new double[18][9]; 
 public static String fileName = null;
 
/* public ApplProfile()throws FileNotFoundException{
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/input.dat", 0);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/output.dat", 1);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/procLocal.dat", 2);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/procRemote.dat", 3);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/timeSend.dat", 4);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/timeReceive.dat", 5);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/energySend.dat", 6);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/energyReceive.dat", 7);
	
		for(int r = 0; r < appProfile.length; r++) {
		  for(int c = 0; c < appProfile[r].length; c++){
			  System.out.print(appProfile[r][c] + " ");
			  }
		  System.out.println();
		  }
 }*/
 
 public static void setValue(double[][] appProfile,  String fileName, int index)throws FileNotFoundException{
	
	 Scanner scan = new Scanner(new File(fileName)); //provide file name from outside
	 int counter = 0; //keep track of how many integers in the file
	 while(scan.hasNextDouble()){
        counter++;
        scan.nextDouble();
        }
    System.out.println("counter" + counter);
    Scanner scan2 = new Scanner(new File(fileName));
   // appProfile = new double[counter][2];
   
    for(int i = 0; i < counter; i++){
   		 appProfile[i][index] = scan2.nextDouble(); //fill the array with the integers
    	// System.out.println("appProfile[0][0]" + appProfile[22][0]);
     
        }
   }
 
 public double[][] getValue()throws FileNotFoundException{
	 setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/input.dat", 0);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/output.dat", 1);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/procLocal.dat", 2);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/procRemote.dat", 3);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/timeSend.dat", 4);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/timeReceive.dat", 5);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/energySend.dat", 6);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/energyReceive.dat", 7);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/procPower.dat", 8);
		
	
		/*for(int r = 0; r < appProfile.length; r++) {
		  for(int c = 0; c < appProfile[r].length; c++){
			  System.out.print(appProfile[r][c] + " ");
			  }
		  System.out.println();
		  }*/
	 return appProfile;
 }
	public static void main (String args[])throws FileNotFoundException  {
	/*	setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/input.dat", 0);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/output.dat", 1);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/procLocal.dat", 2);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/procRemote.dat", 3);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/timeSend.dat", 4);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/timeReceive.dat", 5);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/energySend.dat", 6);
		setValue(appProfile, "/home/mpaul/Dropbox/cplex/scheduler/data/energyReceive.dat", 7);
	
		for(int r = 0; r < appProfile.length; r++) {
		  for(int c = 0; c < appProfile[r].length; c++){
			  System.out.print(appProfile[r][c] + " ");
			  }
		  System.out.println();
		  }*/
		//System.out.println(appProfile[1][0]);
        }
	}
//============================================================


/*	Scanner scan = new Scanner(new File("/home/mpaul/Dropbox/cplex/scheduler/data/input.dat")); //provide file name from outside
int counter = 0; //keep track of how many integers in the file
while(scan.hasNextDouble()){
    counter++;
    scan.nextInt();
    }
Scanner scan2 = new Scanner(new File("/home/mpaul/Dropbox/cplex/scheduler/data/input.dat"));
double a[] = new double[counter];
for(int i = 0; i < counter; i++){
    a[i] = scan2.nextInt(); //fill the array with the integers
    }*/
// for (double lng : appProfile) {System.out.println(lng);}
 /*for(int r = 0; r < appProfile.length; r++) {
	  for(int c = 0; c < appProfile[r].length; c++){
		  System.out.print(appProfile[r][c] + " ");
		  }
	  System.out.println();
	  }*/
