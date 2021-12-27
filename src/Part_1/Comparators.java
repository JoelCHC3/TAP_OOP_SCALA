package Part_1;

import java.util.Comparator;

public class Comparators {
    /**
     * It compares two values in ascending alphabetical order.
     */
    public static class AlphAscending implements Comparator<String> {
        public int compare (String a, String b) {
            return a.compareTo(b);
        }
    }

    /**
     * It compares two values in descending alphabetical order.
     */
    public static class AlphDescending implements Comparator<String> {
        public int compare (String a, String b) {
            return b.compareTo(a);
        }
    }

    /**
     * It compares two values in ascending numerical order.
     */
    public static class SortAscending implements Comparator<String> {
        public int compare (String a, String b) {
            return (int)(Float.parseFloat(a) - Float.parseFloat(b));
        }
    }

    /**
     * It compares two values in descending numerical order.
     */
    public static class SortDescending implements Comparator<String> {
        public int compare (String a, String b) {
            return (int)(Float.parseFloat(b) - Float.parseFloat(a));
        }
    }
}
