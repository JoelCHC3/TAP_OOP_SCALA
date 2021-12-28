package Part_6;
import Part_2.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main6 {
    public static void main(String[] args) {
        DataFrame df = Part_1.Main1.createDataFrame("root/A/B/dfFile_B_2.csv");

        List<Observer> obs = new ArrayList<>();
        obs.add(new LogObserver());
        obs.add(new LogObserver());
        obs.add(new QueryObserver());

        DataFrame d = (DataFrame) Proxy.newProxyInstance(DataFrame.class.getClassLoader(),
                new Class<?>[] {DataFrame.class},
                new TestInvocationHandler(df,obs));
        Predicate<String> isEqual = str -> str.equals("OH");
        d.query(isEqual,"State");
    }
}
