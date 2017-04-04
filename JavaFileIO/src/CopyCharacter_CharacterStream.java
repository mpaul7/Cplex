import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCharacter_CharacterStream {
    public static void main(String[] args) throws IOException {

        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            inputStream = new FileReader("/home/mpaul/Dropbox/cplex/JavaFileIO/data/input.dat");
            outputStream = new FileWriter("/home/mpaul/Dropbox/cplex/JavaFileIO/data/output_CharacterByte.dat");

            int c;
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
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