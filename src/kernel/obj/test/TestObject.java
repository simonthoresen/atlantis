package kernel.obj.test;

public interface TestObject {
    
    public int getValue();

    public Object getContext();

    public void setContext(Object ctx);

    public ClassLoader getClassLoader();
}
