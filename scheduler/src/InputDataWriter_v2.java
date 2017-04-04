

import java.io.*;

public class InputDataWriter_v2 {
	
	static final String dataFile = "/home/mpaul/Dropbox/cplex/scheduler/data/data_DataOutputStream.dat";
	static final double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
	static final int[] units = { 12, 8, 13, 29, 50 };
	static final String[] descs = {
	    "Java T-shirt",
	    "Java Mug",
	    "Duke Juggling Dolls",
	    "Java Pin",
	    "Java Key Chain"
	    };
	
	static DataInputStream in;
	static DataOutputStream out;
	
	public static void main(String Args[]){
		writer();
		reader();
		}
	

	public static void writer(){
		try{
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));
			for (int i = 0; i < prices.length; i ++) {
				out.writeDouble(prices[i]);
				out.writeInt(units[i]);
				out.writeUTF(descs[i]);
				}
			out.close();
			//in.close();
		}catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection.");
			e.printStackTrace();
			System.exit(-1);
			}
	}
	
	public static void reader(){
		try{
			in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
			double price;
			int unit;
			String desc;
			double total = 0.0;	
			
			while (true) {
		        price = in.readDouble();
		        unit = in.readInt();
		        desc = in.readUTF();
		        System.out.format("You ordered %d" + " units of %s at $%.2f%n", unit, desc, price);
		        total += unit * price;
		        }
			} catch (EOFException e) {
				}catch (IOException e) {
					}
		}	  

}