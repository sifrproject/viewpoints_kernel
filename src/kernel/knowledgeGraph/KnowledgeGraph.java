package kernel.knowledgeGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeActivity.semanticNeighbourhood.ShortestPathNeighbourhood;
import kernel.knowledgeGraph.nodes.Edge;
import kernel.knowledgeGraph.nodes.Node;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;
import kernel.tools.LevenshteinDistance;
import kernel.tools.N4jEdgeType;
import kernel.tools.Rand;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import viewpointsprototype2.kernel.tools.DecFormat;


/**
 * Classe du graphe de connaissance. Donne un ensemble de méthodes pour 1/ modidifier l'état
 * du graphe de connaissance en ajoutant/retirant/modifiant les Viewpoints et KnowledgeObjects
 * et 2/ exploiter le graphe de connaissance en calculant le voisinage sémantique d'un KnowledgeObject
 * et la distance entre deux KnowledgeObjects.
 * 
 * @author WillhelmK
 */
@XmlRootElement
public class KnowledgeGraph {
    
    private HashSet<Class> managedTypes;
    private ConcurrentHashMap<Class, HashSet<Resource>> O;
    private ArrayList<ConnectedViewpoint> viewpoints;
    private ConcurrentHashMap<String, Resource> O_byLabels;
    private ConcurrentHashMap<Integer, Resource> O_byID;
    private CopyOnWriteArrayList<Resource> resources;
    
    private GraphDatabaseService dbService;
    
    public static boolean USES_NEO4J = false;
    
    /**
     * 
     */
    public KnowledgeGraph() {
        super();
        
        managedTypes = new HashSet<>();
        O = new ConcurrentHashMap<>();
        viewpoints = new ArrayList<>();
        O_byLabels = new ConcurrentHashMap<>();
        O_byID = new ConcurrentHashMap<>();
        resources = new CopyOnWriteArrayList<>();
        
        Rand.init();
        DecFormat.init();
    }
    
    /**
     * 
     * @param dbService
     */
    public KnowledgeGraph(GraphDatabaseService dbService) {
        super();
        KnowledgeGraph.USES_NEO4J = true;
        
        managedTypes = new HashSet<>();
        O = new ConcurrentHashMap<>();
        viewpoints = new ArrayList<>();
        O_byLabels = new ConcurrentHashMap<>();
        O_byID = new ConcurrentHashMap<>();
        resources = new CopyOnWriteArrayList<>();
        
        Rand.init();
        DecFormat.init();
        
        this.dbService = dbService;
        registerShutdownHook(dbService);
    }
    
    /**
     * 
     * @param constraints
     *          {o1 | o2 | emitter}ID : long
     *          {o1 | o2 | emitter}Label : String
     *          type : String
     * @return 
     */
    public ArrayList<ConnectedViewpoint> getViewpoints(LinkedHashMap<String, Object> constraints) {
        ArrayList<ConnectedViewpoint> result = new ArrayList<>();
        boolean ok = true;
        
        for(ConnectedViewpoint v : viewpoints) {
            for(Entry<String, Object> constraint : constraints.entrySet()) {
                switch(constraint.getKey()) {
                    case "emitterID":
                        if(v.getEmitter().getId() != (long) constraint.getValue())
                            ok = false;
                        break;
                        
                    case "emitterLabel":
                        if(! v.getEmitter().getLabel().equalsIgnoreCase((String) constraint.getValue()))
                            ok = false;
                        break;
                        
                    case "o1ID":
                        if(v.getO1().getId() != (long) constraint.getValue())
                            ok = false;
                        break;
                        
                    case "o1Label":
                        if(! v.getO1().getLabel().equalsIgnoreCase((String) constraint.getValue()))
                            ok = false;
                        break;
                        
                    case "o2ID":
                        if(v.getO2().getId() != (long) constraint.getValue())
                            ok = false;
                        break;
                        
                    case "o2Label":
                        if(! v.getO2().getLabel().equalsIgnoreCase((String) constraint.getValue()))
                            ok = false;
                        break;
                        
                    case "type":
                        if(! v.getType().getSimpleName().equals((String) constraint.getValue()))
                            ok = false;
                        break;
                }
            }
            
            if(ok) {
                result.add(v);
                ok = true;
            }
        }
        
        return result;
    }
    
    /**
     * 
     * @param label
     * @param threshold
     * @return 
     */
    public ArrayList<Resource> getLexicallyCloseObjects(String label, double threshold) {
        ArrayList<Resource> result = new ArrayList<>();
        
        for(String s : O_byLabels.keySet())
            if(LevenshteinDistance.similarity(s, label) <= threshold)
                result.add(getNamedObject(s));
        
        return result;
    }
    
