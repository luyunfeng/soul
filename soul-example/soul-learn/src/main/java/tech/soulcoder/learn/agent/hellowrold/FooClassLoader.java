package tech.soulcoder.learn.agent.hellowrold;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author yunfeng.lu
 * @create 2019/4/24.
 */
public class FooClassLoader extends ClassLoader {

    private static final String NAME
        = "/Applications/code/soul/soul-example/soul-learn/target/classes/tech/soulcoder/learn/agent/hellowrold/";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass == null) {
            String s = name.substring(name.lastIndexOf(".") + 1) + ".class";
            File file = new File(NAME + s);
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] b = new byte[fileInputStream.available()];
                fileInputStream.read(b);
                return defineClass(name, b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return loadedClass;
    }

}
