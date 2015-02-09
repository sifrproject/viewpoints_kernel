package kernel.knowledgeActivity.semanticNeighbourhood;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.knowledgeGraph.nodes.Node;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;
import kernel.tools.Rand;
import kernel.tools.tree.Tree;
import kernel.tools.tree.TreeNode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import viewpointsprototype2.kernel.tools.DecFormat;


/**
 *
 * @author SURROCA Guillaume
 * 
 * Méthode de calcul de voisinage sémantique basé sur le modèle du marcheur aléatoire.
 * 
 * 
 * Ref : Xu, Y., James, K. L., & Pedersen, T. (2011). Random Walk on WordNet to Measure 
 * Lexical Semantic Relatedness.
 * 
 */
public class RandomWalkNeighbourhood extends Neighbourhood {
    
    private LinkedList<Resource> visitedObjects;

    public RandomWalkNeighbourhood() {
        super("Random walk based semantic neighbourhood");
        
        visitedObjects = new LinkedList<>();
        Rand.init();
    }

    /**
     * 
     * Méthode à appeler pour calculer le voisinage. Elle renvoie une liste de noeuds
     * et les pourcentage de chance d'y arriver avec une marche aléatoire en partant
     * d'un noeud central de départ.
     * - Beta           : [0..100]  Pourcentage que le marcheur aléatoire revienne au
     * noeud de départ.
     * - Max iterations : |N        Nombre maximum d'itérations.
     * - Seuil          : [0..100]  Pourcentage minimum pour faire partie des résultats.  
     * 
     * @param origin
     * @param beta
     * @param maxIt
     * @param seuil
     * @return 
     */
    public Map<Resource, Float> process(Resource origin, int beta, int maxIt, int seuil) {
        double start = System.nanoTime();
        
        /**
         * Ré-initialisation des propriétés d'objets modifiés
         */
        if(! visitedObjects.isEmpty()) {
            while(! visitedObjects.isEmpty())
                visitedObjects.pollFirst().setWeight(0.0f);
        }
        
        ArrayList<Resource> neighbourhood = new ArrayList<>();
        int tmp, tmpP, rand;
        
        Tree< ImmutablePair<Resource, Integer> > traversalTree = new Tree<>(
                new TreeNode<>(
                        null, 
                        new ImmutablePair<>(
                                origin, 
                                100)
                )
        );
        traversalTree.getRoot().setParent(traversalTree.getRoot());
        TreeNode< ImmutablePair<Resource, Integer> > currentNode = traversalTree.getRoot();
        TreeNode< ImmutablePair<Resource, Integer> > nextNode = null;
        
        
        for(int it = 0; it < maxIt; it++) {
        
            do {
                
                rand = Rand.randInt(100);
                
                /**
                 * Distribution statistique à calculer. Evaluation des synapses à calculer. 
                 * Stockage des résultats dans un arbre d'exploration pour ne pas avoir à 
                 * recalculer tout ça.
                 */
                if(currentNode.getChildren().isEmpty()) {
                    LinkedHashMap<Resource, Integer> statisticalDistribution = evalStatisticalDistribution( 
                            evalSynapses(currentNode.getContent().getKey(),
                                         ((TreeNode< ImmutablePair<Resource, Integer> >) (currentNode.getParent())).getContent().getKey()
                            )                                                       
                    );
                    
                    tmp = 0;

                    for(Entry<Resource, Integer> e : statisticalDistribution.entrySet()) {
                        TreeNode< ImmutablePair<Resource, Integer> > tmpNode = new TreeNode<>(
                                currentNode, 
                                new ImmutablePair<>(e.getKey(), e.getValue())
                        );
                        currentNode.appendChild(tmpNode);

                        tmpP = tmp + e.getValue();
                        if(rand >= tmp && rand < tmpP) {
                            nextNode = tmpNode;
                        }

                        tmp = tmpP;
                    }
                }
                
                /**
                 * La distribution statistique a précédemment été calculée et stocké dans
                 * l'arbre d'exploration du graphe. Il suffit de récupérer tout ça.
                 */
                else {
                    tmp = 0;
                    
                    for(TreeNode< ImmutablePair<Resource, Integer> > n : currentNode.getChildren()) {
                        tmpP = tmp + n.getContent().getValue();
                        if(rand >= tmp && rand < tmpP) {
                            nextNode = n;
                            break;
                        }
                        
                        tmp = tmpP;
                    }
                }
                
                if(nextNode == traversalTree.getRoot())
                    break;
                
                currentNode = nextNode;

            } while(Rand.randInt(100) >= beta);
        
            currentNode = traversalTree.getRoot();
        
        }
        
        /**
         * Une fois que l'arbre d'exploration du graphe a été rempli il ne reste qu'à le
         * transformer en une distribution statistique en parcourant l'arbre en largeur.
         */
        LinkedHashMap<Resource, Float> distribution = new LinkedHashMap<>();
        LinkedList< TreeNode< ImmutablePair<Resource, Integer> > > todo = new LinkedList<>();
        todo.addLast(traversalTree.getRoot());
        
        while(! todo.isEmpty()) {
            TreeNode< ImmutablePair<Resource, Integer> > n = todo.pollFirst();
            TreeNode< ImmutablePair<Resource, Integer> > tmpNode = n;
            TreeNode< ImmutablePair<Resource, Integer> > father;
            Float weight = distribution.get(n.getContent().getKey());
            float acc = 100f;
            
            while(tmpNode != traversalTree.getRoot()) {
                father = tmpNode.getParent();
                acc *= ((float) father.getContent().getValue()) * 0.01f;
                tmpNode = father;
            }
            
            if(weight == null)
                distribution.put(n.getContent().getKey(), acc);
            else
                distribution.put(n.getContent().getKey(), n.getContent().getValue() + acc);
            
            for(TreeNode< ImmutablePair<Resource, Integer> > child : n.getChildren())
                todo.addLast(child);
        }
        
        double tmicro = System.nanoTime() - start;
        if(tmicro > 1000000.0)
            System.out.println(this + " executed in " + DecFormat.format(tmicro * 0.000001) + " ms");
        else
            System.out.println(this + " executed in " + DecFormat.format(tmicro * 0.001) + " µs");
        
        return distribution;
    }
    
