import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class main {

    public static void main(String[] args) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("SampleTextFiles/10022.txt"))) {
            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                LineParsing(line);
                //sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
            }
            //String everything = sb.toString();

            System.out.println();


        }
        /*
        Test content for proof of concept of csv output (TODO: Check if Sets can be imported into MongoDB and Neo4J this way)
         */
        Book b = new Book();
        b.setAuthor("A. G. Jakobsen");
        b.setTitle("How to Test in modern Computer Science");
        b.addCity("Holte");
        b.addCity("Lyngby");
        b.addCity("Roskilde");

        System.out.println(b);
    }

    private static void LineParsing(String line) {

        String[] words = line.split(" ");

        for (int i = 0; i < words.length; i++) {

            //TODO: Check for title (possibly over multiple lines

            //TODO: Check for auther, likewise

            /*
            TODO: Check for words starting with uppercase
            Drop all special chars ('Calabar,"', is an example of a city in CURRENT BOOK) ?
            Possibly check if  it's a common prefix (Los, New, San, Las, etc) ?
            */

            /*
            Be aware this creates false positives (Names, Countries, first word of a sentence etc.

            Names:                      Hard to avoid and distinguish, maybe we should remove them afterwards in database?
            Countries:                  Similarly hard to avoid, does have some point of being there, if cities are in several countries
            First word of sentence:     Avoid all words after a dot. - Be aware this might skip some city names however it's likely these will be referenced from somewhere else in the book.
            */


            /*
            TODO: Outliers. Sample set contains what appears to be the bible, and contains no title. We need to either skip it, or do something with it
             */

        }

    }

}
