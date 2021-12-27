package Part_2;

import Part_1.AbstractDataFrame;
import Part_1.Comparators;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static Part_1.Main1.createDataFrame;
import static Part_2.Main2.getRootCompositeDF;

public class Test_2 {

    /**
     * Tests the size() method of the DataFrame.
     */
    @Test
    public void testSize() {
        CompositeDataFrame d = getRootCompositeDF(".csv");
        Assert.assertEquals(128, d.size());
    }

    /**
     * Tests the columns() method of the DataFrame.
     */
    @Test
    public void testColumns() {
        CompositeDataFrame d = getRootCompositeDF(".csv");
        Assert.assertEquals(10, d.columns());
    }

    /**
     * Tests the at method of the DataFrame.
     */
    @Test
    public void testAt() {
        CompositeDataFrame d = getRootCompositeDF(".csv");
        Assert.assertEquals("Ravenna", d.at(127, "City"));
    }

    /**
     * Tests the iAt method of the DataFrame.
     */
    @Test
    public void testIat() {
        CompositeDataFrame d = getRootCompositeDF(".csv");
        Assert.assertEquals("Ravenna", d.iat(127, 8));
    }

    /**
     * Tests the query method of the DataFrame.
     */
    @Test
    public void testQuery() {
        CompositeDataFrame d = getRootCompositeDF(".csv");
        Predicate<String> isEqual = str -> Integer.parseInt(str) == 7;
        List<String> list = d.query(isEqual,"LonM");
        List<String> expected = new ArrayList<>();
        expected.add("\t \t|\tLatD \t|\tLatM \t|\tLatS \t|\tNS \t|\tLonD \t|\tLonM \t|\tLonS \t|\tEW \t|\tCity \t|\tState \t|\t");
        expected.add("\t33 \t|\t49 \t|\t16 \t|\t12 \t|\tN \t|\t123 \t|\t7 \t|\t12 \t|\tW \t|\tVancouver \t|\tBC \t|\t");
        Assert.assertEquals(list.toString(), expected.toString());
    }

    /**
     * Tests the sort method of the DataFrame.
     */
    @Test
    public void testSort(){
        CompositeDataFrame d = getRootCompositeDF(".csv");
        List<String> list = d.sort("LatM", new Comparators.SortAscending());
        List<String> expectedColumn = d.getListCol(2);
        expectedColumn.remove(0);
        expectedColumn.sort(new Comparators.SortAscending());

        Assert.assertEquals(list, expectedColumn);
    }

    /**
     * Tests the iterability of the DataFrame.
     */
    @Test
    public void testIterable(){
        CompositeDataFrame d = getRootCompositeDF(".csv");
        List<String> list = new ArrayList<>();
        d.forEach(list::add);
        List<String> expectedList = createListIterator();
        Assert.assertEquals(list, expectedList);
    }

    /**
     * Returns a list of strings with the expected values of the iterated DataFrame in order of iteration.
     * @return The list.
     */
    public List<String> createListIterator() {
        List<String> list = new ArrayList<>();
        AbstractDataFrame d = createDataFrame("C:/Users/joelc/Desktop/root/dfFile_root_1.csv");
        d.forEach(list::add);
        d = createDataFrame("C:/Users/joelc/Desktop/root/dfFile_root_2.csv");
        d.forEach(list::add);
        d = createDataFrame("C:/Users/joelc/Desktop/root/A/dfFile_A_1.csv");
        d.forEach(list::add);
        d = createDataFrame("C:/Users/joelc/Desktop/root/A/dfFile_A_2.csv");
        d.forEach(list::add);
        d = createDataFrame("C:/Users/joelc/Desktop/root/A/B/dfFile_B_1.csv");
        d.forEach(list::add);
        d = createDataFrame("C:/Users/joelc/Desktop/root/A/B/dfFile_B_2.csv");
        d.forEach(list::add);
        return list;
    }

}
