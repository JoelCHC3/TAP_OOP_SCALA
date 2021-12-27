package Part_5;

import Part_2.CompositeDataFrame;
import org.junit.Assert;
import org.junit.Test;

import static Part_2.Main2.getRootCompositeDF;

public class Test_5 {
    private final String extension = ".csv";

    /**
     * Tests the behavior of MaxVisitor
     */
    @Test
    public void testMax() {
        CompositeDataFrame df_root;
        df_root = getRootCompositeDF(extension);
        MaxVisitor v = new MaxVisitor("LatM");
        df_root.accept(v);
        Assert.assertEquals(59, v.getMaximum());
    }

    /**
     * Tests the behavior of MinVisitor
     */
    @Test
    public void testMin() {
        CompositeDataFrame df_root;
        df_root = getRootCompositeDF(extension);
        MinVisitor v = new MinVisitor("LatM");
        df_root.accept(v);
        Assert.assertEquals(1, v.getMinimum());
    }

    /**
     * Tests the behavior of AverageVisitor
     */
    @Test
    public void testAvg() {
        CompositeDataFrame df_root;
        df_root = getRootCompositeDF(extension);
        AverageVisitor v = new AverageVisitor("LatM");
        df_root.accept(v);
        Assert.assertEquals(30.765625, v.getAverage(), 0.0);
    }

    /**
     * Tests the behavior of SumVisitor
     */
    @Test
    public void testSum() {
        CompositeDataFrame df_root;
        df_root = getRootCompositeDF(extension);
        SumVisitor v = new SumVisitor("LatM");
        df_root.accept(v);
        Assert.assertEquals(3938, v.getSum());
    }

}
