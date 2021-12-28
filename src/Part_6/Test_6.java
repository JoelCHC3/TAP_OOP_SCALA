package Part_6;

import Part_2.DataFrame;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Test_6 {

    @Test
    public void testDynamicProxy() {
        DataFrame df = Part_1.Main1.createDataFrame("C:/Users/joelc/Desktop/root/A/B/dfFile_B_2.csv");

        List<Observer> obs = new ArrayList<>();
        obs.add(new LogObserver());
        obs.add(new LogObserver());
        obs.add(new QueryObserver());

        DataFrame d = (DataFrame) Proxy.newProxyInstance(DataFrame.class.getClassLoader(),
                new Class<?>[] {DataFrame.class},
                new TestInvocationHandler(df,obs));

        Predicate<String> isEqual = str -> str.equals("OH");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;       //Saving old System.out
        System.setOut(ps);                  //Setting new System.out

        d.query(isEqual,"State");   //Anything printed will be in baos

        System.out.flush();
        System.setOut(old);                 //Setting old System.out. Things are back to normal
        //The following String is the expected output for the concrete query applied to this file
        String expectedOutput = """
                Method called: query\r
                Method called: query\r
                A query for the column state has been made.\r
                """;
        Assert.assertEquals(baos.toString(), expectedOutput);
    }

}
