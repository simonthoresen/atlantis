package kernel.obj;

import kernel.Kernel;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ObjectManager {

    private final Map<String, Class> cache = new HashMap<String, Class>();
    private final Kernel kernel;

    public ObjectManager(Kernel kernel) {
        this.kernel = kernel;
    }

    public Object create(String className, Class classType) {
        if (!cache.containsKey(className) && reloadClass(className) == null) {
            return null;
        }
        return Proxy.newProxyInstance(
                classType.getClassLoader(),
                new Class[]{ classType },
                new ObjectProxy(kernel, this, className));
    }

    public Class loadClass(String className) {
        return cache.get(className);
    }

    public Class reloadClass(String className) {
        try {
            ForcedClassLoader loader = new ForcedClassLoader(className);
            Class classObj = loader.loadClass(className);
            cache.put(className, classObj);
            return classObj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
