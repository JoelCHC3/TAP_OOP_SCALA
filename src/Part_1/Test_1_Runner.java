package Part_1;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Test_1_Runner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(Test_1_Suite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("TEST OK? " + result.wasSuccessful());
    }
}
