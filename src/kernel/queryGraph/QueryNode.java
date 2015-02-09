package kernel.queryGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;

/**
 *
 * @author WillhelmK
 */
public class QueryNode {

    private Resource referent;
    private ArrayList<ConstrainingEdge> outgoingEdges, ingoingEdges;
    private Set<Resource> buffer;
    
    public QueryNode() {
        outgoingEdges = new ArrayList<>();
        ingoingEdges = new ArrayList<>();
        buffer = new HashSet<>();
    }

    public QueryNode(Resource referent) {
        this.referent = referent;
        outgoingEdges = new ArrayList<>();
        ingoingEdges = new ArrayList<>();
        buffer = new HashSet<>();
    }
    
    public void addConstrainingEdge(ConstrainingEdge e) {
        outgoingEdges.add(e);
    }

    public Set<Resource> getBuffer() {
        return buffer;
    }

    public void setBuffer(Set<Resource> buffer) {
        this.buffer = buffer;
    }

    public ArrayList<ConstrainingEdge> getOutgoingEdges() {
        return outgoingEdges;
    }

    public ArrayList<ConstrainingEdge> getIngoingEdges() {
        return ingoingEdges;
    }

    public Resource getReferent() {
        return referent;
    }
    
    public boolean isLeaf() {
        return ingoingEdges.isEmpty();
    }
    
    public boolean isTerminal() {
        return outgoingEdges.isEmpty();
    }

    @Override
    public String toString() {
        return referent.toString();
    }
    
}
