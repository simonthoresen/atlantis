package kernel.obj;

import util.CompilationFailedException;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ObjectCompiler {

    private final ObjectManager manager;
    private final Path sourcePath;
    private final String separator;

    public ObjectCompiler(ObjectManager manager) {
        this.manager = manager;

        FileSystem fileSystem = FileSystems.getDefault();
        sourcePath = fileSystem.getPath("src").toAbsolutePath();
        separator = fileSystem.getSeparator();
    }

    public String compile(Path sourcePath) throws CompilationFailedException, FileNotFoundException {
        if (!Files.exists(sourcePath)) {
            throw new FileNotFoundException(sourcePath.toString());
        }
        if (!sourcePath.startsWith(this.sourcePath)) {
            throw new IllegalArgumentException("File '" + sourcePath + "' is not in source path.");
        }
        if (!sourcePath.getFileName().toString().endsWith(".java")) {
            throw new IllegalArgumentException("File '" + sourcePath + "' is not a java file.");
        }
        javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        List<String> options = Arrays.asList("-cp", "src;out",
                                             "-d", "out");
        List<String> javaFileName = Arrays.asList(sourcePath.toString());
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        StringWriter out = new StringWriter();
        JavaCompiler.CompilationTask task = compiler.getTask(out, null, null, options, null,
                                                             fileManager.getJavaFileObjectsFromStrings(javaFileName));
        if (!task.call()) {
            throw new CompilationFailedException(out.toString());
        }
        String className = this.sourcePath.relativize(sourcePath).toString();
        className = className.substring(0, className.length() - 5);
        className = className.replace(separator, ".");
        if (manager != null) {
            manager.reloadClass(className);
        }
        return className;
    }
}
