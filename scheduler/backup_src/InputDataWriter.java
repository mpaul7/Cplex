import java.io.*;

    public class InputDataWriter {
    	static String fileName = null;
    	
    	public InputDataWriter(String fileName){
    		this.fileName = fileName;
    	}
    		
    public static void main(String[] args){
    	//int [][] writeIntArrayArray = {{1,2,3,4}, {5,6,7,8}, {4,9,1,0}, {2,5,8,3}};
    	//double [][] writeDoubleArrayArray = {{0,9,8,7}, {6,5,4,3}, {2,1,0, 9}, {8,7,6,5}};
    	//String filename = "Matrix.dat";
    	//writeDoubleArrayArray(writeDoubleArrayArray, filename);
    	//writeIntArrayArray(writeIntArrayArray, filename);
    	
    	}
        
    public void writeDoubleArrayArray(double[][] array, String filename){
    	PrintStream doubleArr;
    	try {
    		doubleArr = new PrintStream(new FileOutputStream(filename, true));
    		
    		for(int row = 0; row < array.length; row++){
    			if(row == 0){
    				//String m = "[";
    				doubleArr.print('[');
    				doubleArr.print('[');
    			}else {
    				//String m = '[';
    				doubleArr.print('[');
    				}
    		
    		    //System.out.print("[");
    			for(int col = 0; col < array[row].length; col++){
    				double s = array[row][col];
    				System.out.print(s);
    				doubleArr.print(s);
    				if(col < (array[row].length) -1){
    					//String l = ',';
    					doubleArr.print(',');	
    					}
    				}
    			
    			if(row == (array.length) - 1){
    				//String m = "]";
    				doubleArr.print(']');
    				doubleArr.print(']');
    				}else {
    					//String m = "]";
    					doubleArr.print(']');
    					}
    			}
    		doubleArr.print("\n");
    		} catch (FileNotFoundException e) {
    			System.out.println(e.getMessage());
    			}
    	}
    
    public void writeIntArray(int[] array){
    	PrintStream intArr;
    	try {
    		intArr = new PrintStream(new FileOutputStream(fileName, true));
    		
    		for(int i = 0; i < array.length; i++){
    			if(i == 0)
    				intArr.print('[');
    			int s = array[i];
    			intArr.print(s);
    			if(i < (array.length) -1){
					intArr.print(',');	
    				}
    			if(i == (array.length) - 1)
    				intArr.print(']');
    			}
    		intArr.print("\n");
    		} catch (FileNotFoundException e) {
    			System.out.println(e.getMessage());
    			}
    	}
    
    public void writeIntArrayArray(int[][] array){
    	PrintStream intArr;
    	try {
    		intArr = new PrintStream(new FileOutputStream(fileName, true));
    		
    		for(int row = 0; row < array.length; row++){
    			if(row == 0){
    				//String m = ',';
    				intArr.print('[');
    				intArr.print('[');
    			}else {
    				//String m = "[";
    				intArr.print('[');
    				}
    		    			
    			//System.out.print("[");
    			for(int col = 0; col < array[row].length; col++){
    				int s = array[row][col];
    				System.out.print(s);
    				intArr.print(s);
    				if(col < (array[row].length) -1){
    					//String l = ", ";
    					intArr.print(',');	
        				}
    			}
    			if(row == (array.length) - 1){
    				//String m = "]";
    				intArr.print(']');
    				intArr.print(']');
    			}else {
    				//String m = ']';
    				intArr.print(']');
    				intArr.print(',');
    				}
    			}
    		intArr.print("\n");
    		} catch (FileNotFoundException e) {
    			System.out.println(e.getMessage());
    			}
    	
    	}
    }