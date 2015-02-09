package kernel.knowledgeActivity.viewpointsInterpretation;

import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author WillhelmK
 */
public class DefaultInterpretationFunction implements InterpretationFunction {

    /**
     * 
     * @param v
     * @param perspective
     * @return 
     */
    @Override
    public float giveWeight(ConnectedViewpoint v, Perspective perspective) {
        return perspective.getTypeWeight(v.getType());
    }

}
