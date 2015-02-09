package viewpointsprototype2.kernel.tools;

import java.text.DecimalFormat;

/**
 *
 * @author WillhelmK
 */
public class DecFormat {
    private static DecimalFormat format;
    
    public static void init() {
        format = new DecimalFormat("0.00");
    }
    
    public static String format(float value) {
        return format.format(value);
    }
    
    public static String format(double value) {
        return format.format(value);
    }
}
