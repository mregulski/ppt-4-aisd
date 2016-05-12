package reshi.common;

import java.io.PrintStream;

/**
 * @author Marcin Regulski on 09.05.2016.
 */
public class BasicLogger {
    PrintStream out;

    public BasicLogger(PrintStream outStream) {
        this.out = outStream;
    }

    public void logln(Object... x) {
        log(null, null, x);
    }

    public void log(String sep, String end, Object... x) {
        sep = sep != null ? sep : " ";
        end = end != null ? end : "\n";
        StringBuilder msg = new StringBuilder();
        if (x != null) {
            for (Object aX : x) {
                msg.append(aX.toString());
                msg.append(sep);
            }
        }
        msg.append(end);
        out.print(msg);
        out.flush();
    }


}
