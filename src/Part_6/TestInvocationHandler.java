package Part_6;

import java.lang.reflect.*;
import java.util.List;

public class TestInvocationHandler implements InvocationHandler {
    private final Object testImpl;
    private final List<Observer> observers;

    /**
     * Constructor of the class. Assigns the object to be treated and its observers list.
     * @param impl
     * @param obs
     */
    public TestInvocationHandler(Object impl, List<Observer> obs) {
        this.testImpl = impl;
        observers = obs;
    }

    /**
     * Processes a method invocation on a proxy instance and returns the result.
     * @param proxy The proxy instance that the method was invoked on
     * @param method The Method instance corresponding to the interface method invoked on the proxy instance.
     * @param args An array of objects containing the values of the arguments passed in the method invocation on
     *             the proxy instance, or null if interface method takes no arguments.
     * @return the value to return from the method invocation on the proxy instance. If the declared return type of
     * the interface method is a primitive type, then the value returned by this method must be an instance of the
     * corresponding primitive wrapper class; otherwise, it must be a type assignable to the declared return type. If
     * the value returned by this method is null and the interface method's return type is primitive, then a
     * NullPointerException will be thrown by the method invocation on the proxy instance. If the value returned by
     * this method is otherwise not compatible with the interface method's declared return type as described above, a
     * ClassCastException will be thrown by the method invocation on the proxy instance.
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        notifyAllObservers(method, args);
        return method.invoke(testImpl, args);
    }

    /**
     * Notifies all of the observers related to the object.
     * @param method The method called.
     * @param args The arguments of the method.
     */
    public void notifyAllObservers(Method method, Object[] args){
        for (Observer observer : observers) {
            observer.update(method, args);
        }
    }
}

