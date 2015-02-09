package kernel.queryGraph;

/**
 *
 * @author WillhelmK
 */
public class ConstrainingEdge {
    
    private float distanceConstraint;
    private QueryNode outputNode;

    public ConstrainingEdge(QueryNode outputNode, float distanceConstraint) {
        this.distanceConstraint = distanceConstraint;
        this.outputNode = outputNode;
        outputNode.getIngoingEdges().add(this);
    }

    public float getDistanceConstraint() {
        return distanceConstraint;
    }

    public QueryNode getOutputNode() {
        return outputNode;
    }
    
}
