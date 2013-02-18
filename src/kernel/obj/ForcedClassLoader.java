package kernel.obj;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ForcedClassLoader extends ClassLoader {

    private final String className;
    private final Class classObj;

    public ForcedClassLoader(String className) throws IOException {
        this.className = className;

        Path classPath = FileSystems.getDefault().getPath("out/" + className.replace(".", "/") + ".class");
        InputStream in = Files.newInputStream(classPath);
        byte[] buf = new byte[in.available()];
        if (in.read(buf) != buf.length) {
            throw new IOException("Failed to read class from file '" + classPath + "'.");
        }
        classObj = defineClass(className, buf, 0, buf.length);
        System.err.println("Loaded class '" + className + "'.");
    }

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        return this.className.equals(className) ? classObj : super.loadClass(className);
    }
}