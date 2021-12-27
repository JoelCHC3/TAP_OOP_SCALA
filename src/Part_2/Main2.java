package Part_2;

import Part_1.*;

import java.util.List;
import java.util.function.Predicate;

import static Part_1.Main1.createDataFrame;

public class Main2 {
    public static void main(String[] args) {
        System.out.println("Test Part_2");
        CompositeDataFrame df_root = getRootCompositeDF(".csv");

        df_root.printDataFrame();
        System.out.println("\n--- Testing at and iat on different files ---");
        System.out.println("Files 1 and 2");
        System.out.println(df_root.at(21,"City")+"\t"+df_root.iat(21,8));
        System.out.println(df_root.at(22,"City")+"\t"+df_root.iat(22,8));
        System.out.println(df_root.at(23,"City")+"\t"+df_root.iat(23,8));
        System.out.println(df_root.at(24,"City")+"\t"+df_root.iat(24,8));
        System.out.println("\nFiles 2 and 3");
        System.out.println(df_root.at(48,"City")+"\t"+df_root.iat(48,8));
        System.out.println(df_root.at(49,"City")+"\t"+df_root.iat(49,8));
        System.out.println(df_root.at(50,"City")+"\t"+df_root.iat(50,8));
        System.out.println(df_root.at(51,"City")+"\t"+df_root.iat(51,8));
        System.out.println("\nFiles 3 and 4");
        System.out.println(df_root.at(71,"City")+"\t"+df_root.iat(71,8));
        System.out.println(df_root.at(72,"City")+"\t"+df_root.iat(72,8));
        System.out.println(df_root.at(73,"City")+"\t"+df_root.iat(73,8));
        System.out.println(df_root.at(74,"City")+"\t"+df_root.iat(74,8));
        System.out.println("\nFiles 4 and 5");
        System.out.println(df_root.at(98,"City")+"\t"+df_root.iat(98,8));
        System.out.println(df_root.at(99,"City")+"\t"+df_root.iat(99,8));
        System.out.println(df_root.at(100,"City")+"\t"+df_root.iat(100,8));
        System.out.println(df_root.at(101,"City")+"\t"+df_root.iat(101,8));
        System.out.println("\nFiles 5 and 6");
        System.out.println(df_root.at(111,"City")+"\t"+df_root.iat(111,8));
        System.out.println(df_root.at(112,"City")+"\t"+df_root.iat(112,8));
        System.out.println(df_root.at(113,"City")+"\t"+df_root.iat(113,8));
        System.out.println(df_root.at(114,"City")+"\t"+df_root.iat(114,8));

        System.out.println("\nTesting query LatM > 40");
        Predicate<String> isEqual = str -> str.equals("OH");
        Predicate<String> biggerThan = str -> Integer.parseInt(str) > 40;
        Predicate<String> biggerEqual = str -> Integer.parseInt(str) >= 40;
        Predicate<String> lessThan = str -> Integer.parseInt(str) < 40;
        Predicate<String> lessEqual = str -> Integer.parseInt(str) <= 40;
        List<String> listquery = df_root.query(biggerThan,"LatM");
        listquery.forEach(System.out::println);

        System.out.println("\nSorting");
        List<String> list = df_root.sort("LatM", new Comparators.SortAscending());
        System.out.println(list.toString());

        System.out.println("\nIterating");
        df_root.iterate();
    }

    /**
     * Creates a CompositeDataFrame for the default directories structure. That structure is:
     * root
     * |- dfFile_root_1
     * |- dfFile_root_2
     * |- A
     *    |- dfFile_A_1
     *    |- dfFile_A_2
     *    |- B
     *       |- dfFile_B_1
     *       |- dfFile_B_1
     * For each file there are three ones with the same name but with different extension (.csv, .json and .txt).
     * @param ext The desired extension.
     * @return The CompositeDataFrame
     */
    public static CompositeDataFrame getRootCompositeDF(String ext) {
        CompositeDataFrame df_root = new CompositeDataFrame();
        AbstractDataFrame dfFile_root_1 = createDataFrame("C:/Users/joelc/Desktop/root/dfFile_root_1"+ext);
        AbstractDataFrame dfFile_root_2 = createDataFrame("C:/Users/joelc/Desktop/root/dfFile_root_2"+ext);
        CompositeDataFrame df_a = new CompositeDataFrame();
        try {
            df_root.addChild(dfFile_root_1);
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        try {
            df_root.addChild(dfFile_root_2);
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        try {
            df_root.addChild(df_a);
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        CompositeDataFrame df_b = new CompositeDataFrame();
        AbstractDataFrame dfFile_A_1 = createDataFrame("C:/Users/joelc/Desktop/root/A/dfFile_A_1"+ext);
        AbstractDataFrame dfFile_A_2 = createDataFrame("C:/Users/joelc/Desktop/root/A/dfFile_A_2"+ext);
        try {
            df_a.addChild(dfFile_A_1);
        } catch (ColumnException e) {
            e.printStackTrace();
        }
        try {
            df_a.addChild(dfFile_A_2);
        } catch (ColumnException e) {
            e.printStackTrace();
        }
        try {
            df_a.addChild(df_b);
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        AbstractDataFrame dfFile_B_1 = createDataFrame("C:/Users/joelc/Desktop/root/A/B/dfFile_B_1"+ext);
        AbstractDataFrame dfFile_B_2 = createDataFrame("C:/Users/joelc/Desktop/root/A/B/dfFile_B_2"+ext);

        try {
            df_b.addChild(dfFile_B_1);
        } catch (ColumnException e) {
            e.printStackTrace();
        }
        try {
            df_b.addChild(dfFile_B_2);
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        //The above file is created in order to proof the correct use of the method removeChild
        AbstractDataFrame repeatedFile = createDataFrame("C:/Users/joelc/Desktop/root/A/B/dfFile_B_2"+ext);
        try {
            df_b.addChild(repeatedFile);
        } catch (ColumnException e) {
            e.printStackTrace();
        }
        df_b.removeChild(repeatedFile);

        return df_root;
    }
}
