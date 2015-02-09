package kernel.tools;

/**
 *
 * @author WillhelmK
 */
import java.util.Random;

public abstract class Rand {
    
    private static Random rand;

    public static void init() {
        rand = new Random();
    }

    /**
     * 
     * @param max
     * @return 
     */
    public static int randInt(int max) {
        return rand.nextInt(max);
    }
    
}
