package kernel.knowledgeGraph.nodes.superModel.viewpoints;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.Node;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.knowledgeGraph.nodes.superModel.resources.HumanAgent;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;

/**
 *
 * @author WillhelmK
 * @param <O1> O1 class, KnowledgeObject by default
 * @param <O2> O2 class, KnowledgeObject by default
 */
@XmlRootElement
public class ConnectedViewpoint<O1 extends Resource, O2 extends Resource> extends Viewpoint {
    
    protected Resource predecessor;
    protected Agent emitter;

    public ConnectedViewpoint() {
    }
    
    public ConnectedViewpoint(Agent emitter, O1 o1, O2 o2, ViewpointPolarity polarity) {
        name = "v" + name;
        this.emitter = emitter;
        neighbours.add(0, o1);
        neighbours.add(1, o2);
        this.polarity = polarity;
        
        if(! types.contains(getClass()))
            types.add(getClass());
    }
    
    public void anonymize() {
        emitter = HumanAgent.getAnonymeAgent();
    }
    
    public void connect() {
        for(Node neighbour : neighbours)
            neighbour.getNeighbours().add(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + getLabel() + "(" + emitter + ", " + getO1() + ", " + getO2() + ")";
    }
    
    public String toXML() {
        return "<" + getClass().getSimpleName() + " emitterID=\"" + emitter.getId() + "\" o1ID=\"" + getO1().getId() + "\" o2ID=\"" + getO1().getId() + "\" />";
    }

    public Agent getEmitter() {
        return emitter;
    }

    public O1 getO1() {
        return (O1) neighbours.get(0);
    }

    public O2 getO2() {
        return (O2) neighbours.get(1);
    }
    
    public void setO1(Resource o1) {
        if(neighbours.size() == 2)
            neighbours.set(0, o1);
        else
            neighbours.add(0, o1);
    }
    
    public void setO2(Resource o2) {
        if(neighbours.size() == 2)
            neighbours.set(1, o2);
        else
            neighbours.add(1, o2);
    }

    public void setEmitter(Agent emitter) {
        this.emitter = emitter;
    }

    public Resource getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Resource predecessor) {
        this.predecessor = predecessor;
    }
    
    public static Collection<ConnectedViewpoint> filterByPredecessor(Collection<ConnectedViewpoint> viewpoints, Resource pred) {
        Collection<ConnectedViewpoint> result = new ArrayList<>();
        
        for(ConnectedViewpoint v : viewpoints) {
            if(v.getPredecessor() == pred)
                result.add(v);
        }
        
        return result;
    }
    
}
