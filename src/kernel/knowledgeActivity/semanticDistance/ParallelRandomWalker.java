package kernel.knowledgeActivity.semanticDistance;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import kernel.knowledgeActivity.viewpointsInterpretation.Perspective;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.tools.Rand;

/**
 *
 * @author WillhelmK
 */
public class ParallelRandomWalker extends RecursiveTask<Integer> {

    private Resource currentResource;
    private Resource destination;
    private int walkSize;
    private Perspective perspective;

    public ParallelRandomWalker(Resource currentResource, Resource destination, int walkSize, Perspective perspective) {
        this.currentResource = currentResource;
        this.destination = destination;
        this.walkSize = walkSize;
        this.perspective = perspective;
    }
    
    @Override
    protected Integer compute() {
        int tmp, tmpP, rand;
        
        for(int i = 0; i < walkSize; i++) {
            if(currentResource == destination)
                return 1;
            
            LinkedHashMap<Resource, Integer> statisticalDistribution = evalStatisticalDistribution(
                    perspective.evalSynapses(currentResource)
            );
            tmp = 0;
            rand = Rand.randInt(100);
            
            for(Map.Entry<Resource, Integer> e : statisticalDistribution.entrySet()) {
                tmpP = tmp + e.getValue();
                if(rand < tmpP && rand >= tmp)
                    currentResource = e.getKey();
                
                tmp = tmpP;
            }
        }
        
        return 0;
    }
    
    private LinkedHashMap<Resource, Integer> evalStatisticalDistribution(LinkedHashMap<Resource, Float> synapses) {
        LinkedHashMap<Resource, Integer> distribution = new LinkedHashMap<>();
        float total = 0.0f;
        
        for(Float v : synapses.values())
            total += v;
        
        float invTotal = 100.0f / total;
        
        for(Map.Entry<Resource, Float> e : synapses.entrySet())
            distribution.put(e.getKey(), (int) (e.getValue() * invTotal));
        
        return distribution;
    }

}
