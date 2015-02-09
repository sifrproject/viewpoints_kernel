package kernel.knowledgeGraph.nodes.webSpecificModel.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.superModel.resources.NonRepresentableResource;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public class Topic extends NonRepresentableResource {
    
    public Topic() {
    }

    public Topic(String name) {
        super(name);
        try {
            uriMap.put(mainDataSet, new URI(mainDataSet + "knowledgeobjects/abstractobjects/topic" + id));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Topic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Topic(int id) {
        super(id);
        try {
            uriMap.put(mainDataSet, new URI(mainDataSet + "knowledgeobjects/abstractobjects/topic" + id));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Topic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
