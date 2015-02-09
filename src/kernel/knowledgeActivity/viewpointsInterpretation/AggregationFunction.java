package kernel.knowledgeActivity.viewpointsInterpretation;

import java.util.Collection;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author WillhelmK
 */
public interface AggregationFunction {

    /**
     * 
     * @param dataSet
     * @param perspective
     * @return 
     */
    public float aggregate(Collection<ConnectedViewpoint> dataSet, Perspective perspective);
    
}
