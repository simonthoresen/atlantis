package kernel.obj;

public interface LoadListener<T> {

    public void onLoad(T obj);

    public T onUnload();
    
}
