package kernel.obj;

import kernel.Kernel;

public interface CreateListener<T> {

    public void onCreate(Kernel kernel, T self);
}
