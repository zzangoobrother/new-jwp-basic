package core.ref;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<?> junit3TestClass = Junit3Test.class;

        Method[] methods = junit3TestClass.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                method.invoke(junit3TestClass.getConstructor().newInstance());
            }
        }
    }
}
