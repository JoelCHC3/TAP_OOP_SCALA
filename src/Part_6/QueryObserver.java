package Part_6;

import java.lang.reflect.Method;

public class QueryObserver implements Observer {
    /**
     * Updates the observer.
     * @param method The method called.
     * @param args The arguments of the method.
     */
    @Override
    public void update(Method method, Object[] args) {
        if(args[1].equals("State")) {
            System.out.println("A query for the column state has been made.");
        }
    }
}
