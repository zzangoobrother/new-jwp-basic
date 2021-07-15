package core.ref;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> junit4TestClass = Junit4Test.class;
        Method[] methods = junit4TestClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyTest.class)) {
                method.invoke(junit4TestClass.getConstructor().newInstance());
            }
        }
    }
}
