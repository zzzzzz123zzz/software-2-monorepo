import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
 * frequent words from an input text file using standard Java components.
 *
 * @author Your Names
 */
public final class TagCloudJCF {

    /**
     * Private constructor: prevents instantiation.
     */
    private TagCloudJCF() {
    }

    /**
     * Comparator: sort entries by decreasing count.
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
     * Comparator: sort entries alphabetically by key.
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
     * Builds the set of separator characters.
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
     * Extract the next word starting at pos.
     */
    private static String nextWord(String s, int start, Set<Character> sep) {
        int i = start;

        while (i < s.length() && sep.contains(s.charAt(i))) {
            i++;
        }
        int begin = i;

        while (i < s.length() && !sep.contains(s.charAt(i))) {
            i++;
        }
        return s.substring(begin, i);
    }

    /**
     * Count words in a file using BufferedReader.
     */
    private static void countWords(String file, Map<String, Integer> map) {
        Set<Character> separators = buildSeparators();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                int pos = 0;

                while (pos < line.length()) {
                    String word = nextWord(line, pos, separators).toLowerCase();

                    if (word.length() > 0) {
                        map.put(word, map.getOrDefault(word, 0) + 1);
                    }

                    // Skip the word
                    pos += word.length();

                    // Skip trailing separators
                    while (pos < line.length()
                            && separators.contains(line.charAt(pos))) {
                        pos++;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    /**
     * Computes a font size between 11 and 48.
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

    private static void printHeader(PrintWriter out, String file, int n) {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        out.println(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                        + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");
        out.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        out.println("<title>Top " + n + " words in " + file + "</title>");
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
     * Prints the footer of the HTML file.
     */
    private static void printFooter(PrintWriter out) {
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.print("Input file: ");
        String inputFile = in.nextLine();

        System.out.print("Output file: ");
        String outputFile = in.nextLine();

        System.out.print("N (number of words): ");
        int n = Integer.parseInt(in.nextLine());

        in.close();

        Map<String, Integer> wordMap = new HashMap<>();

        countWords(inputFile, wordMap);

        List<Map.Entry<String, Integer>> sortedByCount = new ArrayList<>(
                wordMap.entrySet());
        sortedByCount.sort(new CompareCount());

        List<Map.Entry<String, Integer>> topN = new ArrayList<>();
        int limit = Math.min(n, sortedByCount.size());

        for (int i = 0; i < limit; i++) {
            topN.add(sortedByCount.get(i));
        }

        topN.sort(new CompareWords());

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> e : topN) {
            min = Math.min(min, e.getValue());
            max = Math.max(max, e.getValue());
        }

        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(outputFile)))) {

            printHeader(out, inputFile, n);

            for (Map.Entry<String, Integer> e : topN) {
                int f = computeFontSize(e.getValue(), min, max);

                out.println("<span class=\"f" + f + "\" title=\"count: "
                        + e.getValue() + "\">" + e.getKey() + "</span>");
            }

            printFooter(out);

        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
        }
    }
}