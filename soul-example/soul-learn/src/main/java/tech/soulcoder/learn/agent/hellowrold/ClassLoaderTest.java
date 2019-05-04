package tech.soulcoder.learn.agent.hellowrold;

import java.lang.reflect.Method;

/**
 * @author yunfeng.lu
 * @create 2019/4/24.
 */
public class ClassLoaderTest {
    private Object fooTestInstance;
    private FooClassLoader fooClassLoader = new FooClassLoader();


    public static void main(String[] args) throws Exception {
        ClassLoaderTest classLoaderTest = new ClassLoaderTest();
        classLoaderTest.initAndLoad();
        Object fooTestInstance = classLoaderTest.getFooTestInstance();
        System.out.println(fooTestInstance.getClass().getClassLoader());
        Method getFoo = fooTestInstance.getClass().getMethod("getFoo");
        System.out.println(getFoo.invoke(fooTestInstance));

        System.out.println(classLoaderTest.getClass().getClassLoader());
    }

    private void initAndLoad() throws Exception {
        Class<?> aClass = Class.forName("tech.soulcoder.learn.agent.hellowrold.FooTest", true, fooClassLoader);
        fooTestInstance = aClass.newInstance();
    }

    public Object getFooTestInstance() {
        return fooTestInstance;
    }
}
