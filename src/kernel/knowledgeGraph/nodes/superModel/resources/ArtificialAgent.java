package kernel.knowledgeGraph.nodes.superModel.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static kernel.knowledgeGraph.nodes.superModel.resources.Resource.mainDataSet;

/**
 *
 * @author WillhelmK
 */
public class ArtificialAgent extends Agent {

    public ArtificialAgent() {
    }
    
    public ArtificialAgent(String label) {
        super(label);
        
        try {
            uriMap.put(mainDataSet, new URI(mainDataSet + "knowledgeobjects/perceptibleobjects/agent/artificialagents/" + id));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArtificialAgent(int id) {
        super(id);
        
        try {
            uriMap.put(mainDataSet, new URI(mainDataSet + "knowledgeobjects/perceptibleobjects/agent/artificialagents/" + id));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
