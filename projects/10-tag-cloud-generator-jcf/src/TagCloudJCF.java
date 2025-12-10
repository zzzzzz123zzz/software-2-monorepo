import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * TagCloud generates an XHTML file containing a tag cloud of the top N most
 * frequent words from a user-specified input text file.
 *
 * @author LEO ZHUANG, JENG ZHUANG, MICHAEL XU
 *
 */
public final class TagCloudJCF {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudJCF() {
    }

    /**
     * Comparator to sort Map.Entry by decreasing count.
     */
    private static class CompareCount
            implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> a,
                Map.Entry<String, Integer> b) {
            return b.getValue().compareTo(a.getValue());
        }
    }

    /**
     * Comparator to sort Map.Entry alphabetically.
     */
    private static class CompareWords
            implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> a,
                Map.Entry<String, Integer> b) {

            return a.getKey().compareToIgnoreCase(b.getKey());
        }
    }

    /**
     * Returns the next word from the given line starting at the given position.
     * Separators include whitespace and punctuation.
     *
     * @param s
     *            the input string
     * @param start
     *            the starting position in the string
     * @param sep
     *            the set of separator characters
     * @return the next word found in the string starting from the given
     *         position
     */
    private static String nextWord(String s, int start, Set<Character> sep) {
        int i = start;

        // skip separators
        while (i < s.length() && sep.contains(s.charAt(i))) {
            i++;
        }
        int begin = i;

        // read until separator
        while (i < s.length() && !sep.contains(s.charAt(i))) {
            i++;
        }

        return s.substring(begin, i);
    }

    /**
     * Builds the separator set.
     *
     * @return a set of separator characters
     */
    private static Set<Character> buildSeparators() {
        String sep = " \t\n\r,-.!?[]';:/(){}*\"<>`";

        Set<Character> s = new HashSet<>();
        for (int i = 0; i < sep.length(); i++) {
            s.add(sep.charAt(i));
        }
        return s;
    }

    /**
     * Count words from input file into HashMap.
     *
     * @param file
     *            the input file name
     * @param wordMap
     *            the map to store word counts
     */
    private static void countWords(String file,
            HashMap<String, Integer> wordMap) {

        Scanner in = new Scanner(file);
        Set<Character> separators = buildSeparators();

        while (in.hasNextLine()) {
            String line = in.nextLine();

            int pos = 0;
            while (pos < line.length()) {

                String word = nextWord(line, pos, separators).toLowerCase();

                if (!word.isEmpty()) {
                    if (wordMap.containsKey(word)) {
                        wordMap.put(word, wordMap.get(word) + 1);
                    } else {
                        wordMap.put(word, 1);
                    }
                }

                pos += word.length();
                while (pos < line.length()
                        && separators.contains(line.charAt(pos))) {
                    pos++;
                }
            }
        }

        in.close();
    }

    /**
     * Compute font size between 11 and 48.
     *
     * @param count
     *            the word count
     * @param min
     *            the minimum count
     * @param max
     *            the maximum count
     * @return the font size between 11 and 48
     */
    private static int computeFontSize(int count, int min, int max) {
        final int minFont = 11;
        final int maxFont = 48;

        int result = minFont;

        if (max != min) {
            result = minFont
                    + (count - min) * (maxFont - minFont) / (max - min);
        }

        return result;
    }

    /**
     * Print HTML header.
     *
     * @param out
     *            the output writer
     * @param file
     *            the input file name
     * @param n
     *            the number of words to display
     */
    private static void printHeader(PrintWriter out, String file, int n) {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        out.println(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");
        out.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        out.println("<title>Top " + n + " words in " + file + "</title>");
        out.println(
                "<link href=\"https://cse22x1.engineering.osu.edu/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\""
                        + " rel=\"stylesheet\" type=\"text/css\" />");
        out.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\" />");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + n + " words in " + file + "</h2>");
        out.println("<hr />");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");
    }

    /**
     * Print HTML footer.
     *
     * @param out
     *            the output writer
     */
    private static void printFooter(PrintWriter out) {
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * The main method that generates a tag cloud HTML file.
     *
     * @param args
     *            command line arguments (not used)
     *
     * @requires args != null
     * @ensures An XHTML file containing a tag cloud with the top N words from
     *          the input file is created at the specified output file path.
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        out.print("Input file: ");
        String inputFile = in.nextLine();

        out.print("Output file: ");
        String outputFile = in.nextLine();

        out.print("N (number of words): ");
        int topN = Integer.parseInt(in.nextLine());

        PrintWriter fout = new PrintWriter(outputFile);

        HashMap<String, Integer> words = new HashMap<>();
        countWords(inputFile, words);

        // Sort by count first
        Comparator<Map.Entry<String, Integer>> byCount = new CompareCount();
        List<Map.Entry<String, Integer>> countList = new ArrayList<>(
                words.entrySet());
        countList.sort(byCount);

        // Pick top N → sort alphabetically
        Comparator<Map.Entry<String, Integer>> byAlpha = new CompareWords();

        List<Map.Entry<String, Integer>> alphaList = new ArrayList<>();
        for (int i = 0; i < topN && countList.size() > 0; i++) {
            alphaList.add(countList.get(i));
        }
        alphaList.sort(byAlpha);

        // compute min/max
        int min = Integer.MAX_VALUE;
        int max = 0;

        for (Map.Entry<String, Integer> p : alphaList) {
            int v = p.getValue();
            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }

        // Output
        printHeader(fout, inputFile, topN);
        while (alphaList.size() > 0) {
            Map.Entry<String, Integer> p = alphaList.removeFirst();
            int f = computeFontSize(p.getValue(), min, max);

            fout.println("<span class=\"f" + f + "\" title=\"count: "
                    + p.getValue() + "\">" + p.getKey() + "</span>");
        }

        printFooter(fout);

        in.close();
        out.close();
        fout.close();
    }
}
