package kernel.obj.test;

import junit.framework.TestCase;
import kernel.obj.ObjectCompiler;
import kernel.obj.ObjectManager;
import util.CompilationFailedException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ObjectManagerTestCase extends TestCase {

    private final String packageName = this.getClass().getPackage().getName();

    public void testCreate() throws CompilationFailedException, IOException {
        ObjectManager mgr = new ObjectManager(null);
        ObjectCompiler cmp = new ObjectCompiler(mgr);

        String className = packageName + ".TestCreateImpl";
        createTestObjectImpl(cmp, className, 1);

        Object obj = mgr.create(className, TestObject.class);
        assertNotNull(obj);
        assertTrue(obj instanceof TestObject);
        assertEquals(1, ((TestObject)obj).getValue());
    }

    public void testClassLoaderReuse() throws CompilationFailedException, IOException {
        ObjectManager mgr = new ObjectManager(null);
        ObjectCompiler cmp = new ObjectCompiler(mgr);

        String className = packageName + ".TestClassLoaderReuseImpl";
        createTestObjectImpl(cmp, className, 2);

        TestObject obj1 = (TestObject)mgr.create(className, TestObject.class);
        TestObject obj2 = (TestObject)mgr.create(className, TestObject.class);

        assertSame(obj1.getClassLoader(),
                   obj2.getClassLoader());
    }

    public void testDetectRecompile() throws CompilationFailedException, InterruptedException, IOException {
        ObjectManager mgr = new ObjectManager(null);
        ObjectCompiler cmp = new ObjectCompiler(mgr);

        String className = packageName + ".TestDetectRecompileImpl";
        createTestObjectImpl(cmp, className, 3);

        TestObject obj = (TestObject)mgr.create(className, TestObject.class);
        assertEquals(3, obj.getValue());
        assertEquals(3, obj.getValue());

        createTestObjectImpl(cmp, className, 4);

        assertEquals(4, obj.getValue());
        assertEquals(4, obj.getValue());
    }

    public void testLoadListener() throws CompilationFailedException, InterruptedException, IOException {
        ObjectManager mgr = new ObjectManager(null);
        ObjectCompiler cmp = new ObjectCompiler(mgr);

        String className = packageName + ".TestLoadListenerImpl";
        createTestObjectImpl(cmp, className, 5);

        TestObject obj = (TestObject)mgr.create(className, TestObject.class);
        Object ctx = new Object();
        obj.setContext(ctx);
        assertEquals(5, obj.getValue());
        assertSame(ctx, obj.getContext());

        createTestObjectImpl(cmp, className, 6);

        assertEquals(6, obj.getValue());
        assertSame(ctx, obj.getContext());
    }

    private void createTestObjectImpl(ObjectCompiler compiler, String className, int val)
            throws CompilationFailedException, IOException {
        Path filePath =
                FileSystems.getDefault().getPath("src/" + className.replace(".", "/") + ".java").toAbsolutePath();
        PrintWriter out = new PrintWriter(Files.newOutputStream(filePath,
                                                                StandardOpenOption.CREATE,
                                                                StandardOpenOption.TRUNCATE_EXISTING,
                                                                StandardOpenOption.WRITE));
        out.println("package " + packageName + ";");
        out.println("public class " + className.substring(className.lastIndexOf(".") + 1) +
                    " implements kernel.obj.LoadListener<Object>, TestObject {");
        out.println("    Object ctx = null;");
        out.println("    public int getValue() {");
        out.println("        return " + val + ";");
        out.println("    }");
        out.println("    public Object getContext() {");
        out.println("        return ctx;");
        out.println("    }");
        out.println("    public void setContext(Object ctx) {");
        out.println("        this.ctx = ctx;");
        out.println("    }");
        out.println("    public void onLoad(Object obj) {");
        out.println("        ctx = obj;");
        out.println("    }");
        out.println("    public Object onUnload() {");
        out.println("        return ctx;");
        out.println("    }");
        out.println("    public ClassLoader getClassLoader() {");
        out.println("        return getClass().getClassLoader();");
        out.println("    }");
        out.println("}");
        out.close();

        compiler.compile(filePath);
        Files.delete(filePath);
    }
}
