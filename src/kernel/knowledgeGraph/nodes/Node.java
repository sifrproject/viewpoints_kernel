package kernel.knowledgeGraph.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import kernel.tools.Nameable;

/**
 *
 * @author WillhelmK
 */
public abstract class Node extends Nameable implements Serializable {
    
    protected ArrayList<Node> neighbours;
    protected int id;
    protected static int lastID = 0;
    private org.neo4j.graphdb.Node correspondingNode;

    public Node(int id) {
        super("" + id);
        this.id = id;
        lastID++;
        neighbours = new ArrayList<>();
    }
    
    public Node() {
        super("" + lastID);
        this.id = lastID;
        lastID++;
        neighbours = new ArrayList<>();
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }
    
    public void addNeighbour(Node neighbour) {
        neighbours.add(neighbour);
        neighbour.getNeighbours().add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public org.neo4j.graphdb.Node getCorrespondingNode() {
        return correspondingNode;
    }

    public void setCorrespondingNode(org.neo4j.graphdb.Node correspondingNode) {
        this.correspondingNode = correspondingNode;
    }
    
}