    /**
     * 
     */
    public void shutdown() {
        dbService.shutdown();
    }
    
    /**
     * 
     * @param dbService 
     */
    private static void registerShutdownHook(final GraphDatabaseService dbService) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                dbService.shutdown();
            }
        });
    }
    
    /**
     * Permet de récupérer l'ensemble des knowledge objects
     * 
     * @return 
     */
    public Collection<Resource> getO() {
        return resources;
    }
    
    /**
     * 
     * @param a
     * @param o1
     * @param o2
     * @param list
     * @return 
     */
    public static boolean containsViewpoint(Agent a, Resource o1, Resource o2, ArrayList<ConnectedViewpoint> list) {
        for(ConnectedViewpoint v : list)
            if(v.getEmitter() == a && ( (v.getO1() == o1 && v.getO2() == o2) || (v.getO1() == o2 && v.getO2() == o1) ))
                return true;
        
        return false;
    }
    
    /**
     * 
     * @param v_
     * @param list
     * @return 
     */
    public static boolean containsViewpoint(ConnectedViewpoint v_, ArrayList<ConnectedViewpoint> list) {
        for(ConnectedViewpoint v : list)
            if(v.getEmitter() == v_.getEmitter() && ( (v.getO1() == v_.getO1() && v.getO2() == v_.getO2()) || (v.getO1() == v_.getO2() && v.getO2() == v_.getO1()) ))
                return true;
        
        return false;
    }
    
    /**
     * 
     * @param vType
     * @return 
     */
    public ArrayList<ConnectedViewpoint> getViewpoints(Class vType) {
        ArrayList<ConnectedViewpoint> result = new ArrayList<>();
        
        for(ConnectedViewpoint v : viewpoints) {
            if(vType.isInstance(v))
                result.add(v);
        }
        
        return result;
    }
    
    /**
     * Plus court chemin entre deux objets selon l'algorithme de dijkstra.
     * 
     * @param origin
     * @param destination
     * @param m
     * @return Plus cout 
     * 
     */
    public Collection<Edge> shortestPath(Resource origin, Resource destination, float m) {
        ShortestPathNeighbourhood neighbourhood = new ShortestPathNeighbourhood(this);
        Set<Resource> tmp = neighbourhood.process(origin, m);
        
        if(tmp.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "There is no path from you (" + origin.getLabel() + ") to " + destination.getLabel() + ".");
            return new ArrayList<>();
        }
        
        else {
            ArrayList<Edge> path = new ArrayList();
            Resource o = destination;
            
            while(o != origin) {
                Resource best = null;
                ConnectedViewpoint vBest = null;
                float minDist = Float.MAX_VALUE;
                
                for(Node v : o.getNeighbours()) {
                    Resource o2 = ((ConnectedViewpoint) v).getPredecessor();
                    if(o2 != o && o2.getWeight() < minDist) {
                        best = o2;
                        vBest = (ConnectedViewpoint) v;
                    }
                }
                
                path.add(new Edge<Node, String>(o, best, vBest.getClass().getSimpleName()));
                o = best;
            }
            
            return path;
        }
    }
    
    /**
     * 
     * @param o 
     */
    public void removeKnowledgeObject(Resource o) {
        for(ConnectedViewpoint v : viewpoints) {
            if(v.getO1() == o || v.getO2() == o)
                viewpoints.remove(v);
        }
        
        O_byID.remove(o.getId());
        O_byLabels.remove(o.getLabel());
        
        O.get(o.getClass()).remove(o);
    }
    
    /**
     * Ajoute un objet
     * 
     * @param o 
     */
    public void addResource(Node o) {
        if(o != null && o instanceof Resource) {
            managedTypes.add(o.getClass());
            O_byLabels.put(o.getLabel(), (Resource) o);
            O_byID.put(o.getId(), (Resource) o);
            resources.add((Resource) o);
            
            if(! O.containsKey(o.getClass()))
                O.put(o.getClass(), new HashSet<Resource>());
            
            O.get(o.getClass()).add((Resource) o);
            
            if(USES_NEO4J) {
                try(Transaction knowledgeObjectCreation = dbService.beginTx()) {
                    org.neo4j.graphdb.Node n4jNode = dbService.createNode();
                    n4jNode.setProperty("type", o.getClass().getSimpleName());
                    n4jNode.setProperty("name", o.getLabel());
                    n4jNode.setProperty("id", o.getId());
                    o.setCorrespondingNode(n4jNode);

                    knowledgeObjectCreation.success();
                }
            }
        }
    }
    
    /**
     * Vérifie l'existence d'un objet o
     * 
     * @param o
     * @return 
     */
    public boolean containsObject(Resource o) {    
        return O_byID.containsKey(o.getId());
    }
    
    /**
     * Récupère tous les noms de tous les objets
     * 
     * @return 
     */
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        
        for(Entry<Class, HashSet<Resource>> e : O.entrySet()) {
            for(Resource o : e.getValue())
                names.add(o.getLabel());
        }
        
        return names;
    }

    /**
     * 
     * @return 
     */
    public HashSet<Class> getManagedTypes() {
        return managedTypes;
    }
    
    /**
     * Récupère tous les noms de tous les agents
     * 
     * @return 
     */
    public ArrayList<String> getAgentNames() {
        ArrayList<String> names = new ArrayList<>();
        
        for(Resource a : O.get(Agent.class))
            names.add(a.getLabel());
        
        return names;
    }

    /**
     * 
     * @return 
     */
    public GraphDatabaseService getDbService() {
        return dbService;
    }
    
    /**
     * 
     * @param v 
     */
    public void addViewpoint(ConnectedViewpoint v) {
        if(! (v.getO1() == null || v.getO2() == null || v.getEmitter() == null || containsViewpoint(v, viewpoints))) {
            //addVertex(v);
            v.connect();
            viewpoints.add(v);
            
            if(! containsObject(v.getEmitter()))
                addResource(v.getEmitter());

            if(! containsObject(v.getO1()))
                addResource(v.getO1());

            if(! containsObject(v.getO2()))
                addResource(v.getO2());

            /*addEdge(new Edge(v.getEmitter(), v), v.getEmitter(), v);
            addEdge(new Edge(v, v.getO1()), v, v.getO1());
            addEdge(new Edge(v, v.getO2()), v, v.getO2());*/
            
            if(USES_NEO4J) {
                try(Transaction viewpointCreation = dbService.beginTx()) {
                    org.neo4j.graphdb.Node viewpointN4jNode = dbService.createNode();
                    viewpointN4jNode.setProperty("type", v.getClass().getSimpleName());
                    viewpointN4jNode.setProperty("id", v.getId());
                    v.setCorrespondingNode(viewpointN4jNode);
                    v.getEmitter().getCorrespondingNode().createRelationshipTo(
                            viewpointN4jNode, N4jEdgeType.EXPRESSES_VIEWPOINT
                    );

                    viewpointN4jNode.createRelationshipTo(
                            v.getO1().getCorrespondingNode(), N4jEdgeType.VIEWPOINT_CONNECTOR
                    );

                    viewpointN4jNode.createRelationshipTo(
                            v.getO2().getCorrespondingNode(), N4jEdgeType.VIEWPOINT_CONNECTOR
                    );

                    viewpointCreation.success();
                }
            }
        }
    }
    
    /**
     * Récupère un objet o aléatoire différent de l'objet spécifié
     * 
     * @param differentFromThis
     * @return 
     */
    public Resource getRandomObject(Resource differentFromThis) {
        Rand.init();
        ArrayList<Resource> tmp = new ArrayList<>();
        for(HashSet<Resource> os : O.values())
            tmp.addAll(os);
        
        return tmp.get(Rand.randInt(tmp.size()));
    }
    
    /**
     * 
     */
    public void clear() {
        O_byID.clear();
        O_byLabels.clear();
        O.clear();
        viewpoints.clear();
    }
    
    /**
     * Permet de récupérer l'objet dont le nom est spécifié
     * 
     * @param name
     * @return 
     */
    public Resource getNamedObject(String name) {        
        return O_byLabels.get(name);
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public Resource getObjectByID(int id) {
        return O_byID.get(id);
    }

    /**
     * 
     * @param node
     * @return 
     */
    //@Override
    public Collection<Node> getNeighbors(Node node) {
        return node.getNeighbours();
    }

    //@Override
    public int getNeighborCount(Node node) {
        return node.getNeighbours().size();
    }
    
    /**
     * 
     * @param c
     * @return 
     */
    public Collection<Resource> getOByType(Class c) {
        return O.get(c);
    }
    
    /**
     * 
     * @param c
     * @return 
     */
    public HashSet<Resource> getKnowledgeObjectsByClass(Class c) {
        return O.get(c);
    }

    /**
     * 
     * @return 
     */
    public ArrayList<ConnectedViewpoint> getViewpoints() {
        return viewpoints;
    }

}
