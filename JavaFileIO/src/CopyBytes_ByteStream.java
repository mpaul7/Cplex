import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyBytes_ByteStream {
    public static void main(String[] args) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("/home/mpaul/Dropbox/cplex/JavaFileIO/data/input.dat");
            out = new FileOutputStream("/home/mpaul/Dropbox/cplex/JavaFileIO/data/output_CopyBytes.dat");
            int c;
            
          /*  //Notice that read() returns an int value. If the input is a stream of bytes, 
            //why doesn't read() return a byte value? Using a int as a return type allows 
            //read() to use -1 to indicate that it has reached the end of the stream.*/

            while ((c = in.read()) != -1) {
                out.write(c);
            }
            /* Closing a stream when it's no longer needed is very important — 
               so important that CopyBytes uses a finally block to guarantee that both streams 
               will be closed even if an error occurs.*/
            } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
