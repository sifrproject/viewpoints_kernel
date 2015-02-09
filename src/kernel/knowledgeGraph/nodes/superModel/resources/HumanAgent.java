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
public class HumanAgent extends Agent {

    private static Agent anonymeAgent = new HumanAgent("anonym");

    public static Agent getAnonymeAgent() {
        return anonymeAgent;
    }

    public HumanAgent() {
    }

    public HumanAgent(String label) {
        super(label);
        
        try {
            uriMap.put(mainDataSet, new URI(mainDataSet + "knowledgeobjects/perceptibleobjects/agent/humanagents/" + id));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public HumanAgent(int id) {
        super(id);
        
        try {
            uriMap.put(mainDataSet, new URI(mainDataSet + "knowledgeobjects/perceptibleobjects/agent/artificialagents/" + id));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
