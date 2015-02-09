package kernel.knowledgeGraph.nodes.superModel.resources;

/**
 *
 * @author WillhelmK
 */
public abstract class NonRepresentableResource extends Resource {

    public NonRepresentableResource() {
    }
    
    public NonRepresentableResource(String label) {
        super(label);
    }
    
    public NonRepresentableResource(int id) {
        super(id);
    }
    
}