    private LinkedHashMap<Resource, Float> evalSynapses(Resource o, Resource exception) {
        LinkedHashMap<Resource, Float> synapses = new LinkedHashMap<>();
        
        if(exception == null) {
            for(Node n_i : o.getNeighbours()) {
                ConnectedViewpoint v_i = (ConnectedViewpoint) n_i;

                Resource o_j;
                if(v_i.getO1() == o)
                    o_j = v_i.getO2();
                else
                    o_j = v_i.getO1();

                /*if(synapses.containsKey(o_j))
                    synapses.put(o_j, synapses.get(o_j) + v_i.getWeight());
                else
                    synapses.put(o_j, v_i.getWeight());*/
            }
        } 
        
        else {
            for(Node n_i : o.getNeighbours()) {
                ConnectedViewpoint v_i = (ConnectedViewpoint) n_i;

                Resource o_j;
                if(v_i.getO1() == o)
                    o_j = v_i.getO2();
                else
                    o_j = v_i.getO1();

                if(o_j != exception) {
                    /*if(synapses.containsKey(o_j))
                        synapses.put(o_j, synapses.get(o_j) + v_i.getWeight());
                    else
                        synapses.put(o_j, v_i.getWeight());*/
                }
            }
        }
        
        return synapses;
    }
    
    private LinkedHashMap<Resource, Integer> evalStatisticalDistribution(LinkedHashMap<Resource, Float> synapses) {
        LinkedHashMap<Resource, Integer> distribution = new LinkedHashMap<>();
        float total = 0.0f;
        
        for(Float v : synapses.values())
            total += v;
        
        float invTotal = 1.0f / total;
        
        for(Entry<Resource, Float> e : synapses.entrySet())
            distribution.put(e.getKey(), (int) (e.getValue() * 100.0f * invTotal));
        
        return distribution;
    }

}