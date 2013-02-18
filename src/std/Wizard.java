package std;

import kernel.Kernel;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Wizard extends Player {

    private Path currentPath;

    public Wizard(Kernel kernel, String name) {
        super(kernel, name);
        addCommandPackage("cmd.w");
        setCurrentPath(FileSystems.getDefault().getPath("."));
    }

    public Path getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(Path path) {
        currentPath = path.toAbsolutePath().normalize();
    }
}
