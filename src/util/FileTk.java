package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class FileTk {

    public static String readFile(String fileName) throws IOException {
        return readFile(FileSystems.getDefault().getPath(fileName));
    }

    public static String readFile(Path filePath) throws IOException {
        StringBuilder ret = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath)));
        for (String str; (str = in.readLine()) != null; ) {
            ret.append(str).append("\r\n");
        }
        in.close();
        return ret.toString();
    }

    public static long getLastModified(Path filePath) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
        return attrs.lastModifiedTime().toMillis();
    }
}
