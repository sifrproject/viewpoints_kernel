package kernel.knowledgeActivity.viewpointsInterpretation;

import java.util.Collection;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author WillhelmK
 */
public class DefaultAggregationFunction implements AggregationFunction {

    /**
     * 
     * @param dataSet
     * @param perspective
     * @return 
     */
    @Override
    public float aggregate(Collection<ConnectedViewpoint> dataSet, Perspective perspective) {
        float result = 0.0f;
        
        for(ConnectedViewpoint v : dataSet)
            result += perspective.giveWeight(v);
        
        return result;
    }

}
