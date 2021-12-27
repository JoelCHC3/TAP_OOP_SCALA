package Part_2;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Test_2_Runner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(Test_2_Suite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("TEST OK? " + result.wasSuccessful());
    }
}
