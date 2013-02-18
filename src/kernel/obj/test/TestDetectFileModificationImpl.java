package kernel.obj.test;
public class TestDetectFileModificationImpl implements kernel.obj.LoadListener<Object>, TestObject {
    Object ctx = null;
    public int getValue() {
        return 3;
    }
    public Object getContext() {
        return ctx;
    }
    public void setContext(Object ctx) {
        this.ctx = ctx;
    }
    public void onLoad(Object obj) {
        ctx = obj;
    }
    public Object onUnload() {
        return ctx;
    }
    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }
}
