package kernel.knowledgeActivity.viewpointsInterpretation;

import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author WillhelmK
 */
public interface InterpretationFunction {
    
    /**
     * 
     * @param v
     * @param perspective
     * @return 
     */
    public float giveWeight(ConnectedViewpoint v, Perspective perspective);
    
}
