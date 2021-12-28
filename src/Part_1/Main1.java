package Part_1;
import java.util.List;
import java.util.function.Predicate;

public class Main1 {
    public static void main(String[] args) {
        AbstractDataFrame df = createDataFrame("C:/Users/joelc/Desktop/cities.json");
        testDataFrameCities(df);
        //AbstractDataFrame df = createDataFrame("C:/Users/joelc/Desktop/100_Sales_Records.csv");
        //testDataFrameSales(df);
    }

    /**
     * Method to test a DataFrame for the file cities.csv, using every method demanded in the project specifications.
     * @param d The DataFrame to be used.
     */
    public static void testDataFrameCities (AbstractDataFrame d) {
        System.out.println("=== DataFrame ===");
        System.out.println("== Printing DataFrame ==");
        d.printDataFrame();

        System.out.println("\nNumber of columns: "+d.columns());
        System.out.println("Size of de DataFrame: "+d.size());

        System.out.println("\n\n== Testing functions ==");
        int at = 127;
        System.out.println("= At - Looking for values on row "+at+" for each label =");
        System.out.println("Element at ("+at+",LatD): "+d.at(at,"LatD"));
        System.out.println("Element at ("+at+",LatM): "+d.at(at,"LatM"));
        System.out.println("Element at ("+at+",LatS): "+d.at(at,"LatS"));
        System.out.println("Element at ("+at+",NS): "+d.at(at,"NS"));
        System.out.println("Element at ("+at+",LonD): "+d.at(at,"LonD"));
        System.out.println("Element at ("+at+",LonM): "+d.at(at,"LonM"));
        System.out.println("Element at ("+at+",LonS): "+d.at(at,"LonS"));
        System.out.println("Element at ("+at+",EW): "+d.at(at,"EW"));
        System.out.println("Element at ("+at+",City): "+d.at(at,"City"));
        System.out.println("Element at ("+at+",State): "+d.at(at,"State"));

        System.out.println("\n= iAt - Looking for value on position ("+at+", 9) =");
        System.out.println(d.iat(at, 9));

        System.out.println("\n= sort - Sorting column LatM on ascending order =");
        List<String> list = d.sort("LatM", new Comparators.SortAscending());
        System.out.println(list.toString());

        Predicate<String> isEqual = str -> str.equals("OH");
        //Predicate<String> biggerThan = str -> Integer.parseInt(str) > 40;
        //Predicate<String> biggerEqual = str -> Integer.parseInt(str) >= 40;
        //Predicate<String> lessThan = str -> Integer.parseInt(str) < 40;
        //Predicate<String> lessEqual = str -> Integer.parseInt(str) <= 40;
        System.out.println("\n= Query - Looking for all rows where State is OH =");
        List<String> listquery;
        listquery = d.query(isEqual,"State");
        listquery.forEach(System.out::println);

        System.out.println("\n== Testing Iterable ==");
        d.forEach(System.out::println);
    }

    /**
     * Method to test a DataFrame for the file 100_Sales_Records.csv, using every method demanded in the project specifications.
     * @param d The DataFrame to be used.
     */
    public static void testDataFrameSales (AbstractDataFrame d) {
        System.out.println("=== DataFrame ===");
        System.out.println("== Printing DataFrame ==");
        d.printDataFrame();

        System.out.println("\nNumber of columns: "+d.columns());
        System.out.println("Size of de DataFrame: "+d.size());

        System.out.println("\n\n== Testing functions ==");
        System.out.println("Element at ("+4+",Item Type): "+d.at(4,"Item Type"));

        System.out.println("\n= iAt - Looking for value on position (4, 2) =");
        System.out.println(d.iat(4, 2));

        System.out.println("\n= sort - Sorting column Unit Price on ascending order =");
        List<String> list = d.sort("Unit Price", new Comparators.SortAscending());
        System.out.println(list.toString());

        Predicate<String> isEqual = str -> str.equals("Sub-Saharan Africa");
        //Predicate<String> biggerThan = str -> Integer.parseInt(str) > 40;
        //Predicate<String> biggerEqual = str -> Integer.parseInt(str) >= 40;
        //Predicate<String> lessThan = str -> Integer.parseInt(str) < 40;
        //Predicate<String> lessEqual = str -> Integer.parseInt(str) <= 40;
        System.out.println("\n= Query - Looking for all rows where Region is Sub-Saharan Africa =");
        List<String> listquery;
        listquery = d.query(isEqual,"Region");
        listquery.forEach(System.out::println);

        System.out.println("\n== Testing Iterable ==");
        d.forEach(System.out::println);

    }

    /**
     * Creates a DataFrame for a file in the given path.
     * @param path Path of the file.
     * @return The DataFrame.
     */
    public static AbstractDataFrame createDataFrame(String path) {
        AbstractDataFrame df = null;
        try {
            if(path.endsWith(".csv")) df = new DataFrameCSV(path);
            else if(path.endsWith(".json")) df = new DataFrameJSON(path);
            else if(path.endsWith(".txt")) df = new DataFrameTXT(path);
            else throw new ExtensionNotSupported("There is a problem with the extension: "+path);
            df.openDataFrame();
        } catch (ExtensionNotSupported e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return df;
    }
}
