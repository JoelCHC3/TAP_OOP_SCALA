package Part_6;
import java.lang.reflect.Method;
public interface Observer {

    void update(Method method, Object[] args);
}
