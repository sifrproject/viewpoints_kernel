package kernel.knowledgeGraph.nodes.superModel.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashSet;
import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.Node;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public class Resource extends Node {
    
    protected LinkedHashSet<ConnectedViewpoint> referentViewpoints;
    protected float weight;
    protected HashMap< String ,URI > uriMap;
    
    protected static String mainDataSet;
    
    public Resource() {
        referentViewpoints = new LinkedHashSet<>();
        weight = -1.0f;
        uriMap = new HashMap<>();
    }
    
    public Resource(String name) {
        referentViewpoints = new LinkedHashSet<>();
        weight = -1.0f;
        uriMap = new HashMap<>();
        setLabel(name);
    }
    
    public Resource(int id) {
        super(id);
        referentViewpoints = new LinkedHashSet<>();
        weight = -1.0f;
        uriMap = new HashMap<>();
    }

    public Resource(Resource o) {
        super(o.getId());
        this.referentViewpoints = o.getReferentViewpoints();
        this.weight = o.getWeight();
        uriMap = new HashMap<>();
    }

    public static void setMainDataSet(String mainDataSet) {
        Resource.mainDataSet = mainDataSet;
    }

    public static String getMainDataSet() {
        return mainDataSet;
    }
    
    public URI getMainURI() {
        return uriMap.get(Resource.mainDataSet);
    }
    
    public void addURI(String dataset, URI uri) {
        uriMap.put(dataset, uri);
    }

    public HashMap<String, URI> getUriList() {
        return uriMap;
    }
    
    public URI getURI(String dataset) {
        return uriMap.get(dataset);
    }

    public LinkedHashSet<ConnectedViewpoint> getReferentViewpoints() {
        return referentViewpoints;
    }
    
    public Resource getBestPred() {
        float best = Float.MAX_VALUE;
        Resource bestO = null;
        
        for(ConnectedViewpoint v : referentViewpoints) {
            if(v.getPredecessor().getWeight() < best) {
                bestO = v.getPredecessor();
                best = bestO.getWeight();
            }
        }
        
        return bestO;
    }
    
    public void addReferentViewpoint(ConnectedViewpoint v) {
        referentViewpoints.add(v);
    }
    
    public void setWeight(float weight) {   
        this.weight = weight;
    }
    
    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        /*if(weight > 0.0f)
            return name + "| d= " + weight;
        else*/
            return name;
    }
    
    public String toXML() {
        return "<" + getClass().getName() + " id=\"" + id + "\" label=\"" + name + "\" />";
    }

    @Override
    public boolean equals(Object o) {
        Resource r_ = (Resource) o;
        if(r_.getId() == getId())
            return true;
        
        return false;
    }
    
}
