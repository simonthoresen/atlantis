package kernel.net;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum TermCap {

    MARK("%^"),
    ESC("\033"),
    CSI(ESC + "["),

    CLS(CSI + ";H" + CSI + "2J"), // clear screen and home cursor
    HOME(CSI + "H"),
    BOLD(CSI + "1m"),             // turn on bold (extra bright) attribute
    INVERSE(CSI + "7m"),          // turn on reverse-video attribute
    BLINK(CSI + "5m"),            // turn on blinking attribute
    END(CSI + "m"),               // turn off all attributes
    UNDERLINE(CSI + "4m"),        // underline character overstrikes

    BLACK(CSI + "30m"),
    RED(CSI + "31m"),
    GREEN(CSI + "32m"),
    YELLOW(CSI + "33m"),
    BLUE(CSI + "34m"),
    MAGENTA(CSI + "35m"),
    CYAN(CSI + "36m"),
    GREY(CSI + "37m"),
    WHITE(CSI + "37m"),

    L_RED(CSI + "1;31m"),
    L_GREEN(CSI + "1;32m"),
    L_YELLOW(CSI + "1;33m"),
    L_BLUE(CSI + "1;34m"),
    L_MAGENTA(CSI + "1;35m"),
    L_CYAN(CSI + "1;36m"),

    B_BLACK(CSI + "40m"),
    B_RED(CSI + "41m"),
    B_GREEN(CSI + "42m"),
    B_YELLOW(CSI + "43m"),
    B_BLUE(CSI + "44m"),
    B_MAGENTA(CSI + "45m"),
    B_CYAN(CSI + "46m"),
    B_GREY(CSI + "47m"),
    B_WHITE(CSI + "47m");

    private static final Logger log = Logger.getLogger(TermCap.class.getName());
    private final String val;

    private TermCap(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }

    public static String move(int col, int row) {
        return "!" + CSI + "%i" + row + ";" + col + "H";
    }

    /**
     * This method replaces all symbolic control sequences in the given string with the actual escape sequence that are
     * recognized by the reading telnet clients. This is used as a filter by {@link kernel.net.Connection} for anything that is to
     * hit the network.
     *
     * @param str The string to encode.
     * @return The encoded string.
     */
    public static String encode(String str) {
        StringBuilder out = new StringBuilder();
        String mark = TermCap.MARK.toString();
        int markLen = mark.length();
        for (int strPos = 0, strLen = str.length(); strPos < strLen;) {
            int from, to;
            if ((from = str.indexOf(mark, strPos)) < 0 ||
                (to = str.indexOf(mark, from + 1)) < 0) {
                out.append(str.substring(strPos));
                break;
            }
            if (from > strPos) {
                out.append(str.substring(strPos, from));
            }
            try {
                out.append(TermCap.valueOf(str.substring(from + markLen, to)));
            } catch (IllegalArgumentException e) {
                log.log(Level.WARNING, null, e);
            }
            strPos = to + markLen;
        }
        return out.toString();
    }
}
