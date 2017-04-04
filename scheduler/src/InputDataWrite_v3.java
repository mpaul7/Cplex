import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

    public class InputDataWrite_v3 {
    	static String fileName = null;
    	static DataInputStream in;
    	static DataOutputStream out;
    	
    	public InputDataWrite_v3(String fileName){
    		this.fileName = fileName;
    	}
    		
    public static void main(String[] args){
    	//int [][] writeIntArrayArray = {{1,2,3,4}, {5,6,7,8}, {4,9,1,0}, {2,5,8,3}};
    	//double [][] writeDoubleArrayArray = {{0,9,8,7}, {6,5,4,3}, {2,1,0, 9}, {8,7,6,5}};
    	//String filename = "Matrix.dat";
    	//writeDoubleArrayArray(writeDoubleArrayArray, filename);
    	//writeIntArrayArray(writeIntArrayArray, filename);
    	
    	}
        
    public void writeDoubleArrayArray(double[][] array){
    	//PrintStream doubleArr;
      		//================================
    		try{
    			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
    			for(int row = 0; row < array.length; row++){
    	 			for(int col = 0; col < array[row].length; col++){
    				out.writeDouble(array[row][col]);
    				}
    			}
    			out.close();
    			//in.close();
    		}catch (FileNotFoundException e) {
    			System.out.println(e.getMessage());
    			}catch (IOException e) {
    			System.err.println("Couldn't get I/O for the connection.");
    			e.printStackTrace();
    			System.exit(-1);
    			}	
    	}
    public void writeDoubleArray(int[] array){
    	try{
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
			for(int i = 0; i < array.length; i++){
				out.writeDouble(array[i]);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			}catch (IOException e) {
    			System.err.println("Couldn't get I/O for the connection.");
    			e.printStackTrace();
    			System.exit(-1);
    			}	
      	}
    
    public void writeIntArray(int[] writeIntArray){
    	try{
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
			for(int i = 0; i < writeIntArray.length; i++){
				out.writeInt(writeIntArray[i]);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			}catch (IOException e) {
    			System.err.println("Couldn't get I/O for the connection.");
    			e.printStackTrace();
    			System.exit(-1);
    			}	
      	}
    
    public void writeIntArrayArray(int[][] writeIntArrayArray){
    
    	try {
    		out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
    		for(int row = 0; row < writeIntArrayArray.length; row++){
    			for(int col = 0; col < writeIntArrayArray[row].length; col++){
    				out.writeInt(writeIntArrayArray[row][col]);
    				}
    			}
    		out.close();
    		}catch (FileNotFoundException e) {
    			System.out.println(e.getMessage());
    			}catch (IOException e) {
    			System.err.println("Couldn't get I/O for the connection.");
    			e.printStackTrace();
    			System.exit(-1);
    			}	
    		    	
    	}
    }