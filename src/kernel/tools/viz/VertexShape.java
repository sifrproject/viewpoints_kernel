package kernel.tools.viz;

import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;
import java.awt.Shape;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author WillhelmK
 */
class VertexShape<V> extends AbstractVertexShapeTransformer<V> implements Transformer<V, Shape> {
    
    public static int VIEWPOINT_SIZE = 5;
    public static int OBJECT_SIZE = 20;
    
    @Override
    public Shape transform(V v) {
        return factory.getEllipse(v);
    }
    
}
