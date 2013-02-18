package kernel.obj;

import kernel.Kernel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectProxy implements InvocationHandler {

    private final Kernel kernel;
    private final ObjectManager manager;
    private final String className;
    private Object obj = null;

    ObjectProxy(Kernel kernel, ObjectManager manager, String className) {
        this.kernel = kernel;
        this.manager = manager;
        this.className = className;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = getLatest(proxy);
        if (obj == null) {
            throw new RuntimeException("Failed to load class '" + className + "'.");
        }
        try {
            return method.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @SuppressWarnings({ "unchecked" })
    Object getLatest(Object proxy) throws IllegalAccessException, InstantiationException {
        Class mgrClass = manager.loadClass(className);
        if (mgrClass != null && (obj == null || obj.getClass() != mgrClass)) {
            Object reloadObj = null;
            if (obj instanceof LoadListener) {
                reloadObj = ((LoadListener)obj).onUnload();
            }
            obj = mgrClass.newInstance();
            if (obj instanceof CreateListener) {
                ((CreateListener)obj).onCreate(kernel, proxy);
            }
            if (obj instanceof LoadListener) {
                ((LoadListener)obj).onLoad(reloadObj);
            }
        }
        return obj;
    }
}