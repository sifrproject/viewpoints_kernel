package kernel.knowledgeGraph.nodes;

import java.io.Serializable;

/**
 *
 * @author WillhelmK
 * @param <NodeType>
 * @param <LabelType>
 */
public class Edge<NodeType extends Node, LabelType> implements Serializable {

    public NodeType n1, n2;
    public LabelType weight;
    
    public Edge(NodeType n1, NodeType n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public Edge(NodeType n1, NodeType n2, LabelType weight) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = weight;
    }
    
    @Override
    public String toString() {
        return weight.toString();
    }
    
}
