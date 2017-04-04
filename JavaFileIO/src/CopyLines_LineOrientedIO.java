import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

public class CopyLines_LineOrientedIO {
    public static void main(String[] args) throws IOException {

        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader("/home/mpaul/Dropbox/cplex/JavaFileIO/data/input.dat"));
            outputStream = new PrintWriter(new FileWriter("/home/mpaul/Dropbox/cplex/JavaFileIO/data/output_LineOrientedIO.dat"));
            
            //Invoking readLine returns a line of text with the line. CopyLines outputs 
            //each line using println, which appends the line terminator for the current 
            //operating system. This might not be the same line terminator that was used 
            //in the input file.

            String l;
            while ((l = inputStream.readLine()) != null) {
                outputStream.println(l);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}