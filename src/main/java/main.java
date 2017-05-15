import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by flaps_win10 on 15-05-2017.
 */
public class main {

    public static void main(String[] args) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("SampleTextFiles/10022.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

            System.out.println(everything);
        }
    }

}
