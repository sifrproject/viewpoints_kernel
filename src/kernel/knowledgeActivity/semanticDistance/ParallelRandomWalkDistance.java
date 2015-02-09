package kernel.knowledgeActivity.semanticDistance;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.tools.Rand;

/**
 *
 * @author WillhelmK
 */
public class ParallelRandomWalkDistance extends Distance {

    private ForkJoinPool pool;
    private int startingThreads;
    private float invStartingThreads;
    private ArrayList<ParallelRandomWalker> walkers;
    
    public ParallelRandomWalkDistance(String name, int startingThreads) {
        super(name);
        this.startingThreads = startingThreads;
        invStartingThreads = 1.0f / (float) startingThreads;
        walkers = new ArrayList<>();
        Rand.init();
    }

    public float process(Resource origin, Resource destination) {
        float result = 0.0f;
        
        for(int i = 0; i < startingThreads; i++) {
            ParallelRandomWalker rw = new ParallelRandomWalker(origin, destination, i, observer.getPerspective());
            walkers.add(rw);
            rw.fork();
        }
        
        for(ParallelRandomWalker walker : walkers)
            result += walker.join();
        
        return result * invStartingThreads;
    }
    
}
