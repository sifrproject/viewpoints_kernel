
package kernel.knowledgeActivity.semanticNeighbourhood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import kernel.knowledgeActivity.viewpointsInterpretation.Perspective;
import kernel.knowledgeGraph.nodes.Node;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ViewpointPolarity;

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
public class ShortestPathNeighbourhood2 extends Neighbourhood {
    
    private LinkedHashSet<Resource> tmp;
    private LinkedList<Resource> todo;
    private LinkedList<Resource> visitedObjects;
    private LinkedList<ConnectedViewpoint> visitedViewpoints;
    private ArrayList<ConnectedViewpoint> viewpointBuffer;
    private final Comparator knowledgeObjectComparator;
    private Perspective perspective;
    
    /**
     * 
     * @param perspective 
     */
    public ShortestPathNeighbourhood2(Perspective perspective) {
        super("Shortest path based semantic neighbourhood");
        tmp = new LinkedHashSet<>();
        visitedObjects = new LinkedList<>();
        viewpointBuffer = new ArrayList<>();
        todo = new LinkedList<>();
        visitedViewpoints = new LinkedList<>();
        
        this.perspective = perspective;
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
        HashSet<Resource> positiveProcessed = processPositive(origin, m);
        
        for(Resource resource : positiveProcessed) {
            viewpointBuffer.clear();
            
            for(Node n_i : origin.getNeighbours()) {
                ConnectedViewpoint v_i = (ConnectedViewpoint) n_i;
                
                Resource r_j;
                
                if(v_i.getO1() == origin)
                    r_j = v_i.getO2();
                else
                    r_j = v_i.getO1();
                
                if(r_j == resource && v_i.getPolarity() == ViewpointPolarity.NEGATIVE)
                    viewpointBuffer.add(v_i);
            }
            
            resource.setWeight(resource.getWeight() + (1.0f / perspective.aggregateVewpoints(viewpointBuffer)));
        }
        
        return positiveProcessed;
    }
    
    /**
     * 
     * @param origin
     * @param m
     * @return 
     */
    public HashSet<Resource> processPositive(Resource origin, float m) {
        float sum, newWeight;
        HashSet<Resource> result = new HashSet<>();
        result.add(origin);
        boolean good;
        tmp.clear();
        
        init(origin);

        todo.addLast(origin);
        
        while(! todo.isEmpty()) {
            Resource o_i = todo.pollFirst();
            visitedObjects.addLast(o_i);
            
            for(Node n_j : o_i.getNeighbours()) {
                ConnectedViewpoint v_j = (ConnectedViewpoint) n_j;
                
                if(v_j.getPolarity() == ViewpointPolarity.POSITIVE) {
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
                            if(v.getPredecessor() == o_i)
                                viewpointBuffer.add(v);
                        }

                        sum = observer.getPerspective().aggregateVewpoints(viewpointBuffer);
                        viewpointBuffer.clear();

                        if(sum > 0.0f) {
                            newWeight = o_i.getWeight() + (1.0f / sum);

                            if(newWeight < o_k.getWeight() || o_k.getWeight() < 0.0f) {
                                o_k.setWeight(newWeight);
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
        }
        
        return result;
    }
    
    /**
     * 
     * @param origin 
     */
    private void init(Resource origin) {
        while(! visitedObjects.isEmpty()) {
            Resource o_i = visitedObjects.pollFirst();
            o_i.setWeight(-1.0f);
            o_i.getReferentViewpoints().clear();
        }

        while(! visitedViewpoints.isEmpty())
            visitedViewpoints.pollFirst().setPredecessor(null);
        
        origin.setWeight(0.0f);
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
    
}
