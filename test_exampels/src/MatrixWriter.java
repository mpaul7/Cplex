import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

class MatrixWriter {
	public static void main(String ...a) throws FileNotFoundException,
	                                            IOException, 
	                                            ClassNotFoundException {
		int [][][] data = new int [][][] {
				                          { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } }, 
				                          { { 9, 10 }, { 11, 12 } } };
		
		
		String filename = "matrixWriter.dat";
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject(data);
		out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        int [][][] array = (int[][][]) in.readObject();
        in.close();

        for (int [][] b : array) {
        	System.out.print("[");
        	for (int [] c : b) {
        		System.out.print(Arrays.toString(c));
        		}
        	System.out.println("]");
        	}
        }
	}