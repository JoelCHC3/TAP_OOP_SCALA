package Part_4;

import Part_1.AbstractDataFrame;
import Part_2.ColumnException;
import Part_2.CompositeDataFrame;
import Part_2.DataFrame;

import java.util.List;
import java.util.function.Predicate;

import static Part_1.Main1.createDataFrame;

public class Main4 {
    public static void main(String[] args) {
        DFStruct dfStruct = getRootDFStruct();

        long size = dfStruct.MapReduce((DataFrame::size), Integer::sum);
        System.out.println("Valor: "+size);

        Predicate<String> isEqual = str -> Integer.parseInt(str) == 7;
        List<String> listquery;
        listquery = dfStruct.MapReduce(e -> e.query(isEqual,"LonM"), (f,l) -> {
            f.addAll(l);
            return f;
        });
        listquery.forEach(System.out::println);
    }

    /**
     * Creates a DFStruct with the files included in the Composite default directory.
     * @return The DFStruct.
     */
    public static DFStruct getRootDFStruct() {
        DFStruct dfStruct = new DFStruct();
        AbstractDataFrame df_root_1 = createDataFrame("root/dfFile_root_1.csv");
        AbstractDataFrame df_root_2 = createDataFrame("root/dfFile_root_2.csv");
        AbstractDataFrame df_A_1 = createDataFrame("root/A/dfFile_A_1.csv");
        AbstractDataFrame df_A_2 = createDataFrame("root/A/dfFile_A_2.csv");
        AbstractDataFrame df_B_1 = createDataFrame("root/A/B/dfFile_B_1.csv");
        AbstractDataFrame df_B_2 = createDataFrame("root/A/B/dfFile_B_2.csv");
        dfStruct.addDataFrame(df_root_1);
        dfStruct.addDataFrame(df_root_2);
        dfStruct.addDataFrame(df_A_1);
        dfStruct.addDataFrame(df_A_2);
        dfStruct.addDataFrame(df_B_1);
        dfStruct.addDataFrame(df_B_2);
        return dfStruct;
    }
}
