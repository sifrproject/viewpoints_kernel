package kernel.knowledgeGraph.nodes.webSpecificModel.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.superModel.resources.RepresentableResource;
import kernel.tools.Language;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public class Document extends RepresentableResource {
    
    protected Language language;

    public Document() {
    }
    
    public Document(int id) {
        super(id);
    }
    
    public Document(String name) {
        super(name);
        try {
            uriMap.put(mainDataSet, new URI(mainDataSet + "knowledgeobjects/perceptibleobjects/document" + id));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    
}
