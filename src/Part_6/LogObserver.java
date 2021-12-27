package Part_6;

import java.lang.reflect.Method;

public class LogObserver implements Observer {

    /**
     * Updates the observer.
     * @param method The method called.
     * @param args The arguments of the method.
     */
    @Override
    public void update(Method method, Object[] args) {
        System.out.println("Method called: "+method.getName());
    }
}
