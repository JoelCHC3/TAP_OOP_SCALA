package Part_4;

import Part_1.AbstractDataFrame;
import Part_2.DataFrame;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static Part_1.Main1.createDataFrame;
import static Part_4.Main4.getRootDFStruct;

public class Test_4 {

    /**
     * Tests the MapReduce implementation with integers.
     */
    @Test
    public void testInt() {
        DFStruct dfStruct = getRootDFStruct();
        long size = dfStruct.MapReduce((DataFrame::size), Integer::sum);
        Assert.assertEquals(128, size);
    }

    /**
     * Tests the MapReduce implementation with strings.
     */
    @Test
    public void testString() {
        DFStruct dfStruct = getRootDFStruct();
        Predicate<String> isEqual = str -> Integer.parseInt(str) == 7;
        List<String> listquery;
        listquery = dfStruct.MapReduce(e -> e.query(isEqual,"LonM"), (f,l) -> {
            f.addAll(l);
            return f;
        });
        List<String> expectedList = createExpectedList();
        Assert.assertEquals(listquery, expectedList);
    }

    /**
     * Creates a list identic to the one expected to be returned by the MapReduce for the method {@link #testString() testString}
     * @return The list.
     */
    public List<String> createExpectedList() {
        List<String> list = new ArrayList<>();
        list.add("\t \t|\tLatD \t|\tLatM \t|\tLatS \t|\tNS \t|\tLonD \t|\tLonM \t|\tLonS \t|\tEW \t|\tCity \t|\tState \t|\t");
        list.add("\t \t|\tLatD \t|\tLatM \t|\tLatS \t|\tNS \t|\tLonD \t|\tLonM \t|\tLonS \t|\tEW \t|\tCity \t|\tState \t|\t");
        list.add("\t10 \t|\t49 \t|\t16 \t|\t12 \t|\tN \t|\t123 \t|\t7 \t|\t12 \t|\tW \t|\tVancouver \t|\tBC \t|\t");
        list.add("\t \t|\tLatD \t|\tLatM \t|\tLatS \t|\tNS \t|\tLonD \t|\tLonM \t|\tLonS \t|\tEW \t|\tCity \t|\tState \t|\t");
        list.add("\t \t|\tLatD \t|\tLatM \t|\tLatS \t|\tNS \t|\tLonD \t|\tLonM \t|\tLonS \t|\tEW \t|\tCity \t|\tState \t|\t");
        list.add("\t \t|\tLatD \t|\tLatM \t|\tLatS \t|\tNS \t|\tLonD \t|\tLonM \t|\tLonS \t|\tEW \t|\tCity \t|\tState \t|\t");
        list.add("\t \t|\tLatD \t|\tLatM \t|\tLatS \t|\tNS \t|\tLonD \t|\tLonM \t|\tLonS \t|\tEW \t|\tCity \t|\tState \t|\t");
        return list;
    }

}
