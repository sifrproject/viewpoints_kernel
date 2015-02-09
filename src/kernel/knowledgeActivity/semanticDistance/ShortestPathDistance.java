package kernel.knowledgeActivity.semanticDistance;

import kernel.knowledgeActivity.semanticNeighbourhood.ShortestPathNeighbourhood;
import kernel.knowledgeGraph.KnowledgeGraph;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;

/**
 *
 * @author WillhelmK
 */
public class ShortestPathDistance extends Distance {
    
    private ShortestPathNeighbourhood neighbourhood;

    public ShortestPathDistance(KnowledgeGraph KG) {
        super("Shortest path based semantic distance");
        
        neighbourhood = new ShortestPathNeighbourhood(KG);
    }

    /*
     * @param parameters KnowledgeGraph, Objet 1, Objet 2, distance max
     */
    public Float process(Resource o1, Resource o2, float m) {
        neighbourhood.setObserver(observer);
        neighbourhood.process(o1, m);
        
        return o2.getWeight();
    }
    
    
}
