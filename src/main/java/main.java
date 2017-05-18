import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class main {

    private  int TitleLineNumber = -1;
    private  int AuthorLineNumber = -1;
    private  boolean bookStarted = false;
    private boolean lastWordWasLastWordOfSentence = true;
    private String[] commonCityPrefixes = new String[]{"Los", "Las", "New", "San"};

    public boolean getLastWord()
    {
        return lastWordWasLastWordOfSentence;
    }

    public void setLastWord(boolean lastWord)
    {
        this.lastWordWasLastWordOfSentence = lastWord;
    }


    public  int getTitleLineNumber() {
        return TitleLineNumber;
    }

    public  void setTitleLineNumber(int titleLineNumber) {
        TitleLineNumber = titleLineNumber;
    }

    public  int getAuthorLineNumber() {
        return AuthorLineNumber;
    }

    public  void setAuthorLineNumber(int authorLineNumber) {
        AuthorLineNumber = authorLineNumber;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        main m = new main();

    }

    public main() throws IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new FileReader("SampleTextFiles/10022.txt"))) {
            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            Book book = new Book();

            while (line != null) {
                LineParsing(line, book);
                //sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
            }
            //String everything = sb.toString();

            System.out.println(book);
        }
    }

    private void LineParsing(String line, Book b) {

        String[] words = line.split("\\s+");



        if (line.contains("Title:") || (getTitleLineNumber() >=0 && getTitleLineNumber() < 2))
        {

            if(line.isEmpty()){setTitleLineNumber(2);}
            else
            {
                b.addToTitle(line);
                setTitleLineNumber(0);
            }

        }



        if (line.contains("Author:") || (getAuthorLineNumber() >=0 && getAuthorLineNumber() < 2))
        {

            if(line.isEmpty()){setAuthorLineNumber(2);}
            else
            {
                b.addToAuthor(line);
                setAuthorLineNumber(0);
            }

        }

        if (line.toLowerCase().contains("end of this project gutenberg ebook"))
        {
            bookStarted = false;
        }

        if (bookStarted)
        {
             /*

            Drop all special chars ('Calabar,"', is an example of a city in CURRENT BOOK) ?
            Possibly check if it's a common prefix (Los, New, San, Las, etc) ?
            */

            /*
            Be aware this creates false positives (Names, Countries, first word of a sentence etc.

            Names:                      Hard to avoid and distinguish, maybe we should remove them afterwards in database?
            Countries:                  Similarly hard to avoid, does have some point of being there, if cities are in several countries
            First word of sentence:     Avoid all words after a dot. - Be aware this might skip some city names however it's likely these will be referenced from somewhere else in the book.
            */


            for (int i = 0; i < words.length; i++) {


                if ((!line.isEmpty() || !words[i].isEmpty()) && words[i] != null) {
                    //Checks if first word of sentence
                    if (getLastWord()) {
                        setLastWord(!getLastWord());
                    } else {
                        //Checks if uppercase
                        if (Character.isUpperCase(words[i].charAt(0))) ;
                        {
                            //Checks for common prefixes defined in array
                            if (isWordACommonCityPrefix(words[i], commonCityPrefixes)) {
                                b.addCity(words[i] + " " + words[i + 1]);
                                System.out.println(words[i] + words[i+1] + ": is considered a city name, adding...");
                                i++;
                            } else {
                                System.out.println(words[i] + ": is considered a city name, adding...");
                                b.addCity(words[i]);
                            }
                        }
                        if (words[i].charAt(words[i].length() - 1) == '.') {
                            setLastWord(true);
                        }
                    }


                }
                else
                {
                    setLastWord(true);
                }


            }


            /*
            TODO: Outliers. Sample set contains what appears to be the bible, and contains no title. We need to either skip it, or do something with it
             */
        }


        if (line.toLowerCase().contains("start of this project gutenberg ebook"))
        {
            bookStarted = true;
        }



    }

    private boolean isWordACommonCityPrefix(String word, String[] commonPrefix) {
        return (Arrays.asList(commonPrefix).contains(word));
    }


}
