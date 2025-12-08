import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine4;

/**
 * TagCloud generates an XHTML file containing a tag cloud of the top N most
 * frequent words from a user-specified input text file.
 *
 * @author LEO ZHUANG, JENG ZHUANG, MICHAEL XU
 *
 */
public final class TagCloud {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloud() {
    }

    /**
     * Comparator to sort Map.Pair by decreasing count.
     */
    private static class CompareCount
            implements Comparator<Map.Pair<String, Integer>> {

        @Override
        public int compare(Map.Pair<String, Integer> a,
                Map.Pair<String, Integer> b) {
            return b.value().compareTo(a.value());
        }
    }

    /**
     * Comparator to sort Map.Pair alphabetically.
     */
    private static class CompareWords
            implements Comparator<Map.Pair<String, Integer>> {

        @Override
        public int compare(Map.Pair<String, Integer> a,
                Map.Pair<String, Integer> b) {

            return a.key().compareToIgnoreCase(b.key());
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

        Set<Character> s = new Set1L<>();
        for (int i = 0; i < sep.length(); i++) {
            s.add(sep.charAt(i));
        }
        return s;
    }

    /**
     * Count words from input file into Map.
     *
     * @param file
     *            the input file name
     * @param wordMap
     *            the map to store word counts
     */
    private static void countWords(String file, Map<String, Integer> wordMap) {

        SimpleReader in = new SimpleReader1L(file);
        Set<Character> separators = buildSeparators();

        while (!in.atEOS()) {
            String line = in.nextLine();

            int pos = 0;
            while (pos < line.length()) {

                String word = nextWord(line, pos, separators).toLowerCase();

                if (!word.isEmpty()) {
                    if (wordMap.hasKey(word)) {
                        wordMap.replaceValue(word, wordMap.value(word) + 1);
                    } else {
                        wordMap.add(word, 1);
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
    private static void printHeader(SimpleWriter out, String file, int n) {
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
    private static void printFooter(SimpleWriter out) {
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

        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Input file: ");
        String inputFile = in.nextLine();

        out.print("Output file: ");
        String outputFile = in.nextLine();

        out.print("N (number of words): ");
        int topN = Integer.parseInt(in.nextLine());

        SimpleWriter fout = new SimpleWriter1L(outputFile);

        Map<String, Integer> words = new Map1L<>();
        countWords(inputFile, words);

        // Sort by count first
        SortingMachine<Map.Pair<String, Integer>> byCount = new SortingMachine4<>(
                new CompareCount());

        for (Map.Pair<String, Integer> p : words) {
            byCount.add(p);
        }
        byCount.changeToExtractionMode();

        // Pick top N → sort alphabetically
        SortingMachine<Map.Pair<String, Integer>> byAlpha = new SortingMachine4<>(
                new CompareWords());

        for (int i = 0; i < topN && byCount.size() > 0; i++) {
            byAlpha.add(byCount.removeFirst());
        }
        byAlpha.changeToExtractionMode();

        // compute min/max
        int min = Integer.MAX_VALUE;
        int max = 0;

        for (Map.Pair<String, Integer> p : byAlpha) {
            int v = p.value();
            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }

        // Output
        printHeader(fout, inputFile, topN);
        while (byAlpha.size() > 0) {
            Map.Pair<String, Integer> p = byAlpha.removeFirst();
            int f = computeFontSize(p.value(), min, max);

            fout.println("<span class=\"f" + f + "\" title=\"count: "
                    + p.value() + "\">" + p.key() + "</span>");
        }

        printFooter(fout);

        in.close();
        out.close();
        fout.close();
    }
}
