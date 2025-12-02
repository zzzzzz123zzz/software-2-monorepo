import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program is a word counter which users can input a file that contains
 * words into this program. The program can then check the time of appearance of
 * each word in the file. After counting, the program will generate an .html
 * file that includes a table listing all words with their appearing times.
 *
 * @author LEO ZHUANG, JENG ZHUANG, MICHAEL XU
 *
 */
public final class WordCounter1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter1() {
    }

    /**
     * Counts the number of times each word appears in the given file, and
     * stores the results in the provided map. Words are defined as maximal
     * sequences of non-separator characters, where separators include spaces
     * and punctuation. Words are converted to lower case before counting.
     *
     * @param fileName
     *            the name of the file to be read; must not be null and must
     *            reference an existing, readable file
     * @param thelist
     *            the map to update with words and their frequencies; must not
     *            be null
     * @param theSequence
     *            the sequence to store the keys corresponding to the map but in
     *            an alphabetical order
     *
     * @requires fileName != null and thelist != null
     * @ensures thelist contains exactly one entry for each distinct word in the
     *          file, with its value equal to the number of times that word
     *          occurs (in lower case) for int i: theSequence,
     *          theSequence.entry(i).compareTo(theSequence.entry(i+1)) < 1
     */
    public static void counter(String fileName, Map<String, Integer> thelist,
            Sequence<String> theSequence) {
        SimpleReader in = new SimpleReader1L(fileName);
        /*
         * Identify the separators including punctuation and space.
         */
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        separators.add('.');
        separators.add('!');
        separators.add('?');
        separators.add('\n');
        separators.add('\t');
        separators.add('-');
        /*
         * Separate words by separators, and then count them into a Map by
         * setting the words as keys and number of times as values.
         */
        while (!in.atEOS()) {
            /*
             * Read one line each time.
             */
            String line = in.nextLine();
            int start = 0;
            int position = 0;
            /*
             * Set condition to avoid throwing out of bound exception.
             */
            while (position < line.length()) {
                /*
                 * Check if the charAt position is a separator.
                 */
                if (separators.contains(line.charAt(position))
                        || position + 1 == line.length()) {
                    String temp = "";
                    /*
                     * Set conditions to substring the words within the line.
                     * Words will be turned into lower case.
                     */
                    if (position + 1 == line.length()
                            && !separators.contains(line.charAt(position))) {
                        temp = line.substring(start, position + 1)
                                .toLowerCase();
                    } else {
                        temp = line.substring(start, position).toLowerCase();
                    }
                    /*
                     * Set condition to avoid empty words.
                     */
                    if (!temp.isEmpty()) {
                        /*
                         * Set conditions to add counts each time a word is
                         * found.
                         */
                        if (thelist.hasKey(temp)) {
                            thelist.replaceValue(temp, thelist.value(temp) + 1);
                        } else {
                            thelist.add(temp, 1);
                        }
                        /*
                         * Add the keys alphabetically into a sequence. Compare
                         * temp to strings stored in the sequence. If the
                         * sequence is empty, directly add temp to the sequence.
                         */
                        if (theSequence.length() > 0) {
                            /*
                             * Set up the inputIndex for comparing the strings
                             * stored in the sequence and the boolean value for
                             * deciding when to stop comparing.
                             */
                            int inputIndex = 0;
                            boolean toContinue = true;
                            /*
                             * Loop through the sequence.
                             */
                            while (inputIndex < theSequence.length()
                                    && toContinue) {
                                /*
                                 * When temp is smaller than the present string,
                                 * stop looping and set toContinue as false.
                                 * When temp is bigger than the present string,
                                 * increment the index. If temp is the same to
                                 * the present string, set index bigger than the
                                 * sequence's length to stop looping.
                                 */
                                if (temp.compareTo(
                                        theSequence.entry(inputIndex)) < 0) {
                                    toContinue = false;
                                } else if (temp.compareTo(
                                        theSequence.entry(inputIndex)) > 0) {
                                    inputIndex++;
                                } else {
                                    inputIndex = theSequence.length() + 1;
                                }
                            }
                            /*
                             * !toContinue reflects that temp should be added to
                             * the sequence at current inputIndex. inputIndex==
                             * theSequence.length() reflects that temp is the
                             * biggest value, then add temp to the end of the
                             * sequence.
                             */
                            if (!toContinue) {
                                theSequence.add(inputIndex, temp);
                            } else if (inputIndex == theSequence.length()) {
                                theSequence.add(theSequence.length(), temp);
                            }
                        } else {
                            theSequence.add(0, temp);
                        }
                    }
                    /*
                     * Skip the separators to find the next word.
                     */
                    position++;
                    start = position;
                } else {
                    /*
                     * Increase the substring index of the end of a word until a
                     * separator is found.
                     */
                    position++;
                }
            }
        }
        in.close();
    }

    /**
     * The main method of the program. Prompts the user for an input file name
     * and an output file name. Counts the number of times each word appears in
     * the input file, then generates an .html file with a table showing each
     * distinct word and its count.
     *
     * @param args
     *            command line arguments (not used)
     *
     * @requires args != null
     * @ensures an .html file is created, containing a table with each word
     *          listed and the number of times it occurs.
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Prompt the user for the file name that will be modified and the file
         * name that the user want the output to be printed to.
         */
        out.println("Please enter the input file name: ");
        String inputFileName = in.nextLine();
        out.println("Please enter the output file name: ");
        String outputFileName = in.nextLine();
        SimpleWriter fileOut = new SimpleWriter1L(outputFileName);
        /*
         * Map used to store words and their existing times. Queue used to store
         * the key of the map alphabetically
         */
        Map<String, Integer> wordList = new Map1L<String, Integer>();
        Sequence<String> keyList = new Sequence1L<String>();
        /*
         * Print in .html format.
         */
        fileOut.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        fileOut.println(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        fileOut.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        fileOut.println("<head>");
        fileOut.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        fileOut.println("<title>" + "Word Counter" + "</title>");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h1>" + "Word Counted In" + "</h1>");
        fileOut.println("<hr>");
        fileOut.println("<h3>");
        fileOut.println(
                "Please check the table below to see the amount of times that each word appears in your file.");
        fileOut.println(
                "<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\">");
        fileOut.println("<tr><th>Word</th><th>Count</th></tr>");
        /*
         * Count the words from the input file.
         */
        counter(inputFileName, wordList, keyList);
        /*
         * Print each word and its existing time accordingly in .html format
         * with the order sorted in the sequence.
         */
        for (int j = 0; j < keyList.length(); j++) {
            Map.Pair<String, Integer> pair = wordList.remove(keyList.entry(j));
            fileOut.println("<tr><td>" + pair.key() + "</td><td>" + pair.value()
                    + "</td></tr>");
        }
        fileOut.println("</table>");
        fileOut.println("</body>");
        fileOut.println("</html>");
        in.close();
        out.close();
        fileOut.close();
    }

}
