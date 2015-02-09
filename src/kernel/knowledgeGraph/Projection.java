package kernel.knowledgeGraph;

import edu.uci.ics.jung.graph.SparseGraph;
import java.util.ArrayList;
import kernel.knowledgeActivity.semanticNeighbourhood.ShortestPathNeighbourhood;
import kernel.knowledgeGraph.nodes.Edge;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;

/**
 *
 * @author WillhelmK
 */
public class Projection extends SparseGraph<Resource, Edge> {
    
    protected ArrayList<Resource> O;
    protected ArrayList<Edge> E;
    protected Class[] types;
    protected KnowledgeGraph KG;
    
    public Projection() {
    }

    public Projection(KnowledgeGraph KG, float threshold, float m, Class ... types) {
        this.types = types;
        this.KG = KG;
        ShortestPathNeighbourhood neighbourhoodMethod = new ShortestPathNeighbourhood(KG);
        
        for(Resource o : KG.getO()) {
            for(Class c : types) {
                if(c.isInstance(o))
                    O.add(o);
            }
        }
        
        float tmpDist;
        
        for(Resource o_i : O) {
            
            for(Resource o_j : neighbourhoodMethod.process(o_i, m)) {

                if(isAGoodType(o_j) && o_i != o_j) {
                    Resource o_k = o_j;
                    tmpDist = 0.0f;

                    do {
                        Resource bestpred = o_k.getBestPred();
                        tmpDist += (o_k.getWeight() - bestpred.getWeight());
                        o_k = bestpred;
                    } while(! isAGoodType(o_k));

                    if(tmpDist <= threshold && containsEdge(o_k, o_j) == null)
                        addEdge(new Edge(o_k, o_j, tmpDist), o_k, o_j);
                }     
                
            }
            
        }
    }
    
    private boolean isAGoodType(Resource o) {     
        for(Class c : types) {
            if(c.isInstance(o))
                return true;
        }
        
        return false;
    }
    
    private Edge containsEdge(Resource n1, Resource n2) {
        for(Edge e : getEdges()) {
            if((e.n1 == n1 && e.n2 == n2) || (e.n2 == n1 && e.n1 == n2))
                return e;
        }
        
        return null;
    }
    
    /*public void cluster(int n) {
        VoltageClusterer<KnowledgeObject, Edge> clusterer = new VoltageClusterer<>(this, n);
        Collection< Set<KnowledgeObject> > clusters = clusterer.cluster(n);
        int i = 0;
        
        Color[] colors = new Color[n];
        for(int c = 0; c < colors.length; c++) {
            colors[c] = new Color(Rand.randInt(255), Rand.randInt(255), Rand.randInt(255));
        }
        
        for(Set<KnowledgeObject> cluster : clusters) {
            
            for(KnowledgeObject o : cluster)
                o.c = colors[i];
            
            i++;
        }
    }*/
    
}
