package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> questionClass = Question.class;
        log.debug(questionClass.getName());

        Field[] fields = questionClass.getFields();
        for (Field field : fields) {
            log.debug(String.valueOf(field));
        }

        Constructor<?>[] constructors = questionClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            log.debug(String.valueOf(constructor));
        }

        Method[] methods = questionClass.getMethods();
        for (Method method : methods) {
            log.debug(String.valueOf(method));
        }
    }

    @Test
    public void newInstanceWithConstructorArgs() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<User> userClass = User.class;
        Constructor<?>[] constructors = userClass.getDeclaredConstructors();
        User user = null;
        for (Constructor userConstructor : constructors) {
            user = (User) userConstructor.newInstance("aaa", "123", "sss", "sss@naver.com");
            break;
        }

        Assert.assertEquals(user.getUserId(), "aaa");
        Assert.assertEquals(user.getPassword(), "123");
        Assert.assertEquals(user.getName(), "sss");
        Assert.assertEquals(user.getEmail(), "sss@naver.com");

    }

    @Test
    public void privateFieldAccess() throws Exception {
        Student student = new Student();
        Class<Student> studentClass = Student.class;
        Field name = studentClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "안녕");

        Assert.assertEquals(student.getName(), "안녕");

        Field age = studentClass.getDeclaredField("age");
        age.setAccessible(true);
        age.setInt(student, 12);

        Assert.assertEquals(student.getAge(), 12);
    }
}
