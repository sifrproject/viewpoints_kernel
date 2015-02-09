package kernel.tools.viz;

import java.awt.Color;
import java.awt.Paint;
import kernel.knowledgeGraph.nodes.Node;
import org.apache.commons.collections15.Transformer;
/**
 *
 * @author WillhelmK
 */
public class VertexColor implements Transformer<Node, Paint> {
    
    @Override
    public Paint transform(Node v) {
        return Color.BLACK;
    }
    
}
