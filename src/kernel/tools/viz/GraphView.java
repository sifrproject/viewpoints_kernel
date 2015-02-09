package kernel.tools.viz;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.event.MouseEvent;
import java.util.Set;
import kernel.knowledgeGraph.nodes.Edge;
import kernel.knowledgeGraph.nodes.Node;

/**
 *
 * @author surroca
 */
public class GraphView extends VisualizationViewer<Node, Edge> {

    private PluggableGraphMouse pgm;
    
    public GraphView(Layout layout, boolean showEdgesLabels, boolean showVerticeLabel) {
        super(layout);
        
        renderContext.setVertexFillPaintTransformer(new VertexColor());
        renderContext.setVertexShapeTransformer(new VertexShape<Node>());
        if(showVerticeLabel)
            renderContext.setVertexLabelTransformer(new ToStringLabeller<Node>());
        renderContext.setEdgeDrawPaintTransformer(new EdgeColor());
        if(showEdgesLabels)
            renderContext.setEdgeLabelTransformer(new ToStringLabeller());
        renderer.setVertexRenderer(new MultiVertexRenderer<Node, Edge>());
        
        pgm = new PluggableGraphMouse();
        pgm.add(new PickingGraphMousePlugin());
        pgm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON3_MASK));
        pgm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1 / 1.1f, 1.1f));
        setGraphMouse(pgm);
    }
    
    public Set<Edge> getSeldectedEdges() {
        return getPickedEdgeState().getPicked();
    }
    
}
