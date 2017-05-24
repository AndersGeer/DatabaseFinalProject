import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class main {

    private  int TitleLineNumber = -1;
    private  int AuthorLineNumber = -1;
    private  boolean bookStarted = false;
    private boolean lastWordWasLastWordOfSentence = true;
    private String[] commonCityPrefixes = new String[]{"Los", "Las", "New", "San"};
    private HashMap<String,City> Cities = new HashMap<>();
    PrintWriter writerBook = null;
    PrintWriter writerCity = null;

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


        //main m = new main();
        //main m = new main("The Epworth Chipinge Chiredzi Gokwe", new Book());
        main m = new main("BookParser/SampleTextFiles");


    }

    public main(String line, Book b) throws IOException {
        ReadCities("BookParser/CitiesList/extractedCitiesLatLng.csv");
        writerBook = new PrintWriter("BookParser/Output/data.csv", "UTF-8");
        this.bookStarted = true;
        LineParsing(line, b);
        writerBook.close();
        //System.out.println(b);
    }

    public main(String folderLocation) throws IOException {
        ReadCities("BookParser/CitiesList/extractedCitiesLatLng.csv");
        File folder = new File(folderLocation);
        File[] listOfFiles = folder.listFiles();
        InitWriter();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                readFile(file);
            }
        }
        writerBook.close();
    }

    private void ReadCities(String csvFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            line = br.readLine();


            while (line != null) {
                String[] words = line.trim().split("\\s+");
                System.out.println(line);
                if (words.length == 3)
                {
                    Cities.put(words[0], new City(words[0],words[1],words[2]));
                }
                line = br.readLine();
            }
        }
    }

    public void main() throws IOException{
        ReadCities("BookParser/CitiesList/extractedCitiesLatLng.csv");
        InitWriter();
        readFile(new File("BookParser/SampleTextFiles/10022.txt"));
        writerBook.close();
    }
    
    private void CityWriter() throws FileNotFoundException, UnsupportedEncodingException {
        writerBook = new PrintWriter("BookParser/Output/cities.csv", "UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(",");
        sb.append("name");
        sb.append(",");
        sb.append("lat");
        sb.append(",");
        sb.append("lng");
        writerBook.println(sb.toString());
    }
    private void InitWriter() throws FileNotFoundException, UnsupportedEncodingException {
        writerBook = new PrintWriter("BookParser/Output/data.csv", "UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(",");
        sb.append("title");
        sb.append(",");
        sb.append("author");
        sb.append(",");
        sb.append("cities");
       writerBook.println(sb.toString());
    }

    private void readFile(File file) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            Book book = new Book();

            while (line != null) {
                LineParsing(line, book);
                line = br.readLine();
            }
            System.out.println(book);
            String bookLine = book.toString();
            writerBook.println(bookLine);
        }


    }

    private void LineParsing(String line, Book b)
    {
        String[] words = line.trim().split("\\s+");
        if (line.contains("Title:") || (getTitleLineNumber() >=0 && getTitleLineNumber() < 2))
        {
            String[] titleWords = line.trim().split("\\s+");
            if(line.isEmpty()){setTitleLineNumber(2);}
            else if (!titleWords[0].toLowerCase().contains("title:"))
            {
                b.addToTitle(line);
                setTitleLineNumber(0);
            }
            else
            {
                for (int i = 1; i < titleWords.length; i++) {
                    b.addToTitle(titleWords[i]+" ");
                }
                setTitleLineNumber(0);
            }
        }
        if (line.contains("Author:") || (getAuthorLineNumber() >=0 && getAuthorLineNumber() < 2))
        {
            String[] authorWords = line.trim().split("\\s+");
            if(line.isEmpty()){setAuthorLineNumber(2);}
            else if (!authorWords[0].toLowerCase().contains("author:"))
            {
                b.addToAuthor(line);
                setAuthorLineNumber(0);
            }
            else
            {
                for (int i = 1; i < authorWords.length; i++) {
                    b.addToAuthor(authorWords[i]+" ");
                }
                setTitleLineNumber(0);
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
                        Pattern p = Pattern.compile("[A-z]+");
                        Matcher matcher = p.matcher(words[i].trim());
                        if (Character.isUpperCase(words[i].charAt(0)) && matcher.matches())
                        {
                            //Checks for common prefixes defined in array
                            if (isWordACommonCityPrefix(words[i], commonCityPrefixes) && words[i] != words[words.length-1]) {
                                String city = words[i] + " " + words[i+1];
                                if (Cities.keySet().contains(city))
                                {
                                    b.addCity(Cities.get(words[i]));
                                    //System.out.println(words[i] + words[i+1] + ": is considered a city name, adding...");
                                    i++;
                                }

                            } else {
                                String city = words[i];
                                if (Cities.keySet().contains(city))
                                {
                                    //System.out.println(words[i] + ": is considered a city name, adding...");
                                    b.addCity(Cities.get(words[i]));
                                }

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

            if (b.getTitle().isEmpty())
            {
                b.setTitleUnknown();
            }
            if (b.getAuthor().isEmpty())
            {
                b.setAuthorUnknown();
            }

        }



    }

    private boolean isWordACommonCityPrefix(String word, String[] commonPrefix) {
        return (Arrays.asList(commonPrefix).contains(word));
    }


}
