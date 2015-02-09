package kernel.knowledgeGraph.nodes.superModel.resources;

import java.net.URL;

/**
 *
 * @author WillhelmK
 */
public abstract class RepresentableResource extends Resource {

    protected URL url;
    
    public RepresentableResource() {
    }
    
    public RepresentableResource(int id) {
        super(id);
    }
    
    public RepresentableResource(String label) {
        super(label);
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
    
}
