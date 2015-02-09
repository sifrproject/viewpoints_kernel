package kernel.queryGraph;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.graph.SparseGraph;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author WillhelmK
 */
public class QueryGraph extends SparseGraph<QueryNode, ConstrainingEdge> {

    private ArrayList<QueryNode> nodes;
    
    public QueryGraph() {
        nodes = new ArrayList<>();
    }
    
    public ArrayList<QueryNode> getLeaves() {
        ArrayList<QueryNode> result = new ArrayList<>();
        
        for(QueryNode node : nodes) {
            if(node.isLeaf())
                result.add(node);
        }
        
        return result;
    }
    
    public ArrayList<QueryNode> getTerminalNodes() {
        ArrayList<QueryNode> result = new ArrayList<>();
        
        for(QueryNode node : nodes) {
            if(node.isTerminal())
                result.add(node);
        }
        
        return result;
    }
    
    public void addQueryNode(QueryNode n) {
        nodes.add(n);
        addVertex(n);
    }
    
    public void addConstrainingEdge(QueryNode origin, QueryNode target, float constraint) {
        ConstrainingEdge tmp = new ConstrainingEdge(target, constraint);
        addEdge(new ConstrainingEdge(target, constraint), origin, target);
        origin.addConstrainingEdge(tmp);
    }
    
}
