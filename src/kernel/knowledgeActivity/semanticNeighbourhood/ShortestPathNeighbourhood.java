
package kernel.knowledgeActivity.semanticNeighbourhood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import kernel.knowledgeGraph.KnowledgeGraph;
import kernel.knowledgeGraph.nodes.Node;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author SURROCA Guillaume
 * 
 * Méthode de calcul de voisinage sémantique basé sur le plus court chemin entre deux 
 * objets. Les objets renvoyés par cet algorithme qui s'inspire de l'algorithme de Dijkstra
 * appartiennent à tous les chemins de longueur inférieure ou égale à m partant de l'objet
 * recherché.
 * 
 */
public class ShortestPathNeighbourhood extends Neighbourhood {
    
    private LinkedHashSet<Resource> tmp;
    private LinkedList<Resource> todo;
    private LinkedList<Resource> visitedObjects;
    private LinkedList<ConnectedViewpoint> visitedViewpoints;
    private ArrayList<ConnectedViewpoint> viewpointBuffer;
    private final Comparator knowledgeObjectComparator;
    private KnowledgeGraph KG;
    
    public ShortestPathNeighbourhood(KnowledgeGraph KG) {
        super("Shortest path based semantic neighbourhood");
        
        todo = new LinkedList<>();
       // visitedObjects = new LinkedList<>();
        visitedViewpoints = new LinkedList<>();
        tmp = new LinkedHashSet<>();
        viewpointBuffer = new ArrayList<>();
        
        this.KG = KG;
        
        knowledgeObjectComparator = new Comparator<Resource>() {

            @Override
            public int compare(Resource o1, Resource o2) {
                return o1.getWeight()< o2.getWeight() ? -1 : o1.getWeight() == o2.getWeight() ? 0 : 1;
            }
            
        };
    }
    
    /**
     * 
     * @param origin Objet recherché
     * @param m Distance max
     * @return 
     */
    public HashSet<Resource> process(Resource origin, float m) {
        //double start = System.nanoTime();
        
        float sum, newWeight;
        HashSet<Resource> result = new HashSet<>();
        result.add(origin);
        boolean good;
        tmp.clear();
        
        init(origin);

        todo.addLast(origin);
        
        while(! todo.isEmpty()) {
            Resource o_i = todo.pollFirst();
            //visitedObjects.addLast(o_i);
            
            for(Node n_j : o_i.getNeighbours()) {
                ConnectedViewpoint v_j = (ConnectedViewpoint) n_j;
                
                good = true;
                
                for(ConnectedViewpoint v : o_i.getReferentViewpoints()) {
                    if(v == v_j && v_j.getPredecessor() == o_i) {
                       good = false;
                       break;
                    }
                }
                
                if(good) {
                    if(v_j.getPredecessor() == null) {
                        v_j.setPredecessor(o_i);
                        visitedViewpoints.addLast(v_j);
                    }

                    Resource o_k;
                    if(o_i == v_j.getO1())
                        o_k = v_j.getO2();
                    else
                        o_k = v_j.getO1();

                    o_k.addReferentViewpoint(v_j);
                    for(ConnectedViewpoint v : o_k.getReferentViewpoints()) {
                        //if(v.getPredecessor() == o_i)
                        if(v.getO1() == o_i && v.getO2() == o_k || v.getO2() == o_i && v.getO1() == o_k)
                            viewpointBuffer.add(v);
                    }
                    
                    sum = observer.getPerspective().aggregateVewpoints(viewpointBuffer);
                    viewpointBuffer.clear();

                    if(sum > 0.0f) {
                        newWeight = o_i.getWeight() + (1.0f / sum);

                        if(newWeight < o_k.getWeight() || o_k.getWeight() < 0.0f) {
                            o_k.setWeight(newWeight);
                            //visitedObjects.add(o_k);
                            v_j.setPredecessor(o_i);

                            if(o_k.getWeight() <= m) {
                                result.add(o_k);
                                
                                if(o_k.getWeight() < m && tmp.add(o_k))
                                    todo.addLast(o_k);
                            }
                        }
                    }
                }
            }
        }
        
        /*double tmicro = System.nanoTime() - start;
        if(tmicro > 1000000.0)
            System.out.println(this + " executed in " + DecFormat.format(tmicro * 0.000001) + " ms");
        else
            System.out.println(this + " executed in " + DecFormat.format(tmicro * 0.001) + " µs");*/
        
        return result;
    }
    
    /**
     * 
     * @param origin
     * @param m
     * @return 
     */
    public ArrayList<Resource> sortedProcess(Resource origin, float m) {
        ArrayList<Resource> neighbourhood = new ArrayList<>();
        neighbourhood.addAll(process(origin, m));
        Collections.sort(neighbourhood, knowledgeObjectComparator);
        return neighbourhood;
    }
     
    /**
     * 
     * @param origin 
     */
    private void init(Resource origin) {
        for(Resource r : KG.getO()) {
            r.setWeight(-1.0f);
            r.getReferentViewpoints().clear();
        }
        
        /*while(! visitedObjects.isEmpty()) {
            Resource o_i = visitedObjects.pollFirst();
            o_i.setWeight(-1.0f);
            o_i.getReferentViewpoints().clear();
        }
        */
        
        while(! visitedViewpoints.isEmpty())
            visitedViewpoints.pollFirst().setPredecessor(null);
        
        origin.setWeight(0.0f);
    }
    
}
