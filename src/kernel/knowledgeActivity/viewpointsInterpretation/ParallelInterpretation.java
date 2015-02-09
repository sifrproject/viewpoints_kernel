package kernel.knowledgeActivity.viewpointsInterpretation;

import java.util.concurrent.RecursiveTask;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author WillhelmK
 */
public class ParallelInterpretation extends RecursiveTask<Float> implements InterpretationFunction {

    private ConnectedViewpoint v;
    private Perspective p;

    public ParallelInterpretation(ConnectedViewpoint v, Perspective p) {
        this.v = v;
        this.p = p;
    }
    
    @Override
    public float giveWeight(ConnectedViewpoint v, Perspective perspective) {
        return perspective.getTypeWeight(v.getType());
    }

    @Override
    protected Float compute() {
        return giveWeight(v, p);
    }

}
