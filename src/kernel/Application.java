package kernel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Application {

    public static void main(String[] argv) {
        /*System.setSecurityManager(new SecurityManager() {

            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof FilePermission && !perm.getName().startsWith("xxx")) {
                    throw new SecurityException("Access to '" + perm.getName() + "' denied.");
                }
            }
        });*/

        Logger root = Logger.getLogger("");
        root.setLevel(Level.ALL);
        root.getHandlers()[0].setFormatter(new Formatter() {

            @SuppressWarnings({ "ThrowableResultOfMethodCallIgnored" })
            @Override
            public String format(LogRecord
                    record) {
                StringBuilder ret = new StringBuilder();

                ret.append(record.getMillis()).append("\t");
                ret.append(record.getLoggerName()).append("\t");
                ret.append(record.getLevel()).append("\t");
                String msg = record.getMessage();
                if (msg != null) {
                    ret.append(record.getMessage());
                }
                Throwable t = record.getThrown();
                if (t != null) {
                    StringWriter out = new StringWriter();
                    t.printStackTrace(new PrintWriter(out));
                    ret.append("\n").append(out.toString());
                }
                ret.append("\n");
                return ret.toString();
            }
        });


        try {
            Kernel kernel = new Kernel();
            kernel.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
