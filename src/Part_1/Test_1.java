package Part_1;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static Part_1.Main1.createDataFrame;

public class Test_1 {
    private final String path = "files/cities.json";   //path of the file to be tested
    AbstractDataFrame d = createDataFrame(path);

    /**
     * Tests the size() method of the DataFrame.
     */
    @Test
    public void testSize() {
        Assert.assertEquals(128, d.size());
    }

    /**
     * Tests the columns() method of the DataFrame.
     */
    @Test
    public void testColumns() {
        Assert.assertEquals(10, d.columns());
    }

    /**
     * Tests the at method of the DataFrame.
     */
    @Test
    public void testAt() {
        Assert.assertEquals("Ravenna", d.at(127, "City"));
    }

    /**
     * Tests the iAt method of the DataFrame.
     */
    @Test
    public void testIat() {
        Assert.assertEquals("Ravenna", d.iat(127, 8));
    }

    /**
     * Tests the query method of the DataFrame.
     */
    @Test
    public void testQuery() {
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
        list.addAll(d.getListCol(1));
        list.addAll(d.getListCol(2));
        list.addAll(d.getListCol(3));
        list.addAll(d.getListCol(4));
        list.addAll(d.getListCol(5));
        list.addAll(d.getListCol(6));
        list.addAll(d.getListCol(7));
        list.addAll(d.getListCol(8));
        list.addAll(d.getListCol(9));
        list.addAll(d.getListCol(10));
        return list;
    }

}
