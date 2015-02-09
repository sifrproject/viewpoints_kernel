package kernel.tools.viz;

import java.awt.Color;
import java.awt.Paint;
import kernel.knowledgeGraph.nodes.Edge;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author WillhelmK
 */
public class EdgeColor implements Transformer<Edge, Paint> {

    @Override
    public Paint transform(Edge e) {
        return Color.BLACK;
    }

}
