package kernel.knowledgeActivity.viewpointsInterpretation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author WillhelmK
 */
public class ParallelAggregation implements AggregationFunction {
    
    private ForkJoinPool pool;
    private LinkedList<ParallelInterpretation> threads;

    public ParallelAggregation() {
        pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        threads = new LinkedList<>();
    }
    
    @Override
    public float aggregate(Collection<ConnectedViewpoint> dataSet, Perspective perspective) {
        float synapse = 0.0f;
        
        for(ConnectedViewpoint v : dataSet) {
            ParallelInterpretation interpretation = new ParallelInterpretation(v, perspective);
            threads.add(interpretation);
            interpretation.fork();
        }
        
        for(ParallelInterpretation pI : threads)
            synapse += pI.join();
        
        return synapse;
    }

}
