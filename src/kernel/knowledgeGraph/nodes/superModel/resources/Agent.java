package kernel.knowledgeGraph.nodes.superModel.resources;

import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeActivity.viewpointsInterpretation.DefaultAggregationFunction;
import kernel.knowledgeActivity.viewpointsInterpretation.DefaultInterpretationFunction;
import kernel.knowledgeActivity.viewpointsInterpretation.Perspective;
import kernel.knowledgeGraph.KnowledgeGraph;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public abstract class Agent extends RepresentableResource {
    
    private Perspective perspective;

    public Agent() {
        perspective = new Perspective(new DefaultInterpretationFunction(),
                                      new DefaultAggregationFunction());
    }
    
    public Agent(int id) {
        super(id);
        perspective = new Perspective(new DefaultInterpretationFunction(),
                                      new DefaultAggregationFunction());
    }
    
    public Agent(String name) {
        super(name);
        perspective = new Perspective(new DefaultInterpretationFunction(),
                                      new DefaultAggregationFunction());
    }
    
    public void emitViewpoint(ConnectedViewpoint v, KnowledgeGraph KG) {
        perspective.addViewpoint(v);
        KG.addViewpoint(v);
    }

    public Perspective getPerspective() {
        return perspective;
    }

    public void setPerspective(Perspective perspective) {
        this.perspective = perspective;
    }
    
}
